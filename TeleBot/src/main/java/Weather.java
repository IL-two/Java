import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {

    private static String ID = "373a7c37a72cbb3cbd0b561faec136f4";
    private static String METRIC = "&units=metric";

    public static WeatherModel getWeather (String message, WeatherModel model) throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+message+"&"+METRIC+"&APPID="+ID);

        Scanner scanner = new Scanner((InputStream) url.getContent());

        String result = "";
        while (scanner.hasNext()){
            result += scanner.nextLine();
        }

        JSONObject object = new JSONObject(result);
        JSONObject main = object.getJSONObject("main");
        JSONArray array = object.getJSONArray("weather");

        model.setName(object.getString("name"));
        model.setTemp(main.getDouble("temp"));

        for (int i = 0; i < array.length(); i++){
            JSONObject jsonObject = array.getJSONObject(i);
            model.setWeather(jsonObject.getString("main"));
            model.setIcons(jsonObject.getString("icon"));
        }

        return model;
    }
}
