package projects.bank;

public class Account {

    private final String id;
    private final String ownerName;
    private double balance;
    private final AccountType type;

    /**
     * 
     * @param id Unique alphanumeric id for this account.
     * @param ownerName Name of this account's owner.
     * @param balance Initial balance of this account.
     * @param type Type of account this is.
     * 
     * @throws IllegalArgumentException If id is null or ownerName is null.
     */
    public Account(
        String id,
        String ownerName,
        double balance,
        AccountType type
    ) {
        if (id != null) {
            this.id = id;
        } else {
            throw new IllegalArgumentException("id cannot be null");
        }

        if (ownerName != null) {
            this.ownerName = ownerName;
        } else {
            throw new IllegalArgumentException("ownerName cannot be null");
        }

        if (type != null) {
            this.type = type;
        } else {
            throw new IllegalArgumentException("type cannot be null");
        }
        
        this.balance = balance;
    }

    public String getID() { return id; }
    public String getOwnerName() { return ownerName; }
    public double getBalance() {return balance; }
    public AccountType getType() { return type; }
    
    public static Account makeAccount(String input_line) {
        String[] words = input_line.split(",");
        return new Account(words[1], words[2], Double.parseDouble(words[3]),AccountType.valueOf(words[0]));
    }
    public String toCSV() {
        return getType() + "," + getID() + "," + getOwnerName() + "," + getBalance();
    }
    
}
