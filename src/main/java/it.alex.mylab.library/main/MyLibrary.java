package it.alex.mylab.library.main;

import it.alex.mylab.library.catalogOperation.BookOperationProvider;

import java.util.*;


public class MyLibrary {
    private Map<String, Pair<String, BookOperationProvider>> operationMap;

    public void startLibrary() {
        System.out.println("Welcome to the library");
        if (startAuthorization()) {
            loadCatalog();
            startCatalog();
        } else {
            return;
        }
    }

    private boolean startAuthorization() {
        System.out.println("1 -> Enter in the account.\n2 -> Create account.\n0 -> Exit.");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        AuthorizationProvider authorizationProvider = new VerificationProvider();

        return authorizationProvider.getAuthorized(answer) ? true : false;
    }

    private void loadCatalog() {
        Collection<BookOperationProvider> collection = scanOperation();
        Iterator iterator = collection.iterator();
        operationMap = new HashMap<>();
        BookOperationProvider operation;
        while (iterator.hasNext()) {
            operation = (BookOperationProvider) iterator.next();
            operationMap.put(operation.getOperationNumber(), new Pair<>(operation.getOperationName(), operation));
        }
    }

    private Collection scanOperation() {
        AnnotationAnalyzer analyzer = new AnnotationAnalyzer();
        Set<Class<?>> listClass = analyzer.reflectionsScan();
        Collection<BookOperationProvider> collection = new ArrayList<>();
        for (Class clazz : listClass) {
            try {
                if (clazz.newInstance() != null) {
                    collection.add((BookOperationProvider) clazz.newInstance());
                }
            } catch (InstantiationException | IllegalAccessException e) {
                continue;
            }
        }
        return collection;
    }

    private boolean startCatalog() {
        System.out.println("What operation do you want to do in the library?");
        Set<Map.Entry<String, Pair<String, BookOperationProvider>>> entry = operationMap.entrySet();
        String answer;
        while (true) {
            for (Map.Entry line : entry) {
                String number = (String) line.getKey();
                Pair pair = (Pair) line.getValue();
                System.out.println(number + " -> " + pair.getFirst());
            }
            Scanner scanner = new Scanner(System.in);
            answer = scanner.nextLine();
            break;
        }
        operationMap.get(answer).getSecond().getOperation();

        return true;
    }
}

class Pair<A, B> {
    private A first = null;
    private B second = null;

    Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    A getFirst() {
        return first;
    }

    B getSecond() {
        return second;
    }
}