package ru.maxitel.lk.connects;

import android.os.AsyncTask;
import android.text.Html;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ru.maxitel.lk.NewsActivity;
import ru.maxitel.lk.User;


public class NewsConnect extends AsyncTask<Void, String, Void> {
    private NewsActivity newsActivity;
    private final float dp;


    @Override
    protected Void doInBackground(Void... params) {
        try {
            Document document = Jsoup.connect(User.URL_FOR_NEWS).get();
            Elements elements = document.getElementById("nice-menu-1")
                    .getElementsByTag("li");
            for (Element e : elements){
                String href = e.html();
                href = href.substring(href.indexOf("href=\"")+6);
                href = href.substring(0, href.indexOf("\""));
                Document newsDoc = Jsoup.connect(User.URL_FOR_NEWS+href).get();
                Element newsElement = newsDoc.getElementById("post-content");
                publishProgress(newsElement.html());
            }
            return null;
    } catch (Exception e) {
            publishProgress("что то пошло не так :(");
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins((int) (dp*20), (int) (dp*5), (int) (dp*10),0);
        TextView textView = new TextView(newsActivity);
        textView.setLayoutParams(params);
        textView.setText(Html.fromHtml(values[0]));
        newsActivity.setNews(textView);
        super.onProgressUpdate(values);
    }


    public NewsConnect(NewsActivity newsActivity) {
        this.newsActivity = newsActivity;
        dp = newsActivity.getApplicationContext().getResources().getDisplayMetrics().density;
    }
}
