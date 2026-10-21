import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomDataSource implements DataSource<Car> {
    private static final String[] BRANDS = {
        "Toyota", "BMW", "Лада", "Mercedes", "Honda", "Audi", "Chevrolet"
    };

    private final int size;
    private final Random random = new Random();
    
    public RandomDataSource(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Размер списка не может быть отрицательным");
        }

        this.size = size;
    }

    @Override 
    public List<Car> load() {
        return Stream
            .generate(this::newCar)
            .limit(size)
            .collect(Collectors.toCollection(SimpleArrayList::new));
    }

    private Car newCar() {
        String brand = BRANDS[random.nextInt(BRANDS.length)];
        int enginePower = random.nextInt(431) + 70;
        int year = random.nextInt(142) + 1885;

        return Car.builder()
            .brand(brand)
            .enginePower(enginePower)
            .year(year)
            .build();
    }
}
