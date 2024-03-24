package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.PartSeedDto;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartsRepository;
import softuni.exam.service.PartsService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class PartsServiceImpl implements PartsService {

    private static String PARTS_FILE_PATH = "src/main/resources/files/json/parts.json";

    private final PartsRepository partsRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public PartsServiceImpl(PartsRepository partsRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.partsRepository = partsRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.partsRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(PARTS_FILE_PATH)));
    }

    @Override
    public String importParts() throws IOException {
        StringBuilder sb = new StringBuilder();
        PartSeedDto[] seedDtos = this.gson.fromJson(readPartsFileContent(), PartSeedDto[].class);

        for (PartSeedDto dto : seedDtos) {
            Optional<Part> partName = this.partsRepository.findByPartName(dto.getPartName());

            if (!this.validationUtil.isValid(dto) || partName.isPresent()) {
                sb.append("Invalid part\n");

                continue;
            }

            Part part = this.modelMapper.map(dto, Part.class);
            this.partsRepository.saveAndFlush(part);

            sb.append(String.format("Successfully imported part %s - %s\n",
                    dto.getPartName(),
                    dto.getPrice()));
        }

        return sb.toString();
    }
}
