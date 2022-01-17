package com.bayraktar.springboot.entity;

import com.bayraktar.springboot.enums.DebtType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "DEBT")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Debt implements BaseEntity{

    @Id
    @GeneratedValue
    private Long id;

    @Column(updatable = false)
    private Double mainDebtAmount;
    private Double totalDebtAmount;
    private LocalDate expiryDate;
    private LocalDate registrationDate;
    @OneToOne(orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "bound_debt_id", foreignKey = @ForeignKey(name = "BOUND_DEBT_FK"))
    private Debt boundDebt;
    private DebtType debtType;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "DEBT_USER_FK"))
    private User user;

}
