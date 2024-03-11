package gamestore;

import gamestore.entities.dtos.GameAddDTO;
import gamestore.entities.dtos.LoginUserDTO;
import gamestore.entities.dtos.RegisterUserDTO;
import gamestore.services.GameService;
import gamestore.services.OrderService;
import gamestore.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final UserService userService;
    private final GameService gameService;
    private final OrderService orderService;

    public CommandLineRunnerImpl(UserService userService, GameService gameService, OrderService orderService) {
        this.userService = userService;
        this.gameService = gameService;
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        while (!input.equals("END")) {
            String[] data = input.split("\\|");
            String command = data[0];

            String output = "";

            switch (command) {
                case "RegisterUser":
                    output = this.userService.registerUser(new RegisterUserDTO(data[1], data[2], data[3], data[4]));
                    break;
                case "LoginUser":
                    output = this.userService.loginUser(new LoginUserDTO(data[1], data[2]));
                    break;
                case "Logout":
                    output = this.userService.logout();
                    break;

                case "AddGame":
                    output = this.gameService.addGame(new GameAddDTO(data[1], Double.parseDouble(data[2]), Double.parseDouble(data[3]), data[4], data[5], data[6], data[7]));
                    break;
                case "EditGame":
                    Map<String, String> map = Arrays.stream(data).skip(2).map(p -> p.split("="))
                            .collect(Collectors.toMap(p -> p[0], p -> p[1]));
                    output = this.gameService.editGame(Long.parseLong(data[1]), map);
                    break;
                case "DeleteGame":
                    output = this.gameService.deleteGame(Long.parseLong(data[1]));
                    break;

                case "AllGames":
                    output = this.gameService.allGames();
                    break;
                case "DetailGame":
                    output = this.gameService.detailGame(data);
                    break;
                case "OwnedGames":
                    output = this.userService.usersOwnedGames();
                    break;
            }

            System.out.println(output);
            input = scanner.nextLine();
        }
    }
}
