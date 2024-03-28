package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dto.customer.CustomerSeedDto;
import exam.model.entity.Customer;
import exam.model.entity.Town;
import exam.repository.CustomerRepository;
import exam.repository.TownRepository;
import exam.service.CustomerService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMER_FILE_PATH = "src/main/resources/files/json/customers.json";

    private final CustomerRepository customerRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final StringBuilder stringBuilder;
    private final Gson gson;

    public CustomerServiceImpl(CustomerRepository customerRepository, TownRepository townRepository, ModelMapper modelMapper, ValidationUtil validationUtil, StringBuilder stringBuilder, Gson gson) {
        this.customerRepository = customerRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.stringBuilder = stringBuilder;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(Path.of(CUSTOMER_FILE_PATH));
    }

    @Override
    public String importCustomers() throws IOException {
        CustomerSeedDto[] customerSeedDtos = this.gson.fromJson(readCustomersFileContent(), CustomerSeedDto[].class);
        for (CustomerSeedDto customerSeedDto : customerSeedDtos) {
            Optional<Customer> optionalCustomerEmail = this.customerRepository.findByEmail(customerSeedDto.getEmail());
            Optional<Town> optionalTown = this.townRepository.findByName(customerSeedDto.getTown().getName());

            if (!this.validationUtil.isValid(customerSeedDto) || optionalCustomerEmail.isPresent() || optionalTown.isEmpty()) {
                stringBuilder.append("Invalid Customer\n");
                continue;
            }

            Customer customer = this.modelMapper.map(customerSeedDto, Customer.class);
            customer.setTown(optionalTown.get());

            this.customerRepository.saveAndFlush(customer);

            stringBuilder.append(String.format("Successfully imported Customer %s %s - %s\n",
                    customer.getFirstName(), customer.getLastName(), customer.getEmail()));

        }


        return stringBuilder.toString();
    }
}
