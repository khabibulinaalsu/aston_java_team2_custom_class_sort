import java.util.Comparator;
import java.util.List;

public class SortByBrand implements SortStrategy<Car> {

    @Override
    public void sort(List<Car> list, Comparator<Car> comparator) {
        list.sort(Comparator.comparing(Car::getBrand));
    }

}


