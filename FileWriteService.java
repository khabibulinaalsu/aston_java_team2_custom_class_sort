
package aston_java_team2_custom_class_sort;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Objects;

public class FileWriteService {

    private final Path filePath;

    public FileWriterService(String fileName) {
        Objects.requireNonNull(fileName, "Имя файла не должно быть null");

        if (fileName.isBlank()) {
            throw new IllegalArgumentException("Имя файла не должно быть пустым");
        }

        this.filePath = Path.of(fileName);
    }

    public void writeCars(Collection<Car> cars) throws IOException {
        Objects.requireNonNull(cars, "Коллекция автомобилей не должна быть null");

        try (BufferedWriter writer = Files.newBufferedWriter(
                filePath,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.APPEND)) {

            for (Car car : cars) {
                Objects.requireNonNull(car, "Коллекция не должна содержать null");
                writer.write(car.toString());
                writer.newLine();
            }
        }
    }
}
