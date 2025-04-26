package com.example;

import java.util.*;

class InterfaceRedone {

    private boolean fulltime = false;
    public void doInterface(Library lib, Librarians librarians) {
        Scanner in = new Scanner(System.in);
        System.out.println("Input menu number to run command");
        printMenu();

        boolean running = true;
        // switch case break statement for menu options
        while (running) {
            running = runSwitch(in, lib, librarians);
        }
        in.close();
    }

    public void printMenu() {
        System.out.println("0. Login as Librarian(full-time)");
        System.out.println("+-----------------------+");
        System.out.println("1. Add Book");
        System.out.println("2. Remove Book");
        System.out.println("3. Update Book");
        System.out.println("4. Add Member");
        System.out.println("5. Remove Member");
        System.out.println("6. Checkout Book");
        System.out.println("7. Return Book");
        System.out.println("8. Print Member Info");
        System.out.println("9. Print All Books List");
        System.out.println("10. Help");
        System.out.println("11. Exit");
        System.out.println("+-----------------------+\n");
    }

    public boolean runSwitch(Scanner in, Library lib, Librarians librarians) {
        if(fulltime){
            System.out.println("FULLTIME HERE YOU CAN HAVE MORE OPTIONS!!!");
        }

        System.out.print("> ");
        String answer = in.nextLine();
        switch (answer) {
            case "0":
                fulltime = loginFullTime(in, librarians);
                break;
            case "1": // addBook
                addBook(in, lib);
                printMenu();
                break;
            case "2": // removeBook
                // removes a book with a given title from the library
                removeBook(in, lib);
                break;
            case "3": // updateBook
                updateBook(in, lib);
                break;
            case "4": // addMember
                addMember(in, lib);
                break;
            case "5": // removeMember
                removeMember(in, lib);
                break;
            case "6": // checkoutBook
                checkoutBook(in, lib);
                break;
            case "7": // returnBook
                returnBook(in, lib);
                break;
            case "8": // printMemberInfo
                printMemberInfo(in, lib);
                break;
            case "9": // printAllBookNames
                printAllBookNames(in, lib);
                break;
            case "10":
                revokeMembership();
//            case "10": // help
//                printMenu();
//                break;
//            case "11": // EXIT
//                System.out.println("Have a nice day!");
//                return false;
            default:
                System.out.println("Unknown command! " + in);
        }
        return true;
    }

    private void revokeMembership() {
    }

    private boolean loginFullTime(Scanner in, Librarians librarians) {
        System.out.println("Enter librarian code: ");
        return false;
    }

    public void printAllBookNames(Scanner in, Library lib) {
        System.out.println("Printing all books");
        List<Book> allBooks = lib.getAllBooks();
        for (Book book : allBooks) {
            book.getBookInfo();
        }
        System.out.println();
    }

    public void printMemberInfo(Scanner in, Library lib) {
        System.out.println("Enter Member ID:");
        int id = Integer.parseInt(in.nextLine().trim());
        Member member = lib.getMember(null, id);
        if (member != null) {
            member.printMemberInfo();
        } else {
            System.out.println("Member not found.");
        }
    }

