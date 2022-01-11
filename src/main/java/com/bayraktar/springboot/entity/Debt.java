package com.bayraktar.springboot.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "DEBT")
@Getter
@Setter
public class Debt implements BaseEntity{

    @Id
    @GeneratedValue
    private Long id;
    private Long debtAmount;
    private Long expiryDate;
    @ManyToOne
    @JoinColumn(name = "debt_id", foreignKey = @ForeignKey(name = "DEBT_FK"))
    private Debt debt;

}
