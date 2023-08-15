package atm;

//Transaction class implements the Runnable interface, which means it can be used with threads.
public class Transaction implements Runnable {
	// An Account object which this transaction will operate on.
	Account account;
	// A string to specify whether this transaction is a withdrawal or deposit.
	String trans_conditions;
	// The amount of cash to be withdrawn or deposited.
	int cash;

	// Constructor for the Transaction class.
	public Transaction(Account account, String trans_conditions, int cash) {
		// 'this' is used to refer to the current object's variables. It helps distinguish between instance variables and parameters.
		this.account = account;
		this.trans_conditions = trans_conditions;
		this.cash = cash;
	}
	// The run method contains the code that will be executed when the Transaction thread starts.
	public void run() {
		// Check whether this transaction is a withdrawal, deposit or getting the balance.
		if (trans_conditions.equals("withdraw")) {
			// If the transaction is a withdrawal, call the withdraw method on the Account object.
			this.account.withdraw(cash);
		} else if (trans_conditions.equals("deposit")) {
			// If the transaction is a deposit, call the deposit method on the Account object.
			this.account.deposit(cash);
		} else {
			// If the transaction is neither a withdrawal or deposit, get the current balance of the account.
			this.account.getBalance();
		}
	}
}
