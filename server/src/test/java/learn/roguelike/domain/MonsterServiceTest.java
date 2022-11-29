package learn.roguelike.domain;

import learn.roguelike.data.MonsterRepository;
import learn.roguelike.data.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MonsterServiceTest {

    @Autowired
    MonsterService service;

    @MockBean
    MonsterRepository repository;

    @Test
    void shouldFindMonster(){

    }
}
