package com.bayraktar.springboot.converter;

import com.bayraktar.springboot.dto.InterestRateDTO;
import com.bayraktar.springboot.entity.InterestRate;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InterestRateConverter {
    
    InterestRateConverter INSTANCE = Mappers.getMapper(InterestRateConverter.class);
    
    InterestRateDTO convertInterestRateToInterestRateDTO(InterestRate interestRate);

    InterestRate convertInterestRateDTOToInterestRate(InterestRateDTO interestRateDTO);

    List<InterestRateDTO> convertAllInterestRateListToInterestDTOList(List<InterestRate> interestRates);
}
