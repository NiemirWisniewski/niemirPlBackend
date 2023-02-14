package pl.nw.oceniarka.login.security.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextListener;
import pl.nw.oceniarka.exception.userException.UserExceptionSupplier;
import pl.nw.oceniarka.login.security.AuthoritiesConverter;
import pl.nw.oceniarka.user.domain.CurrentUser;
import pl.nw.oceniarka.user.domain.User;
import pl.nw.oceniarka.user.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CustomAuthSuccessHandler.class);

    private final AuthoritiesConverter authoritiesConverter;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final CurrentUser currentUser;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        LOG.info("onAuthenticationSuccess - set status to HttpServletResponse.SC_OK");
        String responseBody = createResponse(authentication);

        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter responseWriter = response.getWriter();
        responseWriter.print(responseBody);
        responseWriter.flush();
    }

    private String createResponse(Authentication authentication) throws JsonProcessingException {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String authenticatedUsername = ((UserDetails) authentication.getPrincipal()).getUsername();
            User user = userRepository.findByUsername(authenticatedUsername)
                    .orElseThrow(UserExceptionSupplier.userNotFound(authenticatedUsername));
            //currentUser.setId(user.getId());
            //System.out.println(currentUser.getId());
            //System.out.println(currentUser);

            List<String> roles = authoritiesConverter.convert(userDetails.getAuthorities());
            SecurityResponseDTO securityDto = new SecurityResponseDTO(userDetails.getUsername(), roles);

            String response = objectMapper.writeValueAsString(securityDto);
            LOG.info("createResponse: " + response);

            return response;
        }

        throw new IllegalArgumentException("Principal invalid");
    }

    @Getter
    @Setter
    class SecurityResponseDTO {
        private final String username;
        private final List<String> roles = new ArrayList<>();

        public SecurityResponseDTO(String username, List<String> roles) {
            this.username = username;
            this.roles.addAll(roles);
        }
    }

}
