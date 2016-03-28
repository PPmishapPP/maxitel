package ru.maxitel.lk.connects;

import android.os.AsyncTask;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ru.maxitel.lk.LoginActivity;
import ru.maxitel.lk.R;
import ru.maxitel.lk.Tariffs;
import ru.maxitel.lk.User;


public abstract class LoginConnect extends  AsyncTask<Void,Void,Document> {
    LoginActivity loginActivity;
    @Override
    protected void onPostExecute(Document document) {
        if (document==null){
            loginActivity.loginFailed(loginActivity.getString(R.string.not_connect));
        }
        else {
            if (document.text().contains(loginActivity.getString(R.string.incorrectPassword))) {
                loginActivity.loginFailed(loginActivity.getString(R.string.incorrectPassword));
            } else {
                initUser(document);
                loginActivity.login();
            }
        }
    }

    private void initUser(Document document){
        Elements elements = document.getElementsByClass("text2");

        for (int i=0; i<elements.size();i++){
            switch (elements.get(i).text()){
                case "Номер договора:":
                    User.setLogin(elements.get(++i).text());
                    break;
                case "Контактное лицо:":
                    User.setName(elements.get(++i).text());
                    break;
                case "Остаток на счете (баланс):":
                    String balance = elements.get(++i).text();
                    User.setBalance(balance.substring(0, balance.indexOf(" ")));
                    break;
                case "Кредит:":
                    User.setCredit(elements.get(++i).text());
            }
        }

        Element element = document.getElementsByClass("text3c").get(0);
        elements = element.getElementsByTag("b");

        User.setTariff(Tariffs.getTarif(elements.get(1).text()));

        if (elements.size()>=4) User.setNextTariff(Tariffs.getTarif(elements.get(3).text()));
        if (document.text().contains("Лицевой счёт отключен."))  User.setBlockInternet(true);
        else User.setBlockInternet(false);
    }
}
