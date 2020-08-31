import search.SearchContext;
import search.SearchType;
import sort.SortContext;
import sort.SortType;
import user.Phone;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String phonesPath = "directory.txt";
    private static final String targetPath = "find.txt";

    public static void main(String[] args) {
        try {
            List<Phone> phones = upload(phonesPath);
            List<String> targets = uploadTargets(targetPath);

            SearchContext searchContext = new SearchContext();
            searchContext.setSearchAlgorithm(SearchType.LinearSearch, phones);
            SortContext sortContext = new SortContext();

            System.out.println("Start searching (linear search)");
            long startTime = System.currentTimeMillis();
            List<Phone> foundPhones = searchContext.startSearch(targets);
            long endTime = System.currentTimeMillis();

            long targetQty = targets.size();
            long foundQty = foundPhones.size();
            long linearTimeTaken = endTime - startTime;

            String timeTaken = formatTime(linearTimeTaken);
            System.out.printf("Found %d / %d entries. Time taken: %s\n",
                    foundQty, targetQty, timeTaken);
            //------------------------------------------------------------

            List<Phone> srcPhone = new ArrayList<>(phones);
            searchContext.setSearchAlgorithm(SearchType.JumpSearch, srcPhone);
            sortContext.setSort(SortType.BubbleSort);

            System.out.println("Start searching (bubble sort + jump search)");
            startTime = System.currentTimeMillis();
            boolean isStopBubbleSort = !sortContext.startSort(srcPhone, linearTimeTaken);
            endTime = System.currentTimeMillis();
            long sortingTime = endTime - startTime;

            startTime = System.currentTimeMillis();
            if (isStopBubbleSort) {
                searchContext.setSearchAlgorithm(SearchType.LinearSearch, srcPhone);
            }
            foundPhones = searchContext.startSearch(targets);
            endTime = System.currentTimeMillis();

            long searchingTime = endTime - startTime;
            foundQty = foundPhones.size();
            timeTaken = formatTime(searchingTime + sortingTime);

            System.out.printf("Found %d / %d entries. Time taken: %s\n",
                    foundQty, targetQty, timeTaken);
            String output = String.format("Sorting time: %s", formatTime(sortingTime));
            if (isStopBubbleSort) {
                output += " - STOPPED, moved to linear search";
            }
            System.out.println(output);
            System.out.printf("Searching time: %s\n", formatTime(searchingTime));
            //----------------------------------------------------

            srcPhone = new ArrayList<>(phones);
            searchContext.setSearchAlgorithm(SearchType.BinarySearch, srcPhone);
            sortContext.setSort(SortType.QuickSort);

            System.out.println("Start searching (quick sort + binary search)");
            startTime = System.currentTimeMillis();
            sortContext.startSort(srcPhone, -1);
            endTime = System.currentTimeMillis();

            sortingTime = endTime - startTime;

            startTime = System.currentTimeMillis();
            foundPhones = searchContext.startSearch(targets);
            endTime = System.currentTimeMillis();

            searchingTime = endTime - startTime;
            foundQty = foundPhones.size();
            timeTaken = formatTime(searchingTime + sortingTime);
            System.out.printf("Found %d / %d entries. Time taken: %s\n",
                    foundQty, targetQty, timeTaken);
            System.out.printf("Sorting time: %s\n", formatTime(sortingTime));
            System.out.printf("Searching time: %s\n", formatTime(searchingTime));
            //----------------------------------------------------

            srcPhone = new ArrayList<>(phones);
            System.out.println("Start searching (hash table)");
            startTime = System.currentTimeMillis();
            searchContext.setSearchAlgorithm(SearchType.HashSearch, srcPhone);
            endTime = System.currentTimeMillis();

            long creatingTime = endTime - startTime;

            startTime = System.currentTimeMillis();
            foundPhones = searchContext.startSearch(targets);
            endTime = System.currentTimeMillis();

            searchingTime = endTime - startTime;
            foundQty = foundPhones.size();
            timeTaken = formatTime(searchingTime + creatingTime);
            System.out.printf("Found %d / %d entries. Time taken: %s\n",
                    foundQty, targetQty, timeTaken);
            System.out.printf("Creating time: %s\n", formatTime(creatingTime));
            System.out.printf("Searching time: %s\n", formatTime(searchingTime));
            //----------------------------------------------------
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String formatTime(long mills) {
        Duration duration = Duration.ofMillis(mills);
        long minutes = duration.toMinutes();
        long secs = duration.minusMinutes(minutes).toSeconds();
        long ms = duration.minusMinutes(minutes).minusSeconds(secs).toMillis();
        return String.format("%d min. %d sec. %d ms.", minutes, secs, ms);
    }

    private static List<Phone> upload(String name) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(name));
        List<Phone> phones = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String number = line.substring(0, line.indexOf(" "));
            String fullName = line.substring(line.indexOf(" ") + 1);
            if (!number.matches("\\d+")) {
                throw new IllegalArgumentException("Несоответствие формату номера телефона");
            }
            Phone phone = new Phone(number, fullName);
            phones.add(phone);
        }
        return phones;
    }

    private static List<String> uploadTargets(String name) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(name));
        List<String> targets = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            targets.add(line);
        }
        return targets;
    }
}
