package ru.maxitel.lk.listeners;

import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import ru.maxitel.lk.ConfirmActivity;
import ru.maxitel.lk.R;
import ru.maxitel.lk.Tariff;
import ru.maxitel.lk.TariffsActivityNew;


public class TariffButtonListener implements View.OnClickListener {
    Tariff tariff;
    TariffsActivityNew context;
    @Override
    public void onClick(View v) {
        final TextView nameTariffTextView = (TextView) context.findViewById(R.id.textView4);
        final TextView priceTextView = (TextView) context.findViewById(R.id.tariff_price1);
        final TextView speedTextView = (TextView) context.findViewById(R.id.speedTextView1);
        final TextView addTariffTextView = (TextView) context.findViewById(R.id.add_tariff_text_view);
        for (Button button : context.buttons){
            button.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }
        v.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));


        Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale);


        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                nameTariffTextView.setText(tariff.getName());
                priceTextView.setText(String.format(context.getString(R.string.rub_mes), tariff.getPrise()));
                speedTextView.setText(tariff.toString());
                addTariffTextView.setText(String.format(context.getString(R.string.rub_add), tariff.getCostOfSwitching()));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        nameTariffTextView.startAnimation(animation);
        speedTextView.startAnimation(animation);
        priceTextView.startAnimation(animation);
        addTariffTextView.startAnimation(animation);

        //кнопка подклюить
        Button button = (Button) context.findViewById(R.id.addTariffButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ConfirmActivity.class);
                intent.setAction(ConfirmActivity.TARIFF_CHANGE);
                intent.putExtra(ConfirmActivity.TARIFF_CHANGE, tariff.getName());
                context.startActivity(intent);
            }
        });


    }

    public TariffButtonListener(Tariff tariff, TariffsActivityNew context) {
        this.tariff = tariff;
        this.context = context;
    }
}
