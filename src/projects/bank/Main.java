
package projects.bank;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    
    public static void main(String[] args) {
        phase1();
        phase2();
        phase3();
    }

    public static void phase1() {
        String logName = "data/phase1.log";
        try {
            FileWriter writer = new FileWriter(new File(logName));

            Account acct = Account.make("savings,rrdfeu,Lloyd,2667.99");
            
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

    public static void phase2() {
        String accountsFilename = "data/accounts.csv";
        Bank bank = new Bank();
        boolean result = bank.loadAccounts(accountsFilename);

        System.out.println("Result of loading account: " + result);
        System.out.println("Number of accounts: " + bank.getCount());

        String outputFilename = "data/phase2.csv";
        bank.writeAccounts(outputFilename);
    }

    public static void phase3() {
        Bank bank = new Bank();
        bank.loadAccounts("data/accounts.csv"); // ignore output
        Transaction[] trs = bank.loadTransactions("data/transactions.csv");
        bank.processTransactions(trs);

        boolean step3 = bank.writeAccounts("data/accounts.csv");
        System.out.println("Transactions process: " + (trs.length > 0));
        System.out.println("Accounts write: " + step3);
    }

}
