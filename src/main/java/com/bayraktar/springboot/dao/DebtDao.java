package com.bayraktar.springboot.dao;

import com.bayraktar.springboot.entity.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebtDao extends JpaRepository<Debt, Long> {
}
