package com.bayraktar.springboot.controller;


import com.bayraktar.springboot.dto.DebtGetDTO;
import com.bayraktar.springboot.dto.DebtSetDTO;
import com.bayraktar.springboot.service.DebtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/debts")
@RequiredArgsConstructor
public class DebtController {

    private final DebtService debtService;

    @GetMapping("/")
    public List<DebtGetDTO> findAllDebts() {
        return debtService.findAll();
    }

    @GetMapping("/dates/")
    public List<DebtGetDTO> findAllDebtsBetweenDates(String startDate, String endDate) {
        return debtService.findDebtsByDates(LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE), LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE));
    }

    @GetMapping("/users/{id}")
    public List<DebtGetDTO> findAllDebtListByUserId(@PathVariable Long id) {
        return debtService.findAllDebtListByUserId(id);
    }

    @GetMapping("/users/overdue-debts/{id}")
    public List<DebtGetDTO> findAllOverdueDebtListByUserId(@PathVariable Long id) {
        return debtService.findAllOverdueDebtListByUserId(id);
    }

    @GetMapping("/users/total-debt-sum/{userId}")
    public ResponseEntity<Long> findTotalDebtSumByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(debtService.findTotalDebtSumByUserId(userId));
    }

    @GetMapping("/users/overdue-debt-sums/{userId}")
    public ResponseEntity<Long> findOverdueDebtSumByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(debtService.findOverdueDebtSumByUserId(userId));
    }

    @GetMapping("/users/overdue-interest-sums/{id}")
    public ResponseEntity<Long> findOverdueDebtInterestSum(@PathVariable Long id) {
        return ResponseEntity.ok(debtService.findAllOverdueInterestSumByUserId(id));
    }

    @PostMapping
    public ResponseEntity<DebtSetDTO> saveDebt(@RequestBody DebtSetDTO debtSetDTO) {
        return ResponseEntity.ok(debtService.saveNormalDebt(debtSetDTO));
    }

    @PutMapping
    public ResponseEntity<DebtSetDTO> updateDebt(@RequestBody DebtSetDTO debtSetDTO) {
        return ResponseEntity.ok(debtService.updateDebt(debtSetDTO));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUncollectedDebt(Long id) {
        debtService.deleteUncollectedDebt(id);
        return ResponseEntity.ok("Debt "+ id + " deleted.");
    }

}
