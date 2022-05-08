package com.stonesmach.mvp.controller;

import com.stonesmach.mvp.entity.Country;
import com.stonesmach.mvp.service.CountryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class GeoController {

    @Autowired
    private CountryDao countryDao;

    @GetMapping("/countries")
    public List<Country> getAllCountries() {
        return countryDao.getAll();
    }

    @GetMapping("/countries/{countryId}")
    public Country getCountryById(@PathVariable("countryId") int id) {
        return countryDao.getById(id);
    }

    @PostMapping("/countries")
    @ResponseStatus(HttpStatus.CREATED)
    public Country createCountry(@RequestBody Country country) {
        return countryDao.create(country);
    }
}