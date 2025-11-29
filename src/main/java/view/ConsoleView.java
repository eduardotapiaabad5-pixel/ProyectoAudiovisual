
package view;

import controller.ContentController;
import model.*;
import repository.FileContentRepository;
import service.ContentService;

import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class ConsoleView {

    private final Scanner scanner = new Scanner(System.in);
    private final ContentController controller;

    public ConsoleView() {
        this.controller = new ContentController(new ContentService(
                new FileContentRepository(Paths.get("src/main/resources/contents.csv"))
        ));
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("=== Sistema Contenido Audiovisual ===");
            System.out.println("1. Listar contenidos");
            System.out.println("2. Agregar contenido (manual)");
            System.out.println("3. Guardar y salir");
            System.out.println("4. Eliminar contenido");  // NUEVA OPCIÓN
            System.out.println("0. Salir sin guardar");
            System.out.print("Ingrese opción: ");
            String opt = scanner.nextLine();

            switch (opt) {
                case "1":
                    listar();
                    break;
                case "2":
                    agregarManual();
                    break;
                case "3":
                    System.out.println("Guardando y saliendo...");
                    running = false;
                    break;
                case "4":            // NUEVO CASE
                    eliminarContenido();
                    break;
                case "0":
                    System.out.println("Saliendo sin guardar...");
                    running = false;
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        }
        scanner.close();
    }

    private void listar() {
        List<Contenido> contenidos = controller.listar();
        if (contenidos.isEmpty()) {
            System.out.println("No hay contenidos registrados.");
            return;
        }
        int i = 1;
        for (Contenido c : contenidos) {
            System.out.printf("%d. %s - %s%n", i++, c.getTitulo(), c.getClass().getSimpleName());
        }
    }

    private void agregarManual() {
        System.out.print("Tipo (Pelicula/Serie/Documental): ");
        String tipo = scanner.nextLine().trim();
        System.out.print("Título: ");
        String titulo = scanner.nextLine().trim();
        System.out.print("Año: ");
        int anio = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Director: ");
        String director = scanner.nextLine().trim();

        Contenido c;

        switch (tipo.toLowerCase()) {
            case "pelicula":
                c = new Pelicula(titulo, anio, director);
                break;
            case "serie":
                c = new Serie(titulo, anio, director);
                break;
            case "documental":
                c = new Documental(titulo, anio, director);
                break;
            default:
                System.out.println("Tipo no reconocido. Se registrará como Documento genérico.");
                c = new Documental(titulo, anio, director);
                break;
        }

        controller.agregar(c);
        System.out.println("Contenido agregado correctamente.");
    }

    // MÉTODO NUEVO – ELIMINAR CONTENIDO
    private void eliminarContenido() {
        List<Contenido> contenidos = controller.listar();
        if (contenidos.isEmpty()) {
            System.out.println("No hay contenidos para eliminar.");
            return;
        }

        System.out.println("Seleccione el número a eliminar:");
        for (int i = 0; i < contenidos.size(); i++) {
            System.out.printf("%d. %s (%s)%n",
                    i + 1, contenidos.get(i).getTitulo(), contenidos.get(i).getClass().getSimpleName());
        }

        try {
            System.out.print("Ingrese número: ");
            int index = Integer.parseInt(scanner.nextLine()) - 1;

            if (index < 0 || index >= contenidos.size()) {
                System.out.println("Número inválido.");
                return;
            }

            contenidos.remove(index);
            controller.actualizarLista(contenidos);
            System.out.println("Contenido eliminado.");

        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }
    }
}
