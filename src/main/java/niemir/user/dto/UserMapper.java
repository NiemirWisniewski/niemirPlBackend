package niemir.user.dto;

import lombok.RequiredArgsConstructor;
import niemir.user.domain.role.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import niemir.user.domain.User;
import niemir.user.dto.request.UserRequestDTO;
import niemir.user.dto.response.UserResponse;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder encoder;

    public User toUser(UserRequestDTO userRequest) {
        User user = new User();
        user.setPassword(encoder.encode(userRequest.getNewPassword()));
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setRole(Role.USER);
        return user;
    }

    public UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getPassword(), user.getRole(), user.getEmail());
    }
}