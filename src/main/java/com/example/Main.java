package com.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
//        Main method so manual testing can be done
        Library library = new Library(); //Create a library
        Librarians librarians = new Librarians();
        InterfaceRedone ui = new InterfaceRedone();
        ui.doInterface(library, librarians);
    }
}