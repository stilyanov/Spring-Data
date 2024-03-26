package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.Town.TownSeedDto;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class TownServiceImpl implements TownService {

    private static final String TOWN_FILE_PATH = "src/main/resources/files/json/towns.json";
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final StringBuilder stringBuilder;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, StringBuilder stringBuilder, ValidationUtil validationUtil, Gson gson) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.stringBuilder = stringBuilder;
        this.validationUtil = validationUtil;
        this.gson = gson;
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
    public String importTowns() throws IOException {
        TownSeedDto[] townSeedDtos = this.gson.fromJson(readTownsFileContent(), TownSeedDto[].class);

        for (TownSeedDto townSeedDto : townSeedDtos) {
            Optional<Town> townName = this.townRepository.findByTownName(townSeedDto.getTownName());

            if (!this.validationUtil.isValid(townSeedDto) || townName.isPresent()) {
                stringBuilder.append("Invalid town\n");
                continue;
            }
            Town town = this.modelMapper.map(townSeedDto, Town.class);

            this.townRepository.saveAndFlush(town);

            stringBuilder.append(String.format("Successfully imported town %s - %d\n",
                    town.getTownName(), town.getPopulation()));

        }


        return stringBuilder.toString();
    }
}
