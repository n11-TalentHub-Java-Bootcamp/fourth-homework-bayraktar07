package com.bayraktar.springboot.service.entityservice;

import com.bayraktar.springboot.dao.DebtDao;
import com.bayraktar.springboot.entity.Debt;
import com.bayraktar.springboot.enums.DebtType;
import com.bayraktar.springboot.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DebtEntityService extends BaseEntityService<Debt, DebtDao>{


    private static final double interestRate1_5 = 1.5;
    private static final double interestRate2 = 2;
    private static final LocalDate interestDate = LocalDate.of(2018, 1, 1);

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
        List<Debt> overdueDebts = findAllOverdueDebtListByUserId(id);
        long debtSum = 0L;
        for(Debt d : overdueDebts) {
            debtSum += findOverdueInterestByDebtId(d.getId());
        }
        debtSum += getDao().findAllOverdueDebtAmountByUserId(id,date);
        return debtSum;
    }

    public Double findOverdueInterestByDebtId (Long debtId) {
        Debt debt = findById(debtId).orElseThrow(() -> new NotFoundException("Debt id" + debtId + " not found"));
        Double totalAmount = debt.getTotalDebtAmount();
        double totalInterest = 0.0;
        if(debt.getDebtType()== DebtType.INTEREST) {
            return 0D;
        }
        if(debt.getExpiryDate().isBefore(LocalDate.now()) && totalAmount > 0) {
            if(debt.getExpiryDate().isBefore(interestDate)) {
                long days1 = ChronoUnit.DAYS.between(debt.getExpiryDate(), interestDate);
                long days2 = ChronoUnit.DAYS.between(debt.getExpiryDate(), LocalDate.now());
                totalInterest += ((days1 * interestRate1_5 * totalAmount) / 100D) + ((days2 * interestRate2 * totalAmount) / 100D);
            } else {
                long days = ChronoUnit.DAYS.between(debt.getExpiryDate(), LocalDate.now());
                totalInterest = (days * interestRate2 * totalAmount) / 100D;
            }
            if(totalInterest == 0) {
                totalInterest++;
            }
        }
        return totalInterest;
    }

}
