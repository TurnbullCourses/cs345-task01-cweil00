package edu.ithaca.dturnbull.bank;;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException, IllegalArgumentException{
        if (amount < 0)
            throw new IllegalArgumentException("Cannot withdraw negative amount");
        else if (amount <= balance)
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
                }
                // validation of domain's second part
                for (int i = 0; i < domain2.length(); i++){
                    String tempChar = String.valueOf(domain2.charAt(i));
                    if (!validChars.contains(tempChar) && !tempChar.equals("-")){
                        return false;
                    }
                }
            }
            return true;
        }
    }
}