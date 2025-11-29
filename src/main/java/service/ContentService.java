package service;

import model.Contenido;
import repository.ContentRepository;

import java.util.List;

public class ContentService {

    private final ContentRepository repository;

    public ContentService(ContentRepository repository) {
        this.repository = repository;
    }

    // Obtiene todos los contenidos desde el repositorio
    public List<Contenido> listarTodos() {
        return repository.findAll();
    }

    // Guarda la lista completa en el repositorio
    public void guardarTodos(List<Contenido> lista) {
        repository.saveAll(lista);
    }
}
