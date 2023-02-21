package pl.nw.oceniarka.user.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Component
@Getter
@Setter
public class CurrentUser {

    private Long id;
    private String username;
}