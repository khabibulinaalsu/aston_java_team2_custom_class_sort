import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SortStrategy<Car> strategy = new BubbleSortStrategy<>();
        String command;
        List<Car> carList = new ArrayList<>(List.of(
                    Car.builder()
                        .brand("brandA")
                        .enginePower(300)
                        .year(2001)
                        .build(),

                    Car.builder()
                        .brand("brandB")
                        .enginePower(200)
                        .year(2003)
                        .build(),
                        
                    Car.builder()
                        .brand("brandC")
                        .enginePower(100)
                        .year(2002)
                        .build()
        ));


        while (true) {
            System.out.println("Выберите действие('1' - ввод данных, '2' - сортировка, '3' - выход).");
            command = scanner.nextLine();

            switch (command) {
                case "1":
                    System.out.println("Выберите способ заполнения данных('1' - вручную, '2' - из файла, '3' - рандом).");
                    String inputMode = scanner.nextLine();
                    
                    System.out.print("Введите длину массива: ");
                    int size = Integer.parseInt(scanner.nextLine());
                    
                    DataSource<Car> dataSource = null;
                    if (inputMode.equals("1")) {
                        dataSource = new ManualDataSource(scanner, size);
                    } else if (inputMode.equals("2")) {
                        System.out.print("Введите путь к файлу: ");
                        dataSource = new FileDataSource(Path.of(scanner.nextLine()), new CarParser());
                    } else if (inputMode.equals("3")) {
                        dataSource = new RandomDataSource(size);
                    }
                    
                    if (dataSource != null) {
                        carList = dataSource.load();
                        System.out.println("Загружено элементов: " + carList.size());
                    } else {
                        System.out.println("Неверный способ ввода.");
                    }
                    break;
                    
                case "2":
                    System.out.println("Сортировать по...('1' - brand, '2' - enginePower, '3' - year).");

                    command = scanner.nextLine();

                    //ИЗМЕНЕНО: убрали throw, который ронял программу
                    Comparator<Car> comparator = switch (command) {
                        case "1" -> Car.BY_BRAND;
                        case "2" -> Car.BY_ENGINE_POWER;
                        case "3" -> Car.BY_YEAR;
                        default -> null;
                    };

                    if (comparator != null) {
                        strategy.sort(carList, comparator);
                        carList.forEach(System.out::println);
                    } else {
                        System.out.println("Неверный выбор поля.");
                    }
                    break;

                case "3":
                    System.out.println("Выход...");
                    return;

                default: // на случай, ошибок
                    System.out.println("Неверная команда. Попробуйте снова.");
            }
        }
    }
}