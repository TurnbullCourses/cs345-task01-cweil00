package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);

        //equivalence case - money in bank account
        assertEquals(200, bankAccount.getBalance());
        //boundary case - no money in bank account
        BankAccount bankAccount1 = new BankAccount("c@d.com", 0);
        assertEquals(0, bankAccount1.getBalance());
    }

    @Test
    void getEmailTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 100); //only one equivalence class
        assertEquals("a@b.com", bankAccount.getEmail());
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

        BankAccount bankAccount1 = new BankAccount("A@b.com", 5);
        assertThrows(IllegalArgumentException.class, ()-> bankAccount1.withdraw(0.001));
    }

    @Test
    void depositTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        //equivalence class - amount is positive
        bankAccount.deposit(100);
        assertEquals(300, bankAccount.getBalance());
        //boundary case - amount is zero
        bankAccount.deposit(0);
        assertEquals(300, bankAccount.getBalance());
        //equivalence class - amount is negative
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-10));
        //equivalence class - amount has more than two decimals
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(0.001));
        //equivalence class - amout has less than two decimals
        bankAccount.deposit(0.50);
        assertEquals(300.50, bankAccount.getBalance());
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

        //equivalence case - invalid character at beginning of prefix
        assertFalse(BankAccount.isEmailValid("#test@test.com"));
        //equivalence case - invalid character in middle of prefix
        assertFalse(BankAccount.isEmailValid("te#st@test.com"));
        //equivalence case - invalid character at end of prefix
        assertFalse(BankAccount.isEmailValid("test#@test.com"));
        //equivalence case - dash, dot, or underscore followed by a letter or number in prefix
        assertTrue(BankAccount.isEmailValid("te-st@test.com"));
        //equivalence case - dash, dot, or underscore not followed by a letter or number in prefix
        assertFalse(BankAccount.isEmailValid("test_@test.com"));
        //equivalence case - more than 2 dots, dashes, underscores in a row in prefix
        assertFalse(BankAccount.isEmailValid("tes-.-t@test.com"));
        //boundary case - 2 dots, dashes, or underscores in a row in prefix
        assertFalse(BankAccount.isEmailValid("t__est@test.com"));
        //equivalence case - less than two characters in last portion of domain
        assertFalse(BankAccount.isEmailValid("test@test.c"));
        //boundary case - 2 characters in last portion of domain
        assertTrue(BankAccount.isEmailValid("test@test.co"));
        //equivalence case - more than two characters in last portion of domain
        assertTrue(BankAccount.isEmailValid("test@test.com"));
        //equivalence case - invalid character at beginning of domain
        assertFalse(BankAccount.isEmailValid("test@#test.com"));
        //equivalence case - invalid character in middle of domain
        assertFalse(BankAccount.isEmailValid("test@te#st.com"));
        //equivalence case - invalid character at end of domain
        assertFalse(BankAccount.isEmailValid("test@test.com#"));
        //equivalence case - dash not followed by a letter or number in domain
        assertFalse(BankAccount.isEmailValid("test@test.com-"));
        //equivalence case - more than 2 dashes in a row in domain
        assertFalse(BankAccount.isEmailValid("test@te---st.com"));
        //boundary case - 2 dashes in a row in domain
        assertFalse(BankAccount.isEmailValid("test@test.c--om"));

    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));

        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -100));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 100.005));
    }

    @Test
    void isAmountValidTest() {
        //amount is negative
        assertFalse(BankAccount.isAmountValid(-10));
        //amount is positive and has more than two decimal places
        assertFalse(BankAccount.isAmountValid(10.001));
        //amount is positive and has two decimal places
        assertTrue(BankAccount.isAmountValid(10.10));
        //amount is positive and has less than two decimal places
        assertTrue(BankAccount.isAmountValid(10.0));
        //amount is positive and has no decimal places
        assertTrue(BankAccount.isAmountValid(10));
    }

    @Test
    void transferTest() throws InsufficientFundsException {
        BankAccount myAccount = new BankAccount("a@b.com", 1000);
        BankAccount otherAccount = new BankAccount("c@d.com", 0);

        //equivlance class - transfer money that is less than balance of my account
        myAccount.transfer(otherAccount, 500);
        assertEquals(500, myAccount.getBalance());
        assertEquals(500, otherAccount.getBalance());
        //equivalence class - transfer money that is more than balance of my account
        assertThrows(InsufficientFundsException.class, ()-> myAccount.transfer(otherAccount, 1000));
        //equivalance class - transfer negative amount of money
        assertThrows(IllegalArgumentException.class, ()-> myAccount.transfer(otherAccount, -100));
        //equivalance class - transfer an invalid amount of money
        assertThrows(IllegalArgumentException.class, ()-> myAccount.transfer(otherAccount, 100.00501));
        //equivalance class - other bank account does not exist
        assertThrows(IllegalArgumentException.class, ()-> myAccount.transfer(null, 100));
    }

}