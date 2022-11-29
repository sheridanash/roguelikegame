package learn.roguelike.models;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Hero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int heroId;
    private int hp;
    private int lives;
    private boolean air;
    private boolean water;
    private boolean earth;
    private boolean fire;
    @Column(name="`keys`")
    private int keys;
    private int gold;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tile_id")
    private Tile tile;


    @Column(name="game_id")
    private int gameId;

}
