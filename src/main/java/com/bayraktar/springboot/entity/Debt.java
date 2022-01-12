package com.bayraktar.springboot.entity;

import com.bayraktar.springboot.enums.DebtType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "DEBT")
@Getter
@Setter
public class Debt implements BaseEntity{

    @Id
    @GeneratedValue
    private Long id;

    @Column(updatable = false)
    private Long mainDebtAmount;
    private Long totalDebtAmount;
    private LocalDate expiryDate;
    private LocalDate registrationDate;
    private Long boundDebtId;
    private DebtType debtType;
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "DEBT_USER_FK"))
    private User user;

}
