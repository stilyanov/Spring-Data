package com.example.datatransferobjects.services.impl;

import com.example.datatransferobjects.repositories.EmployeeRepository;
import com.example.datatransferobjects.services.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

}
