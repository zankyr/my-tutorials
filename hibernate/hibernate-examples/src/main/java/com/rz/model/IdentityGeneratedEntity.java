package com.rz.model;

import javax.persistence.*;

@Entity(name = "IdentityGeneratedEntity")
@Table(name = "identity_gen_entity")
public class IdentityGeneratedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;

    public IdentityGeneratedEntity() {
    }

    public IdentityGeneratedEntity(String value) {
        this.value = value;
    }
}
