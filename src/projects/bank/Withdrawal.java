package projects.bank;

public class Withdrawal extends Transaction{

    protected Withdrawal(String ID, Double amt) {
        super(ID, amt);
        
    }

    @Override
    public void execute(AccountAccess accounts, Audit audit) {
        Account destAcc = accounts.findAccount(getAccountId());
        if (destAcc == null){
            audit.writeError("[Transaction - Withdrawal] could not find account " + getAccountId());
            return;
        }
        if (!destAcc.hasFunds(getAmount())){
            audit.writeWarning("[Transaction - Withdrawal] Account" + destAcc.getID() + "does not have " + getAmount());
            return;
        }
 
        destAcc.debit(getAmount());
        audit.writeInfo("[Transaction - Transfer] debited " + getAmount() + " to " + destAcc.getID());
    }

}
