package ru.maxitel.lk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import ru.maxitel.lk.connects.NewsConnect;

public class NewsActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private float dp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setSupportActionBar((Toolbar) findViewById(R.id.maxitel_toolbar));

        dp = getApplicationContext().getResources().getDisplayMetrics().density;
        linearLayout = (LinearLayout) findViewById(R.id.newsLinerLayout);
        new NewsConnect(this).execute((Void)null);

    }
    //Этот метод вызывает NewsConnect при удачном соединении.
    public void setNews(TextView textView){
            findViewById(R.id.progressBar2).setVisibility(View.INVISIBLE);
            linearLayout.addView(textView);
            View divider = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (1*dp));
            int margin = (int) (10*dp);
            params.weight=1;
            params.setMargins(margin, margin, margin, margin);
            divider.setLayoutParams(params);
            divider.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            linearLayout.addView(divider);
    }
}
