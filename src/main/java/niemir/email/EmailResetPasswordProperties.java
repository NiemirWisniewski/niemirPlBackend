package niemir.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class EmailResetPasswordProperties {

    private String subject;
    private String body;
    @Value("${frontendAddress}")
    private String frontendAddress;

    public EmailResetPasswordProperties() {
        this.subject = "Niemir - Reset Hasla";
        this.body = "\r\nDrogi uzytkowniku, \r\n\r\nBy rozpoczac resetowanie hasla kliknij w link: " +
                "\r\n" + frontendAddress + "/api/users/password/reset?token={0}\r\n\r\n\r\n" +
                "Pozdrawiam,\r\n Niemir Wisniewski.";
    }

}