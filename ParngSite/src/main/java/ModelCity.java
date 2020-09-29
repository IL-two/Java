import java.util.ArrayList;
import java.util.Objects;
import java.util.Collections;

public class ModelCity {

    String City; //Город
    int infected; //Зараженные
    int recovery; //Вылеченные
    int die; //Умершие
    String lambinfected;
    String lambcured;
    String lambdead;

    //Конструкторы
    public ModelCity() {
        City = null;
        infected = 0;
        recovery = 0;
        die = 0;
    }
    public ModelCity(String city, int infected, int recovery, int die) {
        City = city;
        this.infected = infected;
        this.recovery = recovery;
        this.die = die;
    }
    public ModelCity(String city, int infected, int recovery, int die, String lambinfected, String lambcured, String lambdead) {
        City = city;
        this.infected = infected;
        this.recovery = recovery;
        this.die = die;
        this.lambinfected = lambinfected;
        this.lambcured = lambcured;
        this.lambdead = lambdead;
    }

    //Геттеры и сеттеры
    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public int getInfected() {
        return infected;
    }

    public void setInfected(int infected) {
        this.infected = infected;
    }

    public int getRecovery() {
        return recovery;
    }

    public void setRecovery(int recovery) {
        this.recovery = recovery;
    }

    public int getDie() {
        return die;
    }

    public void setDie(int die) {
        this.die = die;
    }

    public String getLambinfected() {
        return lambinfected;
    }

    public void setLambinfected(String lambinfected) {
        this.lambinfected = lambinfected;
    }

    public String getLambcured() {
        return lambcured;
    }

    public void setLambcured(String lambcured) {
        this.lambcured = lambcured;
    }

    public String getLambdead() {
        return lambdead;
    }

    public void setLambdead(String lambdead) {
        this.lambdead = lambdead;
    }

    //Методы

    //Вывод на экран города
    public void outCity()
    {
        System.out.println(City+ ":" +
                "\nЗараженные: " + infected + " (" + lambinfected + ")" +
                "\nВыздоровело: " + recovery + " (" + lambcured + ")" +
                "\nУмерло: " + die + " (" + lambdead + ")" +
                "\n-----------------------------");
    }

    //Сравнение по названию города
    public static boolean cityComparsion(ModelCity one, ModelCity two)
    {
        return one.City.equalsIgnoreCase(two.City);
    }

    //Редактирование города
    public ModelCity updateData (ModelCity two)
    {
        this.infected = two.infected;
        this.recovery = two.recovery;
        this.die = two.die;
        return this;
    }


    public static ArrayList<ModelCity> cities(ArrayList<ModelCity> site, ArrayList<ModelCity> db)
    {
        ArrayList<ModelCity> result = new ArrayList<ModelCity>();

        for (int i = 0; i < site.size(); i++)
        {
            int index = db.indexOf(site.get(i));

            if (index >= 0)
            {
                if ((site.get(i).getInfected() != db.get(index).getInfected()) ||
                        (site.get(i).getRecovery() != db.get(index).getRecovery()) ||
                        (site.get(i).getDie() != db.get(index).getDie()))
                {
                    result.add(new ModelCity(site.get(i).getCity(),
                            site.get(i).getInfected(),
                            site.get(i).getRecovery(),
                            site.get(i).getDie(),
                            DataSharePeople.lambData(site.get(i).getInfected(), db.get(index).getInfected()),
                            DataSharePeople.lambData(site.get(i).getRecovery(), db.get(index).getRecovery()),
                            DataSharePeople.lambData(site.get(i).getDie(), db.get(index).getDie())));
                }
            }
            else System.out.println("2");
        }
        return result;
    }

    public boolean dataCityComparison(ModelCity two)
    {
        boolean result = true;

        return ((this.infected != two.infected) || (this.recovery != two.recovery) || (this.die != two.die));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelCity modelCity = (ModelCity) o;
        return Objects.equals(City, modelCity.City);
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(City, infected, recovery, die);
//    }
}
