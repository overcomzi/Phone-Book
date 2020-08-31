import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TODO: Разбить сортировку на части, константы под пути, оформить общие методы
//TODO: Многопоточность - определение времени выполнения сортировки параллельно
public class Main {
    private static final String phonesPath = "directory.txt";
    private static final String targetPath = "find.txt";

    public static void main(String[] args) {
        try {
            List<Phone> phones = upload(phonesPath);
            List<String> targets = uploadTargets(targetPath);

            SearchContext searchContext = new SearchContext();
            searchContext.setSearchAlgorithm(new LinearSearch(phones));
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

            List<Phone> srcPhone = copyPhones(phones);
            searchContext.setSearchAlgorithm(new JumpSearch(srcPhone));
            sortContext.setSort(new BubbleSort());

            System.out.println("Start searching (bubble sort + jump search)");
            startTime = System.currentTimeMillis();
            boolean isStopBubbleSort = !sortContext.startSort(srcPhone, linearTimeTaken);
            endTime = System.currentTimeMillis();
            long sortingTime = endTime - startTime;

            startTime = System.currentTimeMillis();
            if (isStopBubbleSort) {
                searchContext.setSearchAlgorithm(new LinearSearch(srcPhone));
                foundPhones = searchContext.startSearch(targets);
            } else {
                foundPhones = searchContext.startSearch(targets);
            }
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

            srcPhone = copyPhones(phones);
            searchContext.setSearchAlgorithm(new BinarySearch(srcPhone));
            sortContext.setSort(new QuickSort());

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

            srcPhone = copyPhones(phones);
            System.out.println("Start searching (hash table)");
            startTime = System.currentTimeMillis();
            searchContext.setSearchAlgorithm(new HashSearch(srcPhone));
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

    public static List<Phone> copyPhones(List<Phone> phones) {
        List<Phone> copied = new ArrayList<>(phones.size());
        for (Phone phone : phones) {
            Phone newPhone = new Phone(phone);
            copied.add(newPhone);
        }
        return copied;
    }


    public static String formatTime(long mills) {
        long minutes = ((mills / 1000) / 60) % 60;
        long secs = (mills / 1000) % 60;
        long ms = mills % 1000;
        return String.format("%d min. %d sec. %d ms.", minutes, secs, ms);
    }

    public static List<Phone> upload(String name) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(name));
        List<Phone> phones = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String number = line.substring(0, line.indexOf(" "));
            String fullName = line.substring(line.indexOf(" ") + 1, line.length());
            // TODO: Валидация номера и полного имени
            Phone phone = new Phone(number, fullName);
            phones.add(phone);
        }
        return phones;
    }

    public static List<String> uploadTargets(String name) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(name));
        List<String> targets = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            // TODO: Валидация полного имени
            targets.add(line);
        }
        return targets;
    }
}
