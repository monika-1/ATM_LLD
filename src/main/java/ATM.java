import javax.swing.plaf.synth.SynthStyle;
import java.util.*;

public class ATM {
    private CardReader cardReader;
    private CashDispenser cashDispenser;
    private Printer printer;
    private KeyPad keyPad;
    private State state;
    private Card currentCard;
    private BankAPI bankAPI;
    private String pinEntered;
    private TransactionType type;
    private long atmAmount;
    private Map<Integer, Integer> denomination;
    private int[] cashTypes = new int[] {100, 200, 500, 1000, 2000};

    public ATM(BankAPI bankAPI) {
        cardReader = new CardReader();
        cashDispenser = new CashDispenser();
        printer = new Printer();
        keyPad = new KeyPad();
        state = State.CREATED;
        currentCard = null;
        pinEntered = "";
        this.bankAPI = bankAPI;
        type = null;
        atmAmount = 0;
        denomination = new HashMap<Integer, Integer>();
    }

    public void insertCard(Card card) {
        this.currentCard = card;
        state = State.CHECK_CARD;
        System.out.println("Card Inserted...");
        boolean isValid = cardReader.readAndValidateCard(card, this.bankAPI);

        if (isValid == false) {
            state = State.CARD_FAILED;
            System.out.println("Invalid Card!!! Try Again");
        } else {
            state = State.ASK_PIN;
        }
    }

    public void enterPin(String key) {
        keyPad.addKey(key);
    }

    public void pinEnteringDone() {
        System.out.println("PIN Entered.");
        pinEntered = keyPad.getKeys();
        keyPad.resetKeys();
        state = State.CHECK_PIN;
        if (bankAPI.checkPin(currentCard, pinEntered)) {
            state = State.PIN_PASSED;
            System.out.println("PIN Passed...");
        } else {
            state = State.PIN_FAILED;
            System.out.println("Invalid PIN!!! Try Again.");
        }
    }

    public boolean isValidPin() {
        if (this.state == State.PIN_PASSED) return true;
        return false;
    }

    public void selectTransaction() {
        switch (this.type) {
            case BALANCE: state = State.BALANCE;
                break;
            case WITHDRAW: state = State.WITHDRAW;
                break;
        }
    }

    public void getBalance() {
        Card card = this.currentCard;
        String account_number = card.getAccount_number();
        String bank_name = card.getBank_name();
        Bank bank = bankAPI.getBank(bank_name);
        Account account = bank.getAccount(account_number);

        System.out.println("Account balance is: Rs. "+account.getAmount());
    }

    public void enterWithdrawAmount(long amount) {
        Card card = this.currentCard;
        String account_number = card.getAccount_number();
        String bank_name = card.getBank_name();
        Bank bank = bankAPI.getBank(bank_name);
        Account account = bank.getAccount(account_number);

        if (amount > account.getAmount()) {
            System.out.println("Sorry!!! Low balance in the account");
        } else if (amount > this.atmAmount) {
            System.out.println("Sorry this amount is not available in ATM");
        } else {
            account.incrementAmount(-1*amount);
            deductCash(amount);
            System.out.println(""+amount+" has been deducted from amount.");
            System.out.println("Account balance: "+account.getAmount());
        }
    }

    public void loadCash(Map<Integer, Integer> denomination) {
        for (Map.Entry<Integer, Integer> entry: denomination.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            this.atmAmount += (key*value);
            this.denomination.put(key, denomination.getOrDefault(key, 0)+value);
        }
    }

    public void deductCash(long amount) {

        this.atmAmount -= amount;
    }
    public void setTransactionType(TransactionType type) {
        this.type = type;
    }

    public TransactionType getTransactionType() {
        return type;
    }

    public static void main(String[] args) {
        System.out.println("----------------WELCOME TO ATM MACHINE----------------");
        Scanner sc =new Scanner(System.in);
        BankAPI bankAPI = new BankAPI();

        Bank bank = new Bank("hdfc");
        Account account = new Account(bank.getBank_name(), "110022200454522");
        account.setAmount(1000);
        bank.addAccount(account);
        Card card = new Card("1100", "hdfc", account.getAccount_number());
        bank.addCard(card.getCard_number());
        bank.addPin(card.getCard_number(), "2233");

        bankAPI.addBank(bank);

        ATM atm = new ATM(bankAPI);

        while (true) {
            atm.insertCard(card);
            System.out.print("Please enter pin: ");
            String pin = sc.next();
            atm.enterPin(pin);
            atm.pinEnteringDone();
            if (atm.isValidPin()) {
                System.out.print("Enter transaction type(B or W): ");
                String tranType = sc.next();
                if (tranType.equals("B")) {
                    atm.setTransactionType(TransactionType.BALANCE);
                } else {
                    atm.setTransactionType(TransactionType.WITHDRAW);
                }
                atm.selectTransaction();

                if (atm.getTransactionType() == TransactionType.WITHDRAW) {
                    System.out.print("Enter withdraw amount: ");
                    long amount = sc.nextLong();
                    atm.enterWithdrawAmount(amount);
                } else if (atm.getTransactionType() == TransactionType.BALANCE) {
                    atm.getBalance();
                }
            }

            System.out.println("Transaction is complete. Do You want to continue(Y or N)?");
            String inp = sc.next();
            if (inp.equals("N")) {
                break;
            }
        }

        System.out.println("Bye...Please come again :)");
    }
}

// TODO: Add State design pattern for using Finite State Machine
// TODO: Add denomination in cash