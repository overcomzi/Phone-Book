import java.util.List;

public class SearchContext {
    private SearchAlgorithm searchAlgorithm;

    public void setSearchAlgorithm(SearchAlgorithm searchAlgorithm) {
        this.searchAlgorithm = searchAlgorithm;
    }

    public List<Phone> startSearch(List<String> targets) {
        return searchAlgorithm.search(targets);
    }
}
