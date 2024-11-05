package asm03.models;

import asm02.models.Account;
import asm02.models.Customer;

public class DigitalCustomer extends Customer {

    public DigitalCustomer(String name, String customerId) {
        super(name, customerId);
    }

    public boolean withdraw(String accountNumber, double amount) {
        for (Account account : getAccounts()) {
            if (account.getAccountNumber().equals(accountNumber)) {
                if (account instanceof SavingsAccount) {
                    SavingsAccount savingsAccount = (SavingsAccount) account;
                    if (savingsAccount.isAccepted(amount)) {
                        boolean success = savingsAccount.withdraw(amount);
                        if (success) {
                            savingsAccount.log(amount);
                            savingsAccount.updateBalance();
                        }
                        return success;
                    }
                } else if (account instanceof LoanAccount) {
                    LoanAccount loanAccount = (LoanAccount) account;
                    if (loanAccount.isAccepted(amount)) {
                        boolean success = loanAccount.withdraw(amount);
                        if (success) {
                            loanAccount.log(amount);
                            loanAccount.updateBalance();
                        }
                        return success;
                    }
                }
                return false;
            }
        }
        return false;
    }

    public Account getAccountByAccountNumber(String accountNumber) {
        for (Account account : getAccounts()) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    @Override
    public double getTotalAccountBalance() {
        return super.getTotalAccountBalance();
    }
}
