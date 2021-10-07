package banking;

public class Account {



    private Card card;

    private Account (Card card){
        this.card = card;

    }

    public static Account createAccount (Card card){
        return new Account(card);
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }


}
