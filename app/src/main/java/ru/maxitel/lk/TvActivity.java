package ru.maxitel.lk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import ru.maxitel.lk.connects.GetPlayListConnect;

public class TvActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        GetPlayListConnect connect = new GetPlayListConnect(this);
        connect.execute((Void)null);
    }


    public void onGoodPlayList(ArrayList<String> channels, ArrayList<String> urls) {
        AdapterTvGrid adapterTvGrid = new AdapterTvGrid(channels,urls,this);
        if (urls.size()==0) recyclerView.setLayoutManager(new LinearLayoutManager(this));
        else  recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapterTvGrid);
    }
}