    public void returnBook(Scanner in, Library lib) {
        String bookName;
        Book book;
        System.out.println("Enter Book Title:");
        bookName = in.nextLine().trim().toLowerCase();
        book = lib.findBookIdByName(bookName);

        if (book != null) {
            lib.returnBook(book);
            book.updateBook(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(true),
                    Optional.empty());
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }

    public void checkoutBook(Scanner in, Library lib) {
        System.out.println("Enter Book Title:");
        String bookName = in.nextLine().trim().toLowerCase();
        Book book = lib.findBookIdByName(bookName);

        if (book == null) {
            System.out.println("ERROR: Book not found.");
            return;
        }

        System.out.println("Enter Member ID:");
        int memId = Integer.parseInt(in.nextLine().trim());
        Member member = lib.getMember(null, memId);

        if (member == null) {
            System.out.println("ERROR: Member not found.");
            return;
        }

        if (book != null && member != null && book.checkAvailability()) {
            lib.checkoutBook(book, member);
            book.updateBook(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(false),
                    Optional.empty());
            System.out.println("Book checked out successfully.");
        } else {
            System.out.println("Error: Book not found, unavailable, or member ID invalid.");
        }
        member.printMemberInfo();
    }

    public void removeMember(Scanner in, Library lib) {
        // asks for a member's name and ID
        // removes that member from the memberID list and allMembers list
        System.out.println("Enter Member Name (Last, First)");
        String memberName = in.nextLine().trim();

        System.out.println("Enter Member ID");
        int memberID = Integer.parseInt(in.nextLine().trim());
        Member member = lib.getMember(memberName, memberID);
        lib.removeMember(member);
    }

    public void addMember(Scanner in, Library lib) {
        // asks for all necessary member info (name, email)
        // constructor auto-generates memberID and empty borrowedBookList
        System.out.println("Enter Member Name (Last, First)");
        String memberName = in.nextLine().trim();

        System.out.println("Enter Member email");
        String memberEmail = in.nextLine().trim(); // we could maybe do a check for legit emails later?
                                                   // absolutely not ^^^
        Member member = lib.addMember(memberName, memberEmail);
        member.printMemberInfo();
    }

    public void updateBook(Scanner in, Library lib) {
        // updates a given books info in the library
        System.out.println("Enter Book Title");
        String updateTitle = in.nextLine().trim().toLowerCase();
        Book book = lib.findBookIdByName(updateTitle);

        if (book != null) {
            while (true) {
                // asks for which info to update and updates it
                System.out.println("What would you like to update?");
                System.out.println("1. Update Title");
                System.out.println("2. Update Author");
                System.out.println("3. Update Year");
                System.out.println("4. Update ISBN");
                System.out.println("5. Update Genre");
                System.out.println("6. Mark Unavailable");
                System.out.println("7. Mark Available");
                System.out.println("8. Print Book Info");
                System.out.println("9. Exit");
                System.out.println("+----------------------+");
                System.out.print("> ");

                String input = in.nextLine().trim();

                if (input.equalsIgnoreCase("9"))
                    break;

                // updates the book with the new info
                switch (input.toLowerCase()) {
                    case "1": // update title
                        System.out.println("Enter New Book Title:");
                        String title = in.nextLine().trim();
                        book.updateBook(Optional.of(title), Optional.empty(),
                                Optional.empty(), Optional.empty(),
                                Optional.empty(), Optional.empty());
                        break;

                    case "2": // update author
                        System.out.println("Enter New Book Author:");
                        String bookAuthor = in.nextLine().trim();
                        book.updateBook(Optional.empty(), Optional.of(bookAuthor),
                                Optional.empty(), Optional.empty(),
                                Optional.empty(), Optional.empty());
                        break;

                    case "3": // update year
                        System.out.println("Enter New Book Year:");
                        try {
                            int bookYear = in.nextInt();
                            in.nextLine(); // clear newline
                            book.updateBook(Optional.empty(), Optional.empty(),
                                    Optional.of(bookYear), Optional.empty(),
                                    Optional.empty(), Optional.empty());
                        } catch (Exception e) {
                            System.out.println("Improper type:  " + e);
                            in.nextLine(); // clear invalid input
                        }
                        break;

                    case "4": // update ISBN
                        System.out.println("Enter New Book ISBN:");
                        String bookISBN = in.nextLine().trim();
                        book.updateBook(Optional.empty(), Optional.empty(),
                                Optional.empty(), Optional.of(bookISBN),
                                Optional.empty(), Optional.empty());
                        break;

                    case "5": // update genre
                        System.out.println("Enter New Book Genre:");
                        String bookGenre = in.nextLine().trim();
                        book.updateBook(Optional.empty(), Optional.empty(),
                                Optional.empty(), Optional.empty(),
                                Optional.empty(), Optional.of(bookGenre));
                        break;

                    case "6": // mark unavailable
                        System.out.println("Book marked unavailable");
                        book.updateBook(Optional.empty(), Optional.empty(),
                                Optional.empty(), Optional.empty(),
                                Optional.of(false), Optional.empty());
                        break;

                    case "7": // mark available
                        System.out.println("Book marked available");
                        book.updateBook(Optional.empty(), Optional.empty(),
                                Optional.empty(), Optional.empty(),
                                Optional.of(true), Optional.empty());
                        break;

                    case "8": // print book info
                        System.out.println("Book Info:");
                        book.getBookInfo();
                        break;

                    default: // invalid option
                        System.out.println("Invalid option!");
                        break;
                }
            }
        }
    }

    public void removeBook(Scanner in, Library lib) {
        System.out.println("Enter Book Title");
        String input = in.nextLine().trim();
        String title = input.toLowerCase();
        Book book = lib.findBookIdByName(title);
        lib.removeBook(book);
        System.out.println("Book " + title + " has been removed\n\n");
    }

    public void addBook(Scanner scanner, Library lib) {
        // asks for all info about a book ands adds it to library book list
        // always sets new book to available and bookID is auto-generated by Book
        // constructor
        System.out.println("Enter Book Title");
        String input = scanner.nextLine().trim();
        String bookName = input.toLowerCase();

        System.out.println("Enter Book Author");
        input = scanner.nextLine().trim();
        String bookAuthor = input.toLowerCase();

        System.out.println("Enter Book Year");
        input = scanner.nextLine().trim();
        int bookYear = Integer.parseInt(input);

        System.out.println("Enter Book ISBN");
        String bookISBN = scanner.nextLine().trim();

        System.out.println("Enter Book Genre");
        input = scanner.nextLine().trim();
        String bookGenre = input.toLowerCase();

        Book book = new Book(bookName, bookAuthor, bookYear, bookISBN, bookGenre, lib);
        lib.addBook(book);
    }
}