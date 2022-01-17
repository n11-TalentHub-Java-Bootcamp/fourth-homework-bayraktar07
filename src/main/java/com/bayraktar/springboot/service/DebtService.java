package com.bayraktar.springboot.service;

import com.bayraktar.springboot.converter.DebtConverter;
import com.bayraktar.springboot.converter.DebtMapper;
import com.bayraktar.springboot.dto.DebtGetDTO;
import com.bayraktar.springboot.dto.DebtSetDTO;
import com.bayraktar.springboot.entity.Debt;
import com.bayraktar.springboot.enums.DebtType;
import com.bayraktar.springboot.exception.DebtCollectedException;
import com.bayraktar.springboot.exception.NotFoundException;
import com.bayraktar.springboot.service.entityservice.DebtEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DebtService {

    private final DebtEntityService debtEntityService;
    private final DebtConverter debtConverter;

    public DebtSetDTO saveNormalDebt(DebtSetDTO debtSetDTO) {
        Debt debt = DebtMapper.INSTANCE.convertDebtSetDTOToDebt(debtSetDTO);
        debt.setDebtType(DebtType.NORMAL);
         debt = debtEntityService.save(debt);
         debt = debtConverter.debtMatcher(DebtMapper.INSTANCE.convertDebtToDebtSetDTO(debt));
        return DebtMapper.INSTANCE.convertDebtToDebtSetDTO(debt);
    }

    public void saveDebt(DebtSetDTO debtSetDTO) {
        Debt debt = debtConverter.debtMatcher(debtSetDTO);
        debtEntityService.save(debt);
    }

    public DebtSetDTO findDebtById(Long id) {
        Debt debt = debtEntityService.findById(id).orElseThrow(() -> new NotFoundException("Debt id" + id + " not found"));
        return DebtMapper.INSTANCE.convertDebtToDebtSetDTO(debt);
    }

    public DebtGetDTO findDebtByIdWithInterest(Long id) {
        Debt debt = debtEntityService.findById(id).orElseThrow(() -> new NotFoundException("Debt id" + id + " not found"));
        DebtGetDTO debtGetDTO = DebtMapper.INSTANCE.convertDebtToDebtGetDTO(debt);
        debtGetDTO.setInterest(findOverdueInterestByDebtId(id));
        return debtGetDTO;
    }

    public List<DebtGetDTO> findAllDebtListByUserId(Long id) {
        List<Debt> allDebtListByUserId = debtEntityService.findAllDebtListByUserId(id);
        List<DebtGetDTO> debtGetDTOS = DebtMapper.INSTANCE.convertAllDebtListToDebtGetDTOList(allDebtListByUserId);
        for (DebtGetDTO debtGetDTO : debtGetDTOS) {
            debtGetDTO.setInterest(findOverdueInterestByDebtId(debtGetDTO.getId()));
        }
        return debtGetDTOS;
    }

    public List<DebtGetDTO> findAllOverdueDebtListByUserId(Long id) {
        List<Debt> allOverdueDebtListByUserId = debtEntityService.findAllOverdueDebtListByUserId(id);
        List<DebtGetDTO> debtGetDTOS = DebtMapper.INSTANCE.convertAllDebtListToDebtGetDTOList(allOverdueDebtListByUserId);
        for (DebtGetDTO debt : debtGetDTOS) {
            debt.setInterest(findOverdueInterestByDebtId(debt.getId()));
        }
        return debtGetDTOS;
    }

    public List<DebtGetDTO> findDebtsByDates(LocalDate startDate, LocalDate endDate) {
        List<Debt> allDebtsBetweenDates = debtEntityService.findAllDebtsBetweenDates(startDate, endDate);
        List<DebtGetDTO> debtGetDTOS = DebtMapper.INSTANCE.convertAllDebtListToDebtGetDTOList(allDebtsBetweenDates);
        for (DebtGetDTO allDebtsBetweenDate : debtGetDTOS) {
            allDebtsBetweenDate.setInterest(findOverdueInterestByDebtId(allDebtsBetweenDate.getId()));
        }
        return debtGetDTOS;
    }

    public void deleteUncollectedDebt(Long id) {
        DebtSetDTO debtSetDTO = findDebtById(id);
        if(debtSetDTO.getMainDebtAmount().equals(debtSetDTO.getTotalDebtAmount())){
            debtEntityService.deleteById(id);
            return;
        }
        throw new DebtCollectedException("Debt collected. It can not be deleted.");
    }

    public DebtSetDTO updateDebt(DebtSetDTO debtSetDTO) {
        if(debtSetDTO.getMainDebtAmount().equals(debtSetDTO.getTotalDebtAmount())) {
            Debt debt = debtEntityService.save(debtConverter.debtMatcher(debtSetDTO));
            return DebtMapper.INSTANCE.convertDebtToDebtSetDTO(debt);
        }
        throw new DebtCollectedException("Debt collected. It can not be updated.");
    }

    public List<DebtGetDTO> findAll() {
        List<Debt> all = debtEntityService.findAll();
        List<DebtGetDTO> debtGetDTOS = DebtMapper.INSTANCE.convertAllDebtListToDebtGetDTOList(all);
        for (DebtGetDTO debt : debtGetDTOS) {
            debt.setInterest(findOverdueInterestByDebtId(debt.getId()));
        }
        return debtGetDTOS;
    }

    public Long findTotalDebtSumByUserId(Long id) {
        return debtEntityService.findTotalDebtAmountSumByUserId(id);
    }

    public Long findOverdueDebtSumByUserId(Long id) {
        return debtEntityService.findAllOverdueDebtSumByUserId(id);
    }

    public Long findAllOverdueInterestSumByUserId (Long userId) {
        List<DebtGetDTO> debtGetDTOS = findAllOverdueDebtListByUserId(userId);
        double totalInterest = 0D;
        for(DebtGetDTO d : debtGetDTOS) {
            totalInterest += findOverdueInterestByDebtId(d.getId());
        }
        return Math.round(totalInterest);
    }

    public Double findOverdueInterestByDebtId (Long debtId) {
       return debtEntityService.findOverdueInterestByDebtId(debtId);
    }
}
