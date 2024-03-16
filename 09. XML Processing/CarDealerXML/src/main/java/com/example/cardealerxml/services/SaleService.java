package com.example.cardealerxml.services;

import jakarta.xml.bind.JAXBException;

public interface SaleService {
    void seedSales();

    void exportSales() throws JAXBException;
}
