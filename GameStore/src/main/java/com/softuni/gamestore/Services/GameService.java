package com.softuni.gamestore.Services;

public interface GameService {
    String addGame(String[] args);

    String editGame(String[] args);

    String deleteGame(String[] args);

    String viewAllGames();

    String viewGameDetails(String[] args);

    String viewOwnedGames();
}
