package ru.maxitel.lk.connects;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

import ru.maxitel.lk.ConfirmActivity;
import ru.maxitel.lk.R;
import ru.maxitel.lk.User;

/**
 * Created by Михаил on 07.02.2016.
 */
public class PromisedPaymentConnect extends AsyncTask<Void,Void,Document> {

    ConfirmActivity confirmActivity;

    @Override
    protected Document doInBackground(Void... params) {
        HashMap<String, String> map = new HashMap<>();
        Map<String,String> cookies = User.getCookies();

        Document document;
        try {
            document = Jsoup.connect(User.URL + "/?a=11").cookies(cookies).get();

            Elements elements = document.getElementsByAttributeValue("name", "R2");
            for (Element element : elements) {
                map.put("R2", element.val());
            }
            Elements elements1 = document.getElementsByAttributeValue("name", "B111");
            for (Element element : elements1) {
                map.put("B111", element.val());
            }
            Elements elements2 = document.getElementsByAttributeValue("name", "uid");
            for (Element element : elements2) {
                map.put("uid", element.val());
            }
            Elements elements3 = document.getElementsByAttributeValue("name", "aid");
            for (Element element : elements3) {
                map.put("aid", element.val());
            }
            document = Jsoup.connect(User.URL+"/?a=12")
                    .data(map)
                    .cookies(cookies)
                    .post();
            return document;
        }catch (Exception e){
            return null;
        }
    }


    protected void onPostExecute(Document document) {
        String message;
        if (document == null){
            message = confirmActivity.getString(R.string.off_mobil_inet);
        }
        else{
            if(document.text().contains("Предыдущий кредит не погашен")){
                message = confirmActivity.getString(R.string.uze_est);
            } else if(document.text().contains("Баланс вне допустимых границ")){
                message = confirmActivity.getString(R.string.nizkii_balans);
            } else {
                message = confirmActivity.getString(R.string.good_10_min)
                ;
            }


        }
        confirmActivity.showMessage(message);
    }

    public PromisedPaymentConnect(ConfirmActivity confirmActivity) {
        this.confirmActivity = confirmActivity;
    }
}
