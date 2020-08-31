import java.util.List;

public class QuickSort extends SortAlgorithm {
    long duration;
    long startTime;
    boolean isOk;
    boolean isWithoutDuration;

    @Override
    public boolean sort(List<Phone> source, long duration) {
        isWithoutDuration = duration > -1 ? false : true;
        this.duration = duration;
        this.startTime = System.currentTimeMillis();
        quickSort(source, 0, source.size() - 1);
        return isOk;
    }

    public void quickSort(List<Phone> phones, int left, int right) {
        if (!isWithoutDuration) {
            if ((System.currentTimeMillis() - startTime) > duration * 10) {
                isOk = false;
            }
            if (!isOk) {
                return;
            }
        }
        if (left < right) {
            int pivot = partition(phones, left, right);
            quickSort(phones, left, pivot - 1);
            quickSort(phones, pivot + 1, right);
        }
    }

    public int partition(List<Phone> phones, int left, int right) {
        int partitionIdx = left;
        String pivotName = phones.get(right).getFullName();
        for (int i = left; i <= right; i++) {
            Phone curPhone = phones.get(i);
            if (curPhone.getFullName().compareTo(pivotName) <= -1) {
                swap(phones, i, partitionIdx);
                partitionIdx++;
            }
        }
        swap(phones, partitionIdx, right);
        return partitionIdx;
    }

    public void swap(List<Phone> phones, int first, int second) {
        Phone temp = phones.get(first);
        phones.set(first, phones.get(second));
        phones.set(second, temp);
    }
}
