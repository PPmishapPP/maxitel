package ru.maxitel.lk.listeners;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;

import java.util.Locale;

import ru.maxitel.lk.ConfirmActivity;
import ru.maxitel.lk.R;
import ru.maxitel.lk.User;
import ru.maxitel.lk.notification.State;



public class MaxitelStatesTextViewListener implements View.OnClickListener {

    private State state;
    private Context context;

    public MaxitelStatesTextViewListener(State state, Context context) {
        this.state = state;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        switch (state) {
            case PLAY:
                dialog.setTitle(R.string.inet_on);
                String message = context.getString(R.string.inet_on_message_format);
                message = String.format(Locale.US, message, User.getTariff().getPriseOneDey(), User.getDaysLeft());
                dialog.setMessage(message);
                dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.setNegativeButton(R.string.zablokirovat, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, ConfirmActivity.class);
                        intent.setAction(ConfirmActivity.VOLUNTARY_LOCKED);
                        context.startActivity(intent);
                    }
                });
                dialog.setNeutralButton(R.string.pay_naw2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.payfon24.ru/prudok?card"));
                        context.startActivity(browseIntent);
                    }
                });
                break;
            case PAUSE:
                dialog.setTitle(R.string.voluntarily_locked);
                dialog.setMessage(R.string.voluntarily_locked_info);
                dialog.setPositiveButton(R.string.razblokirovat, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, ConfirmActivity.class);
                        intent.setAction(ConfirmActivity.UNBLOCK);
                        context.startActivity(intent);
                    }
                });
                dialog.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                break;
            case BLOCK:
                dialog.setTitle(R.string.sistem_block);
                dialog.setMessage(R.string.system_block_info);
                dialog.setPositiveButton(R.string.pay_naw2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://m.payfon24.ru/prudok?card"));
                        context.startActivity(browseIntent);
                    }
                });
                dialog.setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        }
        dialog.show();
    }
}