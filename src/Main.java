import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TODO: Разбить сортировку на части, константы под пути, оформить общие методы
//TODO: Многопоточность - определение времени выполнения сортировки параллельно
public class Main {
    // На ресурсе hyperskill можно использовать лишь абсолютные пути
    private static String phonesPath = "D:\\Files\\Projects\\Buffer\\Java\\Phone Book\\directory.txt",
    targetPath = "D:\\Files\\Projects\\Buffer\\Java\\Phone Book\\find.txt";

    public static void main(String[] args) {
        try {
            List<Phone> phones = upload(phonesPath);
            List<String> targets = uploadTargets(targetPath);

            SearchContext searchContext = new SearchContext();
            searchContext.setSearchAlgorithm(new LinearSearch(phones));

            System.out.println("Start searching (linear search)");
            long startTime = System.currentTimeMillis();
            List<Phone> foundPhones = searchContext.startSearch(targets);
            long endTime = System.currentTimeMillis();

            long targetQty = targets.size();
            long foundQty = foundPhones.size();
            long linearTimeTaken = endTime - startTime;
            String timeTaken = formatTime(linearTimeTaken);
            String output = String.format("Found %d / %d entries. Time taken: %s",
                    foundQty, targetQty, timeTaken);
            System.out.println(output);
            //------------------------------------------------------------
            List<Phone> srcPhone = copyPhones(phones);
            searchContext.setSearchAlgorithm(new JumpSearch(srcPhone));

            System.out.println("Start searching (bubble sort + jump search)");
            startTime = System.currentTimeMillis();
            boolean isStopBubbleSort = bubbleSort(srcPhone, linearTimeTaken);
            endTime = System.currentTimeMillis();
            long sortingTime = endTime - startTime;

            startTime = System.currentTimeMillis();
            if (!isStopBubbleSort) {
                searchContext.setSearchAlgorithm(new LinearSearch(srcPhone));
                foundPhones = searchContext.startSearch(targets);
            } else {
                foundPhones = searchContext.startSearch(targets);
            }
            endTime = System.currentTimeMillis();
            long searchingTime = endTime - startTime;

            foundQty = foundPhones.size();
            timeTaken = formatTime(searchingTime + sortingTime);
            output = String.format("Found %d / %d entries. Time taken: %s",
                    foundQty, targetQty, timeTaken);
            System.out.println(output);

            output = String.format("Sorting time: %s", formatTime(sortingTime));
            System.out.println(output + " - STOPPED, moved to linear search");

            output = String.format("Searching time: %s", formatTime(searchingTime));
            System.out.println(output);
            //----------------------------------------------------
            srcPhone = copyPhones(phones);
            searchContext.setSearchAlgorithm(new BinarySearch(srcPhone));
            System.out.println("Start searching (quick sort + binary search)");
            startTime = System.currentTimeMillis();
            quickSort(srcPhone, 0, srcPhone.size() - 1);
            endTime = System.currentTimeMillis();
            sortingTime = endTime - startTime;

            startTime = System.currentTimeMillis();
            foundPhones = searchContext.startSearch(targets);
            endTime = System.currentTimeMillis();
            searchingTime = endTime - startTime;

            foundQty = foundPhones.size();
            timeTaken = formatTime(searchingTime + sortingTime);
            output = String.format("Found %d / %d entries. Time taken: %s",
                    foundQty, targetQty, timeTaken);
            System.out.println(output);

            output = String.format("Sorting time: %s", formatTime(sortingTime));
            System.out.println(output);

            output = String.format("Searching time: %s", formatTime(searchingTime));
            System.out.println(output);
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
            timeTaken = formatTime(searchingTime + sortingTime);
            output = String.format("Found %d / %d entries. Time taken: %s",
                    foundQty, targetQty, timeTaken);
            System.out.println(output);

            output = String.format("Creating time: %s", formatTime(sortingTime));
            System.out.println(output);

            output = String.format("Searching time: %s", formatTime(searchingTime));
            System.out.println(output);
            //----------------------------------------------------


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Phone> copyPhones(List<Phone> phones) {
        List<Phone> copied = new ArrayList<>(phones.size());
        for (Phone phone: phones) {
            Phone newPhone = new Phone(phone);
            copied.add(newPhone);
        }
        return copied;
    }
    public static boolean bubbleSort(List<Phone> phones, long idolTime) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < phones.size() - 1; i++) {
            boolean isOk = true;
            long timeTaken = System.currentTimeMillis() - startTime;
            if (timeTaken > idolTime * 10) {
                return false;
            }
            for (int k = 0; k < phones.size() - i - 1; k++) {
                Phone curPhone = phones.get(k);
                Phone nextPhone = phones.get(k + 1);
                if (curPhone.getFullName().compareToIgnoreCase(
                        nextPhone.getFullName()
                ) >= 1) {
                    isOk = false;
                    phones.set(k, nextPhone);
                    phones.set(k + 1, curPhone);
                }
            }
            if (isOk) {
                break;
            }
        }

        return true;
    }

    public static void quickSort(List<Phone> phones, int left, int right) {
        if (left < right) {
            int pivot = partition(phones, left, right);
            quickSort(phones, left, pivot - 1);
            quickSort(phones, pivot + 1, right);
        }
    }

    public static int partition(List<Phone> phones, int left, int right) {
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

    public static void swap(List<Phone> phones, int first, int second) {
        Phone temp = phones.get(first);
        phones.set(first, phones.get(second));
        phones.set(second, temp);
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
