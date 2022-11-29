package learn.roguelike.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Tile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tileId;
    @NotBlank(message="tile must have type")
    private String type;
    @NotNull
    private int x;
    @NotNull
    private int y;
//    private boolean hasEntity;
    @Column(name="map_id")
    private int mapId;

}
