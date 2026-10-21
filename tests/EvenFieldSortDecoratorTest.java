import java.util.ArrayList;
import java.util.List;

public class EvenFieldSortDecoratorTest {
    public static void main(String[] args) {
        testSortEvenEnginePower();
        testSortEvenYear();
        testAllValuesEven();
        testAllValuesOdd();
        testEmptyList();
        testSingleElement();
        testAlreadySorted();
        testReverseOrder();
        testNullList();
        testNullComparator();

        TestAssertions.printSummary("EvenFieldSortDecoratorTest");
    }

    private static Car car(String brand, int power, int year) {
        return Car.builder()
            .brand(brand)
            .enginePower(power)
            .year(year)
            .build();
    }

    private static List<Car> cars(Car ...cars) {
        return new ArrayList<>(List.of(cars));
    }

    private static void testSortEvenEnginePower() {
        List<Car> actual = cars(
            car("A", 300, 2000),
            car("B", 201, 2001),
            car("C", 100, 2002),
            car("D", 301, 2003),
            car("E", 200, 2004)
        );

        List<Car> expected = cars(
            car("C", 100, 2002),
            car("B", 201, 2001),
            car("E", 200, 2004),
            car("D", 301, 2003),
            car("A", 300, 2000)
        );

        SortStrategy<Car> strategy = new EvenFieldSortDecorator<>(new BubbleSortStrategy<>(), Car::getEnginePower);
        strategy.sort(actual, Car.BY_ENGINE_POWER);

        TestAssertions.assertEquals(expected, actual, "Чётная мощность отсортирована, нечётная мощность на прежнем месте");
    }

    private static void testSortEvenYear() {
        List<Car> actual = cars(
            car("A", 100, 2004),
            car("B", 101, 2001),
            car("C", 102, 1998),
            car("D", 103, 2003),
            car("E", 104, 2000)
        );

        List<Car> expected = cars(
            car("C", 102, 1998),
            car("B", 101, 2001),
            car("E", 104, 2000),
            car("D", 103, 2003),
            car("A", 100, 2004)
        );

        SortStrategy<Car> strategy = new EvenFieldSortDecorator<>(new BubbleSortStrategy<>(), Car::getYear);
        strategy.sort(actual, Car.BY_YEAR);

        TestAssertions.assertEquals(expected, actual, "Чётные годы отсортированы, нечётные годы на прежнем месте");
    }

    private static void testAllValuesEven() {
        List<Car> actual = cars(
            car("A", 300, 2000),
            car("B", 100, 2001),
            car("C", 200, 2002)
        );
        
        List<Car> expected = cars(
            car("B", 100, 2001),
            car("C", 200, 2002),
            car("A", 300, 2000)
        );

        SortStrategy<Car> strategy = new EvenFieldSortDecorator<>(new BubbleSortStrategy<>(), Car::getEnginePower);

        strategy.sort(actual, Car.BY_ENGINE_POWER);

        TestAssertions.assertEquals(expected, actual, "При чётных значениях сортируется весь список");
    }

    private static void testAllValuesOdd() {
        List<Car> actual = cars(
            car("A", 301, 2000),
            car("B", 101, 2001),
            car("C", 201, 2002)
        );

        List<Car> expected = new ArrayList<>(actual);

        SortStrategy<Car> strategy = new EvenFieldSortDecorator<>(new BubbleSortStrategy<>(), Car::getEnginePower);

        strategy.sort(actual, Car.BY_ENGINE_POWER);

        TestAssertions.assertEquals(expected, actual, "При нечётных значениях список не изменяется");
    }

    private static void testEmptyList() {
        List<Car> actual = new ArrayList<>();

        SortStrategy<Car> strategy = new EvenFieldSortDecorator<>(new BubbleSortStrategy<>(), Car::getEnginePower);

        strategy.sort(actual, Car.BY_ENGINE_POWER);

        TestAssertions.assertEquals(0, actual.size(), "Пустой список остаётся пустым");
    }

    private static void testSingleElement() {
        List<Car> actual = cars(
            car("A", 301, 2000)
        );

        List<Car> expected = new ArrayList<>(actual);

        SortStrategy<Car> strategy = new EvenFieldSortDecorator<>(new BubbleSortStrategy<>(), Car::getEnginePower);

        strategy.sort(actual, Car.BY_ENGINE_POWER);

        TestAssertions.assertEquals(expected, actual, "Один элемент остаётся без изменений");    
    }

    private static void testAlreadySorted() {
        List<Car> actual = cars(
            car("C", 100, 2002),
            car("B", 201, 2001),
            car("E", 200, 2004),
            car("D", 301, 2003),
            car("A", 300, 2000)
        );

        List<Car> expected = new ArrayList<>(actual);

        SortStrategy<Car> strategy = new EvenFieldSortDecorator<>(new BubbleSortStrategy<>(), Car::getEnginePower);

        strategy.sort(actual, Car.BY_ENGINE_POWER);

        TestAssertions.assertEquals(expected, actual, "Уже отсортированные чётные элементы не меняются");    
    }

    private static void testReverseOrder() {
        List<Car> actual = cars(
            car("A", 300, 2000),
            car("B", 301, 2001),
            car("C", 200, 2002),
            car("D", 303, 2003),
            car("E", 100, 2004)
        );

        List<Car> expected = cars(
            car("E", 100, 2004),
            car("B", 301, 2001),
            car("C", 200, 2002),
            car("D", 303, 2003),
            car("A", 300, 2000)
        );

        SortStrategy<Car> strategy = new EvenFieldSortDecorator<>(new BubbleSortStrategy<>(), Car::getEnginePower);
        strategy.sort(actual, Car.BY_ENGINE_POWER);

        TestAssertions.assertEquals(expected, actual, "Чётные элементы в обратном порядке сортируются по возрастанию");
    }

    private static void testNullList() {
        SortStrategy<Car> strategy = new EvenFieldSortDecorator<>(new BubbleSortStrategy<>(), Car::getEnginePower);

        TestAssertions.assertThrows(NullPointerException.class, 
            () -> strategy.sort(null, Car.BY_ENGINE_POWER), 
            "Null-список отклоняется");
    }

    private static void testNullComparator() {
        SortStrategy<Car> strategy = new EvenFieldSortDecorator<>(new BubbleSortStrategy<>(), Car::getEnginePower);

        TestAssertions.assertThrows(NullPointerException.class, 
            () -> strategy.sort(cars(car("A", 100, 2000)), null), 
            "Null-компаратор отклоняется");
    }
}
