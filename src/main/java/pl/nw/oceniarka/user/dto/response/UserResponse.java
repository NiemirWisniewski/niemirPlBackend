package pl.nw.oceniarka.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.nw.oceniarka.user.domain.role.Role;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class UserResponse {

    private Long id;
    private String username;
    private String password;
    private Role role;
    private String email;
}
