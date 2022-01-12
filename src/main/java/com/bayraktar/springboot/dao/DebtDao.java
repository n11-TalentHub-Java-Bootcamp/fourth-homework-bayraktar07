package com.bayraktar.springboot.dao;

import com.bayraktar.springboot.entity.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DebtDao extends JpaRepository<Debt, Long> {


    List<Debt> findAllByRegistrationDateGreaterThanEqualAndRegistrationDateLessThanEqual(LocalDate startDate, LocalDate endDate);

    List<Debt> findAllByUserIdAndExpiryDateLessThanAndTotalDebtAmountGreaterThan(Long id, LocalDate date, Long constant);

    @Query("SELECT " +
            " SUM(debt.totalDebtAmount) FROM Debt debt " +
            "WHERE debt.user.id = :id")
    Long findAllCurrentDebtAmountByUserId(Long id);

    @Query("SELECT " +
            " SUM(debt.totalDebtAmount) FROM Debt debt " +
            "WHERE debt.user.id = :id " +
            "AND " +
            "debt.expiryDate < :date")
    Long findAllOverdueDebtAmountByUserId(Long id, LocalDate date);



    List<Debt> findAllDebtListByUserIdAndTotalDebtAmountGreaterThan(Long id, Long constant);
}
