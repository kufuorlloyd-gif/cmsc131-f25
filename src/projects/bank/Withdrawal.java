package projects.bank;

public class Withdrawal extends Transaction{

    protected Withdrawal(String ID, Double amt) {
        super(ID, amt);
        
    }

    @Override
    public void execute(AccountAccess accounts) {
        Account destAcc = accounts.findAccount(getAccountId());
        if (destAcc != null && destAcc.hasFunds(getAmount())){
            destAcc.debit(getAmount());
        
        }
        
    }

}
