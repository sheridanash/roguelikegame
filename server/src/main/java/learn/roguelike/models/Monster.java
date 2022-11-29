package learn.roguelike.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Monster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int monsterId;
    private int hp;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tile_id")
    private Tile tile;

}
