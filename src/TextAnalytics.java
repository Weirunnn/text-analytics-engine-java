import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.Scanner;

public class TextAnalytics {

    public static void main(String[] args) throws IOException {
        //O(n) for reading and processing the file, where n is the number of words
        if (args.length < 1) {
            System.out.println("Usage: java TextAnalytics <file_path>");
            return;
        }

        String filePath = args[0];
        StringBuilder bookContent = new StringBuilder();
        boolean isReadingBookContent = false;

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("*** START OF THIS PROJECT GUTENBERG EBOOK")) {
                isReadingBookContent = true;
                continue;  
            }
            if (line.contains("*** END OF THIS PROJECT GUTENBERG EBOOK")) {
                break;  
            }
            if (isReadingBookContent) {
                bookContent.append(line).append(" ");
            }
        }
        reader.close();

        String content = bookContent.toString(); 
        content = cleanText(content);
        String[] words = content.split("\\s+");

        ObjectHashMap wordMap = new ObjectHashMap(0.9);
        for (String word : words) {
            if (word.isEmpty()) {
                continue;
            }

            Integer currentCount = (Integer) wordMap.find(word); 
            if (currentCount == null) {
                wordMap.put(word, 1);
            } else {
                wordMap.put(word, currentCount + 1);
            }
        }

        Entry[] entries = wordMap.getEntries();
        insertionSort(entries);
        //O(n²) for the sorting

        for (int i = 0; i < Math.min(5, entries.length); i++) {
            String useText = (entries[i].value.equals(1)) ? "use" : "uses";
            System.out.printf("%d.) '%s'%d uses.%n", i + 1, entries[i].key, entries[i].value, useText);
        }

        Scanner scanner = new Scanner(System.in);
        String word = scanner.nextLine();
        while (!word.equals("q")) {
            Integer times = (Integer) wordMap.find(word);
            if (times == null) {
                System.out.println("The word '" + word + "' is not present.");
            } else {
                String useText = (times.equals(1)) ? "time" : "times";
                System.out.println("The word '" + word + "' occurs " + times + " " + useText + ".");
            }
            word = scanner.nextLine();
        }
        scanner.close();
    }        

    public static void insertionSort(Entry[] entries) {
        //O(n²) for insertion sort
        for (int i = 1; i < entries.length; i++) {
            Entry current = entries[i];
            int j = i - 1;
            while (j >= 0 && ((Integer) entries[j].value).compareTo((Integer) current.value) < 0) {
                entries[j + 1] = entries[j];
                j--;
            }
            entries[j + 1] = current;
        }
    }
    private static String cleanText(String content) {
        //O(n), where n is the length of the content
        return content.toLowerCase().replaceAll("[^a-z\\s+]", "");
    }
}



