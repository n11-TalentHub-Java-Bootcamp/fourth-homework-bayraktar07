package com.bayraktar.springboot.service;

import com.bayraktar.springboot.converter.CollectionMapper;
import com.bayraktar.springboot.dto.CollectionDTO;
import com.bayraktar.springboot.dto.DebtDTO;
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
        DebtDTO debtDTO = debtService.findDebtById(debtId);
        Long interest = debtService.findOverdueInterestByDebtId(debtId);
        debtDTO.setTotalDebtAmount(0L);
        if(interest > 0L) {
            debtService.saveDebt(saveInterestDebt(interest, debtDTO));
        }
        return saveCollection(debtDTO.getMainDebtAmount()+interest,LocalDate.now(), debtId, debtDTO.getUserId());
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

    private DebtDTO saveInterestDebt(Long amount, DebtDTO debtDTO) {

        DebtDTO debtDTO1 =
                new DebtDTO(null,
                amount,
                0L,
                LocalDate.now().plusYears(1),
                LocalDate.now(),
                debtDTO.getDebt(),
                DebtType.INTEREST,
                debtDTO.getUserId());
        return debtService.saveDebt(debtDTO);
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
