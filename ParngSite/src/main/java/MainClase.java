import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Collections;
import java.util.Objects;

public class MainClase {


    public static void main(String[] args) {

        try {
            Date date = new Date();
            System.out.println("Начало работы программы: " + date.toString());
            String dateString = DateFormat.dateFormatsTime(date);

            Process proc = null;

            boolean shareDataChange =  true; // флаг для проверки записи общих данных
            boolean dataBaseChange = true; // флаг для проверки записи данных в базу данных

            int update = 0; // счетчик

            DBclass.conectDB();

            do {

                if (shareDataChange) {

                    DataSharePeople dataSite = ParsingClass.sharedDataParsing(dateString); //Общие данные с сайта

                    DataSharePeople dataBD = DBclass.insetrTablePeople(); //Общие данных

                    if (dataSite.dataChange(dataBD)) {

                        //Обновление общих данных
                        DBclass.updateTablePeople(dataSite, dataBD);
                        //DBclass.updateTablePeopleRuch(36793, 3057,  4785, 313, "18 апреля 10:38 МСК", "+467", "+40" );

                        shareDataChange = false;
                    }
                }
                else System.out.println("Общие данные не обновлены по причине их не актуальности!");

                if (dataBaseChange) {

                    ArrayList<ModelCity> cityArrayList = ParsingClass.DataCityParsing(); //Список городов взятых с сайта
                    ArrayList<ModelCity> cityDB = DBclass.ReadBD(); //Список городов взятых с базы данных

//                    if (!cityArrayList.containsAll(cityDB))
//                    {
                        ArrayList<ModelCity> cityResult = ModelCity.cities(cityArrayList, cityDB); //Отредактированный список городов на основе данных взятых из сайта и базы данных

                        for (int i = 0; i < cityResult.size(); i++) {

                            //Обновление базы данных по городам
                            //DBclass.updateTableCity(modelCity);
                            cityResult.get(i).outCity();
                        }
                        System.out.println("Данные по регионам обновлены");
                        dataBaseChange = false;
//                    }
//                    else System.out.println("Данные по регионам НЕ обновлены");
//
////                    if (!cityArrayList.equals(cityDB))
////                    {

                    }
//                    else System.out.println("Данные по регионам не обновлены по причине их не актуальности!");
//                }


                if (shareDataChange || dataBaseChange) {
                    update++;
                    System.out.println("Спим одну минуту");
                    Thread.sleep(6000);
                }
            }
            while (!shareDataChange & !dataBaseChange || update < 10);

            if(!shareDataChange & !dataBaseChange) {
                System.out.println("Начинаю рассылку");
//            proc = Runtime.getRuntime().exec("/home/telegrambot/database/test.sh");
//            proc.waitFor();
            }
            else System.out.println("Внимание! Рассылка не произошла!");

            DBclass.CloseDB();
            Date date2 = new Date();
            System.out.println("Окончание работы программы: " + date2.toString());

            if (proc != null) proc.destroy();
        }
        catch (IOException | SQLException | InterruptedException e)
        {
            System.out.println("АТЕНШЕН!!! Ошибка!!!! Илья тупица, пусть переделывает! " + e);
        }
    }


}
