package com.example;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Interface {
    private final Library library;
    private final LibraryAccounts libraryAccounts;
    private final Librarians librarians;
    private final Scanner scanner;

    private boolean isFullTimeLibrarian = false;
    private String currentLibrarianName = "";

    public Interface(Library library, LibraryAccounts libraryAccounts, Librarians librarians, Scanner scanner) {
        this.library = library;
        this.libraryAccounts = libraryAccounts;
        this.librarians = librarians;
        this.scanner = scanner;
    }

    public void loadInterface() {
        loadSomeStuff();
        System.out.println("Welcome to the Library Management System");
        authenticateLibrarian();
        showMenu();
    }

    private void loadSomeStuff() {
        library.addMember("Smith, John", "john.smith@gmail.com");
        library.addMember("Rabbit, Jessica", "jessica.rabbit@i<3carrots.com");
        library.addBook(new Book("Harry Potter", "JK Rowling", 2001, "7483902", "Fantasy", library));
        library.addBook(new Book("The Hobbit", "JRR Tolkien", 1955, "57849345", "Fantasy", library));
    }

    private void authenticateLibrarian() {
        int tries = 3;
        System.out.println("Enter Librarian Name:");
        String name = scanner.nextLine();
        while (tries > 0) {
            if (!librarians.getLibrariansNames().contains(name)) {
                break;
            }
            System.out.println("Enter 6-digit Authentication Code (separate digits with space, or type 0 for part-time):");
            String[] codeInput = scanner.nextLine().split("");
            if (Integer.parseInt(codeInput[0]) == 0 && codeInput.length == 1) {
                break;
            }
            short[] authCode = new short[6];
            if (codeInput.length != 6) {
                System.out.printf("Incorrect code, please try again. You have %d more tries.\n", tries-1);
                tries--;
                continue;
            }
            try {
                for (int i = 0; i < 6; i++) {
                    authCode[i] = Short.parseShort(codeInput[i]);
                }
                if (librarians.checkLibrarianAuthenticationCode(name, authCode)) {
                    isFullTimeLibrarian = true;
                    currentLibrarianName = name;
                    System.out.println("Authentication successful. Full-time librarian access granted.");
                    return;
                } else {
                    System.out.printf("Incorrect code, please try again. You have %d more tries.\n", tries-1);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid code format.");
            }
            tries--;
        }
        isFullTimeLibrarian = false;
        System.out.println("Part-Time Librarian. Limited access granted.");
    }

    private void showMenu() {
        while (true) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Checkout Book");
            System.out.println("4. Return Book");
            System.out.println("5. Print All Books List");
            if (isFullTimeLibrarian) {
                System.out.println("6. Add Member");
                System.out.println("7. Remove Member");
                System.out.println("8. Print Member Info");
                System.out.println("9. Add Donation to Operating Cash");
                System.out.println("10. Withdraw Salary");
                System.out.println("11. View Operating Balance");
                System.out.println("12. View Purchased Books & Salary");
            }
            System.out.println("0. Exit");
            System.out.print("Select an option: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> addBook();
                case 2 -> removeBook();
                case 3 -> checkoutBook();
                case 4 -> returnBook();
                case 5 -> printAllBookNames();
                case 6 -> checkFullTime(this::addMember);
                case 7 -> checkFullTime(this::removeMember);
                case 8 -> checkFullTime(this::printMemberInfo);
                case 9 -> checkFullTime(this::addDonation);
                case 10 -> checkFullTime(this::withdrawSalary);
                case 11 -> checkFullTime(this::viewOperatingBalance);
                case 12 -> checkFullTime(this::viewPurchasesAndSalaries);
                case 0 -> {
                    System.out.println("Exiting system. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void checkFullTime(Runnable action) {
        if (isFullTimeLibrarian) {
            action.run();
        } else {
            System.out.println("Access Denied: Only full-time librarians can perform this action.");
        }
    }

    private void addBook() {
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

    private void removeBook() {
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

    private void checkoutBook() {
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
                    System.out.println("Enter Author:");
                    String author = scanner.nextLine();
                    System.out.println("Enter Year of Publication:");
                    int year = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter ISBN:");
                    String isbn = scanner.nextLine();
                    System.out.println("Enter Genre:");
                    String genre = scanner.nextLine();
                    Book newBook = new Book(title, author, year, isbn, genre, library);
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

    private void returnBook() {
        System.out.println("Enter Book Title:");
        String bookName = scanner.nextLine().trim().toLowerCase();
        Book book = library.findBookIdByName(bookName);

        if (book != null) {
            library.returnBook(book);
            book.updateBook(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(true),
                    Optional.empty());
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }

    private void printAllBookNames() {
        System.out.println("Printing all books\n");
        List<Book> allBooks = library.getAllBooks();
        for (Book book : allBooks) {
            book.getBookInfo();
        }
        System.out.println();
    }

    private void addMember() {
        System.out.println("Enter Member Name:");
        String name = scanner.nextLine();
        System.out.println("Enter Member Email:");
        String email = scanner.nextLine();
        Member member = library.addMember(name, email);
        System.out.println("Member added successfully. ID: " + member.getMemberId());
    }

    private void removeMember() {
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

    private void printMemberInfo() {
        System.out.println("Enter Member ID:");
        int id = Integer.parseInt(scanner.nextLine().trim());
        Member member = library.getMember(null, id);
        if (member != null) {
            member.printMemberInfo();
        } else {
            System.out.println("Member not found.");
        }
    }

    private void addDonation() {
        System.out.println("Enter donation amount:");
        double amount = Double.parseDouble(scanner.nextLine());
        String result = libraryAccounts.addDonation(amount);
        if (result != null) {
            System.out.println(result);
        }
    }

    private void withdrawSalary() {
        System.out.println("Enter salary amount to withdraw:");
        double amount = Double.parseDouble(scanner.nextLine());
        String result = libraryAccounts.withdrawSalary(currentLibrarianName, amount);
        if (result != null) {
            System.out.println(result);
        }
        else {
            System.out.println("Salary amount attempted is invalid: " + amount);
        }
    }

    private void viewOperatingBalance() {
        double balance = libraryAccounts.getOperatingCash();
        System.out.println("Current Operating Balance: $" + balance);
    }

    private void viewPurchasesAndSalaries() {
        System.out.println("\n--- Librarian Report for " + currentLibrarianName + " ---");
        System.out.println("Total Salary Withdrawn: $" + librarians.getWithdrawnSalary(currentLibrarianName));
        System.out.println("Purchased Books:");
        for (Book b : librarians.getListLibrarianPurchasedBooks(currentLibrarianName)) {
            System.out.println("- " + b.getName());
        }
        System.out.println("Total Value of Purchased Books: $" + librarians.getLibrarianPurchasedBooksTotalValue(currentLibrarianName));
    }
}
