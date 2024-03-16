package com.example.cardealerxml.services.impl;

import com.example.cardealerxml.models.dtos.exports.CustomerOrderedDto;
import com.example.cardealerxml.models.dtos.exports.CustomerOrderedRootDto;
import com.example.cardealerxml.models.dtos.exports.CustomersTotalSalesDto;
import com.example.cardealerxml.models.dtos.exports.CustomersTotalSalesRootDto;
import com.example.cardealerxml.models.dtos.imports.CustomerDto.CustomerSeedDto;
import com.example.cardealerxml.models.dtos.imports.CustomerDto.CustomerSeedRootDto;
import com.example.cardealerxml.models.entities.Customer;
import com.example.cardealerxml.repositories.CustomerRepository;
import com.example.cardealerxml.services.CustomerService;
import com.example.cardealerxml.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final String FILE_IMPORT_PATH = "src/main/resources/xml/customers.xml";

    private static final String FILE_EXPORT_PATH = "src/main/resources/xml/exports/ordered-customers.xml";
    private static final String CUSTOMERS_TOTAL_SALES_EXPORT_PATH = "src/main/resources/xml/exports/customers-total-sales.xml";

    private final CustomerRepository customerRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, XmlParser xmlParser, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCustomers() throws JAXBException {
        if (this.customerRepository.count() == 0) {
            CustomerSeedRootDto customerSeedRootDto = this.xmlParser.parse(CustomerSeedRootDto.class, FILE_IMPORT_PATH);

            for (CustomerSeedDto customerSeedDto : customerSeedRootDto.getCustomerSeedDtoList()) {
                Customer customer = this.modelMapper.map(customerSeedDto, Customer.class);

                this.customerRepository.saveAndFlush(customer);
            }
        }
    }

    @Override
    public void exportOrderedCustomers() throws JAXBException {
        List<CustomerOrderedDto> customerOrderedDtos = this.customerRepository.findAllByOrderByBirthDateAscIsYoungDriverDesc().stream()
                .map(customer -> this.modelMapper.map(customer, CustomerOrderedDto.class))
                .collect(Collectors.toList());

        CustomerOrderedRootDto customerOrderedRootDto = new CustomerOrderedRootDto();
        customerOrderedRootDto.setCustomerOrderedDtoList(customerOrderedDtos);

        this.xmlParser.exportToFile(customerOrderedRootDto, FILE_EXPORT_PATH);
    }

    @Override
    public void exportTotalSalesByCustomer() throws JAXBException {
        CustomersTotalSalesRootDto customersTotalSalesRootDto = new CustomersTotalSalesRootDto();

        List<CustomersTotalSalesDto> customersTotalSalesDtos = this.customerRepository.findAllWithBoughtCars().stream()
                .map(customer -> {
                    CustomersTotalSalesDto customersTotalSalesDto = new CustomersTotalSalesDto();
                    customersTotalSalesDto.setFullName(customer.getName());
                    customersTotalSalesDto.setBoughtCars(customer.getSales().size());

                    double spentMoney = customer.getSales().stream()
                            .mapToDouble(s -> s.getCar().getParts().stream().mapToDouble(p -> p.getPrice().doubleValue()).sum() * s.getDiscount())
                            .sum();

                    customersTotalSalesDto.setSpentMoney(BigDecimal.valueOf(spentMoney));

                    return customersTotalSalesDto;
                }).sorted(Comparator.comparing(CustomersTotalSalesDto::getSpentMoney).reversed()
                        .thenComparing(Comparator.comparing(CustomersTotalSalesDto::getBoughtCars).reversed()))
                .collect(Collectors.toList());

        customersTotalSalesRootDto.setCustomersTotalSalesDtoList(customersTotalSalesDtos);

        this.xmlParser.exportToFile(customersTotalSalesRootDto, CUSTOMERS_TOTAL_SALES_EXPORT_PATH);
    }
}
