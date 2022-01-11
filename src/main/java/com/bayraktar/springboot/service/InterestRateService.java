package com.bayraktar.springboot.service;

import com.bayraktar.springboot.converter.InterestRateConverter;
import com.bayraktar.springboot.dto.InterestRateDTO;
import com.bayraktar.springboot.entity.InterestRate;
import com.bayraktar.springboot.exception.NotFoundException;
import com.bayraktar.springboot.service.entityservice.InterestRateEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class InterestRateService {

    private final InterestRateEntityService interestRateEntityService;

    public List<InterestRateDTO> findAll() {
        return InterestRateConverter.INSTANCE.convertAllInterestRateListToInterestDTOList(interestRateEntityService.findAll());
    }

    public InterestRateDTO findById(Long id) {
        return InterestRateConverter.INSTANCE.convertInterestRateToInterestRateDTO(
                doesInterestRateExist(interestRateEntityService.findById(id)));
    }

    public InterestRateDTO save(InterestRateDTO interestRateDTO) {
        interestRateEntityService.save(InterestRateConverter.INSTANCE.convertInterestRateDTOToInterestRate(interestRateDTO));
        return interestRateDTO;
    }

    public InterestRateDTO update(InterestRateDTO interestRateDTO) {
        return save(findById(interestRateDTO.getId()));
    }

    public void deleteById(Long id) {
        interestRateEntityService.deleteById(id);
    }

    public InterestRate doesInterestRateExist(Optional<InterestRate> optionalInterestRate) {
        return optionalInterestRate.orElseThrow(() -> new NotFoundException("Interest Rate not found."));
    }
}
