public class WeatherModel {

    private String name;
    private Double temp;
    private String weather;
    private String icons;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getIcons() {
        return icons;
    }

    public void setIcons(String icons) {
        this.icons = icons;
    }

    @Override
    public String toString() {
        return  "" + name + '\n' +
                "" + temp + " \u00b0С"+"\n" +
                "" + weather + '\n' +
                "https://openweathermap.org/img/w/" + icons + ".png";
    }
}
