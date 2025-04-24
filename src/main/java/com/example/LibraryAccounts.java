package com.example;

public class LibraryAccounts {
    private double operatingCash = 39000.00;
    private final Purchasing purchasing;

    public LibraryAccounts(Purchasing purchasing){
        this.operatingCash = 39000.0;
        this.purchasing = purchasing;
    }

    //donations of money can be added
    // returns a string describing the money added and the new balance
    // returns null if a negative amount of money tries to be donated
    public String addDonation(double amount){
        if (amount <= 0){
            System.out.println("Invalid donation amount.");
            return null;
        }
        operatingCash += amount;
        return String.format("Donation of $%.2f added. New balance: $%.2f", amount, operatingCash);
    }

    // Librarian can withdraw their salary
    // String describing this action is returned
    // returns null if withdraw amount is negative
    public String withdrawSalary(double amount) {
        if(amount <= 0 || amount > operatingCash){
            System.out.println("Invalid amount");
            return null;
        }
        operatingCash -= amount;
        return String.format("Salary withdrawn: $%.2f. New balance: $%.2f", amount, operatingCash);
    }

    // Librarian can order a book
    // returns a string describing this order
    // returns an error string if the cost of the book is negative or is more than operating cash
    // the cost of the book could, alternatively, be randomly generated in this function
    public String orderBook(String title, double cost){
        if(cost <= 0){
            return "Negative book cost is invalid ";
        } else if (cost > operatingCash) {
            return "Book cost is higher than operating balance";
        }
        operatingCash -= cost;
        purchasing.purchaseBook(title, cost);
        return String.format("Book ordered: %s. New balance: $%.2f", title, operatingCash);
    }

    // returns the current operating cash balance
    public double gerOperatingCash(){
        return operatingCash;
    }
}
