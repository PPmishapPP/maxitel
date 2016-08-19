package ru.maxitel.lk;

import java.util.Date;
import java.util.List;


public class Tariff {
    private String id;
    private String name;
    private int prise;
    private int costOfSwitching;
    private List<String> speed;
    private int [] daysInMonths = {31,28,31,30,31,30,31,31,30,31,30,31};

    public Tariff(String name, int prise, int costOfSwitching, String id, List<String> speed) {
        this.name = name;
        this.prise = prise;
        this.speed = speed;
        this.costOfSwitching = costOfSwitching;
        this.id = id;
    }


    @Override
    public String toString() {
        String text = "";
        for(String s:speed)
        {
            String time = s.substring(0,s.indexOf(" | "));
            String speed = s.substring(s.indexOf("до"));
            text=text+time+"\n"+speed+"\n\n";
        }
        text = text.substring(0,text.length()-2);
        return text;
    }

    public String getName(){
        return name;
    }
    public int getPrise(){
        return prise;
    }
    public int getCostOfSwitching(){
        return costOfSwitching;
    }
    public String getId(){
        return id;
    }
    public double getPriseOneDey(){
        return (double)prise/daysInMonths[new Date().getMonth()];
    }
}
