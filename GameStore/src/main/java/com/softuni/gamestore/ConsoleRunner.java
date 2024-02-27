package com.softuni.gamestore;

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

    @Autowired
    public ConsoleRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        final String[] input = scanner.nextLine().split("\\|");
        final String command = input[0];

        final String output = switch (command) {
            case REGISTER_USER -> userService.registerUser(input);
            case LOGIN_USER -> userService.loginUser(args);
            case LOGOUT_USER -> userService.logoutUser(args);
            default -> COMMAND_NOT_FOUND_MESSAGE;
        };

        System.out.println(output);
    }
}
