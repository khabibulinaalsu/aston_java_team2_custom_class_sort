package aston_java_team2_custom_class_sort;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class SortContext<T> {

    private SortStrategy<T> strategy;

    public SortContext(SortStrategy<T> strategy) {
        setStrategy(strategy);
    }

    public void setStrategy(SortStrategy<T> strategy) {
        this.strategy = Objects.requireNonNull(strategy, "Стратегия сортировки не должна быть null");
    }

    public void sort(List<T> list, Comparator<T> comparator) {
        Objects.requireNonNull(list, "Список не должен быть null");
        Objects.requireNonNull(comparator, "Компаратор не должен быть null");

        strategy.sort(list, comparator);
    }
}
