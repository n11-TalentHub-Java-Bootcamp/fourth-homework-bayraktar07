package com.bayraktar.springboot.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "INTEREST_RATE")
@Getter
@Setter
public class InterestRate implements BaseEntity{

    @Id
    @GeneratedValue
    private Long id;
    private Date appliedDates;
    private Integer interestRate;

}
