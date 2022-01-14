package com.bayraktar.springboot.dto;

import com.bayraktar.springboot.entity.Debt;
import com.bayraktar.springboot.entity.User;
import com.bayraktar.springboot.enums.DebtType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;


@Data
@AllArgsConstructor
public class DebtGetDTO {

    private Long id;
    private Long mainDebtAmount;
    private Long totalDebtAmount;
    private LocalDate expiryDate;
    private LocalDate registrationDate;
    private Debt boundDebt;
    private DebtType debtType;
    private User user;
    private transient Long interest;
}
