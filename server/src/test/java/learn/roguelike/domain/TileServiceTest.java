package learn.roguelike.domain;


import learn.roguelike.data.TileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TileServiceTest {

    @Autowired
    TileService service;

    @MockBean
    TileRepository repository;
}
