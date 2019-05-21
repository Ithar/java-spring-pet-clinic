package com.ithar.malik.udmey.spring.petclinic.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public class Person extends BaseEntity {

    private static final long serialVersionUID = 3976572168425929762L;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

}