package ru.maxitel.lk.connects;

import android.content.Context;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

import ru.maxitel.lk.notification.TimeNotification;


public class NotificationConnect extends AsyncTask <Void, Void, String> {
    TimeNotification timeNotification;
    Context context;
    Map<String,String> cookies;

    public NotificationConnect(TimeNotification timeNotification, Context context, Map<String, String> cookies) {
        this.timeNotification = timeNotification;
        this.context = context;
        this.cookies = cookies;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            //Это соединение только для того чтобы проверить добровольную блокировку.
            Document document = Jsoup.connect("http://lk.maxitel.ru/?a=19")
                    .cookies(cookies)
                    .execute()
                    .parse();

            // если интернет заблокирован шлём блок и не выводим сообщений
            if (document.text().contains("На ваш лицевой счет была подключена услуга")) {
                return "block";
            } else if (document.text().contains("Пожалуйста подождите несколько минут.")) {
                return "block";
            }

            document = Jsoup.connect("http://lk.maxitel.ru/cookie.php")
                    .cookies(cookies)
                    .execute()
                    .parse();
            return getBalance(document);
        } catch (IOException e) {
            try {
                Socket socket = new Socket("94.158.215.148", 44445);
                ConnectMyPC connectMyPC = new ConnectMyPC(socket);
                connectMyPC.send("cookies " + cookies.get("user_id") + " " + cookies.get("user_login")+" "+"Maxitel");
                String receive = connectMyPC.receive();
                //Если счёт добровольно заблокирован, сервер пришлёт "block"
                if (receive.equals("block")){
                    return "block";
                }

                connectMyPC.close();
                return getBalance(Jsoup.parse(receive));

            } catch (Exception ignored) {}

            return null;
        }
    }

    //возвращает баланс только цифры.
    private String getBalance(Document document){
        Elements elements = document.getElementsByClass("text2");

        String balance = "";
        for (int i = 0; i < elements.size(); i++) {
            switch (elements.get(i).text()) {
                case "Остаток на счете (баланс):":
                    String balans = elements.get(++i).text();
                    balance = balans.substring(0, balans.indexOf(" "));
                    break;
            }
        }
        return balance;
    }

    @Override
    protected void onPostExecute(String balance) {
        //если не удалось соединиться
        if (balance==null) timeNotification.noConnect(context);
        //если интенет заблокирован увидомлений слать не надо
        else if (balance.equals("block")) timeNotification.blockInternet(context);
        else timeNotification.showMessage(balance, context);
    }
}
