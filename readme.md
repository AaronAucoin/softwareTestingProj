Library Management and Testing System
=
**Overview**

This project simulates a Library Management System with built-in testing features.
It allows users to interact with the system as a full or part-time librarian and also provides a testing suite to validate system functionality.

**How to Run**

**Manual Testing: Launch the main class to interact with the system manually as a librarian.**

All main source code is located in the src/main/... directory.
Project configuration files (e.g., pom.xml for Maven) are at the root.
All test source code is located in the src/test/... directory.


## **LibraryAccounts Class**

The LibraryAccounts class manages the **book purchases, librarian salaries, and overall financials of the library.**
Maintains operating cash and works with **Purchasing class** and **Librarians class** to complete transactions.

features:

    Donation Management:
    Allows monetary donations to be added to current operating cash.

    Salary Withdrawals:
    Enables librarians to withdraw their salaries.

    Book Ordering:
    Librarians can order books and the cost is taken out of the operating cash.

    Balance Inquiry:
    Provides the current operating cash balance at any time.

Important Methods:

    addDonation(double amount) — Adds a donation if the amount is positive.

    withdrawSalary(String librarianName, double amount) — Allows a librarian to withdraw their salary.

    orderBook(String librarianName, Book book) — Places an order for a book, deducting its cost from operating cash if funds are sufficient.

    getOperatingCash() — Returns the current available cash.

    getLibrarians() — Returns the associated Librarians instance for further librarian operations.

Notes:

    Negative or invalid monetary operations return null or a descriptive error message.

    This class assumes that the Purchasing and Librarians classes are properly implemented to handle cost generation and salary/purchase recording.


## Librarians Class

A set of predefined librarians and access codes. Here salaries, book purchases, and accounts are managed.
Ties in to subclass 'Librarian' which is responsible for each librarians purchases, auth codes, and salaries.

Key Features:

    Authentication:
        Verify if a provided authentication code matches a librarian’s assigned code.

    Salary Management:
        Manage salary withdraws across librarians.

    Book Purchase Tracking:
        Record and retrieve books purchased by each librarian along with their cost.
        Get a list of purchased book names and total purchase value for a librarian.

Important Methods:

    getAllLibrariansInformation() — Returns all info on a given librarian.

    getLibrarianAuthenticationCode(String librarianName) — Returns librarian authentication code.

    checkLibrarianAuthenticationCode(String librarianName, short[] authenticationCode) — Verifies authentication code.

    getWithdrawnSalary(String librarianName) — Retrieves the total salary withdrawn by a librarian.

    addWithdrawnSalary(String librarianName, double amount) — Adds an amount to the librarian's total salary withdrawn.

    purchaseBook(String librarianName, Book book, double cost) — Records a book purchase for a librarian.

    getMapBooksPurchasedByLibrarian(String librarianName) — Returns all books and their costs purchased by a librarian.

    getListLibrarianPurchasedBooks(String librarianName) — Returns books purchased by a librarian.

    getLibrarianPurchasedBooksTotalValue(String librarianName) — Returns the total cost of all books purchased by a librarian.


## Purchasing Class

Responsible for generating the cost of books purchased. A random double between 10.00 and 100.0 is generated.

Key Features:

    Random Cost Generation:
        Generates a random double value representing the cost of a book. [between 10-100]

Important Methods:

    generateRandomBookCost() —
        Returns a double representing a randomly generated book price between $10 and $100.

## Library Class

It maintains a list of all books in the library, along with a mapping of loaned books to their respective members.

Key Features:
    
    Book Management:
        Add and remove books from the library.

    Retrieve a list of all books in the library.
        Generate unique IDs for new books.
    
    Member Management:
        Add and remove library members.

    Track member details (name, email, and ID).
        Retrieve member information by name or ID.

    Book Loaning:
        Checkout books to members, updating both the library and the member's records.
        Return books and remove them from the loaned books list.
        Check the availability of books and determine which member currently holds a specific book.

Important Methods:

    addBook(Book book): Adds a new book to the library's collection.

    removeBook(Book book): Removes a book from the library's collection.

    getAllBooks(): Returns a list of all books currently in the library.
    
    generateBookID(): Generates a new unique book ID for newly added books.
    
    checkoutBook(Book book, Member member): Allows a book to be checked out by a member.
    
    returnBook(Book book): Marks a book as returned and removes it from the loaned books list.
    
    addMember(String name, String email): Adds a new member to the library.
    
    removeMember(Member member): Removes a member from the library.
    
    getMember(String memberName, int memberID): Retrieves a member object based on their name and ID.
    
    bookAvailability(Book book): Returns whether a specific book is available for checkout.
    
    whoHasBook(Book book): Displays the member who currently holds a specified book.
    
    findBookIdByName(String title): Finds a book by its title and returns the corresponding Book object.

## Book Class
Represents a book in the library’s collection.

Key Features:
    
    Book Attributes:
        name: Title of the book.
        author: Author of the book.
        year: Year of publication.
        ISBN: Unique identifier for each edition of the book.
        bookID: A unique identifier for each book in the library (generated automatically).
        isAvailable: Indicates whether the book is available for loan.
        genre: Genre of the book (e.g., Fiction, Non-fiction, Science, etc.).
    
    Book Availability:
        Books can be checked for availability using the checkAvailability() method. If a book is not available, it means it has been loaned out to a member.

Important Methods:
    
    checkAvailability(): Returns whether the book is currently available for loan.

    getName(): Retrieves the name of the book.

    updateBook(Optional<String> name, Optional<String> author, Optional<Integer> year, Optional<String> ISBN, Optional<Boolean> isAvailable, Optional<String> genre): 
        Allows selective updating of a book’s attributes. If an argument is not provided, the corresponding attribute remains unchanged.
    
    getBookInfo(): Returns a list of the book’s details, including name, author, year, ISBN, book ID, availability, and genre. Also prints the details to the console.

## Member Class
Represents a library member who can borrow books.
Stores essential member information such as name, email, and a list of borrowed books. 

Key Features:
    
    name: Member’s legal name (e.g., "Reed, Steven").

    email: Member’s preferred email address (e.g., "sreed43@lsu.edu").

    memberID: Unique identifier for each member.

    borrowedBookList: List of books the member has currently borrowed.

Important Methods: 

    getMemberId(): Retrieves the member's unique identifier.
    
    getName(): Retrieves the member’s name.
    
    getEmail(): Retrieves the member’s email.
    
    addBorrowedBook(Book book): Adds a book to the member’s borrowed book list.
    
    removeBorrowedBook(Book book): Removes a book from the member’s borrowed book list.
    
    hasBorrowed(Book book): Checks if the member has borrowed a particular book.
    
    printMemberInfo(): Prints the member’s name, email, ID, and borrowed books.
    
    updateMemberInfo(String name, String email): Updates the member’s name and email.
    
    printBorrowedBooks(): Prints the list of books the member has borrowed.
    
    getBorrowedBooks(): Returns a list of the books the member has borrowed.