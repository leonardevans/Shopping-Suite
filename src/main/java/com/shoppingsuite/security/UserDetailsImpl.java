package com.shoppingsuite.security;

import com.shoppingsuite.persistence.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserDetailsImpl implements OAuth2User, UserDetails {
    public static final long serialVersionUID = 1L;
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;
    private String password;
    private boolean enabled;
    private boolean emailVerified;

    public UserDetailsImpl(long id, String email, String firstName, String lastName, Collection<? extends GrantedAuthority> authorities, String password, boolean enabled, boolean emailVerified) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorities = authorities;
        this.password = password;
        this.enabled = enabled;
        this.emailVerified = emailVerified;
    }

    public static UserDetailsImpl build(User user){
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return new UserDetailsImpl(user.getId(), user.getEmail(),user.getFirstName(),user.getLastName(), authorities, user.getPassword(), user.isEnabled(), user.isEmailVerified());
    }

    public static UserDetailsImpl build(User user, Map<String, Object> attributes){
        UserDetailsImpl userDetailsImpl = UserDetailsImpl.build(user);
        userDetailsImpl.setAttributes(attributes);
        return userDetailsImpl;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
        return enabled;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(firstName + " ")
                .append(lastName);
        return stringBuilder.toString();
    }
}
