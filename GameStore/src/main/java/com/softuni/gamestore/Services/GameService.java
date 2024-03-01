package com.softuni.gamestore.Services;

import com.softuni.gamestore.entities.Game;

import java.util.Optional;

public interface GameService {
    Optional<Game> findByTitle(String title);

    String addGame(String[] args);

    String editGame(String[] args);

    String deleteGame(String[] args);

    String viewAllGames();

    String viewGameDetails(String[] args);
}
