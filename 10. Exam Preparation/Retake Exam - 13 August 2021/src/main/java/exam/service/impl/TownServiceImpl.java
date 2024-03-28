package exam.service.impl;

import exam.model.dto.town.TownSeedDto;
import exam.model.dto.town.TownSeedRootDto;
import exam.model.entity.Town;
import exam.repository.TownRepository;
import exam.service.TownService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class TownServiceImpl implements TownService {

    private static final String TOWN_FILE_PATH = "src/main/resources/files/xml/towns.xml";
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final StringBuilder stringBuilder;
    private final ValidationUtil validationUtil;

    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, StringBuilder stringBuilder, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.stringBuilder = stringBuilder;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(Path.of(TOWN_FILE_PATH));
    }

    @Override
    public String importTowns() throws JAXBException, FileNotFoundException {
        JAXBContext jaxbContext = JAXBContext.newInstance(TownSeedRootDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        TownSeedRootDto townSeedRootDto = (TownSeedRootDto) unmarshaller.unmarshal(new File(TOWN_FILE_PATH));

        for (TownSeedDto townSeedDto : townSeedRootDto.getTownSeedDtoList()) {
            Optional<Town> optionalTown = this.townRepository.findByName(townSeedDto.getName());

            if (!this.validationUtil.isValid(townSeedDto) || optionalTown.isPresent()) {
                stringBuilder.append("Invalid town\n");
                continue;
            }

            Town town = this.modelMapper.map(townSeedDto, Town.class);

            this.townRepository.saveAndFlush(town);

            stringBuilder.append(String.format("Successfully imported Town %s\n", town.getName()));


        }


        return stringBuilder.toString();
    }
}
