package com.softuni.gamestore.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Game> games;

    public Order() {
        this.games = new HashSet<>();
    }

    public Order(User user, Set<Game> games) {
        this.user = user;
        this.games = games;
    }
}
