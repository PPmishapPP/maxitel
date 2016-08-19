package ru.maxitel.lk.connects;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import ru.maxitel.lk.TariffMaxitelActivity;
import ru.maxitel.lk.User;


public class CheckAvailableTariffs extends AsyncTask<Void,Void, ArrayList<String>>{

    TariffMaxitelActivity tariffMaxitelActivity;

    public CheckAvailableTariffs(TariffMaxitelActivity tariffMaxitelActivity) {
        this.tariffMaxitelActivity = tariffMaxitelActivity;
    }

    @Override
    protected ArrayList<String> doInBackground(Void... params) {
        try {
            Document document = Jsoup.connect(User.URL + "/?a=18")
                    .cookies(User.getCookies())
                    .execute()
                    .parse();
            Elements elements = document.getElementsByTag("option");
            ArrayList<String> list = new ArrayList<>();
            for (Element element : elements){
                list.add(element.text());
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<String> list) {

        tariffMaxitelActivity.publishInfo(list);
        super.onPostExecute(list);
    }
}
