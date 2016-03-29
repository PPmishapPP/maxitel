package ru.maxitel.lk;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


public class HelpAndSupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_and_support);
        setSupportActionBar((Toolbar) findViewById(R.id.maxitel_toolbar_help));

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "+7(343)228-00-20"));
                startActivity(dialIntent);
            }
        });
        findViewById(R.id.requestСall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HelpAndSupportActivity.this, ConfirmActivity.class).setAction(ConfirmActivity.REQUEST_CALL));
            }
        });

        final Intent intent = new Intent(this, DiagnosticsActivity.class);

        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setAction(DiagnosticsActivity.INTERNET);
                startActivity(intent);
            }
        });

        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setAction(DiagnosticsActivity.TV);
                startActivity(intent);
            }
        });
    }
}
