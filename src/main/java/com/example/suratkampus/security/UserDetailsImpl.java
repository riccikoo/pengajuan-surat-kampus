package com.example.suratkampus.security;

import com.example.suratkampus.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Role harus diawali "ROLE_"
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // bisa disesuaikan
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // bisa disesuaikan
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // bisa disesuaikan
    }

    @Override
    public boolean isEnabled() {
        return true; // bisa disesuaikan
    }

    public User getUser() {
        return user;
    }
}
