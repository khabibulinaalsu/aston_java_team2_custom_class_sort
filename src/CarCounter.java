import java.util.List;
import java.util.Scanner;

public class CarCounter {

    public static void countOccurrences(List<Car> cars) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Укажите марку искомого автомобиля: ");
        String brand = scanner.nextLine();

        System.out.print("Укажите мощность двигателя искомого автомобиля: ");
        int enginePower = Integer.parseInt(scanner.nextLine());

        System.out.print("Укажите год выпуска искомого автомобиля: ");
        int year = Integer.parseInt(scanner.nextLine());

        int size = cars.size();

        int threadCount = Runtime.getRuntime().availableProcessors();

        if (threadCount > size) {
            threadCount = size;
        }

        Thread[] threads = new Thread[threadCount];
        int[] results = new int[threadCount];

        int partSize = size / threadCount;
        int remainder = size % threadCount;
        int start = 0;

        for (int i = 0; i < threadCount; i++) {
            final int threadIndex = i;
            final int from = start;

            int currentPartSize = partSize;

            if (i < remainder) {
                currentPartSize++;
            }

            final int to = from + currentPartSize;
            start = to;

            threads[i] = new Thread(() -> {
                int count = 0;

                for (int j = from; j < to; j++) {
                    Car car = cars.get(j);

                    if (car != null
                            && car.getBrand().equals(brand)
                            && car.getEnginePower() == enginePower
                            && car.getYear() == year) {
                        count++;
                    }
                }

                results[threadIndex] = count;
            });

            threads[i].start();
        }

        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Подсчёт прерван.");
                return;
            }
        }

        int total = 0;

        for (int i = 0; i < threadCount; i++) {
            total += results[i];
        }

        System.out.println("Количество вхождений: " + total);
    }
}
