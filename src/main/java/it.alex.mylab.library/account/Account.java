package it.alex.mylab.library.account;

import it.alex.mylab.library.main.AuthorizationException;
import it.alex.mylab.library.main.FileProvider;
import it.alex.mylab.library.main.ResourcesProvider;

import java.io.*;
import java.util.Scanner;

public class Account {
    private final String eMail;
    private final String password;
    private final String hash;
    private String name;
    private String surname;
    private File accountFile;
    private boolean isAuthorized;


    public Account(String eMail, String password) {
        this.eMail = eMail.toLowerCase();
        this.password = password;
        openAccountFile();
        this.hash = hash();
    }

    public void isAuthorized() {
        if (verification()) {
            loadDataAccount();
            this.isAuthorized = true;
        } else {
            this.isAuthorized = false;
        }
    }

    private void loadDataAccount() {
        try (FileReader stream = new FileReader(accountFile);
             Scanner scanner = new Scanner(stream)) {
            String dataAccount;
            String hashAccount;
            while (scanner.hasNextLine()) {
                dataAccount = scanner.nextLine();
                hashAccount = scanner.nextLine();
                if (hashAccount.length() == hash.length()) {
                    if (hashAccount.equals(hash)) {
                        int indexFirst = dataAccount.indexOf(" ");
                        int indexlast = dataAccount.lastIndexOf(" ");
                        this.name = dataAccount.substring(0, indexFirst);
                        this.surname = dataAccount.substring(indexFirst + 1, indexlast);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Account(String eMail, String password, String name, String surname) {
        this.eMail = eMail.toLowerCase();
        this.password = password;
        this.name = name;
        this.surname = surname;
        openAccountFile();
        this.hash = hash();
    }

    private void openAccountFile() {
        FileProvider provider = new ResourcesProvider();
        accountFile = new File(provider.getFile("Account"));
    }

    private boolean verification() {
        try {
            if (checkHash(hash)) {
                return true;
            } else {
                throw new AuthorizationException();
            }
        } catch (AuthorizationException e) {
            return false;
        }
    }

    private boolean checkHash(String hash) {
        try (FileReader stream = new FileReader(accountFile);
             Scanner scanner = new Scanner(stream)) {
            while (scanner.hasNextLine()) {
                String inputLine = scanner.nextLine();
                if (inputLine.length() == hash.length()) {
                    if (inputLine.equals(hash)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String hash() {
        String hash = "";
        try {
            if (eMail == null || password == null) {
                throw new AuthorizationException();
            }
            HashFunction hashing = new HashFunction();
            hash = hashing.authentication(eMail, password);
        } catch (AuthorizationException e) {
            e.printStackTrace();
        }
        return hash;
    }

    public String getName() {
        return name;
    }

    public boolean isCreated() {
        if (!verification()) {
            writeAccountFile();
            this.isAuthorized = true;
            return true;
        } else {
            return false;
        }
    }

    private void writeAccountFile() {
        try (FileWriter writer = new FileWriter(accountFile, true);
             BufferedWriter bufferWriter = new BufferedWriter(writer)) {
            bufferWriter.newLine();
            bufferWriter.write(toString());
            bufferWriter.newLine();
            bufferWriter.write(hash);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return name + " " + surname + " " + eMail;
    }

    public String accessLevel() {
        return "guest";
    }

    public boolean getIsAuthorized() {
        return isAuthorized;
    }
}