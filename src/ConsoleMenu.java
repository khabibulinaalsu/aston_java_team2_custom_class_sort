package aston_java_team2_custom_class_sort.src;

import java.util.Comparator;
import java.util.Scanner;

public class ConsoleMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SortStrategy<Car> strategy = new BubbleSortStrategy<>();
        String command;


        while (true) {
            System.out.println("Выберите действие('1' - ввод данных, '2' - сортировка, '3' - выход).");
            command = scanner.nextLine();

            switch (command) {
                case "1":
                    System.out.println("Выберите способ заполнения данных('1' - вручную, '2' - из файла, '3' - рандом.");
                case "2":
                    System.out.println("Сортировать по...('1' - brand, '2' - enginePower, '3' - year).");

                    command = scanner.nextLine();

                    Comparator<Car> comparator = switch (command) {
                        case "1" -> Comparator.comparing(Car::getBrand);
                        case "2" -> Comparator.comparing(Car::getEnginePower);
                        case "3" -> Comparator.comparing(Car::getYear);
                        default -> throw new IllegalStateException("Unexpected value: " + command);
                    };

                    strategy.sort(Car.carList, comparator); //Запускает сортировку. У меня carList находится в классе Car
                    Car.carList.forEach(System.out::println);   //Проверка сортировщика

                case "3":
                    System.out.println("Выход...");
                    return;

            }
        }
    }
}