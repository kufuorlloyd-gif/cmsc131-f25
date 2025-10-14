package projects.bank;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Bank {

    private Account[] accounts;
    private int idxNextAccount;
    private final int newAccountsIncrement = 100;

    public Bank() {
        accounts = new Account[newAccountsIncrement];
    }

    /**
     * Add an account to this Bank.
     * @param acct - Account to add.
     * @return - true if successful, false if unsuccessful.
     * @throws IllegalArgumentException - If acct is null.
     */
    public boolean add(Account acct) {
        if (acct == null) {
            throw new IllegalArgumentException("account must not be null.");
        }
        if (find(acct.getID()) == -1) {
            try {
                accounts[idxNextAccount] = acct;
            } catch(ArrayIndexOutOfBoundsException e) {
                Account[] accountsExtended = new Account[
                    accounts.length + newAccountsIncrement
                ];
                for (int idx = 0; idx < accounts.length; idx++) {
                    accountsExtended[idx] = accounts[idx];
                }
                accountsExtended[idxNextAccount] = acct;
                accounts = accountsExtended;
            }
            idxNextAccount++;
            return true;
        } else {
            return false;
        }

    }

    /**
     * Find an account in this Bank using its unique ID.
     * @param accountID - Unique ID.
     * @return - Reference to account in this Bank, or -1 if no match.
     * @throws IllegalArgumentException if accountID is null.
     */
    public int find(String accountID) {
        if (accountID == null) {
            throw new IllegalArgumentException("accountID must not be null.");
        }
        for (int idxAcct = 0; idxAcct < idxNextAccount; idxAcct++) {
            if (accounts[idxAcct].getID().equals(accountID)) {
                return idxAcct;
            }
        }
        return -1;
    }

    /**
     * @return - Number of accounts in this Bank.
     */
    public int getCount() {
        return idxNextAccount;
    }

    public void loadAccounts(String fileName) throws FileNotFoundException{ 
        File inputFile = new File(fileName);
        Scanner scanner = new Scanner(inputFile);

        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            Account account = Account.makeAccount(line);
            add(account);
        }
    }

    public boolean saveAccounts(String fileName) throws IOException{
        File outputFile = new File(fileName);
        FileWriter writer = null;
        boolean result; 
        try {
            writer = new FileWriter(outputFile);

            for (int i = 0; i < accounts.length; i++ ){
                if (accounts [i] != null){
                    writer.write (accounts [i].toCSV() + "\n");
                }
            }

            writer.close();
            result = true;
        
        } catch (IOException e) {
            e.printStackTrace();
             result = false;
         }
            return result;
             

        }
    }
