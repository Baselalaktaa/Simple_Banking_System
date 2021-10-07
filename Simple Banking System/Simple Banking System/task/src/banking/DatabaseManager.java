package banking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {



    private final static String NEW_LINE = "\n";

    private final String dbFileName;
    private Connection connection;



    private DatabaseManager (String dbFileName) {
        this.dbFileName = dbFileName;
    }

    public static DatabaseManager createDbManager(String dbFileName) {
        return new DatabaseManager(dbFileName);
    }



    public void establishConnection() {
        String url = "jdbc:sqlite:" + dbFileName;


        final String cardTableCreateStmt = "create table if NOT exists card (" + NEW_LINE +
                " id INTEGER primary key, " + NEW_LINE +
                "number varchar(19)," + NEW_LINE +
                "pin varchar (4) ," + NEW_LINE +
                "balance int default 0" + NEW_LINE +
                ");";


        try {
            connection = DriverManager.getConnection(url);
            if (connection != null) {
                Statement statement = connection.createStatement();
                statement.execute(cardTableCreateStmt);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addAccount(String cardNr, String pin, long balance) {

        final String addingStmt = "insert into card ( number, pin, balance ) values (? , ? , ?) ";

        try {
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(addingStmt);
                preparedStatement.setString(1, cardNr);
                preparedStatement.setString(2, pin);
                preparedStatement.setLong(3, balance);
                preparedStatement.execute();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Card getCardById (int id) {

        final String getStmt = "Select * from card where id = ?";
        Card result = null;
        try {
            PreparedStatement statement = connection.prepareStatement(getStmt);
            statement.setInt(1, id);
            statement.execute();

            ResultSet rs = statement.getResultSet();

            result = Card.createDefaultCard(rs.getString("number") , rs.getString("pin"));
            result.setBalance(rs.getInt("balance"));

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return result;
    }

    public Card getCardByNr (String cardNr){
        final String getStmt = "Select * from card where number = ?";
        Card result = null;
        try {
            PreparedStatement statement = connection.prepareStatement(getStmt);
            statement.setString(1, cardNr);
            statement.execute();

            ResultSet rs = statement.getResultSet();
            while (rs.next()){
                result = Card.createDefaultCard(rs.getString("number") , rs.getString("pin"));
                result.setId(rs.getInt("id"));
                result.setBalance(rs.getInt("balance"));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return result;
    }

    public void updateBalance(long balance , int id ) {
        final String getStmt = "update card set balance = balance + ? where id = ?";
        try {
            if (connection != null){
                PreparedStatement statement = connection.prepareStatement(getStmt);
                statement.setLong(1, balance);
                statement.setInt(2, id);
                statement.execute();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Card> getAllAccounts () {
        List<Card> resultList = new ArrayList<>();
        final String getStmt = "SELECT * FROM card";
        try {
            if (connection != null){
                PreparedStatement preparedStatement = connection.prepareStatement(getStmt);
                preparedStatement.execute();
                ResultSet resultSet = preparedStatement.getResultSet();
                Card tempCard;
                while (resultSet.next()){
                    tempCard = Card.createCard(resultSet.getString("number") , resultSet.getString("pin") , resultSet.getLong("balance"));
                    resultList.add(tempCard);
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return resultList;
    }
    public void dropAccount (int id){
        final String deleteStmt = "delete from card where id = ?;";
        try {
            if (connection != null){
                PreparedStatement preparedStatement = connection.prepareStatement(deleteStmt);
                preparedStatement.setInt(1, id);
                preparedStatement.execute();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
