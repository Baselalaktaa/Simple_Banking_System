package banking;

public class Card {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private final String cardNr;
    private final String pin;
    private long balance;

    private Card (String cardNr , String pin , long balance){
        this.cardNr = cardNr;
        this.pin = pin;
        this.balance = balance;
    }

    private Card (String cardNr , String pin){
        this(cardNr , pin , 0L);
    }

    public static Card createDefaultCard(String cardNr , String pin) {
        return new Card(cardNr, pin);
    }
    public static Card createCard (String cardNr , String pin , long balance){
        return new Card(cardNr , pin , balance);
    }

    public String getCardNr() {
        return cardNr;
    }

    public String getPin() {
        return pin;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

}
