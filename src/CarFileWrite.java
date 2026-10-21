


import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Objects;

public class CarFileWrite {

    public static void appendCars(
            Collection<Car> cars, String fileName) throws IOException {

        Objects.requireNonNull(cars, "Коллекция автомобилей не должна быть null");
        Objects.requireNonNull(fileName, "Имя файла не должно быть null");

        Path path = Path.of(fileName);

        try (BufferedWriter writer = Files.newBufferedWriter(
                path,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.APPEND)) {

            for (Car car : cars) {
                Objects.requireNonNull(car, "Автомобиль не должен быть null");
                writer.write(car.toString());
                writer.newLine();
            }
        }
    }
}
