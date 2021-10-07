package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class CardGenerator {

    private final static int BIN = 400000;

    private String cardNr;
    private String pin;

    private int accountNumber;
    private int checkSum;

    private List<Integer> issuedAccountNumbers;


    private CardGenerator() {

    }

    public static CardGenerator createCardGenerator() {
        return new CardGenerator();
    }

    public Card createCard () {
        generateCardNumber();
        generatePin();
        return Card.createDefaultCard(getCardNr() , getPin());
    }

    private void generatePin(){
        this.pin = String.format("%04d" , (int) ThreadLocalRandom.current().nextLong(0, 9999));
    }

    public boolean checkIfLuhnConform(String cardNr){

        char checkSum = cardNr.charAt(cardNr.length()-1);

        List<Integer> cardNrDigits = new ArrayList<>();
        List<Integer> modifiedDigits = new ArrayList<>();

        for (int i = 0; i <cardNr.length()-1 ; i++) {
            cardNrDigits.add(Integer.parseInt(String.valueOf(cardNr.charAt(i))));
        }

        int temp;
        for (int i = 0; i < cardNrDigits.size() ; i++) {
            if (i %  2 == 0){
                temp = cardNrDigits.get(i) * 2;
                if (temp > 9) temp = temp - 9;
                modifiedDigits.add(temp);
            }
            else {
                modifiedDigits.add(cardNrDigits.get(i));
            }
        }

        int sum = modifiedDigits.stream().reduce(0 , Integer::sum) + Integer.parseInt(String.valueOf(checkSum));
        return sum % 10 == 0;
    }

    private void validate () {
        checkSum = 0;
        while (!checkIfLuhnConform(cardNr+checkSum)){
            checkSum++;
        }
        cardNr = cardNr + checkSum;
    }

    private void generateCardNumber() {
        issuedAccountNumbers = new ArrayList<>();
        StringBuilder cardNr = new StringBuilder();
        generateAccNr();
        cardNr.append(BIN).append(accountNumber);
        this.cardNr = cardNr.toString();
        validate();
    }


    private void generateAccNr() {
        this.accountNumber = (int) ThreadLocalRandom.current().nextLong(100000000, 999999999);
        if (isUnique()) {
            issuedAccountNumbers.add(accountNumber);
        } else {
            generateAccNr();
        }
    }

    private boolean isUnique() {
        return !issuedAccountNumbers.contains(accountNumber);
    }

    public String getPin() {
        return pin;
    }

    public String getCardNr() {
        return cardNr;
    }
}
