
package controller;

import model.Pelicula;
import org.junit.jupiter.api.*;
import repository.ContentRepository;
import service.ContentService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRepository implements ContentRepository {
    private final List list = new ArrayList();
    @Override public List findAll(){ return new ArrayList(list); }
    @Override public void saveAll(List contenidos){ list.clear(); list.addAll(contenidos); }
}

public class ContentControllerTest {
    ContentController controller;
    InMemoryRepository repo;

    @BeforeEach
    void setup() {
        repo = new InMemoryRepository();
        controller = new ContentController(new ContentService(repo));
    }

    @Test
    void testAgregarYListar() {
        controller.agregar(new Pelicula("PeliTest", 2000, "Dir"));
        List lista = controller.listar();
        assertEquals(1, lista.size());
        assertTrue(lista.get(0).toString().contains("PeliTest"));
    }
}
