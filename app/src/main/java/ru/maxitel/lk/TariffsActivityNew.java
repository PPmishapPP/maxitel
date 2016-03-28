package ru.maxitel.lk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ru.maxitel.lk.listeners.TariffButtonListener;

public class TariffsActivityNew extends Activity {
    public Button[] buttons = new Button[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tariffs_new);

        buttons[0] = (Button) findViewById(R.id.button8);
        buttons[1] = (Button) findViewById(R.id.button9);
        buttons[2] = (Button) findViewById(R.id.button10);
        buttons[3] = (Button) findViewById(R.id.button11);

        buttons[0].setOnClickListener(new TariffButtonListener(Tariffs.getTarif("Солнечный"),this));
        buttons[1].setOnClickListener(new TariffButtonListener(Tariffs.getTarif("Сатурн"),this));
        buttons[2].setOnClickListener(new TariffButtonListener(Tariffs.getTarif("Нептун"),this));
        buttons[3].setOnClickListener(new TariffButtonListener(Tariffs.getTarif("Уран"),this));


        //как будто нажата кнпока солнечный
        TextView nameTariffTextView = (TextView) findViewById(R.id.textView4);
        TextView priceTextView = (TextView) findViewById(R.id.tariff_price1);
        TextView speedTextView = (TextView) findViewById(R.id.speedTextView1);
        TextView addTariffTextView = (TextView) findViewById(R.id.add_tariff_text_view);
        final Tariff tariff = Tariffs.getTarif("Солнечный");

        nameTariffTextView.setText(tariff.getName());
        priceTextView.setText(String.format(getString(R.string.rub_mes), tariff.getPrise()));
        speedTextView.setText(tariff.toString());
        addTariffTextView.setText(String.format(getString(R.string.rub_add), tariff.getCostOfSwitching()));

        buttons[0].setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        //кнопка подклюить
        Button button = (Button) findViewById(R.id.addTariffButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TariffsActivityNew.this, ConfirmActivity.class);
                intent.setAction(ConfirmActivity.TARIFF_CHANGE);
                intent.putExtra(ConfirmActivity.TARIFF_CHANGE, tariff.getName());
                startActivity(intent);
            }
        });
    }
}
