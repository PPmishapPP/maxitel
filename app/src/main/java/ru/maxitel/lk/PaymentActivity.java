package ru.maxitel.lk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import ru.maxitel.lk.connects.PaymentHistoryConnect;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        new PaymentHistoryConnect(this).execute((Void)null);
    }

    public void changeView(String[] array) {

        if (array == null || array.length == 0){
            array = new String[]{"Пока платежей нет"};
        }
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.paymentRecyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AdapterPaymentHistory(array));

    }
}
