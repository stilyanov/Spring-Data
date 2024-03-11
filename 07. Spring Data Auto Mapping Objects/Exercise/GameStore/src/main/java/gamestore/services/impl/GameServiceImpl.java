package gamestore.services.impl;

import gamestore.entities.Game;
import gamestore.entities.dtos.DetailsGameDTO;
import gamestore.entities.dtos.GameAddDTO;
import gamestore.entities.dtos.GamesAllDTO;
import gamestore.repositories.GameRepository;
import gamestore.services.GameService;
import gamestore.services.UserService;
import gamestore.util.ValidatorService;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final ValidatorService validatorService;
    private final ModelMapper modelMapper;
    private UserService userService;

    public GameServiceImpl(GameRepository gameRepository, ValidatorService validatorService, ModelMapper modelMapper, UserService userService) {
        this.gameRepository = gameRepository;
        this.validatorService = validatorService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public String addGame(GameAddDTO gameAddDTO) {
        if (this.userService.getLoggedIn() != null && this.userService.getLoggedIn().isAdmin()) {
            if (!this.validatorService.isValid(gameAddDTO)) {
                return this.validatorService.validate(gameAddDTO)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining("\n"));
            }
            Game game = this.modelMapper.map(gameAddDTO, Game.class);
            this.gameRepository.saveAndFlush(game);

            return String.format("Added %s", game.getTitle());
        }
        return "Logged in User is not admin!";
    }

    @Override
    public String editGame(long id, Map<String, String> map) {
        if (this.userService.getLoggedIn() != null && this.userService.getLoggedIn().isAdmin()) {
            Optional<Game> optional = this.gameRepository.findById(id);
            if (optional.isEmpty()) {
                return "No such game exists with given id";
            }

            Game game = optional.get();

            for (Map.Entry<String, String> entry : map.entrySet()) {
                switch (entry.getKey()) {
                    case "title" -> game.setTitle(entry.getValue());
                    case "price" -> game.setPrice(BigDecimal.valueOf(Double.parseDouble(entry.getValue())));
                    case "size" -> game.setSize(Double.parseDouble(entry.getValue()));
                    case "trailer" -> game.setTrailer(entry.getValue());
                    case "thumbnail" -> game.setThumbnail(entry.getValue());
                    case "description" -> game.setDescription(entry.getValue());
                }
            }

            this.gameRepository.saveAndFlush(game);

            return String.format("Edited %s", game.getTitle());
        }
        return "Logged in User is not admin!";
    }

    @Override
    public String deleteGame(long id) {
        if (this.userService.getLoggedIn() != null && this.userService.getLoggedIn().isAdmin()) {
            Optional<Game> optional = this.gameRepository.findById(id);
            if (optional.isEmpty()) {
                return "No such game with given id";
            }
            this.gameRepository.delete(optional.get());

            return String.format("Deleted %s", optional.get().getTitle());
        }
        return "Logged in User is not admin!";
    }

    @Override
    public String allGames() {
        return this.getAllGames()
                .stream()
                .map(GamesAllDTO::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public Set<GamesAllDTO> getAllGames() {
        return this.gameRepository.findAll()
                .stream()
                .map(g -> this.modelMapper.map(g, GamesAllDTO.class))
                .collect(Collectors.toSet());
    }

    @Override
    public String detailGame(String[] data) {
        String title = data[1];

        Optional<Game> firstByTitle = this.gameRepository.findFirstByTitle(title);

        if (firstByTitle.isEmpty()) {
            return String.format("Game title the %s does not exist!", title);
        }

        Game game = firstByTitle.get();

        DetailsGameDTO map = modelMapper.map(game, DetailsGameDTO.class);

        return map.toString();
    }
}
