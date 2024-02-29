package com.softuni.gamestore.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameTitleAndPriceDto {
    private String title;
    private BigDecimal price;

    @Override
    public String toString() {
        return String.format("%s %.2f", title, price);
    }
}
