package projects.bank;

public class Account {
    private String accountId; 
    private String accountOwner;
    private Double accountBalance;
    private AccountType accountType;
    public Account (String accountId, String accountOwner, Double accountBalance, AccountType accountType) {

        this.accountId = accountId;
        this.accountOwner = accountOwner;
        this.accountBalance = accountBalance;
        this.accountType = accountType;
    }

    public String getaccountId (){
        return this.accountId;
    }
    public String getaccountOwner (){
        return this.accountOwner;
    }
    public Double getaccountBalance (){
        return this.accountBalance;
    }
    public AccountType getaccountType (){
        return this.accountType;
    }

}
