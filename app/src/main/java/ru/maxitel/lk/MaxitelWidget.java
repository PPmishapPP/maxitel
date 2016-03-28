package ru.maxitel.lk;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Михаил on 21.03.2016.
 */
public abstract class MaxitelWidget extends AppWidgetProvider {

     Map<String,String> cookies = new HashMap<>();

    public abstract void postConnect(Context context, AppWidgetManager appWidgetManager, String params[]);

    public void saveValues(Context context, String balance, String tariff, String login){
        SharedPreferences mSettings = context.getSharedPreferences(LoginActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(LoginActivity.USER_TARIFF, tariff);
        editor.putString(LoginActivity.USER_BALANCE, balance);
        editor.putString(LoginActivity.USER_LOGIN, login);
        editor.apply();
    }

    public void loadValuesAndRunPostConnect(Context context, AppWidgetManager appWidgetManager){
        String[] params = new String[4];
        SharedPreferences mSettings = context.getSharedPreferences(LoginActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
        params[0] = mSettings.getString(LoginActivity.USER_BALANCE,"0");
        params[1] = mSettings.getString(LoginActivity.USER_LOGIN, "0");
        params[2] = mSettings.getString(LoginActivity.USER_TARIFF, "0");
        params[3] = "";
        postConnect(context, appWidgetManager, params);
    }

    boolean initCookies(Context context){
        SharedPreferences mSettings = context.getSharedPreferences(LoginActivity.APP_PREFERENCES, Context.MODE_PRIVATE);

        if (mSettings.contains(LoginActivity.APP_USER_ID)&&mSettings.contains(LoginActivity.APP_USER_LOGIN)) {
            String saveUserId = mSettings.getString(LoginActivity.APP_USER_ID, "0");
            String saveUserLogin = mSettings.getString(LoginActivity.APP_USER_LOGIN, "0");
            cookies.put(LoginActivity.APP_USER_ID, saveUserId);
            cookies.put(LoginActivity.APP_USER_LOGIN, saveUserLogin);
            return true;
        }
        return false;
    }
}
