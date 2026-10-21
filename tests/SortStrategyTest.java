import java.util.ArrayList;
import java.util.List;

public class SortStrategyTest {
    public static void main(String[] args) {
        testSortByBrand();
        testSortByPower();
        testSortByYear();
        testSortEmpty();
        testSortSingle();
        testSortAlreadySorted();
        testSortContextDelegates();
        testSortContextRejectsNullStrategy();
        TestAssertions.printSummary("SortStrategyTest");
    }

    static List<Car> sample() {
        return new ArrayList<>(List.of(
                Car.builder().brand("C").enginePower(300).year(2001).build(),
                Car.builder().brand("A").enginePower(100).year(2003).build(),
                Car.builder().brand("B").enginePower(200).year(2002).build()
        ));
    }

    static void testSortByBrand() {
        List<Car> cars = sample();
        new BubbleSortStrategy<Car>().sort(cars, Car.BY_BRAND);
        TestAssertions.assertEquals("A", cars.get(0).getBrand(), "brand[0]");
        TestAssertions.assertEquals("B", cars.get(1).getBrand(), "brand[1]");
        TestAssertions.assertEquals("C", cars.get(2).getBrand(), "brand[2]");
    }

    static void testSortByPower() {
        List<Car> cars = sample();
        new BubbleSortStrategy<Car>().sort(cars, Car.BY_ENGINE_POWER);
        TestAssertions.assertEquals(100, cars.get(0).getEnginePower(), "power[0]");
        TestAssertions.assertEquals(200, cars.get(1).getEnginePower(), "power[1]");
        TestAssertions.assertEquals(300, cars.get(2).getEnginePower(), "power[2]");
    }

    static void testSortByYear() {
        List<Car> cars = sample();
        new BubbleSortStrategy<Car>().sort(cars, Car.BY_YEAR);
        TestAssertions.assertEquals(2001, cars.get(0).getYear(), "year[0]");
        TestAssertions.assertEquals(2002, cars.get(1).getYear(), "year[1]");
        TestAssertions.assertEquals(2003, cars.get(2).getYear(), "year[2]");
    }

    static void testSortEmpty() {
        List<Car> cars = new ArrayList<>();
        new BubbleSortStrategy<Car>().sort(cars, Car.BY_BRAND); // не падает
        TestAssertions.assertEquals(0, cars.size(), "пустой список остался пустым");
    }

    static void testSortSingle() {
        List<Car> cars = new ArrayList<>();
        Car only = Car.builder().brand("A").enginePower(1).year(2000).build();
        cars.add(only);
        new BubbleSortStrategy<Car>().sort(cars, Car.BY_BRAND);
        TestAssertions.assertEquals(only, cars.get(0), "один элемент без изменений");
    }

    static void testSortAlreadySorted() {
        List<Car> cars = new ArrayList<>(List.of(
                Car.builder().brand("A").enginePower(100).year(2000).build(),
                Car.builder().brand("B").enginePower(200).year(2001).build()
        ));
        new BubbleSortStrategy<Car>().sort(cars, Car.BY_BRAND);
        TestAssertions.assertEquals("A", cars.get(0).getBrand(), "отсортированный не меняется");
        TestAssertions.assertEquals("B", cars.get(1).getBrand(), "отсортированный не меняется [1]");
    }

    static void testSortContextDelegates() {
        // SortContext должен перепоручить сортировку стратегии
        SortContext<Car> ctx = new SortContext<>(new BubbleSortStrategy<>());
        List<Car> cars = sample();
        ctx.sort(cars, Car.BY_ENGINE_POWER);
        TestAssertions.assertEquals(100, cars.get(0).getEnginePower(), "SortContext: power[0]");
        TestAssertions.assertEquals(300, cars.get(2).getEnginePower(), "SortContext: power[2]");
    }

    static void testSortContextRejectsNullStrategy() {
        TestAssertions.assertThrows(NullPointerException.class,
                () -> new SortContext<Car>(null),
                "SortContext(null) бросает NPE");
    }
}