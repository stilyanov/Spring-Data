package gamestore.services;

import gamestore.entities.User;
import gamestore.entities.dtos.LoginUserDTO;
import gamestore.entities.dtos.RegisterUserDTO;

public interface UserService {
    String registerUser(RegisterUserDTO registerUserDTO);

    String loginUser(LoginUserDTO loginUserDTO);
    String logout();
    User getLoggedIn();
    String usersOwnedGames();
}
