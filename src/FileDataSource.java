import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileDataSource implements DataSource<Car> {
    private final Path path;
    private final Parser<Car> parser;

    public FileDataSource(Path path, Parser<Car> parser) {
        this.path = Objects.requireNonNull(path);
        this.parser = Objects.requireNonNull(parser);
    }

    @Override 
    public List<Car> load() {
        try {
            return Files.readAllLines(path)
                .stream()
                .filter(line -> !line.isBlank())
                .map(parser::parse)
                // ИСПОЛЬЗУЕМ ArrayList вместо toList()
                .collect(Collectors.toCollection(ArrayList::new)); 
        } catch(IOException e) {
            throw new RuntimeException("Не удалось прочитать файл: " + path, e);
        }
    }
}