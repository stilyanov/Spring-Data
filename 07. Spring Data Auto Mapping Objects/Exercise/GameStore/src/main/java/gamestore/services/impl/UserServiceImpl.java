package gamestore.services.impl;

import gamestore.entities.Game;
import gamestore.entities.User;
import gamestore.entities.dtos.LoginUserDTO;
import gamestore.entities.dtos.RegisterUserDTO;
import gamestore.entities.dtos.UserOwnedGamesDTO;
import gamestore.repositories.UserRepository;
import gamestore.services.UserService;
import gamestore.util.ValidatorService;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private User loggedIn;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ValidatorService validatorService;

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, ValidatorService validatorService) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.validatorService = validatorService;
    }

    @Override
    public String registerUser(RegisterUserDTO registerUserDTO) {
        if (!this.validatorService.isValid(registerUserDTO)) {
            Set<ConstraintViolation<RegisterUserDTO>> set = this.validatorService.validate(registerUserDTO);
            return set.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining("\n"));
        }

        if (!registerUserDTO.getPassword().equals(registerUserDTO.getConfirmPassword())) {
            return "Passwords don't match!";
        }

        Optional<User> userByEmail = this.userRepository.findUserByEmail(registerUserDTO.getEmail());
        if (userByEmail.isPresent()) {
            return "User with that email already exists.";
        }

        User user = this.modelMapper.map(registerUserDTO, User.class);
        if (this.userRepository.count() == 0) {
            user.setAdmin(true);
        }

        this.userRepository.saveAndFlush(user);
        return String.format("%s was registered.", user.getFullName());
    }

    @Override
    public String loginUser(LoginUserDTO loginUserDTO) {
        Optional<User> optionalUser = this.userRepository.findByEmailAndPassword(loginUserDTO.getEmail(), loginUserDTO.getPassword());

        if (optionalUser.isEmpty()) {
            return "Email or password is incorrect";
        }

        setLoggedIn(optionalUser.get());
        return String.format("Successfully logged in %s", optionalUser.get().getFullName());
    }

    @Override
    public String logout() {
        if (this.loggedIn == null) {
            return "Cannot log out. No user was logged in.";
        }
        String format = String.format("User %s successfully logged out", this.loggedIn.getFullName());
        setLoggedIn(null);
        return format;
    }

    @Override
    public String usersOwnedGames() {
        if (this.loggedIn == null) {
            return "User must be logged in.";
        }

        User user = this.userRepository.findUserById(this.loggedIn.getId()).get();

        Set<Game> ownedGames = user.getGames();

        if (ownedGames.isEmpty()) {
            return String.format("%s does not own any games", user.getFullName());
        }

        Set<UserOwnedGamesDTO> userOwnedGamesDTOS = ownedGames
                .stream()
                .map(g -> modelMapper.map(g, UserOwnedGamesDTO.class))
                .collect(Collectors.toSet());

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s owns the following games", user.getFullName())).append(System.lineSeparator());

        userOwnedGamesDTOS.forEach(g -> sb.append(g.getTitle()).append(System.lineSeparator()));

        return sb.toString().trim();
    }

    @Override
    public User getLoggedIn() {
        return this.loggedIn;
    }

    private void setLoggedIn(User loggedIn) {
        this.loggedIn = loggedIn;
    }
}
