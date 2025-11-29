
package repository;

import model.*;
import util.CSVHelper;
import java.nio.file.*;
import java.util.*;
import java.io.IOException;

public class FileContentRepository implements ContentRepository {
    private final Path path;

    public FileContentRepository(Path path) {
        this.path = path;
    }

    @Override
    public List<Contenido> findAll() {
        List<Contenido> result = new ArrayList<>();
        if (!Files.exists(path)) return result;
        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                Optional<Contenido> c = CSVHelper.parseLine(line);
                c.ifPresent(result::add);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo archivo: " + e.getMessage(), e);
        }
        return result;
    }

    @Override
    public void saveAll(List<Contenido> contenidos) {
        List<String> lines = new ArrayList<>();
        for (Contenido c : contenidos) lines.add(CSVHelper.toCSV(c));
        try {
            Files.write(path, lines);
        } catch (IOException e) {
            throw new RuntimeException("Error escribiendo archivo: " + e.getMessage(), e);
        }
    }
}
