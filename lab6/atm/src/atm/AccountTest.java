package atm;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AccountTest {
    public static void main(String[] args) {
        Account hoiAccount = new Account(301249275, "HOI", 100);
        // Display account details
        System.out.println("Welcome back to your bank account! " + hoiAccount.getName() + "!");
        System.out.println("Account Number: " + hoiAccount.getAccountNumber());
        System.out.println("Initial Balance: " + hoiAccount.getBalance());
        System.out.println("\n----------------------------------------------");

        //ArrayList of Transactions
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(hoiAccount, "deposit", 50));
        transactions.add(new Transaction(hoiAccount, "withdraw", 200));
        transactions.add(new Transaction(hoiAccount, "deposit", 1000));
        transactions.add(new Transaction(hoiAccount, "withdraw", 67));
        
        //ExecutorService
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        int i = 0;
        do {
            executorService.execute(transactions.get(i));
            i++;
        } while (i < transactions.size());
        executorService.shutdown();
    }
}
