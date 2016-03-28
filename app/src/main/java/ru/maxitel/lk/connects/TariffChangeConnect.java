package ru.maxitel.lk.connects;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;

import ru.maxitel.lk.ConfirmActivity;
import ru.maxitel.lk.R;
import ru.maxitel.lk.Tariff;
import ru.maxitel.lk.User;




public class TariffChangeConnect extends AsyncTask<Void,Void, Document> {

    private Tariff newTariff;
    private ConfirmActivity confirmActivity;

    @Override
    protected Document doInBackground(Void... params) {
        HashMap<String, String> map = new HashMap<>();
        Map<String, String> cookies = User.getCookies();

        Document document;
        try {
            map.put("nexttpid", newTariff.getId());
            map.put("ctpid", User.getTariff().getId());
            map.put("uid",cookies.get("user_id"));
            map.put("change","Сменить");

            document = Jsoup.connect(User.URL + "/?a=18")
                    .data(map)
                    .cookies(cookies)
                    .post();
            return document;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Document document) {
        super.onPostExecute(document);
        String message;


        if (document == null){
            message = confirmActivity.getString(R.string.only_maxitel);
        }
        else{
            if (document.text().contains("Спасибо, тариф изменён.")) {
               message = confirmActivity.getString(R.string.good);
            }
            else message = confirmActivity.getString(R.string.bag);
        }
        confirmActivity.showMessage(message);
    }

    public TariffChangeConnect(Tariff newTariff, ConfirmActivity confirmActivity) {
        this.newTariff = newTariff;
        this.confirmActivity = confirmActivity;
    }
}
