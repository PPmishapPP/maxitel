package ru.maxitel.lk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Михаил on 02.02.2016.
 */
public class AdapterTvGrid extends RecyclerView.Adapter<AdapterTvGrid.ViewHolder> {

    private ArrayList<String> channels;
    private ArrayList<String> urls;
    private Context context;



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv_grid, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(channels.get(position));
        holder.icon.setImageResource(getLogo(channels.get(position)));
        if(urls.size()>0) {
            final String url = urls.get(position);
            holder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setPackage("org.videolan.vlc.betav7neon");
                    i.setDataAndType(Uri.parse(url), "video/h264");
                    context.startActivity(i);

                }
            });
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }


    private int getLogo(String name) {
        switch (name){
            case "Первый канал": return R.drawable.first;
            case "Россия 1": return R.drawable.rossiya_1;
            case "Матч ТВ": return R.drawable.match_tv;
            case "ОТР": return R.drawable.otr;
            case "ТВ3": return R.drawable.tv3;
            case "ТВ3 Мск.": return R.drawable.tv3;
            case "5 канал - Петербург": return R.drawable.pyatiy_kanal;
            case "НТВ": return R.drawable.ntv;
            case "Россия Культура": return R.drawable.rossiya_k;
            case "Россия 24": return R.drawable.rossiya24;
            case "Карусель": return R.drawable.karusel;
            case "Рен-ТВ": return R.drawable.ren_tv;
            case "Спас": return R.drawable.spas;
            case "СТС": return R.drawable.sts;
            case "Домашний": return R.drawable.domashniy;
            case "Пятница": return R.drawable.pyatnica;
            case "Звезда": return R.drawable.zvezda;
            case "Мир": return R.drawable.mir;
            case "ТНТ": return R.drawable.tnt;
            case "Первый канал Мск.": return R.drawable.first;
            case "Россия 1 Мск.": return R.drawable.rossiya_1;
            case "НТВ Мск.": return R.drawable.ntv;
            case "Россия Культура Мск.": return R.drawable.rossiya_k;
            case "Рен-ТВ Мск.": return R.drawable.ren_tv;
            case "СТС Мск.": return R.drawable.sts;
            case "Пятница Мск.": return R.drawable.pyatnica;
            case "Звезда Мск.": return R.drawable.zvezda;
            case "Мир Мск.": return R.drawable.mir;
            case "ТНТ Мск.": return R.drawable.tnt;
            case "5 канал - Петербург Мск.": return R.drawable.pyatiy_kanal;
            case "SONY TV": return R.drawable.sony_tv;
            case "Загородная жизнь": return R.drawable.zagorodnaya_zizn;
            case "Мужской": return R.drawable.muzskoi;
            case "Viasat Explorer": return R.drawable.viasat_explorer;
            case "Viasat History": return R.drawable.viasat_history;
            case "Da Vinci Learning": return R.drawable.da_vinchi;
            case "РБК-ТВ": return R.drawable.rbk;
            case "Russia Today": return R.drawable.russia_today;
            case "Viasat Sport": return R.drawable.viasat_sport;
            case "Спорт Плюс": return R.drawable.sport_plus;
            case "КХЛ": return R.drawable.khl;
            case "Amazing Life": return R.drawable.amazing_life;
            case "Fashion TV Russia": return R.drawable.fashion;
            case "2x2": return R.drawable.dvaxdva;
            case "2x2 Мск.": return R.drawable.dvaxdva;
            case "Russian MusicBox": return R.drawable.russian_music_box;
            case "Шансон ТВ": return R.drawable.shansongif;
            case "ТНТ 4": return R.drawable.tnt4;
        }
        return R.drawable.no_cannel;
    }

    public AdapterTvGrid(ArrayList<String> channels, ArrayList<String> urls,Context context) {
        this.channels = channels;
        this.urls = urls;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textpart);
            icon = (ImageView) itemView.findViewById(R.id.imagepart);
        }
    }
}
