package asm03.models;

import asm02.models.Account;
import asm02.models.Bank;
import asm02.models.Customer;

public class DigitalBank extends Bank {
    public DigitalBank() {
    }

    @Override
    public Customer getCustomerById(String customerId) {
        return super.getCustomerById(customerId);
    }

    @Override
    public void addCustomer(String customerId, String name) {
        if (!isCustomerExisted(customerId)) {
            Customer newCustomer = new DigitalCustomer(name, customerId);
            customers.add(newCustomer);
        }
    }

    @Override
    public void addAccount(String customerId, Account account) {
        super.addAccount(customerId, account);
    }

    public boolean withdraw(String customerId, String accountNumber, double amount) {
        Customer customer = getCustomerById(customerId);
        if (customer == null) {
            System.out.println("Khong tim thay khach hang voi CCCD da nhap.");
            return false;
        }

        if (!(customer instanceof DigitalCustomer)) {
            System.out.println("Khach hang khong ho tro giao dich rut tien.");
            return false;
        }

        DigitalCustomer digitalCustomer = (DigitalCustomer) customer;
        boolean success = digitalCustomer.withdraw(accountNumber, amount);

        if (!success) {
            Account account = digitalCustomer.getAccountByAccountNumber(accountNumber);
            if (account == null) {
                System.out.println("So tai khoan khong hop le.");
            } else if (account instanceof SavingsAccount || account instanceof LoanAccount) {
                System.out.println("So tien rut vuot qua han muc hoac khong du so du.");
            } else {
                System.out.println("Loai tai khoan khong ho tro rut tien.");
            }
        }

        return success;
    }
}
