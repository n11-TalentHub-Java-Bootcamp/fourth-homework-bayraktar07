package com.bayraktar.springboot.service.entityservice;


import com.bayraktar.springboot.dao.InterestRateDao;
import com.bayraktar.springboot.entity.InterestRate;
import org.springframework.stereotype.Service;

@Service

public class InterestRateEntityService extends BaseEntityService<InterestRate, InterestRateDao>{

    public InterestRateEntityService(InterestRateDao dao) {
        super(dao);
    }
}
