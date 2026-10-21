import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ManualDataSource implements DataSource<Car> {
    private final Scanner scanner;
    private final int size;
    private final Parser<Car> parser;

    public ManualDataSource(Scanner scanner, int size, Parser<Car> parser) {
        if (size < 0) {
            throw new IllegalArgumentException("Размер списка не может быть отрицательным");
        }
        this.scanner = scanner;
        this.size = size;
        this.parser = parser;
    }

    @Override 
    public List<Car> load() {
        return Stream
            .generate(this::readValidCar)
            .limit(size)
            .collect(Collectors.toCollection(SimpleArrayList::new));
    }

    private Car readValidCar() {
        while (true) {
            try {
                return parser.parse(scanner.nextLine());
            } catch (ValidationException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
