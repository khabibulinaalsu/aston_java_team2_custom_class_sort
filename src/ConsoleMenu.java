
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
                    System.out.println("Выберите способ заполнения данных('1' - вручную, '2' - из файла, '3' - рандом.");
                    break;
                    
                case "2":
                    System.out.println("Сортировать по...('1' - brand, '2' - enginePower, '3' - year).");

                    command = scanner.nextLine();

                    Comparator<Car> comparator = switch (command) {
                        case "1" -> Comparator.comparing(Car::getBrand);
                        case "2" -> Comparator.comparing(Car::getEnginePower);
                        case "3" -> Comparator.comparing(Car::getYear);
                        default -> throw new IllegalStateException("Unexpected value: " + command);
                    };

                    strategy.sort(carList, comparator); //Запускает сортировку. У меня carList находился в классе Car
                    carList.forEach(System.out::println);   //Проверка сортировщика
                    break;

                case "3":
                    System.out.println("Выход...");
                    return;

            }
        }
    }
}