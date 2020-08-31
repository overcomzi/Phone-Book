package sort;

import user.Phone;

import java.util.List;

public class BubbleSort extends SortAlgorithm {
    @Override
    public boolean sort(List<Phone> source, long duration) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < source.size() - 1; i++) {
            boolean isOk = true;
            long timeTaken = System.currentTimeMillis() - startTime;
            if (timeTaken > duration * 10) {
                return false;
            }
            for (int k = 0; k < source.size() - i - 1; k++) {
                Phone curPhone = source.get(k);
                Phone nextPhone = source.get(k + 1);
                if (curPhone.getFullName().compareToIgnoreCase(
                        nextPhone.getFullName()
                ) >= 1) {
                    isOk = false;
                    source.set(k, nextPhone);
                    source.set(k + 1, curPhone);
                }
            }
            if (isOk) {
                break;
            }
        }

        return true;
    }
}
