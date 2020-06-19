import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Bank {
    private String bank_name;
    private Set<String> cards;
    private Map<String,String> pins;
    private Map<String, Account> accounts;

    public Bank(String bank_name) {
        this.bank_name = bank_name;
        cards = new HashSet<String>();
        pins = new HashMap<String, String>();
        accounts = new HashMap<String, Account>();
    }

    public boolean checkPin(String card_number, String pin) {
        if (pins.containsKey(card_number)) {
            if (pins.get(card_number).equals(pin)) return true;
        }
        return false;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void addCard(String card_name) {
        cards.add(card_name);
    }

    public void addPin(String card_name, String pin_number) {
        pins.put(card_name, pin_number);
    }

    public void addAccount(Account account) {
        accounts.put(account.getAccount_number(), account);
    }

    public boolean checkCard(String card_number) {
        return cards.contains(card_number);
    }

    public Account getAccount(String account_number) {
        return accounts.get(account_number);
    }
}
