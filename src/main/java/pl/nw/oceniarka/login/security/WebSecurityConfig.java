package pl.nw.oceniarka.login.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.util.WebUtils;
import pl.nw.oceniarka.login.security.config.CustomAuthEntryPoint;
import pl.nw.oceniarka.login.security.config.CustomAuthFailureHandler;
import pl.nw.oceniarka.login.security.config.CustomAuthSuccessHandler;
import pl.nw.oceniarka.login.security.config.CustomLogoutSuccessHandler;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private final JpaUserDetailsService jpaUserDetailsService;
    private final CustomAuthSuccessHandler authSuccessHandler;
    private final CustomAuthFailureHandler authFailureHandler;
    private final CustomLogoutSuccessHandler logoutSuccessHandler;
    private final CustomAuthEntryPoint authEntryPoint;
    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().csrfTokenRepository(csrfTokenRepository()).disable()
                .headers().frameOptions().sameOrigin()
                .and()
                .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
                .headers().frameOptions().sameOrigin()
                .and()
                .authorizeRequests(auth -> auth
                        .mvcMatchers("/api/comments/**").hasAnyAuthority("ROLE_USER", "USER", "USER_ROLE", "MOD", "ADMIN")
                        .mvcMatchers("/api/posts/**").hasAnyAuthority("ROLE_USER", "USER", "USER_ROLE", "MOD", "ADMIN")
                        .mvcMatchers("/api/users/**").permitAll()//hasAnyAuthority("ROLE_USER", "USER", "USER_ROLE", "MOD", "ADMIN")
                        .mvcMatchers("/swagger-ui/**").permitAll()
                        .anyRequest().permitAll())
                .formLogin().successHandler(authSuccessHandler).failureHandler(authFailureHandler)
                .usernameParameter("username").passwordParameter("password").permitAll()
                .and().userDetailsService(jpaUserDetailsService)
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
                .and()
                .logout().invalidateHttpSession(true)
                .deleteCookies("JSESSIONID").logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .build();
    }

    private Filter csrfHeaderFilter() {
        return new OncePerRequestFilter() {

            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain) throws ServletException, IOException {

                CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
                if (csrf != null) {
                    Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                    String token = csrf.getToken();
                    if (cookie == null || token != null && !token.equals(cookie.getValue())) {
                        cookie = new Cookie("XSRF-TOKEN", token);
                        cookie.setPath("/");
                        cookie.setDomain("localhost");
                        response.addCookie(cookie);
                    }
                }
                filterChain.doFilter(request, response);
            }
        };
    }

    //swagger element
    @Bean
    public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select username, password from users where username=?")
                .authoritiesByUsernameQuery("select username, role from users where username=?")
                .passwordEncoder(passwordEncoder).and().build();
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
}
