package com.example.cardealerxml.services.impl;

import com.example.cardealerxml.models.dtos.exports.*;
import com.example.cardealerxml.models.dtos.imports.CarsDto.CarSeedDto;
import com.example.cardealerxml.models.dtos.imports.CarsDto.CarSeedRootDto;
import com.example.cardealerxml.models.entities.Car;
import com.example.cardealerxml.models.entities.Part;
import com.example.cardealerxml.repositories.CarRepository;
import com.example.cardealerxml.repositories.PartRepository;
import com.example.cardealerxml.services.CarService;
import com.example.cardealerxml.util.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private static final String FILE_IMPORT_PATH = "src/main/resources/xml/cars.xml";
    private static final String FILE_EXPORT_PATH = "src/main/resources/xml/exports/toyota-cars.xml";
    private static final String CARS_AND_PARTS_EXPORT_PATH = "src/main/resources/xml/exports/cars-and-parts.xml";

    private final CarRepository carRepository;
    private final PartRepository partRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    public CarServiceImpl(CarRepository carRepository, PartRepository partRepository, XmlParser xmlParser, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCars() throws JAXBException {
        if (this.carRepository.count() == 0) {
            CarSeedRootDto carSeedRootDto = this.xmlParser.parse(CarSeedRootDto.class, FILE_IMPORT_PATH);

            for (CarSeedDto carSeedDto : carSeedRootDto.getCarSeedDtoList()) {
                Car car = this.modelMapper.map(carSeedDto, Car.class);

                car.setParts(getRandomParts());

                this.carRepository.saveAndFlush(car);
            }
        }

    }

    private Set<Part> getRandomParts() {
        Set<Part> parts = new HashSet<>();

        int count = ThreadLocalRandom.current().nextInt(2, 4);

        for (int i = 0; i < count; i++) {
            parts.add(this.partRepository
                    .findById(ThreadLocalRandom.current().nextLong(1, this.partRepository.count() + 1))
                    .get());
        }

        return parts;
    }


    @Override
    public void exportCarsFromToyota() throws JAXBException {
        List<CarsFromToyotaDto> fromToyotaDtos = this.carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota")
                .stream()
                .map(car -> this.modelMapper.map(car, CarsFromToyotaDto.class))
                .collect(Collectors.toList());

        CarsFromToyotaRootDto carsFromToyotaRootDto = new CarsFromToyotaRootDto();
        carsFromToyotaRootDto.setCarsFromToyotaDtoList(fromToyotaDtos);

        this.xmlParser.exportToFile(carsFromToyotaRootDto, FILE_EXPORT_PATH);
    }

    @Override
    public void exportCarsAndTheirParts() throws JAXBException {
        CarAndPartsRootDto carAndPartsRootDto = new CarAndPartsRootDto();

        List<CarAndPartsDto> carAndPartsDtos = this.carRepository.findAll().stream()
                .map(car -> {
                    CarAndPartsDto carAndPartsDto = this.modelMapper.map(car, CarAndPartsDto.class);

                    PartRootDto partRootDto = new PartRootDto();
                    List<PartDto> partDtos = car.getParts().stream()
                            .map(part -> this.modelMapper.map(part, PartDto.class))
                            .collect(Collectors.toList());

                    partRootDto.setPartDtoList(partDtos);

                    carAndPartsDto.setPartRootDto(partRootDto);

                    return carAndPartsDto;
                })
                .collect(Collectors.toList());


        carAndPartsRootDto.setCarAndPartsDtoList(carAndPartsDtos);

        this.xmlParser.exportToFile(carAndPartsRootDto, CARS_AND_PARTS_EXPORT_PATH);
    }
}
