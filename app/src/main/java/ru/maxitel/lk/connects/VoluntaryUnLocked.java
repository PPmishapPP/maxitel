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
public class VoluntaryUnLocked extends AsyncTask<Void,Void,Document> {

    ConfirmActivity confirmActivity;

    @Override
    protected Document doInBackground(Void... params) {
        HashMap<String, String> data = new HashMap<>();

        Document document;
        try {
            data.put("RB", "Разблокировать лицевой счет");

            document = Jsoup.connect(User.URL + "/?a=25")
                    .data(data)
                    .cookies(User.getCookies())
                    .post();
            return document;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Document document) {
        String message;
        if (document == null){
            message = confirmActivity.getString(R.string.only_maxitel);
        }
        else{
            if(document.text().contains("Ваш счет будет разблокирован в течение 15 минут.")){

                 message = confirmActivity.getString(R.string.unbock_10_min);

                User.setVoluntarilyLocked(false);
                User.setBlockInternet(false);
            } else {
                message = confirmActivity.getString(R.string.bag);
            }
        }
        confirmActivity.showMessage(message);
    }

    public VoluntaryUnLocked(ConfirmActivity confirmActivity) {
        this.confirmActivity = confirmActivity;
    }
}
