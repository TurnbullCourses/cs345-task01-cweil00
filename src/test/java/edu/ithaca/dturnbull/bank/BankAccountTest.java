package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));
        assertFalse( BankAccount.isEmailValid(""));

        assertFalse(BankAccount.isEmailValid("testa-@c.com"));
        assertTrue(BankAccount.isEmailValid("testb-1@c.com"));
        assertFalse(BankAccount.isEmailValid("a!b@cs.cc"));
        assertFalse(BankAccount.isEmailValid("a@b.c"));
        assertFalse(BankAccount.isEmailValid("a@b..c"));
        assertFalse(BankAccount.isEmailValid("a..b@b.cc"));
        assertFalse(BankAccount.isEmailValid(".a@b.com"));
        assertFalse(BankAccount.isEmailValid("a@b"));
        assertFalse(BankAccount.isEmailValid("a@b!c.com"));
        assertTrue(BankAccount.isEmailValid("a@b-c.com"));
        assertFalse(BankAccount.isEmailValid("a@b_c.com"));
        assertTrue(BankAccount.isEmailValid("a_b@c.com"));
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}