package ru.maxitel.lk;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import ru.maxitel.lk.connects.PromisedPaymentConnect;
import ru.maxitel.lk.connects.TariffChangeConnect;
import ru.maxitel.lk.connects.VoluntaryLocked;
import ru.maxitel.lk.connects.VoluntaryUnLocked;


//Сюда должен приходить интент который содержит информацию о действии пользователя.

public class ConfirmActivity extends AppCompatActivity {

    public static final String TARIFF_CHANGE = "Tariff change";
    public static final String PROMISED_PAYMENT = "promised payment";
    public static final String VOLUNTARY_LOCKED = "voluntary_locked";
    public static final String UNBLOCK = "unblock";


    private TextView bodyTextView;
    private Button button;
    private CheckBox confirmCheckBox;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        Intent intent = getIntent();
        String action = intent.getAction();

        Toolbar toolbar = (Toolbar) findViewById(R.id.maxitel_toolbar_confirm);
        setSupportActionBar(toolbar);

        bodyTextView = (TextView) findViewById(R.id.bodyTextView);
        button = (Button) findViewById(R.id.button2);
        confirmCheckBox = (CheckBox) findViewById(R.id.confirmCheckBox);
        String format;

        switch (action){
            case TARIFF_CHANGE:
                final Tariff newTariff = Tariffs.getTarif(intent.getStringExtra(TARIFF_CHANGE));
                getSupportActionBar().setTitle(newTariff.getName());

                if (User.isBlockInternet() || User.isVoluntarilyLocked()){
                    bodyTextView.setText(R.string.dont_change);
                    confirmCheckBox.setEnabled(false);
                    button.setEnabled(false);
                } else {
                    format = getString(R.string.change_tariff_info);
                    bodyTextView.setText(String.format(format, newTariff.getPrise(), newTariff.getPriseOneDey(), newTariff.getCostOfSwitching()));

                    button.setText(R.string.run_tariff);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (confirmCheckBox.isChecked()) {
                                new TariffChangeConnect(newTariff, ConfirmActivity.this).execute((Void) null);
                            }
                        }
                    });
                }
                break;
            case PROMISED_PAYMENT:
                format = getString(R.string.credit_info);
                bodyTextView.setText(String.format(format, User.getTariff().getPrise()));
                button.setText(R.string.next);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (confirmCheckBox.isChecked()){
                            new PromisedPaymentConnect(ConfirmActivity.this).execute((Void)null);
                        }
                    }
                });
                break;
            case VOLUNTARY_LOCKED:
                bodyTextView.setText(R.string.voluntary_locked_info);
                button.setText(R.string.next);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new VoluntaryLocked(ConfirmActivity.this).execute((Void)null);
                    }
                });
                break;
            case UNBLOCK:
                bodyTextView.setText(R.string.unblock_info);
                button.setText(R.string.next);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new VoluntaryUnLocked(ConfirmActivity.this).execute((Void)null);
                    }
                });

        }
    }


    //этот метод вызывает класс Connect после соединения.
    public void showMessage(String message) {
        Intent intent = new Intent(this, MaxitelActivity.class);
        intent.setAction(MaxitelActivity.SHOW_MESSAGE);
        intent.putExtra(MaxitelActivity.SHOW_MESSAGE, message);
        startActivity(intent);
    }
}
