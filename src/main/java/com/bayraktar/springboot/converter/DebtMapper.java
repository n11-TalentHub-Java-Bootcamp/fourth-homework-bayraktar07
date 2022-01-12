package com.bayraktar.springboot.converter;

import com.bayraktar.springboot.dto.DebtDTO;
import com.bayraktar.springboot.entity.Debt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DebtMapper {

    DebtMapper INSTANCE = Mappers.getMapper(DebtMapper.class);

    @Mapping(source = "user.id", target = "userId")
    DebtDTO convertDebtToDebtDTO(Debt debt);

    @Mapping(source = "userId", target = "user.id")
    Debt convertDebtDTOToDebt(DebtDTO debtDTO);

    List<DebtDTO> convertAllDebtListToDebtDTOList(List<Debt> debts);

}
