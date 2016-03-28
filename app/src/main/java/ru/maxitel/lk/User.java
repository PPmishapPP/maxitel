package ru.maxitel.lk;

import java.util.Map;


public class User {

    //В NotificationConnect ссылка прописана отдельно!!
    public static final String URL="http://lk.prudok.ru";
    public static final String URL_FOR_NEWS="http://www.prudok.ru";




    // Куки. Если есть сохраненые настройки они ставятся из настроек и потом используются для входа без пароля.
    // Если настроек нет куки сетятся после входа. В нити LoginPasswordConnect.
    // Причем ставятся даже если пароль не верный, но в таком случае больше не используются.
    private static Map<String,String> cookies;

    // Добровольная блокировка. Сетится в нити LoginPasswordConnect или LoginCookiesConnect.
    private static boolean voluntarilyLocked;

    //Сетятся в LoginConnect.initUser()
    private static String login, balance, name, credit;
    private static Tariff tariff;
    private static Tariff nextTariff;
    private static boolean blockInternet;





    public static Map<String, String> getCookies() {
        return cookies;
    }

    public static void setCookies(Map<String, String> cookies) {
        User.cookies = cookies;
    }

    public static boolean isVoluntarilyLocked() {
        return voluntarilyLocked;
    }

    public static void setVoluntarilyLocked(boolean voluntarilyLocked) {
        User.voluntarilyLocked = voluntarilyLocked;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        User.login = login;
    }

    public static String getBalance() {
        return balance;
    }

    public static void setBalance(String balance) {
        User.balance = balance;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        User.name = name;
    }

    public static String getCredit() {
        return credit;
    }

    public static void setCredit(String credit) {
        User.credit = credit;
    }

    public static Tariff getTariff() {
        return tariff;
    }

    public static void setTariff(Tariff tariff) {
        User.tariff = tariff;
    }

    public static Tariff getNextTariff() {
        return nextTariff;
    }

    public static void setNextTariff(Tariff nextTariff) {
        User.nextTariff = nextTariff;
    }

    public static boolean isBlockInternet() {
        return blockInternet;
    }

    public static void setBlockInternet(boolean blockInternet) {
        User.blockInternet = blockInternet;
    }
    public static String getDaysLeft(){
        int balanceInt = Integer.parseInt(balance);
        int day = (int) (balanceInt / tariff.getPriseOneDey());
        return String.valueOf(day);
    }
}
