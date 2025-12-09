package projects.bank;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionTest {

   private Bank bank;
   private Audit audit;

   @BeforeEach
   void setup() {
      bank = new Bank();
      audit = new Audit();

      // Add test accounts to bank
      bank.add(new CheckingAccount("acc001", "Alice", 1000.0));
      bank.add(new SavingsAccount("acc002", "Bob", 500.0));
      bank.add(new CheckingAccount("acc003", "Carol", 2000.0));
   }

   // Tests for Transaction.make() factory method

   @Test
   void testMakeDeposit() {
      String csv = "deposit,acc001,250.0";
      Transaction t = Transaction.make(csv);

      assertNotNull(t);
      assertTrue(t instanceof Deposit);
   }

   @Test
   void testMakeWithdrawal() {
      String csv = "withdrawal,acc002,100.0";
      Transaction t = Transaction.make(csv);

      assertNotNull(t);
      assertTrue(t instanceof Withdrawal);
   }

   @Test
   void testMakeTransfer() {
      String csv = "transfer,acc001,300.0,acc002";
      Transaction t = Transaction.make(csv);

      assertNotNull(t);
      assertTrue(t instanceof Transfer);
   }

   @Test
   void testMakeInvalidType() {
      String csv = "invalid,acc001,100.0";
      Transaction t = Transaction.make(csv);

      assertNull(t, "Should return null for invalid transaction type");
   }

   @Test
   void testMakeExactlyFourTokensForTransfer() {
      String csv = "transfer,acc001,200.0,acc002";
      Transaction t = Transaction.make(csv);

      assertNotNull(t, "Should accept 4 tokens for transfer");
      assertTrue(t instanceof Transfer);
   }

   @Test
   void testMakeParsesAmount() {
      String csv = "deposit,acc001,123.45";
      Transaction t = Transaction.make(csv);

      assertNotNull(t);
      // We can't directly access amount without executing, but we'll test it
      // indirectly
   }

   @Test
   void testMakeZeroAmount() {
      String csv = "deposit,acc001,0.0";
      Transaction t = Transaction.make(csv);

      assertNotNull(t, "Should create transaction with zero amount");
   }

   @Test
   void testMakeLargeAmount() {
      String csv = "withdrawal,acc001,999999.99";
      Transaction t = Transaction.make(csv);

      assertNotNull(t);
   }

   // Tests for Deposit execution

   @Test
   void testDepositExecuteSuccess() {
      Transaction t = Transaction.make("deposit,acc001,250.0");
      double initialBalance = bank.findAccount("acc001").getBalance();

      t.execute(bank, audit);

      assertEquals(initialBalance + 250.0, bank.findAccount("acc001").getBalance(), 0.001);
   }

   @Test
   void testDepositExecuteAccountNotFound() {
      Transaction t = Transaction.make("deposit,nonexistent,100.0");

      t.execute(bank, audit);

      // Account should not be created, no exception should be thrown
      assertNull(bank.findAccount("nonexistent"));
   }

   @Test
   void testDepositExecuteZeroAmount() {
      Transaction t = Transaction.make("deposit,acc001,0.0");
      double initialBalance = bank.findAccount("acc001").getBalance();

      t.execute(bank, audit);

      assertEquals(initialBalance, bank.findAccount("acc001").getBalance(), 0.001);
   }

   @Test
   void testDepositExecuteLargeAmount() {
      Transaction t = Transaction.make("deposit,acc002,10000.0");
      double initialBalance = bank.findAccount("acc002").getBalance();

      t.execute(bank, audit);

      assertEquals(initialBalance + 10000.0, bank.findAccount("acc002").getBalance(), 0.001);
   }

   // Tests for Withdrawal execution

   @Test
   void testWithdrawalExecuteSuccess() {
      Transaction t = Transaction.make("withdrawal,acc001,200.0");
      double initialBalance = bank.findAccount("acc001").getBalance();

      t.execute(bank, audit);

      assertEquals(initialBalance - 200.0, bank.findAccount("acc001").getBalance(), 0.001);
   }

   @Test
   void testWithdrawalExecuteInsufficientFunds() {
      Transaction t = Transaction.make("withdrawal,acc002,1000.0");
      double initialBalance = bank.findAccount("acc002").getBalance();

      t.execute(bank, audit);

      // Balance should remain unchanged due to insufficient funds
      assertEquals(initialBalance, bank.findAccount("acc002").getBalance(), 0.001);
   }

   @Test
   void testWithdrawalExecuteAccountNotFound() {
      Transaction t = Transaction.make("withdrawal,nonexistent,100.0");

      t.execute(bank, audit);

      assertNull(bank.findAccount("nonexistent"));
   }

   @Test
   void testWithdrawalExecuteExactBalance() {
      Transaction t = Transaction.make("withdrawal,acc002,500.0");

      t.execute(bank, audit);

      assertEquals(0.0, bank.findAccount("acc002").getBalance(), 0.001);
   }

   @Test
   void testWithdrawalExecuteZeroAmount() {
      Transaction t = Transaction.make("withdrawal,acc001,0.0");
      double initialBalance = bank.findAccount("acc001").getBalance();

      t.execute(bank, audit);

      assertEquals(initialBalance, bank.findAccount("acc001").getBalance(), 0.001);
   }

   // Tests for Transfer execution

   @Test
   void testTransferExecuteSuccess() {
      Transaction t = Transaction.make("transfer,acc001,300.0,acc002");
      double initialFrom = bank.findAccount("acc001").getBalance();
      double initialTo = bank.findAccount("acc002").getBalance();

      t.execute(bank, audit);

      assertEquals(initialFrom - 300.0, bank.findAccount("acc001").getBalance(), 0.001);
      assertEquals(initialTo + 300.0, bank.findAccount("acc002").getBalance(), 0.001);
   }

   @Test
   void testTransferExecuteInsufficientFunds() {
      Transaction t = Transaction.make("transfer,acc002,1000.0,acc001");
      double initialFrom = bank.findAccount("acc002").getBalance();
      double initialTo = bank.findAccount("acc001").getBalance();

      t.execute(bank, audit);

      // Balances should remain unchanged
      assertEquals(initialFrom, bank.findAccount("acc002").getBalance(), 0.001);
      assertEquals(initialTo, bank.findAccount("acc001").getBalance(), 0.001);
   }

   @Test
   void testTransferExecuteSourceAccountNotFound() {
      Transaction t = Transaction.make("transfer,nonexistent,100.0,acc001");
      double initialTo = bank.findAccount("acc001").getBalance();

      t.execute(bank, audit);

      // Target account balance should remain unchanged
      assertEquals(initialTo, bank.findAccount("acc001").getBalance(), 0.001);
   }

   @Test
   void testTransferExecuteTargetAccountNotFound() {
      Transaction t = Transaction.make("transfer,acc001,100.0,nonexistent");
      double initialFrom = bank.findAccount("acc001").getBalance();

      t.execute(bank, audit);

      // Source account balance should remain unchanged
      assertEquals(initialFrom, bank.findAccount("acc001").getBalance(), 0.001);
   }

   @Test
   void testTransferExecuteBothAccountsNotFound() {
      Transaction t = Transaction.make("transfer,fake1,100.0,fake2");

      t.execute(bank, audit);

      // Should not throw exception
      assertNull(bank.findAccount("fake1"));
      assertNull(bank.findAccount("fake2"));
   }

   @Test
   void testTransferExecuteExactBalance() {
      Transaction t = Transaction.make("transfer,acc002,500.0,acc003");
      double initialTo = bank.findAccount("acc003").getBalance();

      t.execute(bank, audit);

      assertEquals(0.0, bank.findAccount("acc002").getBalance(), 0.001);
      assertEquals(initialTo + 500.0, bank.findAccount("acc003").getBalance(), 0.001);
   }

   @Test
   void testTransferExecuteZeroAmount() {
      Transaction t = Transaction.make("transfer,acc001,0.0,acc002");
      double initialFrom = bank.findAccount("acc001").getBalance();
      double initialTo = bank.findAccount("acc002").getBalance();

      t.execute(bank, audit);

      assertEquals(initialFrom, bank.findAccount("acc001").getBalance(), 0.001);
      assertEquals(initialTo, bank.findAccount("acc002").getBalance(), 0.001);
   }

   // Integration tests - multiple transactions

   @Test
   void testMultipleTransactionsSequence() {
      Transaction t1 = Transaction.make("deposit,acc001,500.0");
      Transaction t2 = Transaction.make("withdrawal,acc001,200.0");
      Transaction t3 = Transaction.make("transfer,acc001,300.0,acc002");

      double initial = bank.findAccount("acc001").getBalance();

      t1.execute(bank, audit);
      t2.execute(bank, audit);
      t3.execute(bank, audit);

      // 1000 + 500 - 200 - 300 = 1000
      assertEquals(initial + 500.0 - 200.0 - 300.0,
            bank.findAccount("acc001").getBalance(), 0.001);
   }

   @Test
   void testTransactionArrayWithNullElements() {
      Transaction[] transactions = new Transaction[3];
      transactions[0] = Transaction.make("deposit,acc001,100.0");
      transactions[1] = null;
      transactions[2] = Transaction.make("withdrawal,acc001,50.0");

      // This tests Bank.processTransactions which handles nulls
      bank.processTransactions(transactions);

      // Should process non-null transactions without error
      assertNotNull(bank.findAccount("acc001"));
   }

   @Test
   void testMakeEmptyString() {
      assertThrows(
            ArrayIndexOutOfBoundsException.class,
            () -> Transaction.make(""));
   }

   @Test
   void testTransferToSameAccount() {
      Transaction t = Transaction.make("transfer,acc001,100.0,acc001");
      double initial = bank.findAccount("acc001").getBalance();

      t.execute(bank, audit);

      // Should debit then credit, net zero change
      assertEquals(initial, bank.findAccount("acc001").getBalance(), 0.001);
   }

   @Test
   void testNegativeAmountDeposit() {
      Transaction t = Transaction.make("deposit,acc001,-100.0");
      double initial = bank.findAccount("acc001").getBalance();

      t.execute(bank, audit);

      // Negative deposit reduces balance
      assertEquals(initial - 100.0, bank.findAccount("acc001").getBalance(), 0.001);
   }

   @Test
   void testDecimalPrecision() {
      Transaction t = Transaction.make("deposit,acc001,0.01");
      double initial = bank.findAccount("acc001").getBalance();

      t.execute(bank, audit);

      assertEquals(initial + 0.01, bank.findAccount("acc001").getBalance(), 0.001);
   }
}