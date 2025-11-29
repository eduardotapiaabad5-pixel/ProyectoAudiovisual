
package repository;

import model.Pelicula;
import org.junit.jupiter.api.*;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileContentRepositoryTest {
    Path temp;

    @BeforeEach
    void setup() throws Exception {
        temp = Files.createTempFile("testcontents", ".csv");
    }

    @AfterEach
    void cleanup() throws Exception {
        Files.deleteIfExists(temp);
    }

    @Test
    void testSaveAndRead() {
        FileContentRepository repo = new FileContentRepository(temp);
        repo.saveAll(List.of(new Pelicula("P1",1999,"D1")));
        List lst = repo.findAll();
        assertEquals(1, lst.size());
    }
}
