package gamestore.services;

import gamestore.entities.dtos.GameAddDTO;
import gamestore.entities.dtos.GamesAllDTO;

import java.util.Map;
import java.util.Set;

public interface GameService {
    String addGame(GameAddDTO gameAddDTO);
    String editGame(long id, Map<String, String> map);
    String deleteGame(long id);
    String allGames();
    Set<GamesAllDTO> getAllGames();
    String detailGame(String[] gameTitle);
}
