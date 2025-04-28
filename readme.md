Library Management and Testing System
=
**Overview**

This project simulates a Library Management System with built-in testing features.
It allows users to interact with the system as a librarian and also provides a testing suite to validate system functionality.

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