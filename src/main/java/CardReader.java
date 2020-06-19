public class CardReader {
    public CardReader() {

    }

    public boolean readAndValidateCard(Card card, BankAPI bankAPI) {
        return bankAPI.validateCard(card);
    }
}
