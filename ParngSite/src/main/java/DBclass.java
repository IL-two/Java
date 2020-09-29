import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DBclass {
    public static Connection connection;
    public static ResultSet resultTable;
    public static Statement statement;
    public static PreparedStatement preparedStatement;

    //Подключение к БД
    public static void conectDB()
    {
        try
        {
            //X:/telegrambot.db  -  подключение к БД удаленно
            ///home/telegrambot/database/telegrambot.db  -  подключение к БД на малине
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:E://Test/telegrambot_test4.db");
            statement = connection.createStatement();
            connection.setAutoCommit(false);

            System.out.println("Подключение успешно!");
        }
        catch (Exception e)
        {
            System.out.println("Подключение не удалось!\n" + e);
        }
    }

    //Закрытие БД
    public static void CloseDB()
    {
        try
        {
            //connection.close();
            if (statement != null) statement.close();
            //statement.close();
            if (resultTable != null) resultTable.close();
            //resultTable.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();

            System.out.println("Подключения закрыты.");
        }
        catch (Exception e)
        {
            System.out.println("Соединения не закрыты. " + e);
        }
    }

    //Запись БД города
    public static void insertTableCity(ModelCity modelCity) throws SQLException {
        String sql = "UPDATE INTO regions (name, infected, cured, dead)" +
                        "VALUES (?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, modelCity.getCity());
            preparedStatement.setInt(2, modelCity.getInfected());
            preparedStatement.setInt(3, modelCity.getRecovery());
            preparedStatement.setInt(4, modelCity.getDie());
            preparedStatement.executeUpdate();

            //System.out.println("Данные добавлены");
            connection.commit();
        }
        catch (Exception e)
        {
            System.out.println("Данные по городу не добавлены " + e);
            connection.rollback();
        }
    }

    //Чтение БД города
    public static ArrayList<ModelCity> ReadBD() throws IOException {
        ArrayList<ModelCity> modelCities = new ArrayList<ModelCity>();
        try
        {
            resultTable = statement.executeQuery("SELECT * FROM regions");

            while (resultTable.next())
            {
                modelCities.add(new ModelCity(resultTable.getString("name"),
                                resultTable.getInt("infected"),
                                resultTable.getInt("cured"),
                                resultTable.getInt("dead")));
            }
            //System.out.println("\nТаблица regions прочтена!");
        }
        catch (Exception e)
        {
            System.out.println("Таблица regions не прочтена! " + e);
        }
        return modelCities;
    }

    //Редактирование БД города
    public static void updateTableCity(ModelCity site) throws SQLException {
        String sql = "UPDATE regions SET infected = ?, cured = ?, dead = ?, lambinfected = ?, lambcured = ?, lambdead = ? WHERE name = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, site.getInfected());
            preparedStatement.setInt(2, site.getRecovery());
            preparedStatement.setInt(3, site.getDie());
            preparedStatement.setString(4, site.getLambinfected());
            preparedStatement.setString(5, site.getLambcured());
            preparedStatement.setString(6, site.getLambdead());
            preparedStatement.setString(7, site.getCity());

            preparedStatement.executeUpdate();

            connection.commit();
            //System.out.println("Данные обновлены");
        }
        catch (Exception e)
        {
            System.out.println("Данные по городу не обновлены " + e);
            connection.rollback();
        }
    }

    //Чтение с БД общих данных
    public static DataSharePeople insetrTablePeople()
    {
        DataSharePeople people = new DataSharePeople();
        try {
            resultTable = statement.executeQuery("SELECT * FROM general");
            people = new DataSharePeople(resultTable.getInt("infected"),
                                            resultTable.getInt("lastinfect"),
                                            resultTable.getInt("cured"),
                                            resultTable.getInt("dead"),
                                            resultTable.getString("date"));

            //System.out.println("Общие данные добавлены");
        }
        catch (Exception e)
        {
            System.out.println("Общие данные не добавлены " + e);
        }
        return people;
    }

    //Редактирование БД общих данных
    public static void updateTablePeople(DataSharePeople site, DataSharePeople db) throws SQLException {
        String sql = "UPDATE general SET infected = ?, cured = ?, lastinfect = ?, dead = ?, date = ?, lambcured = ?, lambdead = ? WHERE rowid = 1";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, site.getShareInfected());
            preparedStatement.setInt(2, site.getShareRecovery());
            preparedStatement.setInt(3, site.getLastDay());
            preparedStatement.setInt(4, site.getShareDie());
            preparedStatement.setString(5, site.getDate());
            preparedStatement.setString(6,  DataSharePeople.lambData(site.getShareRecovery(), db.getShareRecovery()));
            preparedStatement.setString(7, DataSharePeople.lambData(site.getShareDie(), db.getShareDie()));
            preparedStatement.executeUpdate();

            connection.commit();
            System.out.println("Общие данные обновлены");
        }
        catch (Exception e)
        {
            System.out.println("Общие данные не обновлены " + e);
            connection.rollback();
        }
    }

    //Редактирование БД общих данных
    public static void updateTablePeopleRuch(int infect, int revover, int lastDay, int day, String date, String shareRecor, String shareDie) throws SQLException {
        String sql = "UPDATE general SET infected = ?, cured = ?, lastinfect = ?, dead = ?, date = ?, lambcured = ?, lambdead = ? WHERE rowid = 1";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, infect);
            preparedStatement.setInt(2, revover);
            preparedStatement.setInt(3, lastDay);
            preparedStatement.setInt(4, day);
            preparedStatement.setString(5, date);
            preparedStatement.setString(6,  shareRecor);
            preparedStatement.setString(7, shareDie);
            preparedStatement.executeUpdate();

            connection.commit();
            System.out.println("Общие данные обновлены");
        }
        catch (Exception e)
        {
            System.out.println("Общие данные не обновлены " + e);
            connection.rollback();
        }
    }
}
