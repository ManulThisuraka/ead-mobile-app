package com.example.ead;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ead.models.CardModel;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    private Context context;
    private List<CardModel> list;

    public HistoryAdapter(Context context, List<CardModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.reservation_history_card, parent, false);
        return new HistoryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.ViewHolder holder, int position) {
        CardModel reservationObj = list.get(position);

        holder.textDate.setText(reservationObj.getDate());
        holder.textFrom.setText(String.valueOf(reservationObj.getFrom()));
        holder.textTo.setText(String.valueOf(reservationObj.getTo()));
        holder.textSeats.setText(String.valueOf(reservationObj.getCount()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textDate, textFrom, textTo, textSeats;


        public ViewHolder(View itemView) {
            super(itemView);

            textDate = itemView.findViewById(R.id.Hdate);
            textFrom = itemView.findViewById(R.id.Hfrom);
            textTo = itemView.findViewById(R.id.Hto);
            textSeats = itemView.findViewById(R.id.Hseats);

        }
    }

}

