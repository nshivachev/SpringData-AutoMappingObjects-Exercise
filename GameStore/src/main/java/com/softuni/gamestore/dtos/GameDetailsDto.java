package com.softuni.gamestore.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameDetailsDto {
    private String title;
    private BigDecimal price;
    private String description;
    private LocalDate releaseDate;

    @Override
    public String toString() {
        return String.format("Title: %s%nPrice: %.2f%nDescription: %s%nRelease date: %s",
                title, price, description, releaseDate);
    }
}
