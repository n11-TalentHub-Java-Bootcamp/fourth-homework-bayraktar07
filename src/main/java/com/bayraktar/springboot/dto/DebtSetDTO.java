package com.bayraktar.springboot.dto;

import com.bayraktar.springboot.enums.DebtType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DebtSetDTO {

    private Long id;
    private Double mainDebtAmount;
    private Double totalDebtAmount;
    private LocalDate expiryDate;
    private LocalDate registrationDate;
    private Long boundDebtId;
    private DebtType debtType;
    private Long userId;

}
