package junitlab.bank;

import junitlab.bank.AccountNotExistsException;
import junitlab.bank.impl.GreatSavingsBank;
import org.junit.Test;

import static org.junit.Assert.*;

public class BankTest {
    @Test
    public void testOpenAccount() throws AccountNotExistsException {
        Bank test = new GreatSavingsBank();
        String accountNumber = test.openAccount();
        assertEquals(0 , test.getBalance(accountNumber));
    }

    @Test
    public void testUniqueAccount(){
        Bank test = new GreatSavingsBank();
        String accountNumber1 = test.openAccount();
        String accountNumber2 = test.openAccount();
        assertNotEquals(accountNumber1 , accountNumber2);
    }

    @Test
    public void testInvalidAccount(){
        Bank test = new GreatSavingsBank();
        String accountNumber = test.openAccount();
        assertThrows(AccountNotExistsException.class , () -> test.getBalance("randomAcount"));
    }

    @Test
    public void testDeposit() throws AccountNotExistsException {
        Bank test = new GreatSavingsBank();
        String accountNumber = test.openAccount();
        test.deposit(accountNumber , 2000);
        assertEquals(2000 , test.getBalance(accountNumber));
    }

//    @Test
//    public void testCloseAccount() {
//    }
//
//    @Test
//    public void testGetBalance() {
//    }
//
//
//    @Test
//    public void testWithdraw() {
//    }
//
//    @Test
//    public void testTransfer() {
//    }
}