package projects.bank;

public class Transfer extends Transaction{
    private final String targetAccountId; 

    protected Transfer(String ID, Double amt, String targetId) {
        super(ID, amt);
        targetAccountId = targetId;
        
    }

    @Override
    public void execute(AccountAccess accounts) {
        Account fromAcc = accounts.findAccount(getAccountId());
        
        Account toAcc = accounts.findAccount(targetAccountId); 
        if (fromAcc != null && toAcc != null && fromAcc.hasFunds(getAmount()) ){
            fromAcc.debit(getAmount());
            toAcc.credit(getAmount());
        }
        
    }
    

}
