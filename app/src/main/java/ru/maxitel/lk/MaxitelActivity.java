package ru.maxitel.lk;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

import ru.maxitel.lk.listeners.MaxitelStatesTextViewListener;
import ru.maxitel.lk.notification.State;

public class MaxitelActivity extends AppCompatActivity {
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    public static final String SHOW_MESSAGE = "show message";
    public static final String NOT_CONNECT = "not connect";
    private int getnerateId;
    float dp;
    int margins;

    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maxitel);
        setSupportActionBar((Toolbar) findViewById(R.id.maxitel_toolbar));

        dp = getApplicationContext().getResources().getDisplayMetrics().density;
        margins = (int) (10*dp);

        TextView balanceView = (TextView) findViewById(R.id.balanceTextView);
        TextView tariffView = (TextView) findViewById(R.id.tariffTextView);
        TextView loginView = (TextView) findViewById(R.id.loginTextView);
        TextView userNameView = (TextView) findViewById(R.id.userNameTextView);
        TextView statusTextView = (TextView) findViewById(R.id.statusTextView);

        //если эта активность вызвана из другой, может быть нужно что то сделать.
        if (getIntent().getAction()!=null) {
            if (getIntent().getAction().equals(SHOW_MESSAGE)) {
                Snackbar.make(balanceView, getIntent().getStringExtra(SHOW_MESSAGE), Snackbar.LENGTH_LONG).show();
            }
            //если не удалось соединиться загружаем всё из настроек
            else if (getIntent().getAction().equals(NOT_CONNECT)){
                String name = getIntent().getStringExtra(LoginActivity.USER_NAME);
                String balance = getIntent().getStringExtra(LoginActivity.USER_BALANCE);
                String tariff = getIntent().getStringExtra(LoginActivity.USER_TARIFF);
                String login = getIntent().getStringExtra(LoginActivity.USER_LOGIN);
                User.setName(name);
                User.setBalance(balance);
                User.setTariff(Tariffs.getTarif(tariff));
                User.setLogin(login);
                final Snackbar snackbar = Snackbar.make(findViewById(R.id.nestedScrollMaxitel),R.string.not_connect_info,Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
            }
        }

        userNameView.setText(User.getName());
        balanceView.setText(String.format(getString(R.string.balanc_format), User.getBalance()));
        tariffView.setText(String.format(getString(R.string.tariff_format), User.getTariff().getName()));
        loginView.setText(String.format(getString(R.string.login_format), User.getLogin()));

        //Статус
        State state = State.PLAY;
        if (User.isVoluntarilyLocked()){
            statusTextView.setText(R.string.voluntarily_locked);
            statusTextView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_av_pause_circle_outline, 0, 0);
            state = State.PAUSE;
        }else if (User.isBlockInternet()){
            statusTextView.setText(R.string.block_internet);
            statusTextView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_av_stop, 0, 0);
            state = State.BLOCK;
        }

        statusTextView.setOnClickListener(new MaxitelStatesTextViewListener(state, this));


        //добовляем информацию от следующем тарифе и взятом кредите
        if (User.getNextTariff()!=null){
            addInfoForNextTariff();
        }
        if (User.getCredit()!=null){
            addInfoForCredit();
        }

        //кнопка заплатить сейчас
        TextView payNaw = (TextView) findViewById(R.id.pay_naw);
        payNaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.payfon24.ru/prudok?card"));
                startActivity(browseIntent);
            }
        });

        //кнопка новости
        TextView newsTextView = (TextView) findViewById(R.id.newsTextView);
        newsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MaxitelActivity.this, NewsActivity.class));
            }
        });

        //кнопка тарифы
        TextView tariffTextView = (TextView) findViewById(R.id.tariffs_textView);
        tariffTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MaxitelActivity.this, TariffsActivityNew.class));
            }
        });

        //кнопка телевидение
        TextView tvTextView = (TextView) findViewById(R.id.tv_text_view);
        tvTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isVLSPlayerAvailable(getApplicationContext()))
                {
                    startActivity(new Intent(MaxitelActivity.this, TvActivity.class));

                }
                else {
                    new AlertDialog.Builder(MaxitelActivity.this)
                            .setTitle("Установить плеер?")
                            .setMessage("Чтобы смотреть телевидиние, нужно установить VLC плеер.")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                            | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
                                            | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                    intent.setData(Uri.parse("market://details?id=org.videolan.vlc.betav7neon"));
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                }

            }
            private boolean isVLSPlayerAvailable(Context context){
                PackageManager pm = context.getPackageManager();
                boolean app_installed = false;
                try {
                    pm.getPackageInfo("org.videolan.vlc.betav7neon",
                            PackageManager.GET_ACTIVITIES);
                    app_installed = true;
                } catch (PackageManager.NameNotFoundException e) {
                    app_installed = false;
                }
                return app_installed;
            }
        });

        //кнопка услуги
        TextView servicesTextView = (TextView) findViewById(R.id.servicesTextView);
        servicesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MaxitelActivity.this, ServicesMaxitelActivity.class));
            }
        });

        //кнопка помощь и поддержка
        TextView helpAndSupportTextView = (TextView) findViewById(R.id.helpAndSupportTextView);
        helpAndSupportTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MaxitelActivity.this, HelpAndSupportActivity.class));
            }
        });

        //Выход
        Button button = (Button) findViewById(R.id.exitButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog  = new AlertDialog.Builder(MaxitelActivity.this);
                dialog.setTitle(R.string.conform);
                dialog.setMessage(R.string.exit_info);
                dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = LoginActivity.mSettings.edit();
                        editor.clear();
                        editor.apply();
                        finish();
                    }
                });
                dialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });

    }

    private void addInfoForCredit() {
        int id;
        if (getnerateId==0){
            id = R.id.loginTextView;
        }else id = getnerateId;

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        //девидер
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dp);
        params.setMargins(margins, margins, margins, margins);
        params.addRule(RelativeLayout.BELOW, id);
        View view = new View(this);
        view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        view.setLayoutParams(params);
        relativeLayout.addView(view);

        //info
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.setMargins(margins, margins * 2, margins, margins);
        params1.addRule(RelativeLayout.BELOW, id);
        TextView textView = new TextView(this);

        String format = getString(R.string.format_credit);
        String textCredit = User.getCredit();
        String date = textCredit.substring(textCredit.indexOf(",") + 2, textCredit.indexOf(")"));
        String time = textCredit.substring(textCredit.indexOf(":")+2,textCredit.indexOf(","));
        String rub = textCredit.substring(0, textCredit.indexOf(".") + 1);


        textView.setText(String.format(format, date, time, rub));
        textView.setLayoutParams(params1);
        textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        textView.setGravity(Gravity.CENTER);
        relativeLayout.addView(textView);

    }

    private void addInfoForNextTariff() {


        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

        //девидер
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)dp);
        params.setMargins(margins, margins, margins, margins);
        params.addRule(RelativeLayout.BELOW, R.id.loginTextView);
        View view = new View(this);
        view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        view.setLayoutParams(params);
        relativeLayout.addView(view);

        //info
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.setMargins(margins, 2 * margins, margins, margins);
        params1.addRule(RelativeLayout.BELOW, R.id.loginTextView);
        TextView textView = new TextView(this);
        String format = getString(R.string.format_next_tp);
        textView.setText(String.format(format, User.getNextTariff().getName()));
        textView.setLayoutParams(params1);
        textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        textView.setGravity(Gravity.CENTER);
        getnerateId = generateViewId();
        textView.setId(getnerateId);
        relativeLayout.addView(textView);

    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis())
            finish();
        else{
            Snackbar.make(findViewById(R.id.balanceTextView), R.string.for_exit ,Snackbar.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    public static int generateViewId() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            for (;;) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }

        } else {

            return View.generateViewId();

        }
    }
}
