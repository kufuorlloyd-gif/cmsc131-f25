package projects.bank;

public abstract class Transaction {
    private final String accountId;
    private final double amount;
    protected Transaction(
        String ID,
        double amt
    ){
        accountId = ID;
        amount = amt;
    }
    protected String getAccountId(){
        return accountId;
    }
    protected double getAmount(){
        return amount;
    }
    public abstract void execute(AccountAccess accounts);

    public static Transaction make(String csv) {
        String[] tokens = csv.split(",");
        if (tokens.length < 3 && tokens.length > 4) {
            return null;
        }

        double amount = (double) Double.valueOf(tokens[2]);
        String id = tokens[1];


        if (tokens[0].equals("deposit")) {
            return new Deposit(id,amount);

        } else if (tokens[0].equals("withdrawal")) {
            return new Withdrawal(id,amount);

        } else if (tokens[0].equals("transfer")) {
            return new Transfer(id,amount,tokens[3]);

        } 
        return null;
    }
}
