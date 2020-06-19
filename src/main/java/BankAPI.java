import java.util.HashMap;
import java.util.Map;

public class BankAPI {
    private Map<String, Bank> map;

    public BankAPI() {
        map = new HashMap<String, Bank>();
    }

    public void addBank(Bank bank) {
        map.put(bank.getBank_name(), bank);
    }

    public boolean checkPin(Card card, String pin) {
        if (map.containsKey(card.getBank_name()) == false) return false;
        Bank bank = map.get(card.getBank_name());

        return bank.checkPin(card.getCard_number(), pin);
    }

    public boolean validateCard(Card card) {
        if (map.containsKey(card.getBank_name()) == false) return false;
        Bank bank = map.get(card.getBank_name());

        return bank.checkCard(card.getCard_number());
    }

    public Bank getBank(String bank_name) {
        return map.get(bank_name);
    }
}
