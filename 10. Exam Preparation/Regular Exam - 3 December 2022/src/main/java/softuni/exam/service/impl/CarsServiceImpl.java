package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.CarSeedDto;
import softuni.exam.models.dto.xml.CarSeedRootDto;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarsRepository;
import softuni.exam.service.CarsService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class CarsServiceImpl implements CarsService {
    private static String CARS_FILE_PATH = "src/main/resources/files/xml/cars.xml";

    private final CarsRepository carsRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CarsServiceImpl(CarsRepository carsRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.carsRepository = carsRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.carsRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(CARS_FILE_PATH)));
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
        JAXBContext jaxbContext = JAXBContext.newInstance(CarSeedRootDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        CarSeedRootDto carSeedRootDto = (CarSeedRootDto) unmarshaller.unmarshal(new File(CARS_FILE_PATH));

        for (CarSeedDto carSeedDto : carSeedRootDto.getCarSeedDtoList()) {
            Optional<Car> plateNumber = this.carsRepository.findByPlateNumber(carSeedDto.getPlateNumber());

            if (!this.validationUtil.isValid(carSeedDto) || plateNumber.isPresent()) {
                sb.append("Invalid car\n");
                continue;
            }

            Car car = this.modelMapper.map(carSeedDto, Car.class);

            this.carsRepository.saveAndFlush(car);

            sb.append(String.format("Successfully imported car %s - %s\n",
                    carSeedDto.getCarMake(),
                    carSeedDto.getCarModel()));
        }

        return sb.toString();
    }
}
