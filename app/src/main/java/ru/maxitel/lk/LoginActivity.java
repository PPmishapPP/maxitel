package ru.maxitel.lk;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ru.maxitel.lk.connects.LoginConnect;
import ru.maxitel.lk.connects.LoginCookiesConnect;
import ru.maxitel.lk.connects.LoginPasswordConnect;
import ru.maxitel.lk.notification.TimeNotification;


public class LoginActivity extends AppCompatActivity {

    public static final String APP_PREFERENCES = "cookies";
    public static final String APP_USER_ID = "user_id";
    public static final String APP_USER_LOGIN = "user_login";
    public static final String APP_USER_SES = "user_ses";
    public static final String USER_LOGIN = "login";
    public static final String USER_BALANCE = "balance";
    public static final String USER_TARIFF = "tariff";
    public static final String USER_NAME = "user_name";


    private EditText loginView;
    private EditText passwordView;
    private Button signInButton;
    public static SharedPreferences mSettings;
    private CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        loginView = (EditText) findViewById(R.id.login);
        passwordView = (EditText) findViewById(R.id.password);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        signInButton = (Button) findViewById(R.id.login_sign_in_button);


        //Много зависит от того, есть ли сохраненные куки. Если есть нужно использовать LoginCookiesConnect.
        //Есил нет нужно спрашивать у пользователя Логин и Пароль и использовать LoginPasswordConnect.
        if (mSettings.contains(APP_USER_ID)&&mSettings.contains(APP_USER_LOGIN)){
            Map<String,String> cookies = new HashMap<>();
            String saveUserId = mSettings.getString(APP_USER_ID, "0");
            String saveUserLogin = mSettings.getString(APP_USER_LOGIN, "0");
            String saveUserSes = mSettings.getString(APP_USER_SES, "0");
            cookies.put(APP_USER_ID, saveUserId);
            cookies.put(APP_USER_LOGIN, saveUserLogin);
            cookies.put(APP_USER_SES, saveUserSes);
            User.setCookies(cookies);
            showProgressBar();

            //запускаем уведомления
            restartNotify();


            new LoginCookiesConnect(this).execute((Void) null);


        }
        else {
            FirstConnect firstConnect = new FirstConnect();
            firstConnect.execute((Void) null);

            passwordView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    if (id == EditorInfo.IME_NULL) {
                        attemptLogin();
                        return true;
                    }
                    return false;
                }
            });

            signInButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    attemptLogin();
                }
            });

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (!isChecked)
                        passwordView.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    else
                        passwordView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            });
        }
    }

    //проверяет валидность логина, пароля. Запускает нить loginPasswordConnect.
    private void attemptLogin() {

        // убрать клавиатуру.
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }


        // Reset errors.
        loginView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        String login = loginView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.

        if (password.length() < 6 || password.length() > 8) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        // Check for a valid login address.
        if (TextUtils.isEmpty(login)) {
            loginView.setError(getString(R.string.error_field_required));
            focusView = loginView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgressBar();
            LoginConnect mAuthTask = new LoginPasswordConnect(login, password, this);
            mAuthTask.execute((Void) null);
        }
    }

    //Этот класс для первого соединения, чтобы узнать какой у человека логин.
    public class FirstConnect extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect(User.URL).get();
                return document.getElementsByTag("b").first().text();

            } catch (IOException ignored) {
            }
            return null;

        }

        @Override
        protected void onPostExecute(String login) {

            //если человек еще ничего не успел ввести, вводим логин за него.
            if (loginView.getText().length() == 0 && login != null) {
                loginView.setText(login);
                passwordView.requestFocus();
            }
        }
    }

    //Этот метод вызывается нитью LoginConnect при неуданом соединени.
    //Если нет связи или не верный пароль. Шлёт сообщение.
    public void loginFailed(String error){
        String noConnect = getString(R.string.not_connect);
        //Если не удалось соединиться с сервером.
        if(error.equals(noConnect)){
            //Если есть сохраненные данные
            if (mSettings.contains(USER_BALANCE)){
                Intent intent = new Intent(this, MaxitelActivity.class);
                intent.setAction(MaxitelActivity.NOT_CONNECT);
                intent.putExtra(USER_NAME, mSettings.getString(USER_NAME, "---"));
                intent.putExtra(USER_BALANCE, mSettings.getString(USER_BALANCE, "---"));
                intent.putExtra(USER_LOGIN, mSettings.getString(USER_LOGIN, "---"));
                intent.putExtra(USER_TARIFF, mSettings.getString(USER_TARIFF,"---"));
                startActivity(intent);
                finish();
            }
        }
            hideProgressBar();
            Snackbar.make(signInButton, error, Snackbar.LENGTH_LONG).show();

    }

    //Этот метод вызывается нитью LoginConnect. При удачном соединении.
    //Если нет сохраненых настроек, нужно предложить юеру запомнить куки.
    public void login() {
        if (!mSettings.contains(APP_USER_ID)){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(R.string.good_login)
                    .setMessage(R.string.remember_you)
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            startActivity(new Intent(LoginActivity.this, MaxitelActivity.class));
                            finish();
                        }
                    });
            dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString(APP_USER_ID, User.getCookies().get(APP_USER_ID));
                    editor.putString(APP_USER_LOGIN, User.getCookies().get(APP_USER_LOGIN));
                    editor.putString(APP_USER_SES, User.getCookies().get(APP_USER_SES));
                    editor.apply();
                    //обновляем виджет
                    Intent active = new Intent(LoginActivity.this, BigMaxitelWidget.class);
                    active.setAction(BigMaxitelWidget.BIG_WIDGET_UPDATE);
                    sendBroadcast(active);
                    startActivity(new Intent(LoginActivity.this, MaxitelActivity.class));
                    finish();
                    if (mSettings.contains(APP_USER_ID) && mSettings.contains(APP_USER_LOGIN)) {
                        Snackbar.make(signInButton, R.string.settings_saved, Snackbar.LENGTH_LONG).show();
                    }
                }
            });
            dialog.show();
        }
        else {
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(USER_NAME, User.getName());
            editor.putString(USER_BALANCE, User.getBalance());
            editor.putString(USER_LOGIN, User.getLogin());
            editor.putString(USER_TARIFF, User.getTariff().getName());
            editor.apply();
            startActivity(new Intent(this, MaxitelActivity.class));
            finish();
        }

    }

    public void showProgressBar(){
        signInButton.setVisibility(View.INVISIBLE);
        checkBox.setVisibility(View.INVISIBLE);

        findViewById(R.id.customLogin).setVisibility(View.INVISIBLE);
        findViewById(R.id.customPassword).setVisibility(View.INVISIBLE);
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        findViewById(R.id.textView2).setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        signInButton.setVisibility(View.VISIBLE);
        checkBox.setVisibility(View.VISIBLE);

        findViewById(R.id.customLogin).setVisibility(View.VISIBLE);
        findViewById(R.id.customPassword).setVisibility(View.VISIBLE);
        findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
        findViewById(R.id.textView2).setVisibility(View.INVISIBLE);
    }

    private void restartNotify() {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, TimeNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT );
// На случай, если мы ранее запускали активити, а потом поменяли время,
// откажемся от уведомления
        am.cancel(pendingIntent);
// Устанавливаем разовое напоминание
        am.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() + AlarmManager.INTERVAL_DAY, pendingIntent);
    }

}

