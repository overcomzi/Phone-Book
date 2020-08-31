package sort;

import user.Phone;

import java.util.List;

public class SortContext {
    private SortAlgorithm sorted;

    public void setSort(SortAlgorithm sorted) {
        this.sorted = sorted;
    }

    public void setSort(SortType type) {
        switch (type) {
            case QuickSort:
                sorted = new QuickSort();
                break;
            case BubbleSort:
                sorted = new BubbleSort();
                break;
            default:
                throw new IllegalArgumentException("В параметрах указан неправильный тип сортировки");
        }
    }

    public boolean startSort(List<Phone> source, long duration) {
        return sorted.sort(source, duration);
    }

}
