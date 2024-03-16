package com.example.cardealerxml.services;

import jakarta.xml.bind.JAXBException;

public interface CarService {
    void seedCars() throws JAXBException;

    void exportCarsFromToyota() throws JAXBException;

    void exportCarsAndTheirParts() throws JAXBException;
}
