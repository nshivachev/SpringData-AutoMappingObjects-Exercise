package com.softuni.gamestore.Services;

import com.softuni.gamestore.dtos.GameDetailsDto;
import com.softuni.gamestore.dtos.GameDto;
import com.softuni.gamestore.dtos.GameTitleAndPriceDto;
import com.softuni.gamestore.entities.Game;
import com.softuni.gamestore.repositories.GameRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.softuni.gamestore.constants.Validations.*;

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
    public Optional<Game> findByTitle(String title) {
        return gameRepository.findByTitle(title);
    }

    @Override
    public String addGame(String[] args) {
        if (userService.getLoggedUser() == null || !userService.getLoggedUser().isAdmin()) {
            return GAME_OPERATIONS_MISSING_RIGHTS_MESSAGE;
        }

        String title = args[1];
        BigDecimal price = new BigDecimal(args[2]);
        float size = Float.parseFloat(args[3]);
        String trailer = args[4];
        String thumbnailURL = args[5];
        String description = args[6];
        LocalDate releaseDate = parseDate(args[7]);

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
    public String editGame(String[] args) {
        if (userService.getLoggedUser() == null || !userService.getLoggedUser().isAdmin()) {
            return GAME_OPERATIONS_MISSING_RIGHTS_MESSAGE;
        }

        final long id = Long.parseLong(args[1]);

        if (!gameRepository.existsGameById(id)) {
            return NO_GAME_ID_MESSAGE;
        }

        Game game = gameRepository.findById(id).get();

        final GameDto gameDto = modelMapper.map(game, GameDto.class);

        final Map<String, String> values = new HashMap<>();

        for (int i = 2; i < args.length; i++) {
            String key = args[i].split("=")[0];
            String value = args[i].split("=")[1];

            values.put(key, value);
        }

        try {
            values.forEach((key, value) -> {
                switch (key) {
                    case "title" -> gameDto.setTitle(value);
//                        game.setTitle(gameDto.getTitle());
                    case "price" -> gameDto.setPrice(new BigDecimal(value));
//                        game.setPrice(gameDto.getPrice());
                    case "size" -> gameDto.setSize(Float.parseFloat(value));
//                        game.setSize(gameDto.getSize());
                    case "trailer" -> gameDto.setTrailerId(value);
//                        game.setTrailerId(gameDto.getTrailerId());
                    case "thumbnailURL" -> gameDto.setImageThumbnail(value);
//                        game.setImageThumbnail(gameDto.getImageThumbnail());
                    case "description" -> gameDto.setDescription(value);
//                        game.setDescription(gameDto.getDescription());
                    case "releaseDate" -> gameDto.setReleaseDate(parseDate(value));
//                        game.setReleaseDate(gameDto.getReleaseDate());
                }
            });
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }

        gameRepository.updateAllFields(
                id,
                gameDto.getTitle(),
                gameDto.getPrice(),
                gameDto.getSize(),
                gameDto.getTrailerId(),
                gameDto.getImageThumbnail(),
                gameDto.getDescription(),
                gameDto.getReleaseDate()
        );

//        gameRepository.save(game);

        return gameDto.successfulEditFormat();
    }

    @Override
    @Transactional
    public String deleteGame(String[] args) {
        if (userService.getLoggedUser() == null || !userService.getLoggedUser().isAdmin()) {
            return GAME_OPERATIONS_MISSING_RIGHTS_MESSAGE;
        }

        final long id = Long.parseLong(args[1]);

        if (!gameRepository.existsGameById(id)) {
            return NO_GAME_ID_MESSAGE;
        }

        final String output = "Deleted " + gameRepository.findById(id).get().getTitle();

        gameRepository.deleteGameById(id);

        return output;
    }

    @Override
    public String viewAllGames() {
        if (gameRepository.count() == 0) {
            return EMPTY_STORE_MESSAGE;
        }

        final StringBuilder result = new StringBuilder();

        gameRepository.findAll().stream()
                .map(game -> modelMapper.map(game, GameTitleAndPriceDto.class))
                .forEach(gameDto -> result.append(gameDto.toString()).append(System.lineSeparator()));

        return result.toString().trim();
    }

    @Override
    public String viewGameDetails(String[] args) {
        if (gameRepository.count() == 0) {
            return EMPTY_STORE_MESSAGE;
        }

        final Optional<Game> game = findByTitle(args[1]);

        if (game.isEmpty()) {
            return NO_GAME_TITLE_MESSAGE;
        }

        return modelMapper.map(game, GameDetailsDto.class).toString();
    }

    @Override
    public String viewOwnedGames() {
        return null;
    }

    private static LocalDate parseDate(String date) {
        int[] dateTokens = Arrays.stream(date.split("-")).mapToInt(Integer::parseInt).toArray();
        return LocalDate.of(dateTokens[2], dateTokens[1], dateTokens[0]);
    }
}
