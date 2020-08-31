package search;

import user.Phone;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class HashSearch extends SearchAlgorithm {
    private Hashtable<String, Phone> phoneHash;

    public HashSearch(List<Phone> phones) {
        phoneHash = new Hashtable<>(phones.size());
        for (Phone phone : phones) {
            phoneHash.put(phone.getFullName(), phone);
        }
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
        return phoneHash.get(target);
    }
}
