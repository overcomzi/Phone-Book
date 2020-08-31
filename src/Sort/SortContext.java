import java.util.List;

public class SortContext {
    private SortAlgorithm sorted;

    public void setSort(SortAlgorithm sorted) {
        this.sorted = sorted;
    }

    public boolean startSort(List<Phone> source, long duration) {
        return sorted.sort(source, duration);
    }

}
