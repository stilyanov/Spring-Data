package com.example.cardealerxml.services.impl;

import com.example.cardealerxml.models.dtos.exports.CarDto;
import com.example.cardealerxml.models.dtos.exports.SalesWithDiscountDto;
import com.example.cardealerxml.models.dtos.exports.SalesWithDiscountRootDto;
import com.example.cardealerxml.models.entities.Car;
import com.example.cardealerxml.models.entities.Customer;
import com.example.cardealerxml.models.entities.Part;
import com.example.cardealerxml.models.entities.Sale;
import com.example.cardealerxml.repositories.CarRepository;
import com.example.cardealerxml.repositories.CustomerRepository;
import com.example.cardealerxml.repositories.SaleRepository;
import com.example.cardealerxml.services.SaleService;
import com.example.cardealerxml.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {
    private static final String EXPORT_SALES_PATH = "src/main/resources/xml/exports/sales-discounts.xml";

    private final List<Double> discounts = List.of(1.0, 0.95, 0.9, 0.85, 0.8, 0.7, 0.6, 0.5);
    private final SaleRepository saleRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    public SaleServiceImpl(SaleRepository saleRepository, CarRepository carRepository, CustomerRepository customerRepository, XmlParser xmlParser, ModelMapper modelMapper) {
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedSales() {
        if (this.saleRepository.count() == 0) {
            for (int i = 0; i < 50; i++) {
                Sale sale = new Sale();

                sale.setCar(getRandomCar());
                sale.setCustomer(getRandomCustomer());
                sale.setDiscount(getRandomDiscount());

                this.saleRepository.saveAndFlush(sale);
            }
        }
    }

    @Override
    public void exportSales() throws JAXBException {
        SalesWithDiscountRootDto salesWithDiscountRootDto = new SalesWithDiscountRootDto();

        List<SalesWithDiscountDto> salesWithDiscountDtos = this.saleRepository.findAll().stream()
                .map(sale -> {
                    SalesWithDiscountDto salesWithDiscountDto = new SalesWithDiscountDto();
                    CarDto car = this.modelMapper.map(sale.getCar(), CarDto.class);

                    salesWithDiscountDto.setCarDto(car);
                    salesWithDiscountDto.setCustomerName(sale.getCustomer().getName());

                    BigDecimal partsPrice = sale.getCar().getParts().stream()
                            .map(Part::getPrice).reduce(BigDecimal::add).get();

                    salesWithDiscountDto.setPrice(partsPrice);

                    BigDecimal priceWithDiscount = salesWithDiscountDto.getPrice().multiply(BigDecimal.valueOf(sale.getDiscount()));

                    salesWithDiscountDto.setPriceWithDiscount(priceWithDiscount);

                    return salesWithDiscountDto;
                }).collect(Collectors.toList());

        salesWithDiscountRootDto.setSalesWithDiscountDtoList(salesWithDiscountDtos);

        this.xmlParser.exportToFile(salesWithDiscountRootDto, EXPORT_SALES_PATH);
    }

    private double getRandomDiscount() {
        return discounts.get(ThreadLocalRandom.current().nextInt(1, discounts.size()));
    }

    private Customer getRandomCustomer() {
        return this.customerRepository
                .findById(ThreadLocalRandom.current().nextLong(1, this.customerRepository.count() + 1)).get();
    }

    private Car getRandomCar() {
        return this.carRepository
                .findById(ThreadLocalRandom.current().nextLong(1, this.carRepository.count() + 1)).get();
    }
}
