package ru.maxitel.lk.connects;

import android.content.Context;
import android.os.AsyncTask;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ru.maxitel.lk.R;
import ru.maxitel.lk.User;


public class RequestCallConnect extends AsyncTask<Void, Void, String> {

    private String phoneNumber;
    private Context context;

    public RequestCallConnect(Context context, String mPhoneNumber) {
        phoneNumber = mPhoneNumber;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.yandex.ru");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("maxitel.auto", "prudok66ru");
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("maxitel.auto@yandex.ru"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("company@prudok.ru "));
            message.setSubject(context.getString(R.string.email_head));
            message.setText(String.format(context.getString(R.string.email_body), User.getLogin(), phoneNumber));
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
