import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DataSourceTest {
    public static void main(String[] args) {
        testRandomDataSource();
        testFileDataSource();
        TestAssertions.printSummary("DataSourceTest");
    }

    static void testRandomDataSource() {
        int size = 5;
        DataSource<Car> ds = new RandomDataSource(size);
        List<Car> cars = ds.load();
        
        TestAssertions.assertEquals(size, cars.size(), "Рандом: размер списка должен быть " + size);
        for (Car c : cars) {
            TestAssertions.assertTrue(c.getEnginePower() >= 70, "Рандом: мощность >= 70");
            TestAssertions.assertTrue(c.getYear() >= 1885, "Рандом: год >= 1885");
        }
    }

    static void testFileDataSource() {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("test_cars", ".txt");
            List<String> lines = List.of(
                "Car: BMW, 200, 2010",
                "Audi, 150, 2015",        // Проверяем, что парсер понимает оба формата
                "Lada,80,1990"            // Проверяем, что пробелы не важны
            );
            Files.write(tempFile, lines);

            DataSource<Car> ds = new FileDataSource(tempFile, new CarParser());
            List<Car> cars = ds.load();

            TestAssertions.assertEquals(3, cars.size(), "Файл: должно загрузиться 3 машины");
            TestAssertions.assertEquals("BMW", cars.get(0).getBrand(), "Файл: первая машина BMW");
            TestAssertions.assertEquals(150, cars.get(1).getEnginePower(), "Файл: мощность второй машины 150");
            TestAssertions.assertEquals(1990, cars.get(2).getYear(), "Файл: год третьей машины 1990");

            // Проверка на то, что список изменяемый (защита от бага с .toList())
            cars.set(0, cars.get(0)); 
            TestAssertions.assertTrue(true, "Список из файла поддерживает изменение (сортировку)");

        } catch (IOException e) {
            TestAssertions.assertTrue(false, "Ошибка при создании временного файла: " + e.getMessage());
        } finally {
            if (tempFile != null) {
                try { Files.delete(tempFile); } catch (IOException ignored) {}
            }
        }
    }
}