package ru.maxitel.lk.connects;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

import ru.maxitel.lk.MaxitelWidget;
import ru.maxitel.lk.User;


public class WidgetConnect extends AsyncTask<Void,Void, Document> {


    //Это нужно для работы этого класса
    private MaxitelWidget maxitelWidget;
    private Map<String,String> cookies;

    //Это просто передаём дальше после соединения
    private Context context;
    private AppWidgetManager appWidgetManager;
    private RemoteViews views;

    @Override
    protected Document doInBackground(Void... params) {
        Connection.Response connection;
        try {
            connection = Jsoup.connect(User.URL)
                    .cookies(cookies)
                    .execute();
            return connection.parse();
        } catch (IOException e) {
            try {
                Socket socket = new Socket("94.158.215.148", 44445);
                ConnectMyPC connectMyPC = new ConnectMyPC(socket);
                connectMyPC.send("widget "+cookies.get("user_id")+ " " + cookies.get("user_login"));
                return Jsoup.parse(connectMyPC.receive());
            } catch (Exception ignored) {
            }
            return null;
        }
    }
    protected void onPostExecute(Document document) {
        super.onPostExecute(document);
        if (document != null) {
            String balance="", login="", credit="", tariff;

            Elements elements = document.getElementsByClass("text2");

            for (int i = 0; i < elements.size(); i++) {
                switch (elements.get(i).text()) {
                    case "Номер договора:":
                        login = elements.get(++i).text();
                        break;
                    case "Остаток на счете (баланс):":
                        balance = elements.get(++i).text();
                        balance = balance.substring(0, balance.indexOf(" "));
                        break;
                    case "Кредит:":
                        credit = elements.get(++i).text();
                }
            }

            Element element = document.getElementsByClass("text3c").get(0);
            elements = element.getElementsByTag("b");

            tariff = elements.get(1).text();

            String[] params = new String[]{balance,login,tariff,credit};
            maxitelWidget.saveValuesAndPostConnect(context, appWidgetManager, views, params);
        }else {
            maxitelWidget.loadValuesAndRunPostConnect(context, appWidgetManager, views);
        }
    }

    public WidgetConnect(MaxitelWidget maxitelWidget, Map<String, String> cookies, Context context, AppWidgetManager appWidgetManager, RemoteViews views) {
        this.maxitelWidget = maxitelWidget;
        this.cookies = cookies;
        this.context = context;
        this.appWidgetManager = appWidgetManager;
        this.views = views;
    }
}
