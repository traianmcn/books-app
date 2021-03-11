package com.booksapp.booksapp.security;

import com.booksapp.booksapp.model.Role;
import com.booksapp.booksapp.model.persistence.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CurrentUser implements UserDetails {

    private String email;
    private String password;
    private Set<GrantedAuthority> roles;

    public CurrentUser(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.roles = new HashSet<>();
        this.roles.add(new SimpleGrantedAuthority(role.name()));
    }

    public static CurrentUser create(UserEntity user) {
        return new CurrentUser(
                user.getEmail(),
                user.getPassword(),
                user.getRole());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
