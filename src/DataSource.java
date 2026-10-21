import java.util.List;

public interface DataSource<T> {
    List<T> load();
}