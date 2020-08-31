package search;

import user.Phone;

import java.util.ArrayList;
import java.util.List;

public class BinarySearch extends SearchAlgorithm {
    private List<Phone> phones;

    public BinarySearch(List<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public List<Phone> search(List<String> targets) {
        List<Phone> found = new ArrayList<>();
        for (String curTarget : targets) {
            Phone foundPhone = findOne(curTarget);
            if (foundPhone != null) {
                found.add(foundPhone);
            }
        }
        return found;
    }

    public Phone findOne(String target) {
        //TODO: Проверить - отсортированы ли phones
        int left = 0, right = phones.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            Phone curPhone = phones.get(mid);
            if (curPhone.getFullName().equalsIgnoreCase(target)) {
                return curPhone;
            }
            if (curPhone.getFullName().compareTo(target) >= 1) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return null;
    }
}
