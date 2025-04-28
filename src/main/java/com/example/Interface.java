package com.example;

import com.example.Librarians;
import com.example.LibraryAccounts;
import com.example.Purchasing;
import com.example.Library;
import com.example.Book;
import com.example.Member;

import java.util.Scanner;

public class Interface {
    private static Library library = new Library();
    private static Purchasing purchasing = new Purchasing();
    private static Librarians librarians = new Librarians();
    private static LibraryAccounts libraryAccounts = new LibraryAccounts(purchasing, librarians);
    private static Scanner scanner = new Scanner(System.in);

    private static boolean isFullTimeLibrarian = false;
    private static String currentLibrarianName = "";

    public static void loadInterface() {
        System.out.println("Welcome to the Library Management System");
        authenticateLibrarian();
        showMenu();
    }

    private static void authenticateLibrarian() {
        System.out.println("Enter Librarian Name:");
        String name = scanner.nextLine();
        System.out.println("Enter 6-digit Authentication Code (separate digits with space, or type 0 for part-time):");
        String[] codeInput = scanner.nextLine().split("");

        if (codeInput.length == 6) {
            short[] authCode = new short[6];
            try {
                for (int i = 0; i < 6; i++) {
                    authCode[i] = Short.parseShort(codeInput[i]);
                }
                if (librarians.checkLibrarianAuthenticationCode(name, authCode)) {
                    isFullTimeLibrarian = true;
                    currentLibrarianName = name;
                    System.out.println("Authentication successful. Full-time librarian access granted.");
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid code format.");
            }
        }

        // Part-time fallback
        isFullTimeLibrarian = false;
        System.out.println("Authentication failed or Part-Time Librarian. Limited access granted.");
    }

    private static void showMenu() {
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Checkout Book");
            if (isFullTimeLibrarian) {
                System.out.println("4. Add Member");
                System.out.println("5. Remove Member");
                System.out.println("6. Add Donation to Operating Cash");
                System.out.println("7. Withdraw Salary");
                System.out.println("8. View Operating Balance");
                System.out.println("9. View Purchased Books & Salaries");
            }
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> addBook();
                case 2 -> removeBook();
                case 3 -> checkoutBook();
                case 4 -> {
                    if (isFullTimeLibrarian) addMember();
                    else System.out.println("Access Denied: Only full-time librarians can add members.");
                }
                case 5 -> {
                    if (isFullTimeLibrarian) removeMember();
                    else System.out.println("Access Denied: Only full-time librarians can remove members.");
                }
                case 6 -> {
                    if (isFullTimeLibrarian) addDonation();
                    else System.out.println("Access Denied: Only full-time librarians can manage donations.");
                }
                case 7 -> {
                    if (isFullTimeLibrarian) withdrawSalary();
                    else System.out.println("Access Denied: Only full-time librarians can withdraw salary.");
                }
                case 8 -> {
                    if (isFullTimeLibrarian) viewOperatingBalance();
                    else System.out.println("Access Denied: Only full-time librarians can view balance.");
                }
                case 9 -> {
                    if (isFullTimeLibrarian) viewPurchasesAndSalaries();
                    else System.out.println("Access Denied: Only full-time librarians can view records.");
                }
                case 0 -> {
                    System.out.println("Exiting system. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void addBook() {
        System.out.println("Enter Book Title:");
        String title = scanner.nextLine();
        System.out.println("Enter Book Author:");
        String author = scanner.nextLine();
        System.out.println("Enter Year of Publication:");
        int year = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter ISBN:");
        String isbn = scanner.nextLine();
        System.out.println("Enter Genre:");
        String genre = scanner.nextLine();

        Book book = new Book(title, author, year, isbn, genre, library);
        library.addBook(book);
        System.out.println("Book added successfully.");
    }

    private static void removeBook() {
        System.out.println("Enter Book Title to Remove:");
        String title = scanner.nextLine();
        Book book = library.findBookIdByName(title);
        if (book != null) {
            library.removeBook(book);
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }

    private static void checkoutBook() {
        System.out.println("Enter Book Title to Checkout:");
        String title = scanner.nextLine();
        Book book = library.findBookIdByName(title);

        if (book != null) {
            if (library.bookAvailability(book)) {
                System.out.println("Enter Member Name:");
                String memberName = scanner.nextLine();
                System.out.println("Enter Member ID:");
                int memberId = Integer.parseInt(scanner.nextLine());
                Member member = library.getMember(memberName, memberId);

                if (member != null) {
                    library.checkoutBook(book, member);
                    System.out.println("Book checked out successfully.");
                } else {
                    System.out.println("Member not found. Checkout failed.");
                }
            } else {
                System.out.println("Book is already checked out.");
            }
        } else {
            if (isFullTimeLibrarian) {
                System.out.println("Book not found. Would you like to purchase it? (yes/no)");
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("yes")) {
                    System.out.println("Enter Year of Publication:");
                    int year = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter ISBN:");
                    String isbn = scanner.nextLine();
                    System.out.println("Enter Genre:");
                    String genre = scanner.nextLine();
                    Book newBook = new Book(title, "Unknown Author", year, isbn, genre, library);
                    String result = libraryAccounts.orderBook(currentLibrarianName, newBook);
                    if (result != null) {
                        library.addBook(newBook);
                        System.out.println(result);
                    } else {
                        System.out.println("Book purchase failed.");
                    }
                } else {
                    System.out.println("Checkout cancelled.");
                }
            } else {
                System.out.println("Book not found. Please ask a full-time librarian for assistance.");
            }
        }
    }

    private static void addMember() {
        System.out.println("Enter Member Name:");
        String name = scanner.nextLine();
        System.out.println("Enter Member Email:");
        String email = scanner.nextLine();
        Member member = library.addMember(name, email);
        System.out.println("Member added successfully. ID: " + member.getMemberId());
    }

    private static void removeMember() {
        System.out.println("Enter Member Name:");
        String name = scanner.nextLine();
        System.out.println("Enter Member ID:");
        int id = Integer.parseInt(scanner.nextLine());
        Member member = library.getMember(name, id);
        if (member != null) {
            library.removeMember(member);
            System.out.println("Member removed successfully.");
        } else {
            System.out.println("Member not found.");
        }
    }

    private static void addDonation() {
        System.out.println("Enter donation amount:");
        double amount = Double.parseDouble(scanner.nextLine());
        String result = libraryAccounts.addDonation(amount);
        if (result != null) {
            System.out.println(result);
        }
    }

    private static void withdrawSalary() {
        System.out.println("Enter salary amount to withdraw:");
        double amount = Double.parseDouble(scanner.nextLine());
        String result = libraryAccounts.withdrawSalary(currentLibrarianName, amount);
        if (result != null) {
            System.out.println(result);
        }
    }

    private static void viewOperatingBalance() {
        double balance = libraryAccounts.getOperatingCash();
        System.out.println("Current Operating Balance: $" + balance);
    }

    private static void viewPurchasesAndSalaries() {
        System.out.println("\n--- Librarian Report for " + currentLibrarianName + " ---");
        System.out.println("Total Salary Withdrawn: $" + librarians.getWithdrawnSalary(currentLibrarianName));
        System.out.println("Purchased Books:");
        for (Book b : librarians.getListLibrarianPurchasedBooks(currentLibrarianName)) {
            System.out.println("- " + b.getName());
        }
        System.out.println("Total Value of Purchased Books: $" + librarians.getLibrarianPurchasedBooksTotalValue(currentLibrarianName));
    }
}
