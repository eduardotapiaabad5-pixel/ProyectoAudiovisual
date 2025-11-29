
package repository;

import model.Contenido;
import java.util.List;

public interface ContentRepository {
    List<Contenido> findAll();
    void saveAll(List<Contenido> contenidos);
}
