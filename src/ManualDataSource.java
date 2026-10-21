import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManualDataSource implements DataSource<Car> {
    private final Scanner scanner;
    private final int size;

    public ManualDataSource(Scanner scanner, int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Размер списка не может быть отрицательным");
        }
        this.scanner = scanner;
        this.size = size;
    }

    @Override 
    public List<Car> load() {
        List<Car> cars = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            System.out.println("\nАвтомобиль " + (i + 1) + " из " + size);
            while (true) {
                try {
                    String brand = readBrand();
                    int enginePower = readInt("Введите мощность двигателя:", 1, Integer.MAX_VALUE);
                    int year = readInt("Введите год выпуска:", 1885, 2026);
                        
                    cars.add(Car.builder()
                        .brand(brand)
                        .enginePower(enginePower)
                        .year(year)
                        .build());
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Ошибка валидации, " + e.getMessage());
                    System.out.println("Попробуйте ввести автомобиль ещё раз");
                }
            }
        }

        return cars;
    }

    private String readBrand() {
        while (true) {
            System.out.println("Введите бренд автомобиля:");
            String brand = scanner.nextLine().trim();

            if (!brand.isEmpty()) {
                return brand;
            }

            System.out.println("Ошибка: бренд не должен быть пустым.");
        }
    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine().trim();

            try {
                int value = Integer.parseInt(input);

                if (value < min || value > max) {
                    System.out.printf(
                        "Ошибка: введите целое число от %d до %d.%n",
                        min, max
                    );
                    continue;
                }

                return value;
            } catch (NumberFormatException e) {
                System.out.println(
                    "Ошибка: необходимо ввести целое число."
                );
            }
        }
    }
}
