package asm03.models;

import asm02.models.Account;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DigitalCustomerTest {
    private DigitalCustomer customer;
    private Account account1;
    private Account account2;
    private Account account3;

    @Before
    public void setUp() {
        customer = new DigitalCustomer("Hanh", "027197010353");

        // Tạo các tài khoản mẫu
        account1 = new SavingsAccount("123456", 1000000);
        account2 = new LoanAccount("234567", 5000000);
        account3 = new SavingsAccount("345678", 2000000);

        // Thêm các tài khoản vào danh sách tài khoản của khách hàng
        customer.addAccount(account1);
        customer.addAccount(account2);
        customer.addAccount(account3);
    }

    // Unit test cho phương thức getAccountByAccountNumber()
    @Test
    public void testGetAccountByAccountNumber_ExistingAccount() {
        Account result = customer.getAccountByAccountNumber("123456");
        assertNotNull(result);
        assertEquals(account1, result);
    }

    @Test
    public void testGetAccountByAccountNumber_NonExistingAccount() {
        Account result = customer.getAccountByAccountNumber("123457");
        assertNull(result);
    }

    @Test
    public void testGetAccountByAccountNumber_NullAccountNumber() {
        Account result = customer.getAccountByAccountNumber(null);
        assertNull(result);
    }

    @Test
    public void testGetAccountByAccountNumber_EmptyAccountNumber() {
        Account result = customer.getAccountByAccountNumber("");
        assertNull(result);
    }

    @Test
    public void testGetAccountByAccountNumber_MultipleAccounts() {
        Account result1 = customer.getAccountByAccountNumber("123456");
        Account result2 = customer.getAccountByAccountNumber("234567");
        Account result3 = customer.getAccountByAccountNumber("345678");

        assertNotNull(result1);
        assertNotNull(result2);
        assertNotNull(result3);

        assertEquals(account1, result1);
        assertEquals(account2, result2);
        assertEquals(account3, result3);
    }
}
