package ru.maxitel.lk;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import ru.maxitel.lk.connects.WidgetConnect;



public class MiniMaxitelWidget extends MaxitelWidget {
    public static String ACTION_WIDGET_RECEIVER = "ActionReceiverWidget";


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        //Интенет для обновления виджета
        Intent active = new Intent(context, MiniMaxitelWidget.class);
        active.setAction(ACTION_WIDGET_RECEIVER);
        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
        views.setOnClickPendingIntent(R.id.mMaxitel, actionPendingIntent);

        //Интернет для запуска приложения
        Intent intent = new Intent(context, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widgetBalanceTextView, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, views);

        if (initCookies(context)) {
            WidgetConnect widgetConnect = new WidgetConnect(this, cookies, context, appWidgetManager,views);
            widgetConnect.execute((Void)null);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (ACTION_WIDGET_RECEIVER.equals(action)) {
            if (initCookies(context)) {
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                ComponentName thisWiget = new ComponentName(context, MiniMaxitelWidget.class);

                views.setTextViewText(R.id.widgetBalanceTextView, ".....");
                appWidgetManager.updateAppWidget(thisWiget, views);
                WidgetConnect widgetConnect = new WidgetConnect(this, cookies, context, appWidgetManager, views);
                widgetConnect.execute((Void) null);
            }
        }
        super.onReceive(context, intent);
    }

    public void postConnect(Context context, AppWidgetManager appWidgetManager, String params[], RemoteViews views){

        CharSequence widgetText = params[0]+"р";
        views.setTextViewText(R.id.widgetBalanceTextView, widgetText);
        ComponentName thisWidget = new ComponentName(context, MiniMaxitelWidget.class);
        appWidgetManager.updateAppWidget(thisWidget, views);
    }
}

