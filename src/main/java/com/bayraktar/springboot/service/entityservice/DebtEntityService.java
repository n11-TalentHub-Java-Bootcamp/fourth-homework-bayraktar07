package com.bayraktar.springboot.service.entityservice;

import com.bayraktar.springboot.dao.DebtDao;
import com.bayraktar.springboot.entity.Debt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DebtEntityService extends BaseEntityService<Debt, DebtDao>{

    public DebtEntityService(DebtDao dao) {
        super(dao);
    }

    public List<Debt> findAllDebtsBetweenDates(LocalDate startDate, LocalDate endDate) {
        return getDao().findAllByRegistrationDateGreaterThanEqualAndRegistrationDateLessThanEqual(startDate,endDate);
    }

    public Long findTotalDebtAmountSumByUserId(Long id)
    {
        return getDao().findAllCurrentDebtAmountByUserId(id);
    }

    public List<Debt> findAllDebtListByUserId (Long id) {
        return getDao().findAllDebtListByUserIdAndTotalDebtAmountGreaterThan(id, 0L);
    }

    public List<Debt> findAllOverdueDebtListByUserId(Long id) {
        LocalDate date = LocalDate.now();
        return getDao().findAllByUserIdAndExpiryDateLessThanAndTotalDebtAmountGreaterThan(id, date, 0L);
    }

    public Long findAllOverdueDebtSumByUserId(Long id) {
        LocalDate date = LocalDate.now();
        return getDao().findAllOverdueDebtAmountByUserId(id,date);
    }

}
