package projects.bank;

public abstract class Account {

    private final String id;
    private final String ownerName;
    private double balance;

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
        Double balance
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
        
        this.balance = balance;
    }

    public String getID() { return id; }
    public String getOwnerName() { return ownerName; }
    public double getBalance() {return balance; }



    /**
     * CSV line holding this account's data.
     * @return Eg, "savings,wz240833,Anna Gomez,8111.00"
     */
    public String toCSV() { return toString(); }

    public void credit (double amount){
        balance += amount;

    }
    public void debit (double amount){
        balance -= amount;

    }
    public boolean hasFunds(double amount) {
        return balance >= amount;

    }
     public static Account make(String inputLine) {
        if (inputLine == null) {
            throw new IllegalArgumentException("inputLine cannot be null");
        }
        String[] tokens = inputLine.split(",");
        // throws on invalid type
        AccountType type = AccountType.valueOf(tokens[0].toUpperCase());
        String id = tokens[1];
        String ownerName = tokens[2];
        double balance = (double) Double.valueOf(tokens[3]);
        if (type == AccountType.CHECKING) {
            return new CheckingAccount(id, ownerName, balance);
        } else {
            return new SavingsAccount(id, ownerName, balance);
        }
    }

}