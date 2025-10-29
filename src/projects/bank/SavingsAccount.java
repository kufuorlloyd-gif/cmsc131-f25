package projects.bank;

public class SavingsAccount extends Account{
    public SavingsAccount(String id, String ownerName, Double balance){
        super(id, ownerName, balance);

    }
    /**
     * Factory method for constructing an Account object from a CSV line.
     * @param inputLine Eg, "savings,wz240833,Anna Gomez,8111.00"
     * @return - new Account from supplied values.
     * @throws {@code IllegalArgumentException}  if null {@code input} is null.
     */
    public static SavingsAccount make(String inputLine) {
        if (inputLine == null) {
            throw new IllegalArgumentException("inputLine cannot be null");
        }
        String[] tokens = inputLine.split(",");
        String id = tokens[1];
        String ownerName = tokens[2];
        double balance = (double) Double.valueOf(tokens[3]);
        return new SavingsAccount(id, ownerName, balance);
    }

    @Override 
    public String toString() {
        return String.format(
            "savings,%s,%s,%.2f", // format double to 2 decimal places
            getID(),
            getOwnerName(),
            getBalance()
        );
    }
}
