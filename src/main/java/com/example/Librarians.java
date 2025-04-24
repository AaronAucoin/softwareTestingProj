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
    private final Librarian[] librarianList = {
            new Librarian("Alice", new short[]{0, 0, 0, 0, 0, 0}),
            new Librarian("Bob", new short[]{1, 1, 1, 1, 1, 1}),
            new Librarian("Carol", new short[]{2, 2, 2, 2, 2, 2})
    };

    // Private method to find a librarian by name
    private Librarian findLibrarianByName(String librarianName) {
        for (Librarian lib : librarianList) {
            if (lib.getName().equals(librarianName)) {
                return lib;
            }
        }
        return null;
    }

    public String getAllLibrariansInformation() {
        StringBuilder sb = new StringBuilder();

        // Loop through each librarian and append their info to the StringBuilder
        for (Librarian lib : librarianList) {
            sb.append(lib.getInfo()).append("\n");
        }

        return sb.toString();  // Return the accumulated string
    }

    public short[] getLibrarianAuthenticationCode(String librarianName) {
        Librarian lib = findLibrarianByName(librarianName);
        return (lib != null) ? lib.getAuthenticationCode() : null;
    }

    public Boolean checkLibrarianAuthenticationCode(String librarianName, short[] AuthenticationCode) {
        Librarian lib = findLibrarianByName(librarianName);
        if (lib == null || AuthenticationCode == null) {
            return null;
        }
        for (int i = 0; i < AuthenticationCode.length; i++) {
            if (AuthenticationCode[i] != lib.getAuthenticationCode()[i]) {
                return false;
            }
        }
        return true;
    }

    public Double getWithdrawnSalary(String librarianName) {
        Librarian lib = findLibrarianByName(librarianName);
        return (lib != null) ? lib.getTotalCashWithdrawnAsSalary() : -1.0;
    }

    public Double addWithdrawnSalary(String librarianName, double amount) {
        Librarian lib = findLibrarianByName(librarianName);
        if (lib != null) {
            lib.addWithdrawnSalary(amount);
            return lib.getTotalCashWithdrawnAsSalary();
        }
        return null;
    }

    public Map<Book, Double> getBooksPurchasedByLibrarian(String librarianName) {
        Librarian lib = findLibrarianByName(librarianName);
        return (lib != null) ? lib.getBooksPurchasedByLibrarian() : null;
    }

    public Map<Book, Double> purchaseBook(String librarianName, Book book, double Cost) {
        Librarian lib = findLibrarianByName(librarianName);
        if (lib != null) {
            lib.addPurchasedBook(book, Cost);
            return lib.getBooksPurchasedByLibrarian();
        }
        return null;
    }

    private class Librarian {
        private String name;
        private short[] authentication_code = new short[6];
        private double totalCashWithdrawnAsSalary;
        private Map<Book, Double> BooksPurchasedByLibrarian;

        public Librarian(String name, short[] authentication_code) {
            this.name = name;
            if (authentication_code.length != 6) {
                throw new IllegalArgumentException();
            }
            this.authentication_code = authentication_code;
            this.totalCashWithdrawnAsSalary = 0.0;
            this.BooksPurchasedByLibrarian = new HashMap<>();
        }

        public String getName() {
            return name;
        }

        public short[] getAuthenticationCode() {
            return authentication_code;
        }

        public double getTotalCashWithdrawnAsSalary() {
            return totalCashWithdrawnAsSalary;
        }

        public Map<Book, Double> getBooksPurchasedByLibrarian() {
            return BooksPurchasedByLibrarian;
        }

        public void addPurchasedBook(Book book, double Cost) {
            this.BooksPurchasedByLibrarian.put(book, Cost);
        }

        public void addWithdrawnSalary(double amount) {
            this.totalCashWithdrawnAsSalary += amount;
        }

        public String getInfo() {
            return "Name: " + name + "\n" +
                    "Authentication code: " + Arrays.toString(authentication_code) + "\n" +
                    "Total Cash: " + totalCashWithdrawnAsSalary + "\n" +
                    "Books purchased by librarian: " + BooksPurchasedByLibrarian + "\n";
        }
    }
}
