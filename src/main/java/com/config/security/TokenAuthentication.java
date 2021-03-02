package com.config.security;

import com.models.User;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class TokenAuthentication implements Authentication {

    private User principal;
    private String token;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return principal.getEmail();
    }

    public String getToken() {
        return token;
    }

    public TokenAuthentication setToken(String token) {
        this.token = token;
        return this;
    }

    public void setPrincipal(User principal) {
        this.principal = principal;
    }
}
