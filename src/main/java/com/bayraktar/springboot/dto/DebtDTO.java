package com.bayraktar.springboot.dto;

import com.bayraktar.springboot.entity.Debt;
import com.bayraktar.springboot.enums.DebtType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DebtDTO {

    private Long id;
    private Long mainDebtAmount;
    private Long totalDebtAmount;
    private LocalDate expiryDate;
    private LocalDate registrationDate;
    private Debt debt;
    private DebtType debtType;
    private Long userId;
}
