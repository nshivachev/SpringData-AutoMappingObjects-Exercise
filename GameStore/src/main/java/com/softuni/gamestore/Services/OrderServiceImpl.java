package com.softuni.gamestore.Services;

import com.softuni.gamestore.constants.Validations;
import com.softuni.gamestore.entities.Game;
import com.softuni.gamestore.entities.User;
import com.softuni.gamestore.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.softuni.gamestore.constants.Validations.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final GameService gameService;
    private final UserRepository userRepository;

    public OrderServiceImpl(UserService userService, GameService gameService, UserRepository userRepository) {
        this.userService = userService;
        this.gameService = gameService;
        this.userRepository = userRepository;
    }

    @Override
    public String addItem(String[] args) {
        final User user = userService.getLoggedUser();

        if (user == null) {
            return NOT_LOGGED_MESSAGE;
        }

        Optional<Game> game = gameService.findByTitle(args[1]);

        if (game.isEmpty()) {
            return Validations.NO_GAME_TITLE_MESSAGE;
        }

        if (doesGameExist(user, game.get())) {
            return GAME_EXISTS_INTO_CART;
        }

        user.getGames().add(game.get());

        userRepository.save(user);

        return args[1] + " added to cart";
    }

    @Override
    public String removeItem(String[] args) {
        final User user = userService.getLoggedUser();

        if (user == null) {
            return NOT_LOGGED_MESSAGE;
        }

        Optional<Game> game = gameService.findByTitle(args[1]);

        if (game.isEmpty()) {
            return Validations.NO_GAME_TITLE_MESSAGE;
        }

        if (!doesGameExist(user, game.get())) {
            return GAME_DOESNT_EXIST_INTO_CART;
        }

        user.getGames().removeIf(g -> g.getTitle().equals(game.get().getTitle()));

        userRepository.save(user);

        return args[1] + " removed from cart";
    }

    private static boolean doesGameExist(User user, Game other) {
        return !user.getGames().stream()
                .filter(game -> game.getTitle().equals(other.getTitle()))
                .toList()
                .isEmpty();
    }
}
