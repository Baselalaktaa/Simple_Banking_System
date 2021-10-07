package banking;


import java.sql.SQLException;
import java.util.List;

public class BankServices {


    private final DatabaseManager databaseManager;
    private final CardGenerator cardGenerator = CardGenerator.createCardGenerator();

    public List<Card> getIssuedAccounts() {
        return issuedAccounts;
    }

    private List<Card> issuedAccounts;

    private BankServices(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        updateIssuedAccounts();
    }

    public static BankServices createBankServices(DatabaseManager databaseManager) {
        return new BankServices(databaseManager);
    }

    private void updateIssuedAccounts() {
        issuedAccounts = databaseManager.getAllAccounts();
    }

    public Account createAccount() {
        CardGenerator cardGenerator = CardGenerator.createCardGenerator();
        Account account = Account.createAccount(cardGenerator.createCard());
        databaseManager.addAccount(account.getCard().getCardNr(), account.getCard().getPin(), account.getCard().getBalance());
        updateIssuedAccounts();
        return account;
    }

    public boolean logIn(String cardNr, String pin) {
        boolean loginGranted = false;
        for (Card account : issuedAccounts) {
            if (account.getCardNr().equals(cardNr) && account.getPin().equals(pin)) {
                loginGranted = true;
                break;
            }
        }
        return loginGranted;
    }


    public void abbBalance(Card account, long balance) {
        databaseManager.updateBalance(balance, account.getId());
        updateIssuedAccounts();
    }

    public boolean validateTransferData(Card sender, String receiver) {

        boolean result = true;

        if (!cardGenerator.checkIfLuhnConform(receiver)) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
            result = false;
        }
        Card receiverCard = databaseManager.getCardByNr(receiver);
        if (receiverCard == null) {
            System.out.println("Such a card does not exist.");
            result = false;
        }

        else if (sender.getId() == receiverCard.getId()) {
            System.out.println("You can't transfer money to the same account!");
            result = false;
        }
        return result;
    }


    public void transferMoney(Card sender, String receiver, long balanceToBeSend) {
        Card receiverCard = databaseManager.getCardByNr(receiver);
        databaseManager.updateBalance(balanceToBeSend, receiverCard.getId());
        databaseManager.updateBalance(-balanceToBeSend, sender.getId());
        updateIssuedAccounts();
    }


    public void closeConnection () {
        try {
            databaseManager.getConnection().close();
        }
        catch (SQLException e){
            System.out.println("here");
            e.getMessage();
        }
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }
}
