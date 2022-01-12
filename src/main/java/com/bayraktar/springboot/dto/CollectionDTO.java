package com.bayraktar.springboot.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CollectionDTO {

    private Long id;
    private LocalDate collectionDate;
    private Long collectedAmount;
    private Long boundDebtId;
    private Long userId;
}
