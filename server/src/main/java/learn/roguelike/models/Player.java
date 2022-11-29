package learn.roguelike.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int playerId;

    @NotBlank(message="Username is required")
    @Column(unique = true)
    private String username;

    @NotBlank(message="Password is required")
    private String passwordHash;

    private String auth = "USER";

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private List<Game> games = new ArrayList<>();

}
