package com.bayraktar.springboot.service;

import com.bayraktar.springboot.converter.CollectionMapper;
import com.bayraktar.springboot.converter.DebtMapper;
import com.bayraktar.springboot.converter.UserMapper;
import com.bayraktar.springboot.dto.CollectionDTO;
import com.bayraktar.springboot.dto.DebtSetDTO;
import com.bayraktar.springboot.entity.Debt;
import com.bayraktar.springboot.entity.User;
import com.bayraktar.springboot.enums.DebtType;
import com.bayraktar.springboot.exception.DebtCollectedException;
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
    private final UserService userService;

    public CollectionDTO collectDebt (Long debtId) {
        DebtSetDTO debtSetDTO = debtService.findDebtById(debtId);
        if(debtSetDTO.getTotalDebtAmount() <= 0D) {
            throw new DebtCollectedException("Total debt amount: " + debtSetDTO.getTotalDebtAmount() + " -- Debt already collected.");
        }
        Debt debt = DebtMapper.INSTANCE.convertDebtSetDTOToDebt(debtSetDTO);
        User user = UserMapper.INSTANCE.convertUserDTOToUser(userService.findById(debt.getUser().getId()));
        debt.setUser(user);
        Double interest = debtService.findOverdueInterestByDebtId(debtId);
        debtSetDTO.setTotalDebtAmount(0D);
        if(interest > 0L) {
            debtService.saveDebt(saveInterestDebt(interest, debt));
        }
        debtService.saveDebt(debtSetDTO);
        return saveCollection(debtSetDTO.getMainDebtAmount()+interest,LocalDate.now(), debt, user);
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

    private DebtSetDTO saveInterestDebt(Double amount, Debt boundDebt) {

        return new DebtSetDTO(null,
        amount,
        0D,
        LocalDate.now(),
        LocalDate.now(),
        boundDebt.getId(),
        DebtType.INTEREST,
        boundDebt.getUser().getId());
    }

    private CollectionDTO saveCollection (Double collectedAmount, LocalDate collectionDate, Debt debt, User user) {
        CollectionDTO collectionDTO = new CollectionDTO();
        collectionDTO.setBoundDebt(debt);
        collectionDTO.setCollectionDate(collectionDate);
        collectionDTO.setCollectedAmount(collectedAmount);
        collectionDTO.setUser(user);
        collectionEntityService.save(CollectionMapper.INSTANCE.convertCollectionDTOToCollection(collectionDTO));
        return collectionDTO;
    }

}
