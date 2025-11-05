package projects.bank;

public class Transfer extends Transaction{
    private final String targetAccountId; 

    protected Transfer(String ID, Double amt, String targetId) {
        super(ID, amt);
        targetAccountId = targetId;
        
    }

    @Override
    public void execute(AccountAccess accounts, Audit audit) {
        Account fromAcc = accounts.findAccount(getAccountId());
        Account toAcc = accounts.findAccount(targetAccountId); 
        if (fromAcc == null) {
            audit.writeError("[Transaction - Transfer] could not find account " + getAccountId());
            return;
        }
        if (toAcc == null){
            audit.writeError("[Transaction - Transfer] could not find account " + targetAccountId);
            return;
        }
        if (!fromAcc.hasFunds(getAmount())) {
            audit.writeWarning("[Transaction - Transfer] Account" + fromAcc.getID() + "does not have " + getAmount());
            return;
        }
        fromAcc.debit(getAmount());
        audit.writeInfo("[Transaction - Transfer] debited " + getAmount() + " to " + fromAcc.getID());

        toAcc.credit(getAmount());
        audit.writeInfo("[Transaction - Transfer] credited " + getAmount() + " to " + toAcc.getID());
        
    }
    

}
