package com.bayraktar.springboot.dto;


import lombok.Data;

import java.util.Date;

@Data
public class InterestRateDTO {

    private Long id;
    private Date appliedDates;
    private Integer interestRate;

}
