package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.Apartment.ApartmentSeedDto;
import softuni.exam.models.dto.Apartment.ApartmentSeedRootDto;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
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
public class ApartmentServiceImpl implements ApartmentService {

    private static final String APARTMENT_FILE_PATH = "src/main/resources/files/xml/apartments.xml";
    private final ApartmentRepository apartmentRepository;
    private final TownRepository townRepository;
    private final ValidationUtil validationUtil;
    private final StringBuilder sb;
    private final ModelMapper mapper;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, TownRepository townRepository, ValidationUtil validationUtil, StringBuilder sb, ModelMapper mapper) {
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.sb = sb;
        this.mapper = mapper;
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(Path.of(APARTMENT_FILE_PATH));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ApartmentSeedRootDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ApartmentSeedRootDto apartmentSeedRootDto = (ApartmentSeedRootDto) unmarshaller.unmarshal(new File(APARTMENT_FILE_PATH));

        for (ApartmentSeedDto apartmentDto : apartmentSeedRootDto.getApartmentSeedDtoList()) {
            Optional<Apartment> optionalApartment = this.apartmentRepository
                    .findByTown_TownNameAndArea(apartmentDto.getTown(), apartmentDto.getArea());
            Optional<Town> optionalTown = this.townRepository.findByTownName(apartmentDto.getTown());

            if (!this.validationUtil.isValid(apartmentDto) || optionalApartment.isPresent() || optionalTown.isEmpty()) {
                sb.append("Invalid apartment\n");
                continue;
            }
            Apartment apartment = this.mapper.map(apartmentDto, Apartment.class);
            apartment.setTown(optionalTown.get());

            this.apartmentRepository.saveAndFlush(apartment);

            sb.append(String.format("Successfully imported apartment %s - %.2f\n",
                    apartment.getApartmentType(), apartment.getArea()));

        }


        return sb.toString();
    }
}
