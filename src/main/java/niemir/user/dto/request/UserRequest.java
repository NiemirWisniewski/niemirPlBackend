package niemir.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import niemir.user.domain.role.Role;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {


    private String username;

    private String password;

    private String email;
    private Role role;
}
