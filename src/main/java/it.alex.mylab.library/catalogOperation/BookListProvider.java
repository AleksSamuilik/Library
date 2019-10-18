package it.alex.mylab.library.catalogOperation;

import it.alex.mylab.library.main.FileProvider;
import it.alex.mylab.library.main.ResourcesProvider;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class BookListProvider implements BookOperationProvider {
    private final File catalog;
    private int countLine = 0;
    private final int NUMBER_BOOK_PRINT_PAGE = 5;
    private int numberOfLines;

    public BookListProvider() {
        FileProvider fileProvider = new ResourcesProvider();
        String path = fileProvider.getFile("Book");
        this.catalog = new File(path);
    }

    @Override
    public String getOperationName() {
        return "Open Catalog";
    }

    @Override
    public void getOperation() {
        System.out.println("Book list:");
        String answer;
        Scanner scanner = new Scanner(System.in);
        numberOfLines = getCountLine();
        do {
            openPage();
            System.out.println("Do you want to see the next page?\n1 -> Yes.\n2 -> No.");
            answer = scanner.nextLine();
            if (numberOfLines <= countLine) {
                return ;
            }
            if (answer.equals("2")) {
                break;
            }
        } while (answer.equals("1"));
        System.out.println("Good bye!");
    }

    @Override
    public String getOperationNumber() {
        return "1";
    }

    @Override
    public String getLevelAccess() {
        return "guest";
    }

    private int getCountLine() {
        try (FileReader input = new FileReader(catalog);
             LineNumberReader count = new LineNumberReader(input)) {
            while (count.skip(Long.MAX_VALUE) > 0) {
            }
            return count.getLineNumber() + 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void openPage() {
        try (Stream<String> stream = Files.lines(catalog.toPath(), Charset.defaultCharset())) {
            AtomicInteger count = new AtomicInteger(0);
            stream.skip(countLine).limit(NUMBER_BOOK_PRINT_PAGE * 3).forEach(line -> {
                count.getAndIncrement();
                System.out.println(count.get() == 1 ? "Kind of book: " + line :
                        count.get() == 2 ? "Title book: " + line :
                                count.get() == 3 ? "Author of book: " + line : line);
                if (count.get() % 3 == 0) {
                    System.out.println();
                    count.lazySet(0);

                }
            });
            countLine += (NUMBER_BOOK_PRINT_PAGE * 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
