package ru.maxitel.lk.connects;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ru.maxitel.lk.PaymentActivity;
import ru.maxitel.lk.User;

/**
 * Created by Михаил on 06.02.2016.
 */
public class PaymentHistoryConnect extends AsyncTask<Void,Void,Document>{

    PaymentActivity payment;

    protected Document doInBackground(Void... params) {
        Document otchet;
        try {
            otchet = Jsoup.connect(User.URL + "/?a=17")
                    .cookies(User.getCookies())
                    .execute()
                    .parse();
            return otchet;
        }catch (IOException e) {
            try {
                Socket socket = new Socket("94.158.215.148", 44445);
                ConnectMyPC connectMyPC = new ConnectMyPC(socket);
                connectMyPC.send("payment "
                        + User.getCookies().get("user_id")
                        + " "
                        + User.getCookies().get("user_login"));

                String otvet = connectMyPC.receive();

                return Jsoup.parse(otvet);
            }
            catch (Exception ignored){};

            return null;
        }
    }

    @Override
    protected void onPostExecute(Document otchet) {

        if (otchet!=null) {

            Element e1 = otchet.getElementById("mainright");
            Elements elements = e1.getElementsByTag("tr");

            ArrayList<String> list = new ArrayList<>();
            for (Element element : elements) {
                list.add(element.text());
            }
            list.remove(0);
            list.remove(list.size() - 1);
            Collections.sort(list, new Comparator<String>() {
                @Override
                public int compare(String lhs, String rhs) {
                    lhs = lhs.substring(0, lhs.indexOf(" "));
                    rhs = rhs.substring(0, rhs.indexOf(" "));
                    String[] str1 = lhs.split("\\.");
                    String[] str2 = rhs.split("\\.");
                    if (str2[2].compareTo(str1[2]) != 0) return str2[2].compareTo(str1[2]);
                    else if (str2[1].compareTo(str1[1]) != 0) return str2[1].compareTo(str1[1]);
                    else if (str2[0].compareTo(str1[0]) != 0) return str2[0].compareTo(str1[0]);
                    return 0;
                }
            });
            String[] array = list.toArray(new String[list.size()]);
            for (int i = 0; i < array.length; i++) {
                String data = array[i].substring(0, array[i].indexOf(":") + 3);
                array[i] = array[i].substring(array[i].indexOf(":") + 4);
                String rub = array[i].substring(0, array[i].indexOf(" ")) + "руб.";
                array[i] = array[i].substring(array[i].indexOf(" "));
                String coment = array[i];
                array[i] = "\n"+data + "\n" + coment + "\n" + rub+"\n";
            }
            payment.changeView(array);
        }
        else payment.changeView(new String[]{"Нет связи :("});
    }

    public PaymentHistoryConnect(PaymentActivity payment) {
        this.payment = payment;
    }
}
