package pl.nw.oceniarka.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.nw.oceniarka.email.EmailService;
import pl.nw.oceniarka.exception.userException.EmailIsTakenException;
import pl.nw.oceniarka.exception.userException.UserExceptionSupplier;
import pl.nw.oceniarka.exception.userException.UsernameIsTakenException;
import pl.nw.oceniarka.login.security.registration.ConfirmationToken;
import pl.nw.oceniarka.login.security.registration.ConfirmationTokenService;
import pl.nw.oceniarka.user.domain.CurrentUser;
import pl.nw.oceniarka.user.domain.User;
import pl.nw.oceniarka.user.dto.UserMapper;
import pl.nw.oceniarka.user.dto.request.UserRequest;
import pl.nw.oceniarka.user.dto.request.UserRequestDTO;
import pl.nw.oceniarka.user.dto.response.UserResponse;
import pl.nw.oceniarka.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final CurrentUser currentUser;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;

    public UserResponse saveUser(UserRequestDTO userRequest) {

        boolean usernameIsTaken = userRepository.existsByUsername(userRequest.getUsername());

        boolean emailIsTaken = userRepository.existsByEmail(userRequest.getEmail());
        if (usernameIsTaken) {
            throw new UsernameIsTakenException(userRequest.getUsername());
        } else if (emailIsTaken) {
            throw new EmailIsTakenException(userRequest.getEmail());
        } else {
            User user = userMapper.toUser(userRequest);
            String token = UUID.randomUUID().toString();
            ConfirmationToken confirmationToken = new ConfirmationToken(token, user);
            userRepository.save(user);
            confirmationTokenService.saveConfirmationToken(confirmationToken);
            String link = "http://localhost:7070/api/users/confirm?token=" + token;
            emailService.sendConfirmationMail(userRequest.getEmail()
                    , userRequest.getUsername(),link);
            return userMapper.toUserResponse(user);
        }
    }

    public List<UserResponse> findAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    public UserResponse getCurrentUser() {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(UserExceptionSupplier.userNotFound(currentUser.getId()));
        return userMapper.toUserResponse(user);
    }

    public UserResponse findUserById(Long id){
            return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id)));
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id));
        userRepository.delete(user);
    }

    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id));
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setRole(userRequest.getRole());
        userRepository.save(user);
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
        userRepository.enableUser(confirmationToken.getUser().getEmail());
        return "confirmed";
    }
}