package com.example;

public class LibraryAccounts {
    private double operatingCash = 39000.00;
    private final Purchasing purchasing;

    public LibraryAccounts(Purchasing purchasing){
        this.operatingCash = 39000.0;
        this.purchasing = purchasing;
    }

    //donations of money can be added
    public void addDonation(double amount){
        if (amount <= 0){
            System.out.println("Invalid donation amount.");
            return;
        }
        operatingCash += amount;
        System.out.printf("Donation of $%.2f added. New balance: $%.2f", amount, operatingCash);
    }

    //Librarian can withdraw their salary
    public boolean withdrawSalary(double amount){
        if(amount <= 0 || amount > operatingCash){
            System.out.println("Invalid amount");
            return false;
        }
        operatingCash -= amount;
        System.out.printf("Salary withdrawn: $%.2f. New balance: $%.2f", amount, operatingCash);
        return true;
    }

    public boolean orderBook(String title, double cost){
        if(cost <= 0 || cost > operatingCash){
            System.out.println("Invalid amount");
            return false;
        }
        operatingCash -= cost;
        purchasing.purchaseBook(title, cost);
        System.out.printf("Book ordered: %s. New balance: $%.2f", title, operatingCash);
        return true;
    }

    public double gerOperatingCash(){
        return operatingCash;
    }
}
