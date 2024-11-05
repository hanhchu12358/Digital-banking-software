package asm03.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Transaction {
    private final String id;
    private final String accountNumber;
    private final double amount;
    private boolean isInitial;
    private final String time;
    private boolean status;

    public Transaction(String accountNumber, double amount) {
        this.id = generateId();
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.time = getDateTime();
        this.status = false; // Mặc định giao dịch chưa thành công
    }

    public Transaction(String accountNumber, double amount, boolean isInitial) {
        this(accountNumber, amount);
        this.isInitial = isInitial;
    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime = new Date();
        return dateFormat.format(currentTime);
    }

    public String getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isInitial() {
        return isInitial;
    }

    public String getTime() {
        return time;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
