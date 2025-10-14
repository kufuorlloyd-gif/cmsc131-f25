package projects.bank;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        phase1();
    }

    public static void phase1() {
        String logName = "phase1.log";
        try {
            FileWriter writer = new FileWriter(new File(logName));

            Account acct = new Account(
                "id1",
                "Owner Name",
                1.0,
                AccountType.SAVINGS
            );
            
            writer.write(
                String.format(
                    "Account setup: acct id=%s, owner=%s, balance=%f",
                    acct.getID(),
                    acct.getOwnerName(),
                    acct.getBalance()
                ) + System.lineSeparator()
            );

            Bank bank = new Bank();
            int numAccounts0 = bank.getCount();
            int findAcct0 = bank.find(acct.getID());

            boolean addResult = bank.add(acct);
            int numAccounts1 = bank.getCount();
            int findAcct1 = bank.find(acct.getID());

            writer.write(
                String.format(
                    "Bank init: getCount=%d, find=%d",
                    numAccounts0, 
                    findAcct0
                ) + System.lineSeparator()
            );

            writer.write(
                String.format(
                    "After add: result=%b, getCount=%d, find=%d",
                    addResult,
                    numAccounts1, 
                    findAcct1
                ) + System.lineSeparator()
            );

            writer.close();
        } catch (IOException e) { e.printStackTrace(); }

    }
}
