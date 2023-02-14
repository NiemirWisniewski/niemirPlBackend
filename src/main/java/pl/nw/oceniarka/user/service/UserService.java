package pl.nw.oceniarka.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nw.oceniarka.exception.userException.EmailIsTakenException;
import pl.nw.oceniarka.exception.userException.UserExceptionSupplier;
import pl.nw.oceniarka.exception.userException.UsernameIsTakenException;
import pl.nw.oceniarka.user.domain.CurrentUser;
import pl.nw.oceniarka.user.domain.User;
import pl.nw.oceniarka.user.dto.UserMapper;
import pl.nw.oceniarka.user.dto.request.UserRequest;
import pl.nw.oceniarka.user.dto.request.UserRequestDTO;
import pl.nw.oceniarka.user.dto.response.UserResponse;
import pl.nw.oceniarka.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface{

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final CurrentUser currentUser;

    @Override
    public UserResponse saveUser(UserRequestDTO userRequest) {

        boolean usernameIsTaken = userRepository.existsByUsername(userRequest.getUsername());

        boolean emailIsTaken = userRepository.existsByEmail(userRequest.getEmail());
        if (usernameIsTaken) {
            throw new UsernameIsTakenException(userRequest.getUsername());
        } else if (emailIsTaken) {
            throw new EmailIsTakenException(userRequest.getEmail());
        } else {
            User user = userMapper.toUser(userRequest);
            userRepository.save(user);
            return userMapper.toUserResponse(user);
        }
    }

    @Override
    public List<UserResponse> findAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(userMapper::toUserResponse).collect(Collectors.toList());
    }

    @Override
    public UserResponse getCurrentUser() {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(UserExceptionSupplier.userNotFound(currentUser.getId()));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse findUserById(Long id){
            return userMapper.toUserResponse(userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id)));
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id));
        userRepository.delete(user);
    }


    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(UserExceptionSupplier.userNotFound(id));
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setRole(userRequest.getRole());
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }
}
