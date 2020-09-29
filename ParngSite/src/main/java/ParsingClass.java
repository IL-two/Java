import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ParsingClass {

    //Z://site.html
    ///home/telegrambot/site.html
    public static String inputFileDirectoty = "C:/Users/idukh/Desktop/testSite.html";
    public static String encoding = "UTF-8";


    //Парсинг сайта
    public static ArrayList<ModelCity> DataCityParsing() throws IOException
        {
            ArrayList<ModelCity> cityArrayList = new ArrayList<ModelCity>();

            File inputFile = new File(inputFileDirectoty);
            Document document = Jsoup.parse(inputFile, encoding, "https://xn--80aesfpebagmfblc0a.xn--p1ai");
            //Document document = Jsoup.connect("https://xn--80aesfpebagmfblc0a.xn--p1ai/information/").referrer("/information/").userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.96 YaBrowser/20.4.0.1461 Yowser/2.5 Safari/537.36").get();

            Element table = document.select("table").first();
            Elements rows = table.select("tbody");

//            Element table = document.select("table").first();
////            Elements rows = table.select("tr");
//            Elements city = table.select("col-region");
//            Elements cured = table.select("col-sick");
//            Elements recovery = table.select("col-healed");
//            Elements dead = table.select("col-died");

            Elements city = rows.select("tr");


            //Берем данные по городам
            for (int i = 0; i < city.size(); i++)
            {
                Element row = city.get(i);
                Elements cols = row.select("td");

                cityArrayList.add(i, new ModelCity(row.select("th").text(),
                        Integer.parseInt(cols.get(0).text()),
                        Integer.parseInt(cols.get(3).text()),
                        Integer.parseInt(cols.get(4).text())));

//                cityArrayList.add(i, new ModelCity(city.get(i).text(),
//                        Integer.parseInt(cured.get(i).text()),
//                        Integer.parseInt(recovery.get(i).text()),
//                        Integer.parseInt(dead.get(i).text())));
            }

            return cityArrayList;
    }

    //Брем общие данные
    public static DataSharePeople sharedDataParsing(String date) throws IOException
    {
        DataSharePeople data;

        //Подключение к скачанному файлу
//        File inputFile = new File(inputFileDirectoty);
//        Document document = Jsoup.parse(inputFile, encoding, "https://xn--80aesfpebagmfblc0a.xn--p1ai");

        //Подключение к сайту на прямую
        Document document = Jsoup.connect("https://xn--80aesfpebagmfblc0a.xn--p1ai").userAgent("Chrome/4.0.249.0 Safari/532.5").get();

        Elements incorectr = document.getElementsByClass("cv-countdown__item-value");


        //Взять ресурсы с сайта
        data = new DataSharePeople(stringToInt(incorectr.get(1).text()),
                                                stringToInt(incorectr.get(2).text()),
                                                stringToInt(incorectr.get(3).text()),
                                                stringToInt(incorectr.get(4).text()),
                                                date);

        //data.outDataSharePeople();

        return data;
    }

    //Перевод String to Integer
    public static int stringToInt(String s)
    {
        try {
            if (s.indexOf(" ") != -1) {
                String[] split = s.split(" ");
                s = "";
                for (int i = 0; i < split.length; i++)
                {
                    s += split[i];
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return Integer.parseInt(s);
    }
}
