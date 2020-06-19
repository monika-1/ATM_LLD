import java.util.List;

public class CashDispenser {
    public CashDispenser() {

    }

    public void dispenseCash(List<String> cash) {
        for (String str: cash) {
            System.out.println(""+str);
        }
    }
}
