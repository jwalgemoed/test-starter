package nl.sourcelabs.banking;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BankingServiceTest {
    
    @InjectMocks
    private BankingService bankingService;
    
    @Mock
    private BankingRepository bankingRepository;
    
    @Test
    public void testDepositOk() throws SQLException {
        final Account account = new Account("NUMBER1", true, 100);
        
        bankingService.deposit(account, 100);
        
        verify(bankingRepository, times(1)).saveAccount(account);
        assertEquals(200, account.getBalance());
    }

    @Test
    public void testDepositError() throws SQLException {
        final Account account = new Account("NUMBER1", true, 100);
        
        doThrow(SQLException.class).when(bankingRepository).saveAccount(account);
        
        bankingService.deposit(account, 100);

        verify(bankingRepository, times(1)).saveAccount(account);
        assertEquals(100, account.getBalance());
    }
    
    @Test
    public void testWithdrawalOk() throws SQLException {
        final Account account = new Account("NUMBER1", true, 100);
        
        bankingService.withdraw(account, 150);
        
        verify(bankingRepository, times(1)).saveAccount(account);
        assertEquals(-50, account.getBalance());
    }

    @Test
    public void testWithdrawalError() throws SQLException {
        final Account account = new Account("NUMBER1", true, 100);

        doThrow(SQLException.class).when(bankingRepository).saveAccount(account);
        
        bankingService.withdraw(account, 150);

        verify(bankingRepository, times(1)).saveAccount(account);
        assertEquals(100, account.getBalance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawalNotAllowed() throws SQLException {
        final Account account = new Account("NUMBER1", false, 100);

        doThrow(SQLException.class).when(bankingRepository).saveAccount(account);

        bankingService.withdraw(account, 150);
    }
    
    @Test
    public void testTransferOk() throws SQLException {
        final Account source = new Account("NUMBER1", false, 100);
        final Account target = new Account("NUMBER2", false, 100);
        
        bankingService.transferAmount(source, target, 50);

        verify(bankingRepository, times(1)).saveAccount(source);
        verify(bankingRepository, times(1)).saveAccount(target);
        
        assertEquals(50, source.getBalance());
        assertEquals(150, target.getBalance());
    }

    @Test
    public void testTransferOkNegativeAmount() throws SQLException {
        final Account source = new Account("NUMBER1", true, 100);
        final Account target = new Account("NUMBER2", false, 100);

        bankingService.transferAmount(source, target, 150);

        verify(bankingRepository, times(1)).saveAccount(source);
        verify(bankingRepository, times(1)).saveAccount(target);

        assertEquals(-50, source.getBalance());
        assertEquals(250, target.getBalance());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferNotOk() throws SQLException {
        final Account source = new Account("NUMBER1", false, 100);
        final Account target = new Account("NUMBER2", false, 100);

        bankingService.transferAmount(source, target, 150);
    }

    @Test
    public void testTransferError() throws SQLException {
        final Account source = new Account("NUMBER1", true, 100);
        final Account target = new Account("NUMBER2", false, 100);

        doThrow(SQLException.class).when(bankingRepository).saveAccount(source);
        
        bankingService.transferAmount(source, target, 150);

        verify(bankingRepository, times(1)).saveAccount(source);
        verify(bankingRepository, times(0)).saveAccount(target);

        assertEquals(100, source.getBalance());
        assertEquals(100, target.getBalance());
    }

    @Test
    public void testTransferErrorTwo() throws SQLException {
        final Account source = new Account("NUMBER1", true, 100);
        final Account target = new Account("NUMBER2", false, 100);

        doThrow(SQLException.class).when(bankingRepository).saveAccount(target);

        bankingService.transferAmount(source, target, 150);

        verify(bankingRepository, times(1)).saveAccount(source);
        verify(bankingRepository, times(1)).saveAccount(target);

        assertEquals(100, source.getBalance());
        assertEquals(100, target.getBalance());
    }
}