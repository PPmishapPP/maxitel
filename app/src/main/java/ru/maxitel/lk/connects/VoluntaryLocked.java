package ru.maxitel.lk.connects;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;

import ru.maxitel.lk.ConfirmActivity;
import ru.maxitel.lk.R;
import ru.maxitel.lk.User;

/**
 * Created by Михаил on 09.02.2016.
 */
public class VoluntaryLocked extends AsyncTask<Void, Void, Document> {
    ConfirmActivity confirmActivity;

    @Override
    protected void onPostExecute(Document document) {
        String message;
        if (document == null) {
            message = confirmActivity.getString(R.string.only_maxitel);
        } else {
            if (document.text().contains("Спасибо, лицевой счёт заблокирован.")) {
                message = "Спасибо, лицевой счёт заблокирован.";
                User.setBlockInternet(true);
                User.setVoluntarilyLocked(true);
            } else {
                message = "Пожалуйста, подождите несколько минут.";
            }
        }
        confirmActivity.showMessage(message);
    }

    @Override
    protected Document doInBackground(Void... params) {
        HashMap<String, String> data = new HashMap<>();

        Document document;
        try {
            data.put("blockanswer", "on");
            data.put("uid", User.getCookies().get("user_id"));
            data.put("block", "Заблокировать");

            document = Jsoup.connect(User.URL + "/?a=19")
                    .data(data)
                    .cookies(User.getCookies())
                    .post();
            return document;
        } catch (Exception e) {
            return null;
        }
    }

    public VoluntaryLocked(ConfirmActivity confirmActivity) {
        this.confirmActivity = confirmActivity;
    }
}
