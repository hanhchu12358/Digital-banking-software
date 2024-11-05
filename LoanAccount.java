package asm03.models;

import asm02.models.Account;
import asm02.models.Asm02;

import java.util.List;

public class LoanAccount extends Account implements ReportService, Withdraw {
    private static LoanAccount[] account;
    private double lastTransactionFee;
    private static final double LOAN_ACCOUNT_WITHDRAW_FEE = 0.05;
    private static final double LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE = 0.01;
    private static final double LOAN_ACCOUNT_MAX_BALANCE = 100_000_000;
    private static final double LOAN_ACCOUNT_MIN_BALANCE = 50_000;

    public LoanAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    public static LoanAccount[] getAccount() {
        return account;
    }

    public static void setAccount(LoanAccount[] account) {
        LoanAccount.account = account;
    }

    public boolean isAccountPremium() {
        return super.isPremiumAccount();
    }

    public double getLastTransactionFee() {
        return lastTransactionFee;
    }

    public void setLastTransactionFee(double fee) {
        this.lastTransactionFee = fee;
    }

    @Override
    public List<Transaction> getTransactions() {
        return super.getTransactions();
    }

    @Override
    public boolean withdraw(double amount) {
        if (isAccepted(amount)) {
            double transactionFee = getTransactionFee(amount);
            setLastTransactionFee(transactionFee);
            double totalAmount = amount + transactionFee;
            setInstantBalance(getBalance() - totalAmount);
            Transaction transaction = new Transaction(getAccountNumber(), amount);
            addTransaction(transaction);
            return true;
        } else {
            System.out.println("So tien rut khong hop le hoac vuot qua han muc.");
            return false;
        }
    }

    public double getTransactionFee(double amount) {
        double feeRate = isAccountPremium() ? LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE : LOAN_ACCOUNT_WITHDRAW_FEE;
        return amount * feeRate;
    }

    @Override
    public boolean isAccepted(double amount) {
        return amount > 0 && (getBalance() - amount - getTransactionFee(amount)) >= LOAN_ACCOUNT_MIN_BALANCE && amount <= LOAN_ACCOUNT_MAX_BALANCE;
    }

    @Override
    public void log(double amount) {
        System.out.println(Asm03.getDivider());
        System.out.printf("%30s%n", getTitle());
        System.out.printf("Ngay G/D: %28s%n", Transaction.getDateTime());
        System.out.printf("ATM ID: %30s%n", "DIGITAL-BANK-ATM 2022");
        System.out.printf("SO TK: %31s%n", getAccountNumber());
        System.out.printf("SO TIEN: %29s%n", Asm02.formatBalance(amount));
        System.out.printf("SO DU: %31s%n", Asm02.formatBalance(getInstantBalance()));
        System.out.printf("PHI + VAT: %27s%n", Asm02.formatBalance(getLastTransactionFee()));
        System.out.println(Asm03.getDivider());
    }
}
