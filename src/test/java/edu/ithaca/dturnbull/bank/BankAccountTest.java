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

        //equivalence class - amount is smaller than balance
        bankAccount.withdraw(50);
        assertEquals(50, bankAccount.getBalance());
        //equivalence class - amount is larger than balance
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(100));
        //boundary case - amount is zero
        bankAccount.withdraw(0);
        assertEquals(50, bankAccount.getBalance());
        //equivalence class - amount is negative
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-10));
        //boundary case - amount is equal to balance
        bankAccount.withdraw(bankAccount.getBalance());
        assertEquals(0, bankAccount.getBalance());
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));
        assertFalse( BankAccount.isEmailValid(""));

        assertFalse(BankAccount.isEmailValid("testa-@c.com")); // prefix cannot end in a dash
        assertTrue(BankAccount.isEmailValid("testb-1@c.com")); // dash within prefix is valid
        assertFalse(BankAccount.isEmailValid("a!b@cs.cc")); // exclamation mark not allowed in prefix
        assertFalse(BankAccount.isEmailValid("a@b.c")); // last section of domain must have at least two characters
        assertFalse(BankAccount.isEmailValid("a@b..c")); // last section of domain must have at least two characters and consecutive periods not allowed
        assertFalse(BankAccount.isEmailValid("a..b@b.cc")); // consecutive periods not allowed in prefix
        assertFalse(BankAccount.isEmailValid(".a@b.com")); // prefix must begin with letter
        assertFalse(BankAccount.isEmailValid("a@b")); // last section of domain must have at least two characters
        assertFalse(BankAccount.isEmailValid("a@b!c.com")); // exclamation mark not allowed in domain
        assertTrue(BankAccount.isEmailValid("a@b-c.com")); // dash allowed in domain
        assertFalse(BankAccount.isEmailValid("a@b_c.com")); // underscore not allowed in domain
        assertTrue(BankAccount.isEmailValid("a_b@c.com")); // underscore allowed in prefix
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