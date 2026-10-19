package aston_java_team2_custom_class_sort.src;

import java.util.Comparator;
import java.util.Scanner;

public class ConsoleMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.println("Выберите действие('1' - ввод данных, '2' - сортировка, '3' - выход).");
            command = scanner.nextLine();

            switch (command) {
                case "1":
                    System.out.println("Выберите способ заполнения данных('1' - вручную, '2' - из файла, '3' - рандом.");
                case "2":
                    System.out.println("Сортировать по...('1' - brand, '2' - enginePower, '3' - year).");

                    SortStrategy<Car> strategy = new BubbleSortStrategy<>();
                    command = scanner.nextLine();
                    Comparator<Car> comparator;

                    switch (command) {
                        case "1":
                            comparator = Comparator.comparing(Car::getBrand);
                            break;
                        case "2":
                            comparator = Comparator.comparing(Car::getEnginePower);
                            break;
                        case "3":
                            comparator = Comparator.comparing(Car::getYear);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + command);
                    }

                    strategy.sort(Car.carList, comparator);
                    Car.carList.forEach(System.out::println);   //Проверка сортировщика

                case "3":
                    System.out.println("Выход...");
                    return;

            }
        }
    }
}