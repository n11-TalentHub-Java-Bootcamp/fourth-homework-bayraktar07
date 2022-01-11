package com.bayraktar.springboot.controller;


import com.bayraktar.springboot.dto.InterestRateDTO;
import com.bayraktar.springboot.service.InterestRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/interest-rates/")
@RequiredArgsConstructor
public class InterestRateController {

    private final InterestRateService interestRateService;

    @GetMapping
    public List<InterestRateDTO> getAllInterestRates() {
        return interestRateService.findAll();
    }

    @GetMapping("{id}")
    public InterestRateDTO getInterestRateById(@PathVariable Long id) {
        return interestRateService.findById(id);
    }

    @PostMapping
    public ResponseEntity<InterestRateDTO> saveInterestRate(@RequestBody InterestRateDTO interestRateDTO) {
        interestRateDTO = interestRateService.save(interestRateDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(interestRateDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping ResponseEntity<InterestRateDTO> updateInterestRate(@RequestBody InterestRateDTO interestRateDTO) {
        interestRateDTO = interestRateService.update(interestRateDTO);
        return ResponseEntity.ok(interestRateDTO);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteInterestRateById(@RequestParam Long id) {
        interestRateService.deleteById(id);
        return ResponseEntity.ok("Interest rate: " + id + " deleted.");
    }
}
