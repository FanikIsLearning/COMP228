package atm;

// Account class: It represents a bank account with balance and account holder's name.
public class Account {
	 // These are the instance variables. Each Account object will have its own copy of these.
    int balance;
    String name;
    // 'final' keyword indicates that the accountNumber can only be set once and cannot be changed.
    final int account_number;
    // This is the constructor. It's called when a new Account object is created.
    public Account(int account_number, String name, int initialBalance) {
    	// 'this' refers to the current object. It's used to distinguish between instance variables and parameters.
        this.account_number = account_number;
        this.balance = initialBalance;
        this.name = name;
    }
    // Getter method for balance.
    public int getBalance() {
        return balance;
    }
    // Setter method for balance.
    public void setBalance(int balance) {
        this.balance = balance;
    }
    // Getter method for name.
    public String getName() {
        return name;
    }
    // Setter method for name.
    public void setName(String name) {
        this.name = name;
    }
    // Getter method for accountNumber.
    public int getAccountNumber() {
        return account_number;
    }
    // Method for depositing cash into the account.
    // 'synchronized' keyword ensures that only one thread can execute this method at a time, preventing race conditions.
    public synchronized void deposit(int cash) {
    	// Displaying message and simulating processing time.
        System.out.println("\nTransaction is processing, please wait ...");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Adding cash to the balance.
        this.balance += cash;
        // Displaying success message.
        System.out.println("Deposited cash:" + cash);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Your account has $" + balance);
        System.out.println("Transaction completed.\n");
        System.out.println("----------------------------------------------");
    }
    // Method for withdrawing cash from the account.
    // If the withdrawal amount is more than the balance, the transaction is cancelled.
    public synchronized void withdraw(int cash) {
        // Displaying message and simulating processing time.
        System.out.println("\nTransaction is processing, please wait ...");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Checking if there's enough balance for the withdrawal.
        if (cash > this.balance) {
            System.out.println("Transaction cancelled: withdrawal exceeds current account balance.\n");
            System.out.println("----------------------------------------------");
            return;
        }
        // Subtracting cash from the balance.
        this.balance -= cash;
        System.out.println("Withdraw cash:" + cash);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Your account has $" + balance);
        System.out.println("Transaction completed.\n");
        System.out.println("----------------------------------------------");
    }
}
