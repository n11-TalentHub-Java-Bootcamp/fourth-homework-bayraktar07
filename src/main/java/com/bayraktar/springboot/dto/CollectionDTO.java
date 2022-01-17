package com.bayraktar.springboot.dto;

import com.bayraktar.springboot.entity.Debt;
import com.bayraktar.springboot.entity.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CollectionDTO {

    private Long id;
    private LocalDate collectionDate;
    private Double collectedAmount;
    private Debt boundDebt;
    private User user;
}
