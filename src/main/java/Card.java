public class Card {
    private String card_number;
    private String bank_name;
    private String account_number;

    public Card(String card_number, String bank_name, String account_number) {
        this.card_number = card_number;
        this.bank_name = bank_name;
        this.account_number = account_number;
    }

    public String getCard_number() {
        return card_number;
    }

    public String getBank_name() {
        return bank_name;
    }

    public String getAccount_number() {
        return account_number;
    }
}
