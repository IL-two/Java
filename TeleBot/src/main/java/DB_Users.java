import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;
import java.sql.*;

public class DB_Users {

    public static Connection connection;
    public static ResultSet resultTable;
    public static Statement statement;
    public static PreparedStatement preparedStatement;
    public static String databaseUrl = "jdbc:sqlite:E://Test/telegrambot_test4.db";

   

    //Закрытие БД
    public static void CloseDB() {
        try {

            if (statement != null) statement.close();
            if (resultTable != null) resultTable.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
            System.out.println("Подключения закрыты.");
        } catch (Exception e) {
            System.out.println("Соединения не закрыты. " + e);
        }
    }

    public void addUser(UserModel user) throws SQLException, IOException {

        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);

        Dao<UserModel, Integer> userDao = DaoManager.createDao(connectionSource, UserModel.class);

        UserModel searchUser = userDao.queryForId(user.getUserID());
        if(searchUser == null) {
            userDao.create(user);
            connectionSource.close();
        } else return;
    }

    public void searchSubscribleUser(SubscribleCity city) throws SQLException, IOException {

         ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);

         Dao<SubscribleCity, Integer> SubDao = DaoManager.createDao(connectionSource, SubscribleCity.class);

        Where<SubscribleCity, Integer> subscrible = SubDao.queryBuilder().where()
                                                                .eq("UserId", city.getUserId())
                                                                .and()
                                                                .eq("City", city.getCity());

        if (subscrible == null) {
            SubDao.create(city);
            connectionSource.close();
        }
        connectionSource.close();
    }
}
