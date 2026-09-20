package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.example.demo.entity.Country;
import com.example.demo.repository.CountryRepository;
import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryRestController {

    private final CountryRepository countryRepository;

    public CountryRestController(
            CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @PostMapping
    public ResponseEntity<Country> create(
            @RequestBody Country country) {

        Country saved = countryRepository.save(country);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @GetMapping
    public List<Country> getAll() {

        return countryRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Country> getById(
            @PathVariable int id) {

        return countryRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id) {

        if (!countryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        countryRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}