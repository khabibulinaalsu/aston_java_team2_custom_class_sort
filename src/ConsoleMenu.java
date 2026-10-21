import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class ConsoleMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SortStrategy<Car> strategy = new BubbleSortStrategy<>();
        String command;
        List<Car> carList = new ArrayList<>();


        while (true) {
            System.out.println("Выберите действие('1' - ввод данных, '2' - сортировка, '3' - запись, '4' - выход).");
            command = scanner.nextLine();

            switch (command) {
                case "1":
                    System.out.println("Выберите способ заполнения данных('1' - вручную, '2' - из файла, '3' - рандом).");
                    String inputMode = scanner.nextLine();
                    int size;
                    DataSource<Car> dataSource;
                    
                    try {
                        switch (inputMode) {
                            case "1":
                                System.out.print("Введите длину массива: ");
                                size = Integer.parseInt(scanner.nextLine());
                                dataSource = new ManualDataSource(scanner, size);
                                break;
                            case "2":
                                System.out.print("Введите путь к файлу: ");
                                dataSource = new FileDataSource(Path.of(scanner.nextLine()), new CarParser());
                                break;
                            case "3":
                                System.out.print("Введите длину массива: ");
                                size = Integer.parseInt(scanner.nextLine());
                                dataSource = new RandomDataSource(size);
                                break;
                            default:
                                throw new IllegalArgumentException("Неверная команда: " + inputMode);
                        }

                        carList = dataSource.load();
                        System.out.println("Загружено элементов: " + carList.size());
                    } catch (Exception e) {
                        System.out.println("Ошибка при заполнении данных: " + e.getMessage());
                    }

                    break;
                    
                case "2":
                    System.out.println("Сортировать по...('1' - brand, '2' - enginePower, '3' - year).");

                    Comparator<Car> comparator = switch (scanner.nextLine()) {
                        case "1" -> Car.BY_BRAND;
                        case "2" -> Car.BY_ENGINE_POWER;
                        case "3" -> Car.BY_YEAR;
                        default -> null;
                    };

                    if (comparator != null) {
                        strategy.sort(carList, comparator);
                        // DEBUG: carList.forEach(System.out::println);
                    } else {
                        System.out.println("Неверный выбор поля.");
                    }
                    break;
                case "3":
                    try {
                        CarFileWrite.appendCars(carList, "sorted-cars.txt");
                        System.out.println("Автомобили добавлены в файл sorted-cars.txt.");
                    } catch (IOException | NullPointerException e) {
                        System.out.println("Ошибка записи в файл: " + e.getMessage());
                    }
                    break;
                case "4":
                    System.out.println("Выход...");
                    scanner.close();
                    return;

                default: 
                    System.out.println("Неверная команда. Попробуйте снова.");
            }
        }
    }
}