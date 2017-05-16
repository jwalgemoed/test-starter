package nl.sourcelabs.banking;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface BankingRepository {

    public void saveAccount(final Account account) throws SQLException;
    
    public Account findAccount(final String accountname) throws SQLException;
    
}
