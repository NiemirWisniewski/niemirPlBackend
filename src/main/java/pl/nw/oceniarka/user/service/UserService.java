package pl.nw.oceniarka.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.nw.oceniarka.email.EmailService;
import pl.nw.oceniarka.exceptions.userException.EmailIsTakenException;
import pl.nw.oceniarka.exceptions.userException.UsernameIsTakenException;
import pl.nw.oceniarka.registration.ConfirmationToken;
import pl.nw.oceniarka.registration.ConfirmationTokenService;
import pl.nw.oceniarka.user.domain.CurrentUser;
import pl.nw.oceniarka.user.domain.User;
import pl.nw.oceniarka.user.dto.UserMapper;
import pl.nw.oceniarka.user.dto.request.NewPasswordRequestDTO;
import pl.nw.oceniarka.user.dto.request.UserRequestDTO;
import pl.nw.oceniarka.user.dto.response.UserResponse;
import pl.nw.oceniarka.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final CurrentUser currentUser;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Value("${backendAddress}")
    private String backendAddress;

    public UserResponse saveUser(UserRequestDTO userRequest) {

        Optional<Boolean> usernameIsTaken =
                Optional.ofNullable(userRepository.isUsernameTaken(userRequest.getUsername()));
        Optional<Boolean> emailIsTaken =
                Optional.ofNullable(userRepository.isEmailTaken(userRequest.getEmail()));

        if(usernameIsTaken.orElse(false)) {
            throw new UsernameIsTakenException(userRequest.getUsername());
        }
        if (emailIsTaken.orElse(false)){
            throw new EmailIsTakenException(userRequest.getEmail());
        }
            User user = userMapper.toUser(userRequest);
            String token = UUID.randomUUID().toString();
            ConfirmationToken confirmationToken = new ConfirmationToken(token, user);
            userRepository.save(user);
            confirmationTokenService.saveConfirmationToken(confirmationToken);
            String link = backendAddress + "/api/users/confirm?token=" + token;
            emailService.sendConfirmationMail(userRequest.getEmail()
                    , userRequest.getUsername(),link);
            return userMapper.toUserResponse(user);
        }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userRepository.enableUser(confirmationToken.getUser().getEmail(), confirmationToken.getUser().getUsername());
        userRepository.deleteOtherUsers(confirmationToken.getUser().getEmail(), confirmationToken.getUser().getUsername());
        return "confirmed";
    }

    @Transactional
    public void saveTokenAndSendEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Taki email nie został zarejestrowany"));
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        user.setTokenTime(new Date());
        userRepository.save(user);

        emailService.sendResetPasswordEmail(user);
    }

    public void validateToken(String token) {
        User user = userRepository.findByToken(token)
                .orElseThrow( () -> new RuntimeException("Token jest nieprawidłowy lub wygasły \n " +
                        "Token is invalid or expired"));
    }

    public void saveNewPassword(NewPasswordRequestDTO newPasswordRequestDTO) {
        User user = userRepository.findByToken(newPasswordRequestDTO.getToken())
                .orElseThrow( () -> new RuntimeException("Token jest nieprawidłowy lub wygasły \n " +
                        "Token is invalid or expired"));;
        user.setPassword(passwordEncoder.encode(newPasswordRequestDTO.getNewPassword()));
        user.setToken(null);
        user.setTokenTime(null);

        save(user);
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }
}