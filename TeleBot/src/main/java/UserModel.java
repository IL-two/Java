import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class UserModel {

    @DatabaseField(columnName = "User_id", id = true)
    private int UserID;
    @DatabaseField(columnName = "first_name")
    private String UserFirstName;
    @DatabaseField(columnName = "last_name")
    private String UserLastName;
    @DatabaseField(columnName = "Sbscrible")
    private boolean Subscrible = false;

    public UserModel(){}

    public boolean isSubscrible() {
        return Subscrible;
    }

    public void setSubscrible(boolean subscrible) {
        Subscrible = subscrible;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getUserFirstName() {
        return UserFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        UserFirstName = userFirstName;
    }

    public String getUserLastName() {
        return UserLastName;
    }

    public void setUserLastName(String userLastName) {
        UserLastName = userLastName;
    }

    @Override
    public String toString() {
        return "Привет, " + UserFirstName + "! Я погодный телеграмм бот производства ДИМ.\n" +
                "Нажми /help, что бы узнать, что я умею.\n" +
                "Удачи, и хорошей погоды! =)";
    }
}
