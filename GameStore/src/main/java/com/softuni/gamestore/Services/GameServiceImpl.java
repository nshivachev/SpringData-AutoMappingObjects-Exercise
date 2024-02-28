package com.softuni.gamestore.Services;

import com.softuni.gamestore.dtos.GameDto;
import com.softuni.gamestore.entities.Game;
import com.softuni.gamestore.repositories.GameRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static com.softuni.gamestore.constants.Validations.GAME_CREATION_MISSING_RIGHTS;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public GameServiceImpl(GameRepository gameRepository, ModelMapper modelMapper, UserService userService) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public String addGame(String[] args) {
        if (userService.getLoggedUser() == null || !userService.getLoggedUser().isAdmin()) {
            return GAME_CREATION_MISSING_RIGHTS;
        }

        String title = args[1];
        BigDecimal price = new BigDecimal(args[2]);
        float size = Float.parseFloat(args[3]);
        String trailer = args[4];
        String thumbnailURL = args[5];
        String description = args[6];
        int[] dateTokens = Arrays.stream(args[7].split("-")).mapToInt(Integer::parseInt).toArray();
        LocalDate releaseDate = LocalDate.of(dateTokens[2], dateTokens[1], dateTokens[0]);

        final GameDto gameDto;

        try {
            gameDto = new GameDto(title, trailer, thumbnailURL, size, price, description, releaseDate);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

        final Game game = modelMapper.map(gameDto, Game.class);

        gameRepository.save(game);

        return gameDto.successfulAddFormat();
    }

    @Override
    public String editGame(String[] arguments) {
        return null;
    }

    @Override
    public String deleteGame(String[] arguments) {
        return null;
    }
}
