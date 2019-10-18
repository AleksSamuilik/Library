package it.alex.mylab.library.account;

public class Admin extends Account {
    private final String passAdminAccess;
    private final boolean adminAccess;
    private final String hash = "bc261087182e9c6441b095d6bfb214db362434ac5241211ca9d38d318fd433f4";
    public static final String ADMIN_EMAIL = "alkspawn@gmail.com";

    public Admin(String eMail, String password, String passwordAdminAccess) {
        super(eMail, password);
        this.passAdminAccess = passwordAdminAccess;
        if (super.getIsAuthorized() && isAdminAccess()) {
            this.adminAccess = true;
        } else {
            this.adminAccess = false;
        }
    }

    private boolean isAdminAccess() {
        HashFunction hashing = new HashFunction();
        if (hash.equals(hashing.authentication(ADMIN_EMAIL, passAdminAccess))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String accessLevel() {
        return "administrator";
    }

    public boolean getAdminAccess() {
        return adminAccess;
    }
}