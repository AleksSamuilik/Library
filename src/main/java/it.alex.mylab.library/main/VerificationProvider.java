package it.alex.mylab.library.main;

import it.alex.mylab.library.account.Account;
import it.alex.mylab.library.account.Admin;

import java.util.Scanner;

public class VerificationProvider implements AuthorizationProvider {
    private Account account;
    private boolean adminAccess;

    @Override
    public boolean getAuthorized(String answer) {
        if (answer.equals("1")) {
            verifiesAccount();
        } else if (answer.equals("2")) {
            createAccount();
        } else {
            System.out.println("Good Bye!");
            return false;
        }
        if (account.getIsAuthorized()) {
            System.out.println("Authorization is successful!");
            System.out.println("Hello " + account.getName() + "!");
            System.out.println("You are logged in as " + account.accessLevel() + ".");
            return true;
        } else {
            System.out.println("Sorry. Authorization Error");
            return false;
        }
    }

    private void verifiesAccount() {
        System.out.println("Select account type: \n1 -> Log in guest account.\n2 -> Log in administrator account.\n0 -> Exit.");
        String answer = request("select");
        if (answer.equals("1")) {
            verifiesUserAccount();
        } else if (answer.equals("2")) {
            verifiesAdminAccount();
        } else {
            System.out.println("Good Bye!");
        }
    }

    private void verifiesUserAccount() {
        String eMail = request("Email");
        String password = request("Password");
        try {
            account = new Account(eMail, password);
            account.isAuthorized();
        } catch (AuthorizationException e) {
            e.printStackTrace();
        }
    }

    private void verifiesAdminAccount() {
        String eMail = request("Email");
        String password = request("Password");
        String adminPass = request("Admin password");
        try {
            account = new Admin(eMail, password, adminPass);
            this.adminAccess = ((Admin) account).getAdminAccess();
        } catch (AuthorizationException e) {
            e.printStackTrace();
        }
    }

    private String request(String whatRequest) {
        System.out.println("Enter your " + whatRequest + ":");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private boolean createAccount() {
        String eMail = request("Email");
        String password = request("Password");
        String name = request("Name");
        String surname = request("Surname");
        account = new Account(eMail, password, name, surname);
        return account.isCreated();
    }


}