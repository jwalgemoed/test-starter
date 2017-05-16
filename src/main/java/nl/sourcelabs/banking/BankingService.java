package nl.sourcelabs.banking;

import java.sql.SQLException;

/**
 * Crazy bankingservice implementation. Not optimal or realistic but a good base
 * for some unit testing and mocking.
 */
public class BankingService {

    private BankingRepository bankingRepository;

    public void withdraw(final Account from, final int amount) throws SQLException {
        // Subtract the balance
        from.subtractBalance(amount);
        try {
            // Try to save
            bankingRepository.saveAccount(from);
        } catch (final SQLException se) {
            // Unable to persist? Restore the balance
            from.addBalance(amount);
        }
    }

    public void deposit(final Account from, final int amount) throws SQLException {
        // Add to the balance
        from.addBalance(amount);

        try {
            // Try to save
            bankingRepository.saveAccount(from);
        } catch (final SQLException se) {
            // Unable to persist? Restore the balance
            from.subtractBalance(amount);
        }
    }

    public void transferAmount(final Account from, final Account to, final int amount) throws SQLException {
        // Subtract the balance
        from.subtractBalance(amount);
        try {
            // Try to save
            bankingRepository.saveAccount(from);
        } catch (final SQLException se) {
            // Unable to persist? Restore the balance
            from.addBalance(amount);
            return; // Exit to prevent further processing
        }

        // add the balance
        to.addBalance(amount);
        try {
            // Try to save
            bankingRepository.saveAccount(to);
        } catch (final SQLException se) {
            // Unable to persist? Restore the balance
            to.subtractBalance(amount);
            from.addBalance(amount);
        }
    }
    
    public Account find(final String accountNumber) throws SQLException {
        return bankingRepository.findAccount(accountNumber);
    }
}
