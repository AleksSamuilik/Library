package it.alex.mylab.library;

import it.alex.mylab.library.main.MyLibrary;

public class MyLibraryApp {
    /**
     * account - sam@gmail.com
     * pass - 123456789
     *
     * account - balabol@mail.ru
     * pass - 987654321
     *
     *
     *
     * @param args
     * admin pass = admin$access
     */

    public static void main(String[] args) {
        MyLibrary myLibrary = new MyLibrary();
        myLibrary.startLibrary();
    }
}
