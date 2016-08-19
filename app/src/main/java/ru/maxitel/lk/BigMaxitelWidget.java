package ru.maxitel.lk;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.RemoteViews;

import java.util.Calendar;

import ru.maxitel.lk.connects.WeatherConnect;
import ru.maxitel.lk.connects.WidgetConnect;

/**
 * Implementation of App Widget functionality.
 */
public class BigMaxitelWidget extends MaxitelWidget {
    public static final String CLOCK = "update clock";
    public static final String BIG_WIDGET_UPDATE = "big widget update";




    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.big_maxitel_widget);
        ComponentName thisWidget = new ComponentName(context, BigMaxitelWidget.class);
        runUpdateWidget(context, views, appWidgetManager, thisWidget);

    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);

        long time = (long) Math.rint(System.currentTimeMillis()/60000);
        time = time*60000 + 60000;
        Intent intent = new Intent(context, BigMaxitelWidget.class);
        intent.setAction(CLOCK);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.big_maxitel_widget);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisWidget = new ComponentName(context, BigMaxitelWidget.class);
        updateTime(context, views, appWidgetManager, thisWidget);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC, time, 60000, pIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.big_maxitel_widget);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisWidget = new ComponentName(context, BigMaxitelWidget.class);

        if(CLOCK.equals(intent.getAction())){
           updateTime(context, views, appWidgetManager, thisWidget);
        }
        if(BIG_WIDGET_UPDATE.equals(intent.getAction())){
           runUpdateWidget(context, views, appWidgetManager, thisWidget);
        }
        super.onReceive(context, intent);
    }


//этот метод вызвается каждую минуту
    private void updateTime(Context context, RemoteViews views, AppWidgetManager appWidgetManager, ComponentName thisWidget){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        String sHour, sMin;
        if (min<10) sMin = "0" + min;
        else  sMin = String.valueOf(min);
        if (hour<10) sHour = "0" + hour;
        else  sHour = String.valueOf(hour);

        views.setTextViewText(R.id.bDayOfWeekTextVeiw, getDayOfWeek(day,context));
        views.setTextViewText(R.id.bTimeTextView, sHour + ":" + sMin);
        loadValuesAndRunPostConnect(context, appWidgetManager, views);
    }

    private void runUpdateWidget(Context context, RemoteViews views, AppWidgetManager appWidgetManager, ComponentName thisWidget){
        if(initCookies(context)) {
            views.setTextViewText(R.id.bBalanceTextView, ".....");
            appWidgetManager.updateAppWidget(thisWidget, views);
            new WidgetConnect(this, cookies, context, appWidgetManager, views).execute((Void) null);
        }else {
            views.setTextViewText(R.id.bBalanceTextView, context.getString(R.string.voidite));
            appWidgetManager.updateAppWidget(thisWidget, views);
        }
        new WeatherConnect(this, context).execute((Void) null);
    }

    private String getDayOfWeek(int day,Context context){
        switch (day){
            case 1: return context.getString(R.string.voskr);
            case 2: return context.getString(R.string.pon);
            case 3: return context.getString(R.string.vtor);
            case 4: return context.getString(R.string.sreda);
            case 5: return context.getString(R.string.chet);
            case 6: return context.getString(R.string.pyat);
            case 7: return context.getString(R.string.sub);
            default: return  "????";
        }
    }

    public void postWeatherConnect(Context context, String temp, Bitmap bitmap) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.big_maxitel_widget);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisWiget = new ComponentName(context, getClass().getName());

        views.setTextViewText(R.id.bWaterTextVeiw, temp+"°C");
        views.setBitmap(R.id.bWeatherImage, "setImageBitmap", bitmap);
        appWidgetManager.updateAppWidget(thisWiget, views);
    }


    @Override
    public void postConnect(Context context, AppWidgetManager appWidgetManager, String[] params, RemoteViews views) {
        CharSequence widgetText = params[0]+" руб.";
        String login = params[1];
        String tariff = Tariffs.getTarif(params[2]).getName();
        String credit = params[3];
        int balanceInt = Integer.parseInt(params[0]);

        views.setTextViewText(R.id.bBalanceTextView, widgetText);
        views.setTextViewText(R.id.bPymentDay, getPayDays(tariff, balanceInt));
        views.setTextViewText(R.id.bLogin, login);
        views.setTextViewText(R.id.bTariff, tariff);

        //интернет обновления
        Intent active = new Intent(context, BigMaxitelWidget.class);
        active.setAction(BIG_WIDGET_UPDATE);
        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
        views.setOnClickPendingIntent(R.id.bUpdateImageView, actionPendingIntent);

        //Интернет для запуска приложения
        Intent intent = new Intent(context, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.bBalanceTextView, pendingIntent);
        views.setOnClickPendingIntent(R.id.bLogoImageView, pendingIntent);


        ComponentName thisWiget = new ComponentName(context, BigMaxitelWidget.class);
        appWidgetManager.updateAppWidget(thisWiget, views);
    }

    private String getPayDays(String tariff, int balance){
        int day = (int) (balance / Tariffs.getTarif(tariff).getPriseOneDey());
        if (day == 1) return "Оплачен 1 день";
        if ((day>1)&&(day<5)) return "Оплачено "+day+" дня";
        if ((day>=5)&&(day<21)) return "Оплачено "+day+" дней";
        int last = day%10;
        switch (last){
            case 1: return "Оплачен "+day+" день";
            case 2: return "Оплачено "+day+" дня";
            case 3: return "Оплачено "+day+" дня";
            case 4: return "Оплачено "+day+" дня";
            default:return "Оплачено "+day+" дней";
        }
    }

}

