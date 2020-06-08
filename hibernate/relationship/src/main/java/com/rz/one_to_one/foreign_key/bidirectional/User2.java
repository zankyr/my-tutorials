package com.rz.one_to_one.foreign_key.bidirectional;

import javax.persistence.*;

@Entity
@Table(name = "USER_2")
public class User2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USR_ID")
    private Long id;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "USR_DET_ID")
    private UserDetail2 userDetail2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDetail2 getUserDetail2() {
        return userDetail2;
    }

    public void setUserDetail2(UserDetail2 userDetail2) {
        this.userDetail2 = userDetail2;
    }

    @Override
    public String toString() {
        return "User2{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userDetail2=" + userDetail2 +
                '}';
    }
}
