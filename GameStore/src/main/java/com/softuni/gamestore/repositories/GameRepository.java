package com.softuni.gamestore.repositories;

import com.softuni.gamestore.dtos.GameDetailsDto;
import com.softuni.gamestore.dtos.GameTitleAndPriceDto;
import com.softuni.gamestore.entities.Game;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    boolean existsGameById(Long id);

    Optional<Game> findById(Long id);

    Optional<Game> findByTitle(String title);

    @Query("select new com.softuni.gamestore.dtos.GameDetailsDto(g.title, g.price, g.description, g.releaseDate) " +
            "from Game g " +
            "where g.title = :title")
    Optional<GameDetailsDto> getGameDetailsByTitle(String title);

    @Query("select new com.softuni.gamestore.dtos.GameTitleAndPriceDto(g.title, g.price) from Game g")
    List<GameTitleAndPriceDto> getAllGamesTitleAndPrice();

    void deleteGameById(Long id);

    @Modifying
    @Transactional
    @Query("update Game g " +
            "set g.title = :title, g.price = :price, g.size = :size, g.trailerId = :trailerId, g.imageThumbnail = :imageThumbnail, g.description = :description, g.releaseDate = :releaseDate " +
            "where g.id = :id")
    void updateAllFields(
            Long id,
            String title,
            BigDecimal price,
            float size,
            String trailerId,
            String imageThumbnail,
            String description,
            LocalDate releaseDate);
}
