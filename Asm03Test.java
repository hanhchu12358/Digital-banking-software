package asm03.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Asm03Test {

    @Before
    public void setUp() {
        // Thiết lập dữ liệu mẫu cho các test
        SavingsAccount[] savingsAccount = new SavingsAccount[1];
        savingsAccount[0] = new SavingsAccount("123456", 1000000);
        SavingsAccount.setAccount(savingsAccount);

        LoanAccount[] loanAccount = new LoanAccount[1];
        loanAccount[0] = new LoanAccount("345678", 3000000);
        LoanAccount.setAccount(loanAccount);
    }

    // Unit test cho phương thức isAccountExisted()
    @Test
    public void testIsAccountExisted_ExistingSavingsAccount() {
        assertTrue(Asm03.isAccountExisted("123456"));
    }

    @Test
    public void testIsAccountExisted_ExistingLoanAccount() {
        assertTrue(Asm03.isAccountExisted("345678"));
    }

    @Test
    public void testIsAccountExisted_NonExistingAccount() {
        assertFalse(Asm03.isAccountExisted("999999"));
    }

    @Test
    public void testIsAccountExisted_EmptyAccounts() {
        SavingsAccount.setAccount(null);
        LoanAccount.setAccount(null);
        assertFalse(Asm03.isAccountExisted("123456"));
    }

    // Unit test cho phương thức validateAccount()
    @Test
    public void testValidateAccount_ValidAccount() {
        assertFalse(Asm03.validateAccount("123456"));
    }

    @Test
    public void testValidateAccount_InvalidLength() {
        assertTrue(Asm03.validateAccount("12345"));
        assertTrue(Asm03.validateAccount("1234567"));
    }

    @Test
    public void testValidateAccount_NonNumericCharacters() {
        assertTrue(Asm03.validateAccount("12A456"));
        assertTrue(Asm03.validateAccount("1234B6"));
    }

    @Test
    public void testValidateAccount_EmptyString() {
        assertTrue(Asm03.validateAccount(""));
    }
}
