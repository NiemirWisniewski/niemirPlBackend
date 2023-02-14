package pl.nw.oceniarka.login.security.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import pl.nw.oceniarka.login.security.AuthoritiesConverter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    private final Logger LOG = LoggerFactory.getLogger(CustomAuthEntryPoint.class);
    private final AuthoritiesConverter authoritiesConverter;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {

        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            List<String> roles = authoritiesConverter.convert(userDetails.getAuthorities());
            System.out.println(roles);
        }*/

        System.out.println(e);
        LOG.info("commence - set status to HttpServletResponse.SC_UNAUTHORIZED");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}