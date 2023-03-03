package niemir.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPasswordRequestDTO {

    private String newPassword;

    private String newRepeatedPassword;

    private String token;

    public boolean isPasswordChangeDetected() {
        return this.newPassword != null;
    }
}
