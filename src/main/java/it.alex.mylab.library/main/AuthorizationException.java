package it.alex.mylab.library.main;

public class AuthorizationException extends RuntimeException {

    public AuthorizationException(){
        super();
    }

    public AuthorizationException(RuntimeException message){
        System.out.println(message.getClass().getName());
    }
}
