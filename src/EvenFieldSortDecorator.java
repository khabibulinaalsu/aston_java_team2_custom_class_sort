import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.ToIntFunction;

public class EvenFieldSortDecorator<T> implements SortStrategy<T> {
    private final SortStrategy<T> delegate;
    private final ToIntFunction<T> fieldExtractor;
    
    public EvenFieldSortDecorator(SortStrategy<T> delegate, ToIntFunction<T> fieldExtractor) {
        this.delegate = Objects.requireNonNull(delegate);
        this.fieldExtractor = Objects.requireNonNull(fieldExtractor);
    }

    @Override 
    public void sort(List<T> list, Comparator<T> comparator) {
        Objects.requireNonNull(list, "Список не должен быть null");
        Objects.requireNonNull(comparator, "Компаратор не должен быть null");

        List<Integer> evenIndexes = new ArrayList<>();
        List<T> evenElements = new ArrayList<>(); 

        //Сбор чётных элементов и их позиций
        for (int i = 0; i < list.size(); ++i) {
            if (fieldExtractor.applyAsInt(list.get(i)) % 2 == 0) {
                evenIndexes.add(i);
                evenElements.add(list.get(i));
            }
        }

        delegate.sort(evenElements, comparator);

        //Возврат отсортированного на чётные позиции
        for (int i = 0; i < evenIndexes.size(); ++i) {
            list.set(evenIndexes.get(i), evenElements.get(i));
        }
    }
}
