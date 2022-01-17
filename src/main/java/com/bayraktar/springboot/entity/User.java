package com.bayraktar.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class User implements BaseEntity{

    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 30, nullable = false)
    private String name;
    @Column(length = 30, nullable = false)
    private String surname;
    @Column(length = 15, nullable = false)
    private String phoneNumber;
    @Column(length = 30, nullable = false)
    private String email;
}
