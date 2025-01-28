package com.artemsolovev.security.jwt;

import com.artemsolovev.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class UserDetailsImpl implements UserDetails {

    private long id;
    private String userName;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserDetailsImpl(User user) {
        this.id = user.getId();
        this.userName = user.getLogin();
        this.password = user.getPassword();
        this.authorities
                = List.of(new SimpleGrantedAuthority("ROLE_" + user.getClass().getSimpleName().toUpperCase()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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

    public long getId() {
        return id;
    }
}