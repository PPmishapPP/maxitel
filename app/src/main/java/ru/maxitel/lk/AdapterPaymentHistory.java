package ru.maxitel.lk;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


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
            holder.paymentTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.gift,0,0,0);
        }
        else if (payments[position].contains("услуги связи")){
            holder.paymentTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.money,0,0,0);
        }
        else if (payments[position].contains("Мобильный плат")){
            holder.paymentTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pyament,0,0,0);
        }
        else if (payments[position].contains("-")){
            holder.paymentTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.minus87,0,0,0);
        }
        else if(payments[position].contains("Кредит")){
            holder.paymentTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.credit,0,0,0);
        }
        //первая буква может быть и большой и маленькой, поэтому так
        else if(payments[position].contains("ерерасчет")){
            holder.paymentTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.pereraschet,0,0,0);
        }

    }

    @Override
    public int getItemCount() {
        return payments.length;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView paymentTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            paymentTextView = (TextView) itemView.findViewById(R.id.paymentTextView);
        }
    }

    public AdapterPaymentHistory(String[] payments) {
        this.payments = payments;
    }
}
