package com.softuni.gamestore.Services;

import com.softuni.gamestore.constants.Validations;
import com.softuni.gamestore.dtos.UserLoginDto;
import com.softuni.gamestore.dtos.UserRegisterDto;
import com.softuni.gamestore.entities.User;
import com.softuni.gamestore.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.softuni.gamestore.constants.Validations.*;

@Service
public class UserServiceImpl implements UserService {
    private User loggedUser;

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String registerUser(String[] args) {
        final UserRegisterDto userRegisterDto;

        final String email = args[1];
        final String password = args[2];
        final String confirmPassword = args[3];
        final String fullName = args[4];

        try {
            userRegisterDto = new UserRegisterDto(email, password, confirmPassword, fullName);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

        final User user = modelMapper.map(userRegisterDto, User.class);

        if (userRepository.count() == 0) {
            user.setAdmin(true);
        }

        if (userRepository.existsUserByEmail(userRegisterDto.getEmail())) {
            return EMAIL_ALREADY_EXISTS_MESSAGE;
        }

        userRepository.save(user);

        return userRegisterDto.successfulRegisterFormat();
    }

    @Override
    public String loginUser(String[] args) {
        final String email = args[1];
        final String password = args[2];

        UserLoginDto userLoginDto = new UserLoginDto(email, password);

        Optional<User> existingUser = userRepository.findByEmail(email);

        if (this.loggedUser == null
                && existingUser.isPresent()
                && existingUser.get().getPassword().equals(userLoginDto.getPassword())) {

            this.loggedUser = existingUser.get();

            return "Successfully logged in " + this.loggedUser.getFullName();
        }

        return USERNAME_OR_PASSWORD_NOT_VALID_MESSAGE;
    }

    @Override
    public String logoutUser() {
        if (this.loggedUser == null) {
            return NOT_LOGGED_USER;
        }

        final String output = "User " + this.loggedUser.getFullName() + " successfully logged out";

        this.loggedUser = null;

        return output;
    }

    @Override
    public User getLoggedUser() {
        return this.loggedUser;
    }

    @Override
    public String viewOwnedGames() {
        if (loggedUser == null) {
            return NOT_LOGGED_MESSAGE;
        }

        StringBuilder result = new StringBuilder();

        loggedUser.getOrders()
                .forEach(order -> order.getGames()
                        .forEach(game -> result.append(game.getTitle()).append(System.lineSeparator())));

        return result.toString().trim();
    }
}
