package learn.roguelike.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameId;
    private boolean isBlueprint;

    @NotNull
    private int score;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private List<Map> maps = new ArrayList<>();

    @Transient
    //@OneToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "game_id")
    private Hero hero;

    @Column(name="player_id")
    private int playerId;
}
