package projects.bank;

import static org.junit.jupiter.api.Assertions.*;


import java.io.IOException;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TransactionTest {


   @Test
   void testDeposit() {
       Transaction[] transactions = new Transaction[1];
       transactions [0] = Transaction.make("deposit,vc906780,766.53");
        bank = new Bank();
        acct = new SavingsAccount("id0", "Owner Name", 1.0);
        boolean addAccountResult = bank.add(acct);



   }


   @Test
   void testWithdrawal() {
       
   }


   @Test
   void testTransfer() {
       
   }
}
