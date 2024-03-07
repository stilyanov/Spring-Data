package com.example.datatransferobjects;

import com.example.datatransferobjects.entities.Address;
import com.example.datatransferobjects.entities.Employee;
import com.example.datatransferobjects.entities.WorkStatus;
import com.example.datatransferobjects.entities.dtos.EmployeeDTO;
import com.example.datatransferobjects.entities.dtos.ManagerDTO;
import com.example.datatransferobjects.services.AddressService;
import com.example.datatransferobjects.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final AddressService addressService;
    private final EmployeeService employeeService;

    public CommandLineRunnerImpl(AddressService addressService, EmployeeService employeeService) {
        this.addressService = addressService;
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {

        ModelMapper mapper = new ModelMapper();

        Address addressManager = new Address("Sofia", "Bulgaria");
        Address addressStephen = new Address("Plovdiv", "Bulgaria");
        Address addressKirilyc = new Address("Varna", "Bulgaria");

        Employee manager = new Employee("Steve", "Jobbsen", BigDecimal.valueOf(3000), LocalDate.now(),
                addressManager, WorkStatus.PRESENT, null, List.of());

        Employee stephen = new Employee("Stephen", "Bjorn", BigDecimal.valueOf(4300), LocalDate.now(),
                addressStephen, WorkStatus.SICK, manager, List.of());

        Employee kirilyc = new Employee("Kirilyc", "Lefi", BigDecimal.valueOf(4400), LocalDate.now(),
                addressKirilyc, WorkStatus.PAID_TIME_OFF, manager, List.of());

        manager.setEmployees(List.of(stephen, kirilyc));

        EmployeeDTO employeeStephenDTO = mapper.map(stephen, EmployeeDTO.class);
        EmployeeDTO employeeKirilycDTO = mapper.map(kirilyc, EmployeeDTO.class);
        ManagerDTO managerDTO = mapper.map(manager, ManagerDTO.class);

    }
}
