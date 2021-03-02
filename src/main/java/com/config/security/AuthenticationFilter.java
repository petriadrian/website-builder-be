package com.config.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.models.Site;
import com.models.User;
import com.service.SiteService;
import com.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Collections.singletonList;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    private UserService userService;
    private SiteService siteService;

    public AuthenticationFilter(UserService userService, SiteService siteService) {
        this.userService = userService;
        this.siteService = siteService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            String origin = request.getParameter("origin");
            if (isNotBlank(authToken)) {
                GoogleIdToken idToken = new GoogleIdTokenVerifier
                        .Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                        .setAudience(singletonList(googleClientId))
                        .build()
                        .verify(authToken);
                if (idToken != null) {
                    User user = userService.findOrThrow((String) idToken.getPayload().get("email"));
                    Site site = siteService.findOrThrow(origin);
                    user.setRole(site.getRoleOrThrowFor(user));
                    user.setImageUrl((String) idToken.getPayload().get("picture"));
                    user.setEmail((String) idToken.getPayload().get("email"));
                    user.setPages(site.getPages());
                    user.setCommons(site.getCommons());
                    user.setToken(authToken);
                    TokenAuthentication tokenAuthentication = new TokenAuthentication();
                    tokenAuthentication.setAuthenticated(true);
                    tokenAuthentication.setToken(authToken);
                    tokenAuthentication.setPrincipal(user);
                    SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.getWriter().write(new ErrorResponse(e).getAsJson());
        }

    }

    @Data
    @Slf4j
    static class ErrorResponse {

        private String message;

        ErrorResponse(Exception exception) {
            message = exception.getMessage() + ". " + exception.getClass().getSimpleName();
            log.error(message, exception);
        }

        private String getAsJson() throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        }
    }

}
