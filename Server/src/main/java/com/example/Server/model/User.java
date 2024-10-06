package com.example.Server.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "usermaster")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Specify the generation strategy for the primary key
    private long user_id;

    private String username;

    private String email;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)  // Define the relationship with Role (assuming you have a Role entity)
    @JoinTable(name = "rolemaster",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Transient
    private String otp;

    @Transient
    private String otpExpiry;

    // Custom constructor for setting user details
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public long getId() {return user_id;}
    public String getUsername() {return username;}
    public String getEmail() {return email;}
    public String getPassword() {return password;}
    public Set<Role> getRoles() {return roles;}
    public String getOtp() {return otp;}
    public String getOtpExpiry() {return otpExpiry;}
    public void setUsername(String username) {this.username = username;}
    public void setEmail(String email) {this.email = email;}
    public void setPassword(String password) {this.password = password;}
    public void setRoles(Set<Role> roles) {this.roles = roles;}
    public void setOtp(String otp) {this.otp = otp;}

}
