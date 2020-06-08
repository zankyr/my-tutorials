package com.rz.model.one_to_one.foreign_key.bidirectional;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "USER_DETAIL_2")
public class UserDetail2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USR_DET_ID")
    private long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DBO")
    private LocalDate dob;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "userDetail2")
    // @JoinColumn(name = "USR_ID")
    private User2 user2;

    public UserDetail2() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public User2 getUser2() {
        return user2;
    }

    public void setUser2(User2 user2) {
        this.user2 = user2;
    }

    @Override
    public String toString() {
        return "UserDetail2{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                '}';
    }
}
