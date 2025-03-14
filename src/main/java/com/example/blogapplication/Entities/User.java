package com.example.blogapplication.Entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails{

    @Id
    private String id;

    private String name;

    @Column(
        unique = true,
        nullable = false
    )
    private String email;

    @Column(
        nullable = false
    )
    private String password;

    @Column(
        name = "verification_code"
    )
    private String verificationCode;

    @Column(
        name = "verified_date"
    )
    private LocalDateTime verificationDate;

    @Column(
        name ="verification_expiration"
    )
    private LocalDateTime verificationCodeExpiresAt;

    private String token;

    private boolean enabled;

    @Transient
    @Builder.Default
    private List<Blog> blogs = new ArrayList<>();

    @PrePersist
    private void generateId(){
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return List.of();
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

    @Override
    public String getUsername() {
        return email;
    }

}
