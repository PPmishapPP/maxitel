package ru.maxitel.lk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Михаил on 13.01.2016.
 */
public class Tariffs {
    static List<Tariff> tarifs=new ArrayList<>();

    static {
        List<String> list = new ArrayList<>();
        list.add("02:00 - 09:00 | до 100 мбит/с");
        list.add("09:00 - 17:00 | до 50 мбит/с");
        list.add("17:00 - 02:00 | до 6 мбит/с");
        tarifs.add(new Tariff("Солнечный", 350, 60, "157", list, R.color.sun));

        List<String> list1 = new ArrayList<>();
        list1.add("02:00 - 09:00 | до 100 мбит/с");
        list1.add("09:00 - 17:00 | до 50 мбит/с");
        list1.add("17:00 - 02:00 | до 16 мбит/с");
        tarifs.add(new Tariff("Сатурн", 444, 50, "169", list1, R.color.saturn));

        List<String> list2 = new ArrayList<>();
        list2.add("02:00 - 14:00 | до 100 мбит/с");
        list2.add("14:00 - 02:00 | до 50 мбит/с");
        tarifs.add(new Tariff("Нептун", 599, 40, "168", list2,R.color.neptune));

        List<String> list3 = new ArrayList<>();
        list3.add("Круглосуточно | до 100 мбит/с");
        tarifs.add(new Tariff("Уран", 799, 30, "167", list3, R.color.uranus));

        List<String> list4 = new ArrayList<>();
        list4.add("02:00 - 8:00 | до 5 мбит/с");
        list4.add("08:00 - 2:00 | до 2 мбит/с");
        tarifs.add(new Tariff("Коттеджный - 1", 444, 0, "151", list4,R.color.unknown));

        List<String> list5 = new ArrayList<>();
        list5.add("02:00 - 8:00 | до 10 мбит/с");
        list5.add("08:00 - 2:00 | до 5 мбит/с");
        tarifs.add(new Tariff("Коттеджный - 2", 649, 0, "152", list5,R.color.unknown));

        List<String> list6 = new ArrayList<>();
        list6.add("02:00 - 8:00 | до 20 мбит/с");
        list6.add("08:00 - 2:00 | до 10 мбит/с");
        tarifs.add(new Tariff("Коттеджный - 3", 899, 0, "153", list6,R.color.unknown));

        List<String> list7 = new ArrayList<>();
        list7.add("02:00 - 14:00 | до 100 мбит/с");
        list7.add("14:00 - 02:00 | до 50 мбит/с");
        tarifs.add(new Tariff("Акционный 275", 275, 0, "1", list7,R.color.unknown));

    }

    public static Tariff getTarif(String name){
        for (Tariff tarif:tarifs)
        {
            if (tarif.getName().equals(name)) return tarif;
        }
        return new Tariff("Неизвестный",0,0,"0",new ArrayList<String>(),R.color.unknown);
    }
}
