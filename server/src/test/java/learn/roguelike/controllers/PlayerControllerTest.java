package learn.roguelike.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.roguelike.data.PlayerRepository;
import learn.roguelike.models.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerTest {

    @MockBean
    PlayerRepository repository;

    @Autowired
    MockMvc mvc;


    @Test
    void addShouldReturn400WhenEmpty() throws Exception {

        var request = post("/api/player")
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void addShouldReturn400WhenInvalid() throws Exception {

        ObjectMapper jsonMapper = new ObjectMapper();

        Player player = new Player();
        String playerJson = jsonMapper.writeValueAsString(player);

        var request = post("/api/player")
                .contentType(MediaType.APPLICATION_JSON)
                .content(playerJson);

        mvc.perform(request)
                .andExpect(status().isBadRequest());

    }

}
