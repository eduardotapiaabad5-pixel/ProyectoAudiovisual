package util;

import model.*;
import java.util.Optional;

public class CSVHelper {

    // Formato CSV simple: tipo,titulo,anio,director
    public static Optional<Contenido> parseLine(String line) {
        if (line == null || line.trim().isEmpty()) return Optional.empty();

        // ⚠ Ignorar la línea de cabecera CSV (tipo,titulo,anio,director)
        if (line.toLowerCase().startsWith("tipo,")) return Optional.empty();

        // Dividir el CSV, incluso si tiene comillas
        String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

        // Limpiar espacios y comillas
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim().replaceAll("^\"|\"$", "");
        }

        // Validación mínima
        if (parts.length < 4) return Optional.empty();

        // Intentar parsear la línea
        try {
            String tipo = parts[0];
            String titulo = parts[1];
            int anio = Integer.parseInt(parts[2]);  // Aquí fallaba antes
            String director = parts[3];

            Contenido contenido;
            switch (tipo.toLowerCase()) {
                case "pelicula":
                    contenido = new Pelicula(titulo, anio, director);
                    break;
                case "serie":
                    contenido = new Serie(titulo, anio, director);
                    break;
                case "documental":
                    contenido = new Documental(titulo, anio, director);
                    break;
                default:
                    contenido = new Documental(titulo, anio, director);
                    break;
            }
            return Optional.of(contenido);

        } catch (NumberFormatException e) {
            System.out.println("⚠ Línea inválida (se ignora): " + line);
            return Optional.empty();
        }
    }

    public static String toCSV(Contenido c) {
        String tipo = c.getClass().getSimpleName();
        return String.join(",", tipo, escape(c.getTitulo()), String.valueOf(c.getAnio()), escape(c.getDirector()));
    }

    private static String escape(String s) {
        if (s == null) return "";
        if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
            return '"' + s.replace("\"", "\"\"") + '"';
        }
        return s;
    }
}
