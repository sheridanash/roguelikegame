package learn.roguelike.domain;

import learn.roguelike.data.HeroRepository;
import learn.roguelike.data.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class HeroServiceTest {

    @Autowired
    HeroService service;

    @MockBean
    HeroRepository repository;


}
