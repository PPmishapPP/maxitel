package ru.maxitel.lk.connects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ru.maxitel.lk.BigMaxitelWidget;

/**
 * Created by Михаил on 21.03.2016.
 */
public class WeatherConnect extends AsyncTask <Void, Void, String> {

    private Context context;

    private BigMaxitelWidget bigMaxitelWidget;
    private String sURL = "http://api.openweathermap.org/data/2.5/weather?id=1486209&appid=93792087279a8a52d899ae7c586ffdd6";

    private Bitmap bitmap;

    @Override
    protected String doInBackground(Void... params) {
        double temp = 0;
        try {
            //получаем джисон с помощью API если случитсся ошибка вернётся пустая строка
            String JSON = getJSON();
            //если пришла пустая строка выходим из метода
            if (JSON.length()==0) return null;

            JSONObject jsonObject = new JSONObject(getJSON());

            //Получаем температуру по цельсию
            JSONObject main = jsonObject.getJSONObject("main");
            temp = main.getDouble("temp");
            temp = temp - 273.15;
            long lTemp = Math.round(temp);

            //Получаем ссылку на иконку с погодой
            JSONArray array = jsonObject.getJSONArray("weather");
            JSONObject weatherJSON = array.getJSONObject(0);
            String img = weatherJSON.getString("icon");
            URL url = new URL("http://openweathermap.org/img/w/"+img+".png");

            //Загружаем картинку
            InputStream is = url.openStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return String.valueOf(lTemp);


        } catch (Exception ignored) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(String temp) {
        if (temp!=null){
            bigMaxitelWidget.postWeatherConnect(context, temp, bitmap);
        }
    }

    public WeatherConnect(BigMaxitelWidget bigMaxitelWidget, Context context) {
        this.bigMaxitelWidget=bigMaxitelWidget;
        this.context = context;
    }

    private String getJSON(){
        String resultJson = "";
        try {
            URL url = new URL(sURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            resultJson = buffer.toString();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJson;
    }

}
