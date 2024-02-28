package com.softuni.gamestore.Services;

import com.softuni.gamestore.entities.User;

public interface UserService {
    String registerUser(String[] args);

    String loginUser(String[] args);

    String logoutUser();

    User getLoggedUser();
}
