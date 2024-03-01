package com.softuni.gamestore.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "games")
public class Game extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(name = "trailer_id")
    private String trailerId;

    @Column(name = "image_thumbnail")
    private String imageThumbnail;

    @Basic
    private float size;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String description;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game game)) return false;
        return Float.compare(getSize(), game.getSize()) == 0 && Objects.equals(getTitle(), game.getTitle()) && Objects.equals(getTrailerId(), game.getTrailerId()) && Objects.equals(getImageThumbnail(), game.getImageThumbnail()) && Objects.equals(getPrice(), game.getPrice()) && Objects.equals(getDescription(), game.getDescription()) && Objects.equals(getReleaseDate(), game.getReleaseDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getTrailerId(), getImageThumbnail(), getSize(), getPrice(), getDescription(), getReleaseDate());
    }
}
