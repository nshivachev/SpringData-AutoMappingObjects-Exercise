package com.softuni.gamestore.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.softuni.gamestore.constants.Validations.*;

public class GameDto {
    private String title;
    private String trailerId;
    private String imageThumbnail;
    private float size;
    private BigDecimal price;
    private String description;
    private LocalDate releaseDate;

    public GameDto(String title, String trailerId, String imageThumbnail, float size, BigDecimal price, String description, LocalDate releaseDate) {
        setTitle(title);
        setTrailerId(trailerId);
        setImageThumbnail(imageThumbnail);
        setSize(size);
        setPrice(price);
        setDescription(description);
        setReleaseDate(releaseDate);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title != null
                && (Character.isLowerCase(title.charAt(0))
                || title.length() < 3
                || title.length() > 100)) {
            throw new IllegalArgumentException(GAME_TITLE_NOT_VALID_MESSAGE);
        }
        this.title = title;
    }

    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        if (trailerId != null && trailerId.length() != 11) {
            throw new IllegalArgumentException(GAME_TRAILER_ID_NOT_VALID_MESSAGE);
        }
        this.trailerId = trailerId;
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {
        if (imageThumbnail != null
                && !imageThumbnail.startsWith("http://")
                && !imageThumbnail.startsWith("https://")) {
            throw new IllegalArgumentException(GAME_IMAGE_THUMBNAIL_NOT_VALID_MESSAGE);
        }
        this.imageThumbnail = imageThumbnail;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        if (size <= 0.0) {
            throw new IllegalArgumentException(GAME_SIZE_NOT_VALID_MESSAGE);
        }
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price != null && price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(GAME_PRICE_NOT_VALID_MESSAGE);
        }
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null && description.length() < 20) {
            throw new IllegalArgumentException(GAME_DESCRIPTION_NOT_VALID_MESSAGE);
        }
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String successfulAddFormat() {
        return "Added " + title;
    }
}
