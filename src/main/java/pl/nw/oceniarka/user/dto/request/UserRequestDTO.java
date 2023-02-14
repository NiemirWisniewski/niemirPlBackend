package pl.nw.oceniarka.user.dto.request;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    private String username;

    private String newPassword;

    private String email;

    private String oldPassword;

    private String newRepeatedPassword;

    public boolean isPasswordChangeDetected() {
        return this.newPassword != null;
    }


}
