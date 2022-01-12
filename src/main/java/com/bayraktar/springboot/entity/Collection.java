package com.bayraktar.springboot.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "COLLECTION")
@Getter
@Setter
public class Collection implements BaseEntity {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate collectionDate;
    private Long collectedAmount;
    private Long boundDebtId;
    private Long userId;

}
