package com.example.cardealerxml.services;

import jakarta.xml.bind.JAXBException;

public interface CustomerService {
    void seedCustomers() throws JAXBException;

    void exportOrderedCustomers() throws JAXBException;

    void exportTotalSalesByCustomer() throws JAXBException;
}
