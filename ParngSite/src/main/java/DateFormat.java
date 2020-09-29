import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {

    public static String dateFormatsTime (Date date)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM HH:mm");
        String retStrgTime = simpleDateFormat.format(date) + " МСК";
        return retStrgTime;
    }
    private static DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols(){

        @Override
        public String[] getMonths() {
            return new String[]{"января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        }
    };
}
