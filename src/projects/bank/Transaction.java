package projects.bank;

public abstract class Transaction {
    private final String accountId;
    private final double amount;
    protected Transaction(
        String id,
        double amt
    ){
        /** dusel
         * validate id
         * validate amt
         */
        accountId = id;
        amount = amt;
    }
    protected String getAccountId(){
        return accountId;
    }
    protected double getAmount(){
        return amount;
    }
    public abstract void execute(AccountAccess accounts, Audit audit);

    public static Transaction make(String csv) {
        String[] tokens = csv.split(",");
        if (tokens.length < 3 && tokens.length > 4) {
            /** dusel
             * better to throw then return null, we don't want to 
             * create a null Transaction
             */
            return null;
        }

        double amount = (double) Double.valueOf(tokens[2]);
        String id = tokens[1];

        /** dusel
         * use an enum instead of parsing a string 
         * parsing a string is too error prone
         */
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
