package com.example.football.service.impl;

import com.example.football.models.dto.town.TownSeedDto;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.example.football.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;


@Service
public class TownServiceImpl implements TownService {

    private static final String TOWN_FILE_PATH = "src/main/resources/files/json/towns.json";
    private final TownRepository townRepository;
    private final ValidationUtil validationUtil;
    private final StringBuilder sb;
    private final Gson gson;
    private final ModelMapper modelMapper;

    public TownServiceImpl(TownRepository townRepository, ValidationUtil validationUtil, StringBuilder sb, Gson gson, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.sb = sb;
        this.gson = gson;
        this.modelMapper = modelMapper;
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
            Optional<Town> optionalTown = this.townRepository.findByName(townSeedDto.getName());

            if (!this.validationUtil.isValid(townSeedDto) || optionalTown.isPresent()) {
                sb.append("Invalid town\n");
                continue;
            }

            Town town = this.modelMapper.map(townSeedDto, Town.class);

            this.townRepository.saveAndFlush(town);

            sb.append(String.format("Successfully imported Town %s - %d\n",
                    town.getName(), town.getPopulation()));
        }


        return sb.toString();
    }
}
