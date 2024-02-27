package com.softuni.gamestore.Services;

import com.softuni.gamestore.dtos.UserRegisterDto;
import com.softuni.gamestore.entities.User;
import com.softuni.gamestore.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.softuni.gamestore.constants.Validations.EMAIL_ALREADY_EXISTS_MESSAGE;

@Service
public class UserServiceImpl implements UserService {
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
            userRegisterDto= new UserRegisterDto(args[1], args[2], args[3], args[4]);
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
        return null;
    }

    @Override
    public String logoutUser(String[] args) {
        return null;
    }
}
