package com.softuni.gamestore.Services;

import com.softuni.gamestore.entities.Game;
import com.softuni.gamestore.entities.Order;
import com.softuni.gamestore.entities.User;
import com.softuni.gamestore.repositories.OrderRepository;
import com.softuni.gamestore.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static com.softuni.gamestore.constants.Validations.*;

@Service
public class OrderServiceImpl implements OrderService {
    private User loggedUser;
    private final UserService userService;
    private final GameService gameService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(UserService userService, GameService gameService, UserRepository userRepository, OrderRepository orderRepository) {
        this.userService = userService;
        this.gameService = gameService;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public String addItem(String[] args) {
        loggedUser = userService.getLoggedUser();


        if (loggedUser == null) {
            return NOT_LOGGED_MESSAGE;
        }

        final String gameTitle = args[1];

        final Set<Game> cart = loggedUser.getGames();

        final Optional<Game> game = gameService.findByTitle(gameTitle);

        if (game.isEmpty()) {
            return NO_GAME_TITLE_MESSAGE;
        }

//        if (doesGameExist(loggedUser, game.get())) {
//            return GAME_EXISTS_INTO_CART;
//        }

        if (cart.contains(game.get())) {
            return GAME_EXISTS_INTO_CART;
        }

        cart.add(game.get());

        userRepository.save(loggedUser);

        return gameTitle + " added to cart";
    }

    @Override
    public String removeItem(String[] args) {
        loggedUser = userService.getLoggedUser();

        if (loggedUser == null) {
            return NOT_LOGGED_MESSAGE;
        }

        final String gameTitle = args[1];

        final Optional<Game> game = gameService.findByTitle(gameTitle);

        final Set<Game> cart = loggedUser.getGames();

        if (game.isEmpty()) {
            return NO_GAME_TITLE_MESSAGE;
        }

//        if (!doesGameExist(loggedUser, game.get())) {
//            return GAME_DOESNT_EXIST_INTO_CART;
//        }

        if (!cart.contains(game.get())) {
            return GAME_DOESNT_EXIST_INTO_CART;
        }

        cart.removeIf(g -> g.getTitle().equals(game.get().getTitle()));

        userRepository.save(loggedUser);

        return gameTitle + " removed from cart";
    }

    @Override
    public String buyItem() {
        loggedUser = userService.getLoggedUser();

        if (loggedUser == null) {
            return NOT_LOGGED_MESSAGE;
        }

        final Set<Game> cart = loggedUser.getGames();

        if (cart.isEmpty()) {
            return CART_IS_EMPTY;
        }

        final Order order = new Order(loggedUser, cart);

        if (loggedUser.getOrders().contains(order)) {
            return ORDER_EXISTS;
        }

        final StringBuilder result = new StringBuilder("Successfully bought games: \n");

        order.getGames().forEach(game -> result
                .append(" -")
                .append(game.getTitle())
                .append(System.lineSeparator()));

        orderRepository.save(order);

        cart.clear();

        userRepository.save(loggedUser);

        return result.toString().trim();
    }

    private static boolean doesGameExist(User user, Game other) {
        return !user.getGames().stream()
                .filter(game -> game.getTitle().equals(other.getTitle()))
                .toList()
                .isEmpty();
    }
}
