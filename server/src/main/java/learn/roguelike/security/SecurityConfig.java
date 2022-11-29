package learn.roguelike.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@ConditionalOnWebApplication
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConverter converter;

    public SecurityConfig(JwtConverter converter) {
        this.converter = converter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.cors();

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/health_check").permitAll()
                .antMatchers(HttpMethod.POST, "/authenticate", "/encode", "/user/create").permitAll()
                .antMatchers(HttpMethod.POST, "/refresh_token").authenticated()
                .antMatchers(HttpMethod.GET, "/api/hc").permitAll()
                .antMatchers(HttpMethod.GET, "/api/player/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/player/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/player/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/game/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/game/player/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/game/player/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/game").permitAll()
                .antMatchers(HttpMethod.POST, "/api/game/create").permitAll()
                .antMatchers(HttpMethod.POST, "/api/game/create/*").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/game/save/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/hero/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/hero").permitAll()
                .antMatchers(HttpMethod.GET, "/api/monster/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/monster/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/monster/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/tile/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/tile").permitAll()
                .antMatchers(HttpMethod.GET, "/api/tile/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/tile/edit/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/map/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/map/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/map").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/map/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/*").permitAll()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/player/create/*").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/**").authenticated()
                .antMatchers(HttpMethod.PUT, "/user/password").authenticated()
                .antMatchers(HttpMethod.POST, "/api").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/*").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/*").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/user/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/user/update").hasAuthority("ADMIN")
                .antMatchers("/**").denyAll()
                .and()
                .addFilter(new JwtRequestFilter(authenticationManager(), converter))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
