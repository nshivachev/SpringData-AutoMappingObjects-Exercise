package com.softuni.gamestore;

import com.softuni.gamestore.Services.GameService;
import com.softuni.gamestore.Services.OrderService;
import com.softuni.gamestore.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

import static com.softuni.gamestore.constants.Commands.*;
import static com.softuni.gamestore.constants.Validations.COMMAND_NOT_FOUND_MESSAGE;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private static final Scanner scanner = new Scanner(System.in);
    private final UserService userService;
    private final GameService gameService;
    private final OrderService orderService;

    @Autowired
    public ConsoleRunner(UserService userService, GameService gameService, OrderService orderService) {
        this.userService = userService;
        this.gameService = gameService;
        this.orderService = orderService;
    }

    @Override
    public void run(String... args) throws Exception {
        String input = scanner.nextLine();

        while (!CLOSE.equalsIgnoreCase(input)) {
            final String[] arguments = input.split("\\|");
            final String command = arguments[0];

            final String output = switch (command) {
                case REGISTER_USER -> userService.registerUser(arguments);
                case LOGIN_USER -> userService.loginUser(arguments);
                case LOGOUT_USER -> userService.logoutUser();
                case ADD_GAME -> gameService.addGame(arguments);
                case EDIT_GAME -> gameService.editGame(arguments);
                case DELETE_GAME -> gameService.deleteGame(arguments);
                case ALL_GAMES -> gameService.viewAllGames();
                case DETAIL_GAME -> gameService.viewGameDetails(arguments);
                case OWNED_GAMES -> gameService.viewOwnedGames();
                case ADD_ITEM -> orderService.addItem(arguments);
                case REMOVE_ITEM -> orderService.removeItem(arguments);
                default -> COMMAND_NOT_FOUND_MESSAGE;
            };

            System.out.println(output);

            input = scanner.nextLine();
        }
    }
}
