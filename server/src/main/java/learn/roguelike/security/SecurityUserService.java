package learn.roguelike.security;

import learn.roguelike.data.PlayerRepository;
import learn.roguelike.models.Player;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserService  {

    private final PlayerRepository playerRepository;

    public SecurityUserService(PlayerRepository userRepository) {
        this.playerRepository = userRepository;
    }

    public Player loadUserByUsername(String username) throws UsernameNotFoundException {

        var user = playerRepository.findPlayerByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("username " + username + " not found.");
        }

        return user;
    }
}
