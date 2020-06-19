public class Account {
    private String account_number;
    private long amount;
    private String bank_name;

    public Account(String bank_name, String account_number) {
        this.bank_name = bank_name;
        this.account_number = account_number;
        this.amount = 0;
    }

    public String getAccount_number() {
        return account_number;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void incrementAmount(long increment) {
        this.amount += increment;
    }
}
