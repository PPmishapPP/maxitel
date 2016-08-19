package ru.maxitel.lk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import ru.maxitel.lk.connects.CheckAvailableTariffs;

public class TariffMaxitelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tariff_maxitel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tariff_toolbar);
        setSupportActionBar(toolbar);
        new CheckAvailableTariffs(this).execute((Void)null);

    }

    public void publishInfo(ArrayList<String> list){
        ArrayList<Tariff> tariffs = new ArrayList<>();
        for (String s: list){
            tariffs.add(Tariffs.getTarif(s));
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.tariffRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new AdapterTariffGrid(tariffs));
    }

}
