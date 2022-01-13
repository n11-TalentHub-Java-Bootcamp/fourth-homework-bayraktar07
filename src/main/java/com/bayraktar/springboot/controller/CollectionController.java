package com.bayraktar.springboot.controller;

import com.bayraktar.springboot.dto.CollectionDTO;
import com.bayraktar.springboot.service.CollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/collections")
@RequiredArgsConstructor
public class CollectionController {

    private final CollectionService collectionService;

    @GetMapping("/users/{userId}")
    public List<CollectionDTO> getAllCollectionsByUserId(@PathVariable Long userId) {
        return collectionService.findCollectionListByUserId(userId);
    }

    @GetMapping("/dates")
    public List<CollectionDTO> getCollectionsBetweenDates(String startDate, String endDate) {
        return collectionService.findCollectionsBetweenDates(LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE),LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE));
    }

    @GetMapping("/interests/{id}")
    public ResponseEntity<Long> getCollectedInterestSum (@PathVariable Long id) {
        return ResponseEntity.ok(collectionService.getCollectedInterestSumByUserId(id));
    }

    @PostMapping
    public ResponseEntity<CollectionDTO> collectDebt (Long id) {
        return ResponseEntity.ok(collectionService.collectDebt(id));
    }
}
