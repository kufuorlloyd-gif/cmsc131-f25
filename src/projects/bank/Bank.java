package projects.bank;

public class Bank {
    private Account[] accounts;
    private int count; // number of accounts currently stored

    // --- Constructor ---
    public Bank(int capacity) {
        accounts = new Account[capacity]; // plain array, no ArrayList
        count = 0;
    }

    // Add a new account to the bank
    public boolean addAccount(Account account) {
        if (count >= accounts.length) {
            return false; // array is full
        }
        accounts[count] = account;
        count++;
        return true;
    }

    // Find an account by ID using linear search
    public Account findAccount(String accountID) {
        for (int i = 0; i < count; i++) {
            if (accounts[i].getaccountId().equals(accountID)) {
                return accounts[i];
            }
        }
        return null; // not found
    }

    // Return the number of accounts currently in the bank
    public int getAccountCount() {
        return count;
    }

    // Optional: find all accounts by owner name
    public Account[] findAccountsByOwner(String accountOwner) {
        // First pass: count matches
        int matches = 0;
        for (int i = 0; i < count; i++) {
            if (accounts[i].getaccountOwner().equalsIgnoreCase(accountOwner)) {
                matches++;
            }
        }

        // Second pass: collect matches
        Account[] result = new Account[matches];
        int index = 0;
        for (int i = 0; i < count; i++) {
            if (accounts[i].getaccountOwner().equalsIgnoreCase(accountOwner)) {
                result[index] = accounts[i];
                index++;
            }
        }
        return result;
    }
}