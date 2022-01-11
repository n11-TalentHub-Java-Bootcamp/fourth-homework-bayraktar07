package com.bayraktar.springboot.dao;

import com.bayraktar.springboot.entity.InterestRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRateDao extends JpaRepository<InterestRate, Long> {
}
