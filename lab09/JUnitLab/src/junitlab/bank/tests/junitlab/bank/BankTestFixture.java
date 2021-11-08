package junitlab.bank;

import junitlab.bank.impl.GreatSavingsBank;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BankTestFixture {
    private Bank testBank;
    private String account1;
    private String account2;

    @Before
    public void setup() throws AccountNotExistsException {
        testBank = new GreatSavingsBank();
        account1 = testBank.openAccount();
        account2 = testBank.openAccount();
        testBank.deposit(account1 , 1500);
        testBank.deposit(account2 , 12000);
    }

    @Test
    public void testTransfer() throws NotEnoughFundsException, AccountNotExistsException {
        testBank.transfer(account2 , account1 , 3456);
        assertEquals(4956 , testBank.getBalance(account1));
        assertEquals(8544 , testBank.getBalance(account2));
    }

    @Test
    public void testTransferWithoutEnoughFunds(){
        assertThrows(NotEnoughFundsException.class , () -> testBank.transfer(account1 , account2 , 3456));
    }
}