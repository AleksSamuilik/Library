package it.alex.mylab.library.catalogOperation;

import it.alex.mylab.library.main.FileProvider;
import it.alex.mylab.library.main.ResourcesProvider;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class BookSearchProvider implements BookOperationProvider {
    private final File catalog;
    private String kindBook;
    private String author;
    private String title;

    public BookSearchProvider() {
        FileProvider fileProvider = new ResourcesProvider();
        String path = fileProvider.getFile("Book");
        this.catalog = new File(path);
    }


    @Override
    public String getOperationName() {
        return "Find book";
    }


    //this method needs refinement
    private void operationSelection() {
        String answer;
        Scanner scanner = new Scanner(System.in);
        int numberOperation = 3;
        System.out.println("Choose the criterion by which you will search. Enter one or more options:\n1 -> Book author.\n2 -> Book title.\n3 -> Kind of book.\n0 -> Exit.");
        answer = scanner.nextLine();
        if (answer.length() <= numberOperation && answer.matches("[123]*")) {
            if (answer.contains("3")) {
                searchKind();
            }
            if (answer.contains("2")) {
                searchTitle();
            }
            if (answer.contains("1")) {
                searchAuthor();
            }
        } else {
            System.out.println("Error input.");
            return;
        }
    }

    @Override
    public void getOperation() {
        operationSelection();
        search();
    }

    private void search() {
        try (FileReader stream = new FileReader(catalog);
             Scanner scanner = new Scanner(stream)) {
            boolean isOneBookFound = true;
            while (scanner.hasNextLine()) {
                boolean isKind = false;
                boolean isTitle = false;
                boolean isAuthor = false;
                String inputKind = scanner.nextLine();
                String inputTitle = scanner.nextLine();
                String inputAuthor = scanner.nextLine();
                if (kindBook != null) {
                    if (inputKind.toLowerCase().contains(kindBook)) {
                        isKind = true;
                    }
                } else {
                    isKind = true;
                }
                if (title != null) {
                    if (inputTitle.toLowerCase().contains(title)) {
                        isTitle = true;
                    }
                } else {
                    isTitle = true;
                }
                if (author != null) {
                    if (inputAuthor.toLowerCase().contains(author)) {
                        isAuthor = true;
                    }
                } else {
                    isAuthor = true;
                }
                if (isAuthor && isTitle && isKind) {
                    if (isOneBookFound) {
                        isOneBookFound = false;
                        System.out.println("List of all books found:\n");
                    }
                    System.out.println("Kind of book: " + inputKind + "\nTitle book: " + inputTitle + "\nAuthor of book: " + inputAuthor+"\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getOperationNumber() {
        return "2";
    }

    @Override
    public String getLevelAccess() {
        return "guest";
    }

    private void searchAuthor() {
        System.out.println("Enter the author of the book: ");
        Scanner scanner = new Scanner(System.in);
        this.author = scanner.nextLine().toLowerCase();
    }

    private void searchTitle() {
        System.out.println("Enter book title: ");
        Scanner scanner = new Scanner(System.in);
        this.title = scanner.nextLine().toLowerCase();
    }

    private void searchKind() {
        System.out.println("Enter kind of book: ");
        Scanner scanner = new Scanner(System.in);
        this.kindBook = scanner.nextLine().toLowerCase();
    }
}
