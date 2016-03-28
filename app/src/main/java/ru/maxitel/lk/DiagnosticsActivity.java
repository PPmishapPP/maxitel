package ru.maxitel.lk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DiagnosticsActivity extends Activity {
    public static final String INTERNET = "internet";
    public static final String TV = "tv";

    private Button button1;
    private Button button2;
    private TextView questionTextView;
    private int step = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostics);
        button1 = (Button) findViewById(R.id.button6);
        button2 = (Button) findViewById(R.id.button7);
        questionTextView = (TextView) findViewById(R.id.infoTextView);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getAction().equals(INTERNET)) {
                    switch (step) {
                        case 0:
                            pc();
                            break;
                        case 100:
                            podklNorm();
                            break;
                        case 200:
                            iLampaNeGorit();
                            break;
                        case 110:
                            sovsemNeRabotaet();
                            break;
                        case 120:
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                            findViewById(R.id.customViewInfo).setVisibility(View.INVISIBLE);
                            nomer_oshibki();
                            break;
                        case 210:
                            povrezdenieVKv();
                            break;
                        case 220:
                            rabotaetNaOdnom();
                            break;
                        case 999:
                            pozvoni();
                            break;
                    }
                } else if (getIntent().getAction().equals(TV)) {
                    switch (step) {
                        case 0:
                            pcTv();
                            break;
                        case 999:
                            pozvoni();
                            break;
                        case 300:
                            pcMarshruti();
                            break;
                    }
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getAction().equals(INTERNET)) {
                    switch (step) {
                        case 0:
                            router();
                            break;
                        case 100:
                            podkl_error();
                            break;
                        case 200:
                            lampa_gorit();
                            break;
                        case 110:
                            rabotaetMedlenno();
                            break;
                        case 120:
                            findViewById(R.id.customViewInfo).setVisibility(View.INVISIBLE);
                            podklNorm();
                            break;
                        case 210:
                            kabelCel();
                            break;
                        case 220:
                            neRabotaetSovsem();
                            break;
                        case 999:
                            finish();
                            break;
                    }
                } else if (getIntent().getAction().equals(TV)) {
                    switch (step) {
                        case 0:
                            routerTv();
                            break;
                        case 300:
                            pcVseRovno();
                            break;
                        case 999:
                            finish();
                            break;
                    }
                }
            }
        });
    }

    private void pcTv() {
        questionTextView.setText(R.string.q2_tv_pc);
        button1.setText("Работает");
        button2.setText("Всё ровно не работает");
        step = 300;
    }
    private void pcVseRovno(){
        questionTextView.setText(R.string.q3_tv_ne_rabotaet);
        step = 999;
        button1.setText("Позвонить");
        button2.setText("Попробую справиться сам");
        ((TextView) findViewById(R.id.textView5)).setText("Решение");
    }
    private void pcMarshruti(){
        questionTextView.setText(R.string.q3_tv_marshruti);
        step = 999;
        button1.setText("Позвонить");
        button2.setText("Попробую справиться сам");
        ((TextView) findViewById(R.id.textView5)).setText("Решение");
    }

    private void routerTv() {
        questionTextView.setText(R.string.q2_tv_router);
        step = 999;
        button1.setText("Позвонить");
        button2.setText("Попробую справиться сам");
        ((TextView) findViewById(R.id.textView5)).setText("Решение");
    }

    private void pc() {
        questionTextView.setText(R.string.question_1_pc);
        button1.setText("Подключается нормально");
        button2.setText("Не подклюается");
        step = 100;
    }

    private void router() {
        questionTextView.setText(R.string.question_1_router);
        button1.setText("Лампочка не горит");
        button2.setText("Лампочка горит но интернет не работает");
        step = 200;
    }

    private void podklNorm() {
        questionTextView.setText(R.string.question_2_pc_norm);
        button1.setText("Совсем не работает");
        button2.setText("Работает, но очень медленно");
        step = 110;
    }

    private void sovsemNeRabotaet() {
        questionTextView.setText(R.string.q3_pc_sovsem_ne_rabotaet);
        step = 999;
        button1.setText("Позвонить");
        button2.setText("Попробую справиться сам");
        ((TextView) findViewById(R.id.textView5)).setText("Решение");
    }

    private void rabotaetMedlenno() {
        questionTextView.setText(R.string.q3_pc_medlenno_rabotaet);
        step = 999;
        button1.setText("Позвонить");
        button2.setText("Попробую справиться сам");
        ((TextView) findViewById(R.id.textView5)).setText("Решение");
    }

    private void podkl_error() {
        questionTextView.setText(R.string.question_2_pc_false);
        findViewById(R.id.customViewInfo).setVisibility(View.VISIBLE);
        button1.setText("Далее");
        button2.setText("Нет никакой ошибки");
        step = 120;
    }

    private void nomer_oshibki() {
        String failNumber = ((EditText) findViewById(R.id.infoEditText)).getText().toString();
        switch (failNumber) {
            case "691":
                questionTextView.setText(R.string.q3_691);
                break;
            case "720":
                questionTextView.setText(R.string.q3_720);
                break;
            case "800":
                questionTextView.setText(R.string.q3_800);
                break;
            default:
                questionTextView.setText(R.string.q3_neizvestnoya_oshibka);
        }
        step = 999;
        button1.setText("Позвонить");
        button2.setText("Попробую справиться сам");
        ((TextView) findViewById(R.id.textView5)).setText("Решение");
    }

    private void iLampaNeGorit() {
        questionTextView.setText(R.string.question_2_router_lampa_ne_gorit);
        button1.setText("Првреждние внутри квартиры");
        button2.setText("Кабель в квартире цел");
        step = 210;
    }

    private void povrezdenieVKv() {
        questionTextView.setText(R.string.q3_povregdenie);
        step = 999;
        button1.setText("Позвонить");
        button2.setText("Попробую справиться сам");
        ((TextView) findViewById(R.id.textView5)).setText("Решение");
    }

    private void kabelCel() {
        questionTextView.setText(R.string.q3_kabel_cel);
        step = 999;
        button1.setText("Позвонить");
        button2.setText("Попробую справиться сам");
        ((TextView) findViewById(R.id.textView5)).setText("Решение");
    }

    private void lampa_gorit() {
        questionTextView.setText(R.string.question_2_router_lampa_gorit);
        button1.setText("Работает на одном из устройств");
        button2.setText("Не работает нигде");
        step = 220;
    }

    private void rabotaetNaOdnom() {
        questionTextView.setText(R.string.q3_rabotaet_na_1_ustr);
        step = 999;
        button1.setText("Позвонить");
        button2.setText("Попробую справиться сам");
        ((TextView) findViewById(R.id.textView5)).setText("Решение");
    }

    private void neRabotaetSovsem() {
        questionTextView.setText(R.string.q3_router_ne_rabotaet_sovsem);
        step = 999;
        button1.setText("Позвонить");
        button2.setText("Попробую справиться сам");
        ((TextView) findViewById(R.id.textView5)).setText("Решение");
    }

    private void pozvoni() {
        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "+7(343)228-00-20"));
        startActivity(dialIntent);
    }


    @Override
    public void onBackPressed() {
        step = 0;
        super.onBackPressed();
    }
}
