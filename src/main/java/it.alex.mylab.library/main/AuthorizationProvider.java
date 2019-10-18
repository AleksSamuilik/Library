package it.alex.mylab.library.main;

public interface AuthorizationProvider {
    boolean getAuthorized(String answer);
}