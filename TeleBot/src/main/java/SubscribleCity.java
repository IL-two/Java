import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Subscriptions")
public class SubscribleCity {

    @DatabaseField(columnName = "UserId")
    private Integer UserId;
    @DatabaseField(columnName = "City")
    private String City;

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public SubscribleCity() {
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}
