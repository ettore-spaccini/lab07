package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;



/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    private final static double IMPORT = 100; 

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(mRossi, 0.0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(0.0, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }

    void assertTransactionsAre(final int expectedTransactions) {
        assertEquals(expectedTransactions, bankAccount.getTransactionsCount());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        assertTransactionsAre(0);
        bankAccount.deposit(mRossi.getUserID(), IMPORT);
        assertTransactionsAre(1);
        assertEquals(IMPORT, bankAccount.getBalance());
        bankAccount.chargeManagementFees(mRossi.getUserID());
        assertTransactionsAre(0);
        assertEquals(IMPORT - (SimpleBankAccount.MANAGEMENT_FEE + StrictBankAccount.TRANSACTION_FEE), bankAccount.getBalance());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(mRossi.getUserID(), -IMPORT));
        assertNotNull(e.getMessage());
        assertFalse(e.getMessage().isBlank());  
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test 
    public void testWithdrawingTooMuch() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(mRossi.getUserID(), IMPORT)); 
        assertNotNull(e.getMessage());
        assertFalse(e.getMessage().isBlank());  
    }
}
