package ru.maxitel.lk;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import java.util.HashMap;
import java.util.Map;


public abstract class MaxitelWidget extends AppWidgetProvider {

     Map<String,String> cookies = new HashMap<>();

    public abstract void postConnect(Context context, AppWidgetManager appWidgetManager, String params[], RemoteViews views);

    //сохраняем данные и обновляем виджет
    public void saveValuesAndPostConnect(Context context, AppWidgetManager appWidgetManager, RemoteViews views, String[] params){
        SharedPreferences mSettings = context.getSharedPreferences(LoginActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(LoginActivity.USER_BALANCE, params[0]);
        editor.putString(LoginActivity.USER_LOGIN, params[1]);
        editor.putString(LoginActivity.USER_TARIFF, params[2]);
        editor.apply();

        postConnect(context, appWidgetManager, params, views);
    }

    //загружаем данные и обновляем виджет
    public void loadValuesAndRunPostConnect(Context context, AppWidgetManager appWidgetManager, RemoteViews views){
        String[] params = new String[4];
        SharedPreferences mSettings = context.getSharedPreferences(LoginActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
        params[0] = mSettings.getString(LoginActivity.USER_BALANCE,"0");
        params[1] = mSettings.getString(LoginActivity.USER_LOGIN, "0");
        params[2] = mSettings.getString(LoginActivity.USER_TARIFF, "0");
        params[3] = "";

        postConnect(context, appWidgetManager, params, views);
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
