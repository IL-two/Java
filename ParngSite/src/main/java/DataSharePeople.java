public class DataSharePeople {
    int shareInfected; //Зараженные
    int shareRecovery; //Вылеченные
    int lastDay; //Зараженных за последние сутки
    int shareDie; //Умершие
    String date; //Дата обновления

    //Конструкторы
    public DataSharePeople() {
        this.shareInfected = 0;
        this.shareRecovery = 0;
        this.lastDay = 0;
        this.shareDie = 0;
        this.date = "";
    }

    public DataSharePeople(int shareInfected, int lastDay, int shareRecovery, int shareDie, String date) {
        this.shareInfected = shareInfected;
        this.shareRecovery = shareRecovery;
        this.lastDay = lastDay;
        this.shareDie = shareDie;
        this.date = date;
    }

    //Геттеры и сеттеры
    public int getShareInfected() {
        return shareInfected;
    }

    public void setShareInfected(int shareInfected) {
        this.shareInfected = shareInfected;
    }

    public int getShareRecovery() {
        return shareRecovery;
    }

    public void setShareRecovery(int shareRecovery) {
        this.shareRecovery = shareRecovery;
    }

    public int getShareDie() {
        return shareDie;
    }

    public void setShareDie(int shareDie) {
        this.shareDie = shareDie;
    }

    public int getLastDay() {
        return lastDay;
    }

    public void setLastDay(int lastDay) {
        this.lastDay = lastDay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    //Вывод данных в консоль
    public void outDataSharePeople()
    {
        System.out.println("Зараженные: " + shareInfected +
                "\nЗаразилось за последние сутки: " + lastDay +
                "\nВыздоровело: " + shareRecovery +
                "\nУмерло: " + shareDie +
                "\nДата обновления: " + date +
                "\n-----------------------------");
    }

    //Сравнение данных
    public boolean dataChange(DataSharePeople two)
    {
        return ((this.shareInfected != two.shareInfected) || (this.lastDay != two.lastDay) || (this.shareRecovery != two.shareRecovery) || (this.shareDie != two.shareDie));
    }

    public static String lambData(int site, int db)
    {
        String result = "";

        site = site - db;
        result = site <= 0 ? String.valueOf(site) : ("+" + site);

        return result;
    }
}
