package ru.maxitel.lk;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class AdapterTariffGrid extends RecyclerView.Adapter<AdapterTariffGrid.ViewHolder> {

    private ArrayList<Tariff> tariffs;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_tariff, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tariffPrise.setText(String.valueOf(tariffs.get(position).getPrise())+"руб.");
        holder.tariffSpeed.setText(tariffs.get(position).toString());
        holder.addTariffTextView.setText(String.valueOf(tariffs.get(position).getCostOfSwitching())+"руб.");
        holder.tariffName.setText(tariffs.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return tariffs.size();
    }

    public AdapterTariffGrid(ArrayList<Tariff> tariffs) {
        this.tariffs = tariffs;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tariffPrise;
        private TextView tariffSpeed;
        private TextView addTariffTextView;
        private TextView tariffName;

        public ViewHolder(View itemView) {
            super(itemView);
            tariffPrise = (TextView) itemView.findViewById(R.id.tariff_price);
            tariffSpeed = (TextView) itemView.findViewById(R.id.speedTextView);
            addTariffTextView = (TextView) itemView.findViewById(R.id.addTariffTextView);
            tariffName = (TextView) itemView.findViewById(R.id.tariffNameTextView);
        }
    }
}
