package com.bayraktar.springboot.dao;

import com.bayraktar.springboot.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CollectionDao extends JpaRepository<Collection, Long> {


    List<Collection> findAllByCollectionDateGreaterThanEqualAndCollectionDateLessThanEqual(LocalDate startDate, LocalDate endDate);

    List<Collection> findAllCollectionByUserId(Long id);


    @Query("SELECT " +
            " SUM(debt.mainDebtAmount) FROM Debt debt " +
            "WHERE debt.user.id = :id " +
            "AND " +
            "debt.debtType = DebtType.INTEREST")
    Long findAllInterestSumByUserId(Long id);

}
