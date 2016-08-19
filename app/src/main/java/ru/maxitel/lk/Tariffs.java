package ru.maxitel.lk;

import java.util.ArrayList;
import java.util.List;


public class Tariffs {
    static List<Tariff> tarifs=new ArrayList<>();

    static {
        List<String> list = new ArrayList<>();
        list.add("02:00 - 09:00 | до 100 мбит/с");
        list.add("09:00 - 17:00 | до 50 мбит/с");
        list.add("17:00 - 02:00 | до 6 мбит/с");
        tarifs.add(new Tariff("Солнечный", 350, 60, "157", list));

        List<String> list1 = new ArrayList<>();
        list1.add("02:00 - 09:00 | до 100 мбит/с");
        list1.add("09:00 - 17:00 | до 50 мбит/с");
        list1.add("17:00 - 02:00 | до 16 мбит/с");
        tarifs.add(new Tariff("Сатурн", 444, 50, "169", list1));

        List<String> list2 = new ArrayList<>();
        list2.add("02:00 - 14:00 | до 100 мбит/с");
        list2.add("14:00 - 02:00 | до 50 мбит/с");
        tarifs.add(new Tariff("Нептун", 599, 40, "168", list2));

        List<String> list3 = new ArrayList<>();
        list3.add("Круглосуточно | до 100 мбит/с");
        tarifs.add(new Tariff("Уран", 799, 30, "167", list3));

        List<String> list4 = new ArrayList<>();
        list4.add("02:00 - 8:00 | до 5 мбит/с");
        list4.add("08:00 - 2:00 | до 2 мбит/с");
        tarifs.add(new Tariff("Коттеджный - 1", 444, 0, "151", list4));

        List<String> list5 = new ArrayList<>();
        list5.add("02:00 - 8:00 | до 10 мбит/с");
        list5.add("08:00 - 2:00 | до 5 мбит/с");
        tarifs.add(new Tariff("Коттеджный - 2", 649, 0, "152", list5));

        List<String> list6 = new ArrayList<>();
        list6.add("02:00 - 8:00 | до 20 мбит/с");
        list6.add("08:00 - 2:00 | до 10 мбит/с");
        tarifs.add(new Tariff("Коттеджный - 3", 899, 0, "153", list6));

        List<String> list7 = new ArrayList<>();
        list7.add("02:00 - 14:00 | до 100 мбит/с");
        list7.add("14:00 - 02:00 | до 50 мбит/с");
        tarifs.add(new Tariff("Акционный 275", 275, 0, "1", list7));

        List<String> list8 = new ArrayList<>();
        list8.add("02:00 - 14:00 | до 100 мбит/с");
        list8.add("14:00 - 02:00 | до 50 мбит/с");
        tarifs.add(new Tariff("Оптима 450", 450, 50, "189", list8));
        tarifs.add(new Tariff("Оптима 450 (Новый друг-20%)", 360, 0, "0", list8));
        tarifs.add(new Tariff("Оптима 450 (Новый линк-30%)", 315, 0, "0", list8));
        tarifs.add(new Tariff("Оптима 450 (Плачу вперед-12)", 382, 0, "0", list8));
        tarifs.add(new Tariff("Оптима 450 (Плачу вперед-3)", 436, 0, "0", list8));
        tarifs.add(new Tariff("Оптима 450 (Плачу вперед-6)", 418, 0, "0", list8));
        tarifs.add(new Tariff("Оптима 450 (Плачу вперед-9)", 400, 0, "0", list8));
        tarifs.add(new Tariff("Оптима 450 (Своих не забываем-50%)", 225, 0, "0", list8));


        List<String> list9 = new ArrayList<>();
        list9.add("Круглосуточно | до 100 мбит/с");
        tarifs.add(new Tariff("Максима 650", 650, 50, "229", list9));
        tarifs.add(new Tariff("Максима 650 (Новый друг-20%)", 520, 0, "0", list9));
        tarifs.add(new Tariff("Максима 650 (Новый линк-50%)", 325, 0, "0", list9));
        tarifs.add(new Tariff("Максима 650 (Плачу вперед-12)", 552, 0, "0", list9));
        tarifs.add(new Tariff("Максима 650 (Плачу вперед-3)", 630, 0, "0", list9));
        tarifs.add(new Tariff("Максима 650 (Плачу вперед-6)", 604, 0, "0", list9));
        tarifs.add(new Tariff("Максима 650 (Плачу вперед-9)", 578, 0, "0", list9));
        tarifs.add(new Tariff("Максима 650 (Своих не забываем-50%)", 325, 0, "0", list9));

        List<String> list10 = new ArrayList<>();
        list10.add("Круглосуточно | до 150 мбит/с");
        tarifs.add(new Tariff("Компания", 250, 0, "221", list10));

        List<String> list11 = new ArrayList<>();
        list11.add("2:00 - 14:00 | до 10 мбит/c");
        list11.add("14:00 - 2:00 | до 5 мбит/c");
        tarifs.add(new Tariff("Коттедж 600", 600, 50, "191", list11));
        tarifs.add(new Tariff("Коттедж 600 (Новый друг-20%)", 480, 0, "0", list11));
        tarifs.add(new Tariff("Коттедж 600 (Новый линк-30%)", 420, 0, "0", list11));
        tarifs.add(new Tariff("Коттедж 600 (Плачу вперед-12)", 510, 0, "0", list11));
        tarifs.add(new Tariff("Коттедж 600 (Плачу вперед-3)", 582, 0, "0", list11));
        tarifs.add(new Tariff("Коттедж 600 (Плачу вперед-6)", 558, 0, "0", list11));
        tarifs.add(new Tariff("Коттедж 600 (Плачу вперед-9)", 534, 0, "0", list11));
        tarifs.add(new Tariff("Коттедж 600 (Своих не забываем-50%)", 300, 0, "0", list11));

        List<String> list12 = new ArrayList<>();
        list12.add("2:00 - 14:00 | до 20 мбит/c");
        list12.add("14:00 - 2:00 | до 10 мбит/c");
        tarifs.add(new Tariff("Усадьба 850", 850, 50, "192", list12));
        tarifs.add(new Tariff("Усадьба 850 (Новый друг-20%)", 658, 0, "0", list12));
        tarifs.add(new Tariff("Усадьба 850 (Новый линк-50%)", 400, 0, "0", list12));
        tarifs.add(new Tariff("Усадьба 850 (Плачу вперед-12)", 704, 0, "0", list12));
        tarifs.add(new Tariff("Усадьба 850 (Плачу вперед-3)", 776, 0, "0", list12));
        tarifs.add(new Tariff("Усадьба 850 (Плачу вперед-6)", 752, 0, "0", list12));
        tarifs.add(new Tariff("Усадьба 850 (Плачу вперед-9)", 785, 0, "0", list12));
        tarifs.add(new Tariff("Усадьба 850 (Своих не забываем-50%)", 400, 0, "0", list12));
    }

    public static Tariff getTarif(String name){
        for (Tariff tarif:tarifs)
        {
            if (name.equals(tarif.getName())) return tarif;
        }
        return new Tariff("Неизвестный",0,0,"0",new ArrayList<String>());
    }
}
