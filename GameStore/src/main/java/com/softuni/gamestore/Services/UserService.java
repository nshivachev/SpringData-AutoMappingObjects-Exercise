package com.softuni.gamestore.Services;

public interface UserService {
    String registerUser(String[] args);

    String loginUser(String[] args);

    String logoutUser();
}
