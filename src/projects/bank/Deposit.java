/** TODO
 * 
 * execute
 * you should verify that account exists in the bank class, not in here
 */
package projects.bank;

public class Deposit extends Transaction {

    protected Deposit(String ID, Double amt) {
        super(ID, amt);
    }

    @Override
    public void execute(AccountAccess accounts, Audit audit) {
        Account destAcc = accounts.findAccount(getAccountId());
        if (destAcc == null){
            audit.writeError("[Transaction - Deposit] could not find account " + getAccountId());
            return;
        }

        destAcc.credit(getAmount());
        audit.writeInfo("[Transaction - Deposit] credited " + getAmount() + " to " + destAcc.getID());

    }

}
