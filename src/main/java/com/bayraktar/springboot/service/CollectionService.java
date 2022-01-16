package com.bayraktar.springboot.service;

import com.bayraktar.springboot.converter.CollectionMapper;
import com.bayraktar.springboot.converter.DebtMapper;
import com.bayraktar.springboot.dto.CollectionDTO;
import com.bayraktar.springboot.dto.DebtSetDTO;
import com.bayraktar.springboot.entity.Debt;
import com.bayraktar.springboot.enums.DebtType;
import com.bayraktar.springboot.service.entityservice.CollectionEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CollectionService {

    private final CollectionEntityService collectionEntityService;
    private final DebtService debtService;

    public CollectionDTO collectDebt (Long debtId) {
        DebtSetDTO debtSetDTO = debtService.findDebtById(debtId);
        Long interest = debtService.findOverdueInterestByDebtId(debtId);
        debtSetDTO.setTotalDebtAmount(0L);
        if(interest > 0L) {
            debtService.saveDebt(saveInterestDebt(interest, debtSetDTO));
        }
        debtService.saveDebt(debtSetDTO);
        return saveCollection(debtSetDTO.getMainDebtAmount()+interest,LocalDate.now(), debtId, debtSetDTO.getUserId());
    }

    public List<CollectionDTO> findCollectionListByUserId(Long userId) {
        return CollectionMapper.INSTANCE.convertCollectionListToCollectionListDTO(collectionEntityService.findAllCollectionByUserId(userId));
    }

    public Long getCollectedInterestSumByUserId(Long userId) {
        return collectionEntityService.findAllInterestSumByUserId(userId);
    }

    public List<CollectionDTO> findCollectionsBetweenDates(LocalDate startDate, LocalDate endDate) {
        return CollectionMapper.INSTANCE.convertCollectionListToCollectionListDTO(collectionEntityService.findCollectionsBetweenDates(startDate, endDate));
    }

    private DebtSetDTO saveInterestDebt(Long amount, DebtSetDTO debtSetDTO) {
        Debt boundDebt = DebtMapper.INSTANCE.convertDebtSetDTOToDebt(debtSetDTO);

        return new DebtSetDTO(null,
        amount,
        0L,
        LocalDate.now(),
        LocalDate.now(),
        boundDebt.getId(),
        DebtType.INTEREST,
        debtSetDTO.getUserId());
    }

    private CollectionDTO saveCollection (Long collectedAmount, LocalDate collectionDate, Long debtId, Long userId) {
        CollectionDTO collectionDTO = new CollectionDTO();
        collectionDTO.setBoundDebtId(debtId);
        collectionDTO.setCollectionDate(collectionDate);
        collectionDTO.setCollectedAmount(collectedAmount);
        collectionDTO.setUserId(userId);
        collectionEntityService.save(CollectionMapper.INSTANCE.convertCollectionDTOToCollection(collectionDTO));
        return collectionDTO;
    }

}
