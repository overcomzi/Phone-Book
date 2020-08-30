import java.util.ArrayList;
import java.util.List;

public class LinearSearch extends SearchAlgorithm {
    private List<Phone> phones;

    public LinearSearch(List<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public List<Phone> search(List<String> targets) {
        List<Phone> found = new ArrayList<>();
        for(String curTargt: targets) {
            Phone foundPhone = findOne(curTargt);
            if (foundPhone!= null) {
                found.add(foundPhone);
            }
        }
        return found;
    }

    public Phone findOne(String target) {
        for (Phone curPhone: phones) {
            if (curPhone.getFullName().equalsIgnoreCase(target)) {
                return curPhone;
            }
        }
        return null;
    }
}
