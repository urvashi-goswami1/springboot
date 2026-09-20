package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Capital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int capitalId;

    private String cityName;
    private long population;

    @OneToOne(mappedBy = "capital")
    @JsonIgnore
    private Country country;

    public Capital() {
    }

    public int getCapitalId() {
        return capitalId;
    }

    public void setCapitalId(int capitalId) {
        this.capitalId = capitalId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}