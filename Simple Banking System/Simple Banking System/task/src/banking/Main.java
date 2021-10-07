package banking;


import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {


    private final static String NEW_LINE = "\n";

    private final static String MAIN_MENU_INFO = "1. Create an account" + NEW_LINE +
            "2. Log into account" + NEW_LINE +
            "0. Exit";
    private final static String IN_ACCOUNT_INFO = "1. Balance" + NEW_LINE
            + "2. Add income" + NEW_LINE +
            "3. Do transfer" + NEW_LINE +
            "4. Close account" + NEW_LINE +
            "5. Log out" + NEW_LINE +
            "0. Exit";


    private BankServices serviceProvider;

    private Card loggedInAccount;


    public void reactToUserInput(int userInput, SystemState state) {

        Scanner scanner = new Scanner(System.in);

        try {
            if (state == SystemState.MAIN_MENU) {

                if (userInput == 1) {
                    Account account = serviceProvider.createAccount();
                    System.out.println(account.getCard().getCardNr());
                    System.out.println(account.getCard().getPin());
                    System.out.println();
                    System.out.println(MAIN_MENU_INFO);
                    reactToUserInput(scanner.nextInt(), SystemState.MAIN_MENU);
                }
                if (userInput == 2) {
                    System.out.println("Your card number:");
                    String enteredCardNr = scanner.next();
                    System.out.println("Your card PIN:");
                    String enteredPin = scanner.next();

                    boolean canGrant = serviceProvider.logIn(enteredCardNr, enteredPin);
                    if (canGrant) {
                        System.out.println("You have successfully logged in!");
                        System.out.println();
                        loggedInAccount = serviceProvider.getDatabaseManager().getCardByNr(enteredCardNr);
                        System.out.println(IN_ACCOUNT_INFO);
                        reactToUserInput(scanner.nextInt(), SystemState.IN_ACCOUNT);
                    } else {
                        System.out.println("Wrong card number or PIN!");
                        System.out.println(MAIN_MENU_INFO);
                        reactToUserInput(scanner.nextInt(), SystemState.MAIN_MENU);
                    }
                } else if (userInput == 0){
                    serviceProvider.closeConnection();
                    System.out.println("Bye!");
                    return;
                }
            }

            if (state == SystemState.IN_ACCOUNT) {
                loggedInAccount = serviceProvider.getDatabaseManager().getCardByNr(loggedInAccount.getCardNr());
                if (userInput == 1) {
                    System.out.println("Balance: " + loggedInAccount.getBalance());
                    System.out.println();
                    System.out.println(IN_ACCOUNT_INFO);
                    reactToUserInput(scanner.nextInt(), SystemState.IN_ACCOUNT);
                } else if (userInput == 2) {
                    System.out.println("Enter income:");
                    assert loggedInAccount != null;
                    serviceProvider.abbBalance(loggedInAccount, scanner.nextInt());
                    System.out.println("Income was added!");
                    System.out.println();
                    System.out.println(IN_ACCOUNT_INFO);
                    reactToUserInput(scanner.nextInt(), SystemState.IN_ACCOUNT);
                } else if (userInput == 3) {
                    System.out.println("Enter card number:");
                    assert loggedInAccount != null;
                    String receiver = scanner.next();
                    boolean canTransfer = serviceProvider.validateTransferData(loggedInAccount , receiver);
                    if (canTransfer){
                        System.out.println("Enter how much money you want to transfer:");
                        int balanceToSend = scanner.nextInt();
                        if (balanceToSend == 0){
                            serviceProvider.closeConnection();
                            System.out.println("Bye!");
                            return;
                        }
                        if (balanceToSend > loggedInAccount.getBalance()){
                            System.out.println("Not enough money!");
                            System.out.println();
                            System.out.println(IN_ACCOUNT_INFO);
                            reactToUserInput(scanner.nextInt(), SystemState.IN_ACCOUNT);
                        } else {
                            serviceProvider.transferMoney(loggedInAccount, receiver, balanceToSend);
                            System.out.println("Succeed!");
                            System.out.println();
                            System.out.println(IN_ACCOUNT_INFO);
                            reactToUserInput(scanner.nextInt(), SystemState.IN_ACCOUNT);
                        }
                    }
                    else {
                        System.out.println();
                        System.out.println(IN_ACCOUNT_INFO);
                        reactToUserInput(scanner.nextInt(), SystemState.IN_ACCOUNT);
                    }
                } else if (userInput == 4) {
                    assert loggedInAccount != null;
                    serviceProvider.getDatabaseManager().dropAccount(loggedInAccount.getId());
                    System.out.println("The account has been closed!");
                    System.out.println();
                    System.out.println(MAIN_MENU_INFO);
                    reactToUserInput(scanner.nextInt(), SystemState.MAIN_MENU);
                } else if (userInput == 5) {
                    System.out.println("You have successfully logged out!");
                    System.out.println();
                    System.out.println(MAIN_MENU_INFO);
                    reactToUserInput(scanner.nextInt(), SystemState.MAIN_MENU);
                } else if (userInput == 0){
                    serviceProvider.closeConnection();
                    System.out.println("Bye!");
                    return;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("please use from the Menu");
            serviceProvider.closeConnection();
        }
    }

    public static void main(String[] args) {
        System.out.println(MAIN_MENU_INFO);
        Scanner scanner = new Scanner(System.in);
        Main main = new Main();


        DatabaseManager manager = DatabaseManager.createDbManager(args[1]);
        manager.establishConnection();

        //inject
        main.serviceProvider = BankServices.createBankServices(manager);


        main.reactToUserInput(scanner.nextInt(), SystemState.MAIN_MENU);
        main.serviceProvider.closeConnection();
    }
}