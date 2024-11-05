package asm03.models;

import asm02.models.Account;
import asm02.models.Asm02;

import java.util.List;

public class SavingsAccount extends Account implements ReportService, Withdraw {

    public static SavingsAccount[] account;
    private static final double SAVINGS_ACCOUNT_MAX_WITHDRAW = 5_000_000;

    public SavingsAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    public static SavingsAccount[] getAccount() {
        return account;
    }

    public static void setAccount(SavingsAccount[] account) {
        SavingsAccount.account = account;
    }

    public boolean isAccountPremium() {
        return super.isPremiumAccount();
    }

    @Override
    public boolean withdraw(double amount) {
        if (isAccepted(amount)) {
            setInstantBalance(getBalance() - amount);
            Transaction transaction = new Transaction(getAccountNumber(), amount);
            addTransaction(transaction);
            return true;
        } else {
            System.out.println("So tien rut khong hop le hoac vuot qua han muc.");
            return false;
        }
    }

    @Override
    public boolean isAccepted(double amount) {
        return amount >= 50_000 && amount % 10_000 == 0 &&
                (isAccountPremium() || amount <= SAVINGS_ACCOUNT_MAX_WITHDRAW) &&
                getBalance() - amount >= 50_000;
    }

    public double getTransactionFee() {
        return 0;
    }

    @Override
    public List<Transaction> getTransactions() {
        return super.getTransactions();
    }

    @Override
    public void log(double amount) {
        System.out.println(Asm03.getDivider());
        System.out.printf("%30s%n", getTitle());
        System.out.printf("Ngay G/D: %28s%n", Transaction.getDateTime());
        System.out.printf("ATM ID: %30s%n", "DIGITAL-BANK-ATM 2022");
        System.out.printf("SO TK: %31s%n", getAccountNumber());
        System.out.printf("SO TIEN: %29s%n", Asm02.formatBalance(amount));
        System.out.printf("SO DU: %31s%n",Asm02.formatBalance(getInstantBalance()));
        System.out.printf("PHI + VAT: %27s%n", Asm02.formatBalance(getTransactionFee()));
        System.out.println(Asm03.getDivider());
    }
}
