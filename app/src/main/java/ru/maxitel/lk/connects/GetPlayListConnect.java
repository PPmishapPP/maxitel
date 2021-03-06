package ru.maxitel.lk.connects;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import ru.maxitel.lk.R;
import ru.maxitel.lk.TvActivity;


public class GetPlayListConnect extends AsyncTask<Void,Void,Void> {
    private static final String URL = "http://rlist.maxitel.ru/tv/iptv.m3u";
    private ArrayList<String> channels = new ArrayList<>();
    private ArrayList<String> urls = new ArrayList<>();
    TvActivity activity;

    @Override
    protected Void doInBackground(Void... params) {

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(URL).openStream()));

            //так и долно быть!
            String s = reader.readLine();

            while ((s=reader.readLine())!=null) {
                if (s.contains("<head>")){
                    channels.add(activity.getString(R.string.iptv_ne_v_seti));
                    break;
                }
                if (!s.contains("#EXTINF:")) continue;
                channels.add(s.substring(s.indexOf(",") + 1));
                urls.add(reader.readLine());
            }
            reader.close();
            if (channels.size() == 0) channels.add(activity.getString(R.string.iptv_ne_v_seti));
        } catch (IOException e) {
            channels.add(activity.getString(R.string.iptv_ne_v_seti));
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        activity.onGoodPlayList(channels, urls);
        super.onPostExecute(aVoid);
    }

    public GetPlayListConnect(TvActivity activity) {
        this.activity = activity;
    }
}
