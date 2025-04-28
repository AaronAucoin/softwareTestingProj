package com.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create instances of the required dependencies
        Library library = new Library(); // Assuming the Library class exists
        Purchasing purchasing = new Purchasing();
        Librarians librarians = new Librarians();
        LibraryAccounts libraryAccounts = new LibraryAccounts(purchasing, librarians); // Assuming the LibraryAccounts class exists
        Scanner scanner = new Scanner(System.in); // Create a scanner for user input

        // Create an instance of the Interface class
        Interface ui = new Interface(library, libraryAccounts, libraryAccounts.getLibrarians(), scanner);

        // Call loadInterface() on the Interface instance
        ui.loadInterface();
    }
}
