package com.example.datatransferobjects;

import com.example.datatransferobjects.entities.Address;
import com.example.datatransferobjects.entities.Employee;
import com.example.datatransferobjects.entities.WorkStatus;
import com.example.datatransferobjects.entities.dtos.EmployeeDTO;
import com.example.datatransferobjects.entities.dtos.ManagerDTO;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ModelMapperMain {
    public static void main(String[] args) {
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

//        System.out.println(employeeStephenDTO);
//        System.out.println(employeeKirilycDTO);
        System.out.println(managerDTO);
    }
}
