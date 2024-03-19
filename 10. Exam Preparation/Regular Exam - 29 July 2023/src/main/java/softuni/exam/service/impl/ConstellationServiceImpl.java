package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.ConstellationSeedDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class ConstellationServiceImpl implements ConstellationService {

    private static final String FILE_PATH = "src/main/resources/files/json/constellations.json";
    private final ConstellationRepository constellationRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public ConstellationServiceImpl(ConstellationRepository constellationRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.constellationRepository = constellationRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.constellationRepository.count() > 0;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importConstellations() throws IOException {
        StringBuilder sb = new StringBuilder();

        ConstellationSeedDto[] constellationSeedDtos = this.gson.fromJson(readConstellationsFromFile(), ConstellationSeedDto[].class);
        for (ConstellationSeedDto constellationSeedDto : constellationSeedDtos) {
            Optional<Constellation> name = this.constellationRepository.findByName(constellationSeedDto.getName());

            if (!this.validationUtil.isValid(constellationSeedDto) || name.isPresent()) {
                sb.append("Invalid constellation\n");

                continue;
            }

            Constellation constellation = this.modelMapper.map(constellationSeedDto, Constellation.class);

            this.constellationRepository.saveAndFlush(constellation);

            sb.append(String.format("Successfully imported constellation %s - %s\n",
                    constellation.getName(),
                    constellation.getDescription()));
        }

        return sb.toString();
    }
}
