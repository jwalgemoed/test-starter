package nl.sourcelabs.banking;

public class Account {

    private String accountnumber;
    private boolean negativeAmountAllowed;
    private int balance;

    public Account(String accountnumber, boolean negativeAmountAllowed, int balance) {
        this.accountnumber = accountnumber;
        this.negativeAmountAllowed = negativeAmountAllowed;
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void addBalance(final int amount) {
        balance += amount;
    }

    public void subtractBalance(final int amount) {
        if(!negativeAmountAllowed && balance - amount < 0) {
            throw new IllegalArgumentException("Balance not allowed to be negative.");
        }
        balance -= amount;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public boolean isNegativeAmountAllowed() {
        return negativeAmountAllowed;
    }
}
