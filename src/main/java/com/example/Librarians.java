package com.example;

import java.util.*;


// this class has the following functionality:
// 3 librarians: Alice, Bob, and Carol with access codes define below
// getAllLibrariansInformation()
// public String getLibrarianAuthenticationCode(String librarianName)
// public Boolean checkLibrarianAuthenticationCode(String librarianName, short[] authenticationCode)
// public Double getWithdrawnSalary(String librarianName)
// public Double addWithdrawnSalary(String librarianName, double amount)
// public Map<Book, Double> getBooksPurchasedByLibrarian(String librarianName)
// public Map<Book, Double> purchaseBook(String librarianName, Book book, double Cost)
// public List<String> getListLibrarianPurchasedBooksNames(String librarianName)
// public Double getLibrarianPurchasedBooksTotalValue(String librarianName)

public class Librarians {
    // list of predefined librarians
    private final Librarian[] librarianList = {
            new Librarian("Alice", new short[]{0, 0, 0, 0, 0, 0}),
            new Librarian("Bob", new short[]{1, 1, 1, 1, 1, 1}),
            new Librarian("Carol", new short[]{2, 2, 2, 2, 2, 2})
    };

    // returns the names of all full time librarians
    public List<String> getLibrariansNames() {
        List<String> librariansNames = new ArrayList<>();
        for (Librarian librarian : librarianList) {
            librariansNames.add(librarian.getName());
        }
        return librariansNames;
    }

    // Private method to find a librarian object given a name
    private Librarian findLibrarianByName(String librarianName) {
        for (Librarian lib : librarianList) {
            if (lib.getName().equals(librarianName)) {
                return lib;
            }
        }
        return null;
    }

    // gets the information of all librarians in a string
    public String getAllLibrariansInformation() {
        StringBuilder sb = new StringBuilder();

        // Loop through each librarian and append their info to the StringBuilder
        for (Librarian lib : librarianList) {
            sb.append(lib.getInfo()).append("\n");
        }

        return sb.toString();  // Return the accumulated string
    }

    // gets the authentication code of a librarian
    public short[] getLibrarianAuthenticationCode(String librarianName) {
        Librarian lib = findLibrarianByName(librarianName);
        return (lib != null) ? lib.getAuthenticationCode() : null;
    }

    // checks that the entered authentication code matches the librarians pre-defined authentication code
    public Boolean checkLibrarianAuthenticationCode(String librarianName, short[] AuthenticationCode) {
        Librarian lib = findLibrarianByName(librarianName);
        if (lib == null || AuthenticationCode == null) {
            return false;
        }
        for (int i = 0; i < AuthenticationCode.length; i++) {
            if (AuthenticationCode[i] != lib.getAuthenticationCode()[i]) {
                return false;
            }
        }
        return true;
    }

    // gets the withdrawn salary for a given librarian
    public Double getWithdrawnSalary(String librarianName) {
        Librarian lib = findLibrarianByName(librarianName);
        return (lib != null) ? lib.getTotalCashWithdrawnAsSalary() : -1.0;
    }

    // adds withdrawn salary for a given librarian
    public Double addWithdrawnSalary(String librarianName, double amount) {
        Librarian lib = findLibrarianByName(librarianName);
        if (lib != null) {
            lib.addWithdrawnSalary(amount);
            return lib.getTotalCashWithdrawnAsSalary();
        }
        return null;
    }

    // returns the map of book, cost values for librarians purchased books
    public Map<Book, Double> getMapBooksPurchasedByLibrarian(String librarianName) {
        Librarian lib = findLibrarianByName(librarianName);
        return (lib != null) ? lib.getBooksPurchasedByLibrarian() : null;
    }

    // adds a new book with associated cost to a librarians purchased books map
    public Map<Book, Double> purchaseBook(String librarianName, Book book, double Cost) {
        Librarian lib = findLibrarianByName(librarianName);
        if (lib != null) {
            lib.addPurchasedBook(book, Cost);
            return lib.getBooksPurchasedByLibrarian();
        }
        return null;
    }

    // gets the list of a all book names for books a librarian has purchased
    public List<Book> getListLibrarianPurchasedBooks(String librarianName){
        Librarian lib = findLibrarianByName(librarianName);
        if (lib != null) {
            return new ArrayList<>(lib.getBooksPurchasedByLibrarian().keySet());
        }
        return null;
    }

    // gets the total value of all books a librarian has purchase
    public Double getLibrarianPurchasedBooksTotalValue(String librarianName) {
        Librarian lib = findLibrarianByName(librarianName);
        if (lib != null) {
            double total_value = 0;
            for (double value : lib.getBooksPurchasedByLibrarian().values()) {
                total_value += value;
            }
            return total_value;
        }
        return null;
    }


    // member class of librarians
    // repsonsble for creating and managing the librarian object
    private class Librarian {
        private String name;
        private short[] authentication_code = new short[6];
        private double totalCashWithdrawnAsSalary;
        private Map<Book, Double> BooksPurchasedByLibrarian;

        // creates a librarian with set name and authentication code
        // no books purchased and no salary withdrawn yet
        public Librarian(String name, short[] authentication_code) {
            this.name = name;
            if (authentication_code.length != 6) {
                throw new IllegalArgumentException();
            }
            this.authentication_code = authentication_code;
            this.totalCashWithdrawnAsSalary = 0.0;
            this.BooksPurchasedByLibrarian = new HashMap<>();
        }

        // returns the name of a librarian object
        public String getName() {
            return name;
        }

        // gets the authentication code of a librarian
        public short[] getAuthenticationCode() {
            return authentication_code;
        }

        // gets the total salary a librarian has withdrawn
        public double getTotalCashWithdrawnAsSalary() {
            return totalCashWithdrawnAsSalary;
        }

        // gets the purchased book list of the librarian
        public Map<Book, Double> getBooksPurchasedByLibrarian() {
            return BooksPurchasedByLibrarian;
        }

        // adds a book to a librarians purchased book list
        public void addPurchasedBook(Book book, double Cost) {
            this.BooksPurchasedByLibrarian.put(book, Cost);
        }

        // adds a withdrawn salary amount to a librarians total withdrawn salary
        public void addWithdrawnSalary(double amount) {
            this.totalCashWithdrawnAsSalary += amount;
        }

        // returns all the info about a certain librarian in a formatted string
        public String getInfo() {
            return "Name: " + name + "\n" +
                    "Authentication code: " + Arrays.toString(authentication_code) + "\n" +
                    "Total Cash: " + totalCashWithdrawnAsSalary + "\n" +
                    "Books purchased by librarian: " + BooksPurchasedByLibrarian + "\n";
        }
    }
}
