package ru.maxitel.lk;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ServicesMaxitelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servises_maxitel);
        setSupportActionBar((Toolbar) findViewById(R.id.maxitel_toolbar_services));

        //кнопка история платежей
        TextView paymentHistoryTextView = (TextView) findViewById(R.id.historyPayment);
        paymentHistoryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServicesMaxitelActivity.this, PaymentActivity.class));
            }
        });
        //кнопка упровление счётом
        TextView tariffsTextView = (TextView) findViewById(R.id.newTariffTextView);
        tariffsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, R.string.service_in_development, Snackbar.LENGTH_LONG).show();
            }
        });
        //кнопка обещаный платёж
        TextView promisedPay = (TextView) findViewById(R.id.promisedPaymentTextView);
        promisedPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServicesMaxitelActivity.this, ConfirmActivity.class);
                intent.setAction(ConfirmActivity.PROMISED_PAYMENT);
                startActivity(intent);
            }
        });
        //кнопка добровольная блокировка
        TextView voluntaryLockedTextView = (TextView) findViewById(R.id.voluntarilyLockedTextView);
        final Intent intent = new Intent(ServicesMaxitelActivity.this, ConfirmActivity.class);
        if (!User.isVoluntarilyLocked()) {
            voluntaryLockedTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.setAction(ConfirmActivity.VOLUNTARY_LOCKED);
                    startActivity(intent);
                }
            });
        } else {
            voluntaryLockedTextView.setText("Разблокировать интернет");
            voluntaryLockedTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.setAction(ConfirmActivity.UNBLOCK);
                    startActivity(intent);
                }
            });
        }
    }
}
