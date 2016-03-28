package ru.maxitel.lk;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Михаил on 06.02.2016.
 */
public class AdapterPaymentHistory extends RecyclerView.Adapter<AdapterPaymentHistory.ViewHolder> {

    private String[] payments;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_history, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.paymentTextView.setText(payments[position]);
        if (payments[position].contains("Бонус") || payments[position].contains("кция")){
            holder.imageView.setImageResource(R.drawable.gift);
        }
        else if (payments[position].contains("услуги связи")){
            holder.imageView.setImageResource(R.drawable.money);
        }
        else if (payments[position].contains("Мобильный плат")){
            holder.imageView.setImageResource(R.drawable.pyament);
        }
        else if (payments[position].contains("-")){
            holder.imageView.setImageResource(R.drawable.minus87);
        }
        else if(payments[position].contains("Кредит")){
            holder.imageView.setImageResource(R.drawable.credit);
        }
        //первая буква может быть и большой и маленькой, поэтому так
        else if(payments[position].contains("ерерасчет")){
            holder.imageView.setImageResource(R.drawable.pereraschet);
        }
        else holder.imageView.setImageResource(R.drawable.questions);

    }

    @Override
    public int getItemCount() {
        return payments.length;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView paymentTextView;
        private ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            paymentTextView = (TextView) itemView.findViewById(R.id.paymentTextView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView2);
        }
    }

    public AdapterPaymentHistory(String[] payments) {
        this.payments = payments;
    }
}
