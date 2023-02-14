package pl.nw.oceniarka.login.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {

        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                LOG.info("onLogoutSuccess - user logged out:");
                LOG.info(userDetails.getUsername());
            }
        }

        LOG.info("onLogoutSuccess: set status to HttpServletResponse.SC_OK");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
