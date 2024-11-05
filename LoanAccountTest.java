package asm03.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoanAccountTest {
    private LoanAccount loanAccount;
    private LoanAccount premiumLoanAccount;

    @Before
    public void setUp() {
        loanAccount = new LoanAccount("123456", 5000000);
        premiumLoanAccount = new LoanAccount("678910", 10000000);
    }

    // Unit test cho phương thức IsAccountPremium()
    @Test
    public void testIsAccountPremium_BalanceAboveThreshold() {
        assertTrue(premiumLoanAccount.isAccountPremium());
    }

    @Test
    public void testIsAccountPremium_BalanceBelowThreshold() {
        assertFalse(loanAccount.isAccountPremium());
    }

    // Unit test cho phương thức withdraw() (tài khoản thường)
    @Test
    public void testWithdraw_ValidAmount() {
        assertTrue(loanAccount.withdraw(1000000));
        assertEquals(3950000, loanAccount.getInstantBalance(), 0.001);
        assertEquals(50000, loanAccount.getLastTransactionFee(), 0.001);
    }

    @Test
    public void testWithdraw_InvalidAmount() {
        assertFalse(loanAccount.withdraw(4750000));
        assertEquals(5000000, loanAccount.getBalance(), 0.001);
    }

    // Unit test cho phương thức withdraw() (tài khoản premium)
    @Test
    public void testWithdraw_Premium_ValidAmount() {
        assertTrue(premiumLoanAccount.withdraw(5000000));
        assertEquals(4950000, premiumLoanAccount.getInstantBalance(), 0.001);
        assertEquals(50000, premiumLoanAccount.getLastTransactionFee(), 0.001);
    }

    @Test
    public void testWithdraw_Premium_InvalidAmount() {
        assertFalse(premiumLoanAccount.withdraw(9900000));
        assertEquals(10000000, premiumLoanAccount.getBalance(), 0.001);
    }

    // Unit test cho phương thức isAccepted()
    @Test
    public void testIsAccepted_ValidAmount() {
        assertTrue(loanAccount.isAccepted(4000000));
    }

    @Test
    public void testIsAccepted_InvalidAmount_BelowMinBalance() {
        assertFalse(loanAccount.isAccepted(4750000));
    }

    @Test
    public void testIsAccepted_InvalidAmount_Negative() {
        assertFalse(loanAccount.isAccepted(-1000000));
    }

    @Test
    public void testIsAccepted_InvalidAmount_AboveMaxBalance() {
        assertFalse(loanAccount.isAccepted(100000001));
    }

    // Unit test cho phương thức getTransactionFee()
    @Test
    public void testGetTransactionFee_NormalAccount() {
        assertEquals(50000, loanAccount.getTransactionFee(1000000), 0.001);
    }

    @Test
    public void testGetTransactionFee_PremiumAccount() {
        assertEquals(10000, premiumLoanAccount.getTransactionFee(1000000), 0.001);
    }
}
