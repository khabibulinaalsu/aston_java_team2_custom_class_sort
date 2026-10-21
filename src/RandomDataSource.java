import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        List<Car> cars = new ArrayList<>(size);

        for (int i = 0; i < size; ++i) {
            String brand = BRANDS[random.nextInt(BRANDS.length)];
            int enginePower = random.nextInt(431) + 70; // 70-500
            int year = random.nextInt(142) + 1885; // 1885-2026

            Car car = Car.builder()
                .brand(brand)
                .enginePower(enginePower)
                .year(year)
                .build();
            cars.add(car);
        }
        return cars;
    }
}
