package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.MechanicSeedDto;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.service.MechanicsService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class MechanicsServiceImpl implements MechanicsService {

    private static String MECHANICS_FILE_PATH = "src/main/resources/files/json/mechanics.json";

    private final MechanicsRepository mechanicsRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public MechanicsServiceImpl(MechanicsRepository mechanicsRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.mechanicsRepository = mechanicsRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.mechanicsRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(MECHANICS_FILE_PATH)));
    }

    @Override
    public String importMechanics() throws IOException {
        StringBuilder sb = new StringBuilder();
        MechanicSeedDto[] mechanicSeedDtos = this.gson.fromJson(readMechanicsFromFile(), MechanicSeedDto[].class);

        for (MechanicSeedDto mechanicSeedDto : mechanicSeedDtos) {
            Optional<Mechanic> email = this.mechanicsRepository.findByEmail(mechanicSeedDto.getEmail());

            if (!this.validationUtil.isValid(mechanicSeedDto) || email.isPresent()) {
                sb.append("Invalid mechanic\n");

                continue;
            }

            Mechanic mechanic = this.modelMapper.map(mechanicSeedDto, Mechanic.class);

            this.mechanicsRepository.saveAndFlush(mechanic);

            sb.append(String.format("Successfully imported mechanic %s %s\n",
                    mechanicSeedDto.getFirstName(),
                    mechanicSeedDto.getLastName()));
        }


        return sb.toString();
    }
}
