package asm03.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SavingsAccountTest {
    private SavingsAccount savingsAccount;
    private SavingsAccount premiumSavingsAccount;

    @Before
    public void setUp() {
        savingsAccount = new SavingsAccount("123456", 7000000);
        premiumSavingsAccount = new SavingsAccount("234567", 10000000);
    }

    // Unit test cho phương thức IsAccountPremium()
    @Test
    public void testIsAccountPremium_BalanceAboveThreshold() {
        assertTrue(premiumSavingsAccount.isAccountPremium());
    }

    @Test
    public void testIsAccountPremium_BalanceBelowThreshold() {
        assertFalse(savingsAccount.isAccountPremium());
    }

    // Unit test cho phương thức withdraw() (tài khoản thường)
    @Test
    public void testWithdraw_ValidAmount() {
        assertTrue(savingsAccount.withdraw(4000000));
        assertEquals(3000000, savingsAccount.getInstantBalance(), 0.001);
    }

    @Test
    public void testWithdraw_InvalidAmount_BelowMinimum() {
        assertFalse(savingsAccount.withdraw(40000)); // Below 50,000
        assertEquals(7000000, savingsAccount.getBalance(), 0.001);
    }

    @Test
    public void testWithdraw_InvalidAmount_NotMultipleOf10000() {
        assertFalse(savingsAccount.withdraw(55555));
        assertEquals(7000000, savingsAccount.getBalance(), 0.001);
    }

    @Test
    public void testWithdraw_InvalidAmount_ExceedsMaximum() {
        assertFalse(savingsAccount.withdraw(6000000));
        assertEquals(7000000, savingsAccount.getBalance(), 0.001);
    }

    // Unit test cho phương thức withdraw() (tài khoản premium)
    @Test
    public void testWithdraw_Premium_ValidAmount() {
        assertTrue(premiumSavingsAccount.withdraw(6000000));
        assertEquals(4000000, premiumSavingsAccount.getInstantBalance(), 0.001);
    }

    @Test
    public void testWithdraw_Premium_InvalidAmount() {
        assertFalse(premiumSavingsAccount.withdraw(9960000));
        assertEquals(10000000, premiumSavingsAccount.getBalance(), 0.001);
    }

    // Unit test cho phương thức isAccepted()
    @Test
    public void testIsAccepted_ValidAmount() {
        assertTrue(savingsAccount.isAccepted(4000000));
    }

    @Test
    public void testIsAccepted_InvalidAmount_BelowMinBalance() {
        assertFalse(savingsAccount.isAccepted(6960000));
    }

    @Test
    public void testIsAccepted_InvalidAmount_Negative() {
        assertFalse(savingsAccount.isAccepted(-1000000));
    }
}
