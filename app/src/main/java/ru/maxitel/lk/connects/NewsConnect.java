package ru.maxitel.lk.connects;

import android.os.AsyncTask;
import android.text.Html;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

import ru.maxitel.lk.NewsActivity;
import ru.maxitel.lk.User;


public class NewsConnect extends AsyncTask<Void, Void, ArrayList<String>> {
    private NewsActivity newsActivity;
    private final float dp;


    @Override
    protected ArrayList<String> doInBackground(Void... params) {
        ArrayList<String> news = new ArrayList<>();
        try {
            Document document = Jsoup.connect(User.URL_FOR_NEWS).get();
            Element element = document.getElementsByClass("news-list").first();
            String html = element.html();
            //String html = "<p class=\"news-item\"> test";
            while (true) {
                html = html.substring(html.indexOf("<p class=\"news-item\">") + 22);
                if (html.contains("<p class=\"news-item\">")) {
                    String text = html.substring(0, html.indexOf("<p class=\"news-item\">"));
                    news.add(text);
                } else {
                    news.add(html);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            news.add("что то пошло не так :(");
        }
        return news;
    }

    @Override
    protected void onPostExecute(ArrayList<String> news) {
        if (news == null) {
            newsActivity.badNews();
        } else {
            ArrayList<TextView> list = new ArrayList<>();
            for (String s : news) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins((int) (dp*20), (int) (dp*5), (int) (dp*10),0);
                TextView textView = new TextView(newsActivity);
                textView.setLayoutParams(params);
                textView.setText(Html.fromHtml(s));
                list.add(textView);
            }
            newsActivity.setNews(list);
        }
    }

    public NewsConnect(NewsActivity newsActivity) {
        this.newsActivity = newsActivity;
        dp = newsActivity.getApplicationContext().getResources().getDisplayMetrics().density;
    }
}
