package com.config.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.models.user.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import static com.utils.Utils.getDomainFromOrigin;
import static com.utils.Utils.getContentAsObject;
import static org.apache.http.util.TextUtils.isBlank;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (!isBlank(authToken)) {
                GoogleIdToken idToken = new GoogleIdTokenVerifier
                        .Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                        .setAudience(Collections.singletonList(googleClientId))
                        .build()
                        .verify(authToken);
                if (idToken != null && idToken.getPayload().getEmail().equalsIgnoreCase(getRegisteredUserEmail(request))) {
                    SecurityContextHolder.getContext().setAuthentication(getAuthentication(authToken, idToken));
                } else {
                    SecurityContextHolder.clearContext();
                }
            }
            filterChain.doFilter(request, response);
        } catch (AuthenticationException | GeneralSecurityException | URISyntaxException exception) {
            SecurityContextHolder.clearContext();
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("Access-Control-Allow-Origin", "*");
            LOG.error("Auth filter exception", exception);
        }

    }

    private String getRegisteredUserEmail(HttpServletRequest request) throws URISyntaxException, IOException {
        return getContentAsObject(getDomainFromOrigin(request) + "/user", User.class).getEmail();
    }

    private TokenAuthentication getAuthentication(String authToken, GoogleIdToken idToken) {
        TokenAuthentication tokenAuthentication = new TokenAuthentication();
        tokenAuthentication.setAuthenticated(true);
        tokenAuthentication.setToken(authToken);
        tokenAuthentication.setPrincipal(getUser(idToken, authToken));
        return tokenAuthentication;
    }

    private User getUser(GoogleIdToken idToken, String authToken) {
        User user = new User();
        user.setName((String) idToken.getPayload().get("name"));
        user.setImageUrl((String) idToken.getPayload().get("picture"));
        user.setEmail((String) idToken.getPayload().get("email"));
        user.setToken(authToken);
        return user;
    }

}
