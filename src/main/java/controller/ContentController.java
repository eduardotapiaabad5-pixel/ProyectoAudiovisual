package controller;

import model.Contenido;
import service.ContentService;

import java.util.List;

public class ContentController {

    private final ContentService service;

    public ContentController(ContentService service) {
        this.service = service;
    }

    // Devuelve la lista actual de contenidos
    public List<Contenido> listar() {
        return service.listarTodos();
    }

    // Agrega un contenido y guarda la lista actualizada
    public void agregar(Contenido c) {
        List<Contenido> actuales = service.listarTodos();
        actuales.add(c);
        service.guardarTodos(actuales);
    }

    // Actualiza la lista completa (usado por la opción eliminar/editar)
    public void actualizarLista(List<Contenido> nuevaLista) {
        service.guardarTodos(nuevaLista);
    }
}
