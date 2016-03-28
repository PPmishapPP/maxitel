package ru.maxitel.lk.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ru.maxitel.lk.LoginActivity;
import ru.maxitel.lk.R;
import ru.maxitel.lk.connects.NotificationConnect;

public class TimeNotification extends BroadcastReceiver {
    public static final String APP_PREFERENCES = "cookies";
    public static final String APP_USER_ID = "user_id";
    public static final String APP_USER_LOGIN = "user_login";
    private AlarmManager alarmManager;
    PendingIntent pendingIntent;
    public TimeNotification() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        int hour = new Date().getHours();

        //чтобы уведомления не работали ночью
        if (hour >=10 && hour < 22) {
            //Если есть схоранёные куки запусаем NotificationConnect
            if (mSettings.contains(APP_USER_ID) && mSettings.contains(APP_USER_LOGIN)) {
                Map<String, String> cookies = new HashMap<>();
                String saveUserId = mSettings.getString(APP_USER_ID, "0");
                String saveUserLogin = mSettings.getString(APP_USER_LOGIN, "0");
                cookies.put(APP_USER_ID, saveUserId);
                cookies.put(APP_USER_LOGIN, saveUserLogin);

                NotificationConnect connect = new NotificationConnect(this, context, cookies);
                connect.execute((Void) null);
            }
        }
        //Если сейчас ночь
        else {
            if (hour == 23) alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (12*60000), pendingIntent);
            else {
                int timeSleep = 11 - hour;
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (timeSleep*60000), pendingIntent);
            }
        }
    }

    //Если интернет не заблокирован NotificationConnect запустит этот метод
    public void showMessage(String balance, Context context){

        //Следующее напоминание через пол дня
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + AlarmManager.INTERVAL_HALF_DAY, pendingIntent);

        CharSequence title = "Maxitel";
        CharSequence message = String.format("На вашем счету %s руб.", balance);
        showNotification(title, message, context);
    }

    //Этот метод не тестировался!
    public void blockInternet(Context context){
        //при заблокированном интернете уведомление слать не надо.
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + AlarmManager.INTERVAL_HALF_DAY, pendingIntent);
    }
    public void noConnect(Context context){
        //Если не удалось проверить баланс следующее соединение через пол часа
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + AlarmManager.INTERVAL_HALF_HOUR, pendingIntent);
    }

    private void showNotification(CharSequence title, CharSequence message, Context context){
        //Интенет для нажатия на уведомление
        Intent intent = new Intent(context, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(title)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        Notification notification = builder.build();
        //Звук и вибрация по умолчанию
        notification.defaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;

        notificationManager.notify(1, notification);

    }
}
