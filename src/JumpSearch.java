import java.util.ArrayList;
import java.util.List;

public class JumpSearch extends SearchAlgorithm {
    private List<Phone> phones;

    public JumpSearch(List<Phone> phones) {
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
        int blockSize = (int) Math.sqrt(phones.size());
        int right = 0, left = 0;

        while (right < phones.size() - 1) {
            right = Math.min(phones.size(), left + blockSize) - 1;
            Phone rightPhone = phones.get(right);
            if (rightPhone.getFullName().compareToIgnoreCase(target) >= 0) {
                return backwardLinearSearch(target, left, right);
            }
            left = right + 1;
        }
        return null;
    }

    public Phone backwardLinearSearch(String target, int left, int right) {
        for (int i = right; i >= left; i--) {
            if (phones.get(i).getFullName().equalsIgnoreCase(target)) {
                return phones.get(i);
            }
        }
        return null;
    }
}
