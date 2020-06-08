package com.rz.one_to_one.shared_key;

import javax.persistence.*;

@Entity
@Table(name = "T_STUDENT_ACCOUNT")
public class StudentAccount {

    @Id
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "ACC_NUMBER", unique = true, nullable = false)
    private String accountNumber;

    @OneToOne
    @JoinColumn
    @MapsId
    private Student student;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
