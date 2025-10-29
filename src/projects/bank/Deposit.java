package projects.bank;

public class Deposit extends Transaction {

    protected Deposit(String ID, Double amt) {
        super(ID, amt);
    }

    @Override
    public void execute(AccountAccess accounts) {
        Account destAcc = accounts.findAccount(getAccountId());
        if (destAcc != null){
            destAcc.credit(getAmount());
        }
        
    }

}
