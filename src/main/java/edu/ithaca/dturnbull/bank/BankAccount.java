package edu.ithaca.dturnbull.bank;;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email or startingBalance is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){ //make sure email is valid
            this.email = email;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
        if (isAmountValid(startingBalance)){ //make sure startingBalance is valid
            this.balance = startingBalance;
        }
        else{
            throw new IllegalArgumentException("Amount is not valid");
        }
    }

    /**
     * @return balance of bank account
     */
    public double getBalance(){
        return balance;
    }

    /**
     * @return email associated with bank account
     */
    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * If amount larger than balance:
     * @throws InsufficentFundsException
     * If amount is negative:
     * @throws IllegalArgumentException
     */
    public void withdraw (double amount) throws InsufficientFundsException, IllegalArgumentException{
        if (!isAmountValid(amount)) //makes sure amount is valid
            throw new IllegalArgumentException("Cannot withdraw negative amount");
        else if (amount <= balance) //withdraw money if amount is below balance
            balance -= amount;
        else
            throw new InsufficientFundsException("Not enough money");
    }


    public static boolean isEmailValid(String email){
        if (email.indexOf('@') == -1){
            return false;
        }

        else {
            String validChars = "abcdefghijklmnopqrstuvwxyz1234567890"; // all valid chars
            String specialChars = "_-.";

            String[] splitEmail = email.split("@"); // separate prefix and domain into diff arrays
            String prefix = splitEmail[0];
            String domain = splitEmail[1];
            prefix = prefix.toLowerCase(); // normalizing prefix
            domain = domain.toLowerCase(); // normalizing domain

            // first char of prefix must be in alphabet
            if (!validChars.substring(0, 26).contains(String.valueOf(prefix.charAt(0)))){
                return false;
            }

            // last char of prefix must be in validChars
            if (!validChars.contains(String.valueOf(prefix.charAt(prefix.length()-1)))){
                return false;
            }
            
            for (int i = 1; i < prefix.length(); i++){
                String tempChar = String.valueOf(prefix.charAt(i));
                
                // is rest of prefix valid
                if (!validChars.contains(tempChar) && !specialChars.contains(tempChar)){ 
                    return false;
                }
                else if (tempChar.equals(".") || tempChar.equals("_") || tempChar.equals("-")){
                    
                    // no consecutive symbols
                    if (!validChars.contains(String.valueOf(prefix.charAt(i+1)))){ 
                        return false;
                    }
                }
            }
            // does domain have '.'
            if (domain.indexOf('.') == -1){ 
                return false;
            }
            else{
                String[] domainSplit = domain.split("\\."); // split domain into before and after '.'
                String domain1 = domainSplit[0];
                String domain2 = domainSplit[1];
                // make sure the end has 2 or more characters
                if (domain2.length() < 2){ 
                    return false;
                }
                // validation of domain's first part
                for (int i = 0; i < domain1.length(); i++){
                    String tempChar = String.valueOf(domain1.charAt(i));
                    if (!validChars.contains(tempChar) && !tempChar.equals("-")){
                        return false;
                    }
                    else if (tempChar.equals("-")){
                    
                        // no consecutive symbols
                        if (!validChars.contains(String.valueOf(domain1.charAt(i+1)))){ 
                            return false;
                        }
                    }
                }
                // validation of domain's second part
                for (int i = 0; i < domain2.length() - 1; i++){
                    String tempChar = String.valueOf(domain2.charAt(i));
                    if (!validChars.contains(tempChar) && !tempChar.equals("-")){
                        return false;
                    }
                    else if (tempChar.equals("-")){
                    
                        // no consecutive symbols
                        if (!validChars.contains(String.valueOf(domain2.charAt(i+1)))){ 
                            return false;
                        }
                    }
                }

                if (!validChars.contains(String.valueOf(domain2.charAt(domain2.length()-1)))){
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * If an amount is positive and has 2 or less decimal points, returns true
     * Otherwise returns false
     * @param amount
     * @return boolean indicating if the amount is valid or not
     */
    public static boolean isAmountValid(double amount){
        if (amount < 0.0){ //checks if amount is negative
            return false;
        }
        else{
            String temp = Double.toString(amount); //put amount to string
            int intPlaces = temp.indexOf('.'); //get . position
            if ((temp.length() - intPlaces - 1) > 2){ //figure out number of decimals and check if more than 2
                return false;
            }
            else{
                return true;
            }
        }
    }

    /**
     * Adds money to the bank account
     * @param amount double, amount of money to add
     * @throws IllegalArgumentException if amount is invalid
     */
    public void deposit(double amount) throws IllegalArgumentException{
        if (isAmountValid(amount)){ //make sure amount is valid
            balance = balance + amount;
        }
        else{
            throw new IllegalArgumentException("Amount is invalid");
        }
    }

    /**
     * Transfers money from your account to a different bank account
     * @param otherAccount account to transfer money to
     * @param amount amount of money to transfer
     * @throws IllegalArgumentException
     * @throws InsufficientFundsException
     */
    public void transfer(BankAccount otherAccount, double amount) throws IllegalArgumentException, InsufficientFundsException {
        if (!isAmountValid(amount) || otherAccount == null){
            throw new IllegalArgumentException(); //make sure other bank account exists
        }
        else{
            this.withdraw(amount); //transfer the money
            otherAccount.deposit(amount);
        }
    }
}