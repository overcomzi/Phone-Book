package search;

import user.Phone;

import java.util.List;

public class SearchContext {
    private SearchAlgorithm searchAlgorithm;

    public void setSearchAlgorithm(SearchAlgorithm searchAlgorithm) {
        this.searchAlgorithm = searchAlgorithm;
    }
    public void setSearchAlgorithm(SearchType type, List<Phone> src) {
        switch(type) {
            case LinearSearch:
                searchAlgorithm = new LinearSearch(src);
                break;
            case JumpSearch:
                searchAlgorithm = new JumpSearch(src);
                break;
            case BinarySearch:
                searchAlgorithm = new BinarySearch(src);
                break;
            case HashSearch:
                searchAlgorithm = new HashSearch(src);
                break;
            default:
                throw new IllegalArgumentException("Указан неправильный тип поиска");
        }
    }

    public List<Phone> startSearch(List<String> targets) {
        return searchAlgorithm.search(targets);
    }
}
