package com.bayraktar.springboot.dto;

import com.bayraktar.springboot.entity.Debt;
import com.bayraktar.springboot.entity.User;
import com.bayraktar.springboot.enums.DebtType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;


@Data
@AllArgsConstructor
public class DebtGetDTO {

    private Long id;
    private Double mainDebtAmount;
    private Double totalDebtAmount;
    private LocalDate expiryDate;
    private LocalDate registrationDate;
    private Debt boundDebt;
    private DebtType debtType;
    private User user;
    @NumberFormat(pattern = "###.##")
    private transient Double interest;
}
