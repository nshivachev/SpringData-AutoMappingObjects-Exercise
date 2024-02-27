package com.softuni.gamestore.Services;

import com.softuni.gamestore.dtos.UserLoginDto;
import com.softuni.gamestore.dtos.UserRegisterDto;
import com.softuni.gamestore.entities.User;
import com.softuni.gamestore.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.softuni.gamestore.constants.Validations.EMAIL_ALREADY_EXISTS_MESSAGE;
import static com.softuni.gamestore.constants.Validations.USERNAME_OR_PASSWORD_NOT_VALID_MESSAGE;

@Service
public class UserServiceImpl implements UserService {
    private User user;

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

        try {
            userRegisterDto = new UserRegisterDto(args[1], args[2], args[3], args[4]);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

        final User user = modelMapper.map(userRegisterDto, User.class);

        if (userRepository.count() == 0) {
            user.setIsAdmin(true);
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

        if (this.user == null
                && existingUser.isPresent()
                && existingUser.get().getPassword().equals(userLoginDto.getPassword())) {
            this.user = existingUser.get();
            return "Successfully logged in " + this.user.getFullName();
        }
        return USERNAME_OR_PASSWORD_NOT_VALID_MESSAGE;
    }

    @Override
    public String logoutUser() {
        if (this.user == null) {
            return  "Cannot log out. No user was logged in.";
        }

        final String output = "User " + this.user.getFullName() + " successfully logged out";

        this.user = null;

        return output;
    }
}
