package com.bayraktar.springboot.service;

import com.bayraktar.springboot.converter.DebtConverter;
import com.bayraktar.springboot.converter.DebtMapper;
import com.bayraktar.springboot.dto.DebtDTO;
import com.bayraktar.springboot.entity.Debt;
import com.bayraktar.springboot.enums.DebtType;
import com.bayraktar.springboot.exception.DebtCollectedException;
import com.bayraktar.springboot.exception.NotFoundException;
import com.bayraktar.springboot.service.entityservice.DebtEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DebtService {

    private final DebtEntityService debtEntityService;
    private final DebtConverter debtConverter;
    private static final LocalDate interestDate = LocalDate.of(2018, 1, 1);
    private static final double interestRate1_5 = 1.5;
    private static final double interestRate2 = 2;

    public DebtDTO saveDebt(DebtDTO debtDTO) {
        Debt debt = debtConverter.debtUserMatcher(debtDTO);
        debt.setDebtType(DebtType.NORMAL);
         debt = debtEntityService.save(debt);
        return DebtMapper.INSTANCE.convertDebtToDebtDTO(debt);
    }

    public DebtDTO findDebtById(Long id) {
        Debt debt = debtEntityService.findById(id).orElseThrow(() -> new NotFoundException("Debt id" + id + " not found"));
        return DebtMapper.INSTANCE.convertDebtToDebtDTO(debt);
    }

    public List<DebtDTO> findAllDebtListByUserId(Long id) {
        return DebtMapper.INSTANCE.convertAllDebtListToDebtDTOList(debtEntityService.findAllDebtListByUserId(id));
    }

    public List<DebtDTO> findAllOverdueDebtListByUserId(Long id) {
        return DebtMapper.INSTANCE.convertAllDebtListToDebtDTOList(debtEntityService.findAllOverdueDebtListByUserId(id));
    }

    public List<DebtDTO> findDebtsByDates(LocalDate startDate, LocalDate endDate) {
        return DebtMapper.INSTANCE.convertAllDebtListToDebtDTOList(debtEntityService.findAllDebtsBetweenDates(startDate, endDate));
    }

    public void deleteUncollectedDebt(Long id) {
        DebtDTO debtDTO = findDebtById(id);
        if(debtDTO.getMainDebtAmount().equals(debtDTO.getTotalDebtAmount())){
            debtEntityService.deleteById(id);
            return;
        }
        throw new DebtCollectedException("Debt collected. It can not be deleted.");
    }

    public DebtDTO updateDebt(DebtDTO debtDTO) {
        if(debtDTO.getMainDebtAmount().equals(debtDTO.getTotalDebtAmount())) {
            Debt debt = debtEntityService.save(debtConverter.debtUserMatcher(debtDTO));
            return DebtMapper.INSTANCE.convertDebtToDebtDTO(debt);
        }
        throw new DebtCollectedException("Debt collected. It can not be updated.");
    }

    public List<DebtDTO> findAll() {
        return DebtMapper.INSTANCE.convertAllDebtListToDebtDTOList(debtEntityService.findAll());
    }

    public Long findTotalDebtSumByUserId(Long id) {
        return debtEntityService.findTotalDebtAmountSumByUserId(id);
    }

    public Long findOverdueDebtSumByUserId(Long id) {
        return debtEntityService.findAllOverdueDebtSumByUserId(id);
    }

    public Long findAllOverdueInterestSumByUserId (Long userId) {
        List<DebtDTO> debtDTOS = findAllOverdueDebtListByUserId(userId);
        long totalInterest = 0L;
        for(DebtDTO d : debtDTOS) {
            if(d.getExpiryDate().isBefore(interestDate)) {
                long days1 = ChronoUnit.DAYS.between(d.getExpiryDate(), interestDate);
                long days2 = ChronoUnit.DAYS.between(d.getExpiryDate(), LocalDate.now());
                totalInterest += (days1 * (interestRate1_5 / 30)) + (days2 * (interestRate2 / 30));
            } else {
                long days = ChronoUnit.DAYS.between(d.getExpiryDate(), LocalDate.now());
                totalInterest += days * (interestRate2 / 30);
            }
        }
        if(totalInterest == 0) {
            totalInterest++;
        }
        return totalInterest;
    }

    public Long findOverdueInterestByDebtId (Long debtId) {
        DebtDTO debtDTO = findDebtById(debtId);
        long totalInterest = 0L;
        if(debtDTO.getExpiryDate().isAfter(LocalDate.now())) {
            if(debtDTO.getExpiryDate().isBefore(interestDate)) {
                long days1 = ChronoUnit.DAYS.between(debtDTO.getExpiryDate(), interestDate);
                long days2 = ChronoUnit.DAYS.between(debtDTO.getExpiryDate(), LocalDate.now());
                totalInterest += (days1 * (interestRate1_5 / 30)) + (days2 * (interestRate2 / 30));
            } else {
                long days = ChronoUnit.DAYS.between(debtDTO.getExpiryDate(), LocalDate.now());
                totalInterest += days * (interestRate2 / 30);
            }
            if(totalInterest == 0) {
                totalInterest++;
            }
        }
        return totalInterest;
    }
}
