package com.bayraktar.springboot.converter;


import com.bayraktar.springboot.dto.DebtSetDTO;
import com.bayraktar.springboot.entity.Debt;
import com.bayraktar.springboot.entity.User;
import com.bayraktar.springboot.exception.NotFoundException;
import com.bayraktar.springboot.service.entityservice.DebtEntityService;
import com.bayraktar.springboot.service.entityservice.UserEntityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DebtConverter {

    private final UserEntityService userEntityService;
    private final DebtEntityService debtEntityService;

    public Debt debtMatcher(DebtSetDTO debtSetDTO) {
        Debt debt = DebtMapper.INSTANCE.convertDebtSetDTOToDebt(debtSetDTO);
        User user = userEntityService.findById(debtSetDTO.getUserId()).orElseThrow(() -> new NotFoundException("User not found"));
        if(debtSetDTO.getBoundDebtId() != null) {
            Debt boundDebt = debtEntityService.findById(debtSetDTO.getBoundDebtId()).orElseThrow(() -> new NotFoundException("Debt not found"));
            debt.setBoundDebt(boundDebt);
        }
        debt.setUser(user);
        return debt;
    }
}
