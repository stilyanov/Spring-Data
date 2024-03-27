package com.example.football.service.impl;

import com.example.football.models.dto.player.PlayerSeedDto;
import com.example.football.models.dto.player.PlayerSeedRootDto;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import com.example.football.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    private static final String PLAYER_FILE_PATH = "src/main/resources/files/xml/players.xml";
    private final PlayerRepository playerRepository;
    private final TownRepository townRepository;
    private final TeamRepository teamRepository;
    private final StatRepository statRepository;
    private final ModelMapper modelMapper;
    private final StringBuilder stringBuilder;
    private final ValidationUtil validationUtil;

    public PlayerServiceImpl(PlayerRepository playerRepository, TownRepository townRepository, TeamRepository teamRepository, StatRepository statRepository, ModelMapper modelMapper, StringBuilder stringBuilder, ValidationUtil validationUtil) {
        this.playerRepository = playerRepository;
        this.townRepository = townRepository;
        this.teamRepository = teamRepository;
        this.statRepository = statRepository;
        this.modelMapper = modelMapper;
        this.stringBuilder = stringBuilder;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(Path.of(PLAYER_FILE_PATH));
    }

    @Override
    public String importPlayers() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(PlayerSeedRootDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        PlayerSeedRootDto playerSeedRootDto = (PlayerSeedRootDto) unmarshaller.unmarshal(new File(PLAYER_FILE_PATH));

        for (PlayerSeedDto playerSeedDto : playerSeedRootDto.getPlayerSeedDtoList()) {
            Optional<Player> optionalPlayerEmail = this.playerRepository.findByEmail(playerSeedDto.getEmail());
            Optional<Town> optionalTown = this.townRepository.findByName(playerSeedDto.getTownNameDto().getName());
            Optional<Team> optionalTeam = this.teamRepository.findByName(playerSeedDto.getTeamNameDto().getName());
            Optional<Stat> optionalStatId = this.statRepository.findById(playerSeedDto.getStatIdDto().getId());

            if (!this.validationUtil.isValid(playerSeedDto) || optionalPlayerEmail.isPresent()
                    || optionalTown.isEmpty() || optionalTeam.isEmpty() || optionalStatId.isEmpty()) {
                stringBuilder.append("Invalid player\n");
                continue;
            }

            Player player = this.modelMapper.map(playerSeedDto, Player.class);
            player.setTown(optionalTown.get());
            player.setTeam(optionalTeam.get());
            player.setStat(optionalStatId.get());

            this.playerRepository.saveAndFlush(player);

            stringBuilder.append(String.format("Successfully imported Player %s %s - %s\n",
                    player.getFirstName(), player.getLastName(), player.getPositionType()));

        }


        return stringBuilder.toString();
    }

    @Override
    public String exportBestPlayers() {
        List<Player> playersResult = this.playerRepository
                .findAllByBirthDateAfterAndBirthDateBeforeOrderByStat_shootingDescStat_passingDescStat_enduranceDescLastName
                        (LocalDate.of(1995, 1, 1),
                                LocalDate.of(2003, 1, 1));

        playersResult.forEach(player -> {
            stringBuilder.append(String.format("""
                                    Player - %s %s
                                            Position - %s
                                            Team - %s
                                            Stadium - %s""",
                            player.getFirstName(), player.getLastName(),
                            player.getPositionType().toString(),
                            player.getTeam().getName(),
                            player.getTeam().getStadiumName()))
                    .append(System.lineSeparator());
        });


        return stringBuilder.toString().trim();
    }
}
