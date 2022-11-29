package learn.roguelike.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Map {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mapId;

    private int x;
    private int y;

    @Column(name="game_id")
    private int gameId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "map_id")
    private List<Tile> tiles = new ArrayList<>();
}
