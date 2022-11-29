package learn.roguelike.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import learn.roguelike.models.Player;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtConverter {

    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final String ISSUER = "roguelike";
    private final int EXPIRATION_MINUTES = 15;
    private final int EXPIRATION_MILLIS = EXPIRATION_MINUTES * 60 * 1000;

    public String getTokenFromUser(Player player) {

        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(player.getUsername())
                .claim("id", player.getPlayerId())
                .claim("auth", player.getAuth())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
                .signWith(key)
                .compact();
    }

    public Player getUserFromToken(String token) {

        if (token == null) {
            return null;
        }

        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .requireIssuer(ISSUER)
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            Player player = new Player();

            player.setUsername(jws.getBody().getSubject());
            player.setPlayerId(jws.getBody().get("id", Integer.class));

            String authStr = jws.getBody().get("auth", String.class);
            player.setAuth(authStr);

            return player;

        } catch (JwtException e) {
            System.out.println(e);
        }

        return null;
    }
}
