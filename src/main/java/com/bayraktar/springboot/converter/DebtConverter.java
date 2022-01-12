package com.bayraktar.springboot.converter;


import com.bayraktar.springboot.dto.DebtDTO;
import com.bayraktar.springboot.entity.Debt;
import com.bayraktar.springboot.entity.User;
import com.bayraktar.springboot.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DebtConverter {

    private final UserService userService;

    public Debt debtUserMatcher(DebtDTO debtDTO) {
        User user = UserMapper.INSTANCE.convertUserDTOToUser(userService.findById(debtDTO.getUserId()));
        Debt debt = DebtMapper.INSTANCE.convertDebtDTOToDebt(debtDTO);
        debt.setUser(user);
        return debt;
    }
}
