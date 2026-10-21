import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class ConsoleMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SortStrategy<Car> strategy = new BubbleSortStrategy<>();
        String command;
        List<Car> carList = new SimpleArrayList<>();


        while (true) {

            System.out.println("Выберите действие('1' - ввод данных, '2' - сортировка, '3' - особая сортировка, '4' - запись, '5' - подсчёт автомобилей, '6' - выход).");

            command = scanner.nextLine();

            switch (command) {
                case "1":
                    System.out.println("Выберите способ заполнения данных('1' - вручную, '2' - из файла, '3' - рандом).");
                    String inputMode = scanner.nextLine();
                    DataSource<Car> dataSource;
                    int size;
                    
                    try {
                        switch (inputMode) {
                            case "1":
                                System.out.print("Введите длину массива: ");
                                size = Integer.parseInt(scanner.nextLine());
                                dataSource = new ManualDataSource(scanner, size, new CarParser());
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
                    System.out.println("Сортировать чётные элементы по... ('1' - enginePower, '2' - year.)");
                    String field = scanner.nextLine();
                    if (!field.equals("1") && !field.equals("2")) {
                        System.out.println("Неверный выбор поля.");
                    }

                    SortStrategy<Car> specialStrategy = new EvenFieldSortDecorator<>(
                        new BubbleSortStrategy<>(),
                        field.equals("1")
                            ? Car::getEnginePower
                            : Car::getYear
                    );

                    Comparator<Car> specialComparator =
                        field.equals("1")
                            ? Car.BY_ENGINE_POWER
                            : Car.BY_YEAR;

                    specialStrategy.sort(carList, specialComparator);

                    System.out.println("Особая сортировка выполнена.");

                    break;
                case "4":
                    System.out.println("Введите название файла:");
                    String fileName = scanner.nextLine();
                    try {
                        CarFileWrite.appendCars(carList, fileName);
                        System.out.println(carList.size() + " автомобилей добавлено в файл " + fileName);
                    } catch (IOException | NullPointerException e) {
                        System.out.println("Ошибка записи в файл: " + e.getMessage());
                    }
                    break;

                case "4":
                    CarCounter.countOccurrences(carList);
                    break;
                    
                case "5":
                    System.out.println("Выход...");
                    scanner.close();
                    return;


                default: 
                    System.out.println("Неверная команда. Попробуйте снова.");
            }
        }
    }
}