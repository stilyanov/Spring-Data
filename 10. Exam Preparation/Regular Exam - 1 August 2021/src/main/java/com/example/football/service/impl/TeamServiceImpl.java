package com.example.football.service.impl;

import com.example.football.models.dto.team.TeamSeedDto;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.example.football.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class TeamServiceImpl implements TeamService {

    private static final String TEAM_FILE_PATH = "src/main/resources/files/json/teams.json";
    private final TeamRepository teamRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final StringBuilder stringBuilder;
    private final Gson gson;

    public TeamServiceImpl(TeamRepository teamRepository, TownRepository townRepository, ModelMapper modelMapper, ValidationUtil validationUtil, StringBuilder stringBuilder, Gson gson) {
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.stringBuilder = stringBuilder;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(Path.of(TEAM_FILE_PATH));
    }

    @Override
    public String importTeams() throws IOException {
        TeamSeedDto[] teamSeedDtos = this.gson.fromJson(readTeamsFileContent(), TeamSeedDto[].class);
        for (TeamSeedDto teamSeedDto : teamSeedDtos) {
            Optional<Team> optionalTeam = this.teamRepository.findByName(teamSeedDto.getName());
            Optional<Town> optionalTown = this.townRepository.findByName(teamSeedDto.getTownName());

            if (!this.validationUtil.isValid(teamSeedDto) || optionalTeam.isPresent() || optionalTown.isEmpty()) {
                stringBuilder.append("Invalid team\n");
                continue;
            }

            Team team = this.modelMapper.map(teamSeedDto, Team.class);
            team.setTown(optionalTown.get());

            this.teamRepository.saveAndFlush(team);

            stringBuilder.append(String.format("Successfully imported Team %s - %d\n",
                    team.getName(), team.getFanBase()));

        }


        return stringBuilder.toString();
    }
}
