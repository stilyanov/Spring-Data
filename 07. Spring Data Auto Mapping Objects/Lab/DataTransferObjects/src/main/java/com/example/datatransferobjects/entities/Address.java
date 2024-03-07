package com.example.datatransferobjects.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address extends BaseEntity {
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String country;

    public Address() {
    }

    public Address(String city, String street) {
        this.city = city;
        this.country = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String street) {
        this.country = street;
    }
}
