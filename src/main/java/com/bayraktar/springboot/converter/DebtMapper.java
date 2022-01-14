package com.bayraktar.springboot.converter;

import com.bayraktar.springboot.dto.DebtGetDTO;
import com.bayraktar.springboot.dto.DebtSetDTO;
import com.bayraktar.springboot.entity.Debt;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DebtMapper {

    DebtMapper INSTANCE = Mappers.getMapper(DebtMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "boundDebt.id", target = "boundDebtId")
    DebtSetDTO convertDebtToDebtSetDTO(Debt debt);

    DebtGetDTO convertDebtToDebtGetDTO(Debt debt);

    Debt convertDebtGetDTOToDebt(DebtGetDTO debtGetDTO);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "boundDebtId", target = "boundDebt.id")
    Debt convertDebtSetDTOToDebt(DebtSetDTO debtSetDTO);

    List<DebtGetDTO> convertAllDebtListToDebtGetDTOList(List<Debt> debts);

    @AfterMapping
    default void setNull (@MappingTarget Debt debt, DebtSetDTO debtSetDTO) {
        if(debtSetDTO.getBoundDebtId() == null) {
            debt.setBoundDebt(null);
        }
    }
}
