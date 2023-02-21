package pl.nw.oceniarka.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "email", ignoreInvalidFields = true)
public class EmailProperties {

    private String host;
    private Integer port;
    private String protocol;
    private String auth;
    private String tls;
    private String debug;
    private String from;
    private String password;
    private String ssl;

}
