package com.example.ead;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ead.constants.CommonMethods;
import com.example.ead.models.CardModel;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReservationsAdapter extends RecyclerView.Adapter<ReservationsAdapter.ViewHolder> {

    private Context context;
    private List<CardModel> list;

    public ReservationsAdapter(Context context, List<CardModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.existing_reservation_card, parent, false);
        return new ViewHolder(v);
    }


//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        CardModel reservationObj = list.get(position);
//
//        holder.textDate.setText(reservationObj.getDate());
//        holder.textFrom.setText(String.valueOf(reservationObj.getFrom()));
//        holder.textTo.setText(String.valueOf(reservationObj.getTo()));
//        holder.textSeats.setText(String.valueOf(reservationObj.getCount()));
//
//        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext(),UpdateReservation.class);
//                intent.putExtra("ReservationId",reservationObj.getId());
//                intent.putExtra("ReservationTSId",reservationObj.getTrainScheduleid());
//                intent.putExtra("ReservationNIC",reservationObj.getNic());
//                intent.putExtra("ReservationCreatedAt",reservationObj.getCreatedAt());
//                intent.putExtra("ReservationUpdatedAt",reservationObj.getUpdatedAt());
//                intent.putExtra("ReservationDate",reservationObj.getDate());
//                intent.putExtra("ReservationCount",reservationObj.getCount());
//                intent.putExtra("status",reservationObj.getStatus());
//                intent.putExtra("ReservationFrom",reservationObj.getFrom());
//                intent.putExtra("ReservationTo",reservationObj.getTo());
//                intent.putExtra("ReservationTime",reservationObj.getTime());
//                v.getContext().startActivity(intent);
//            }
//        });
//
//    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardModel reservationObj = list.get(position);

        holder.textDate.setText(reservationObj.getDate());
        holder.textFrom.setText(String.valueOf(reservationObj.getFrom()));
        holder.textTo.setText(String.valueOf(reservationObj.getTo()));
        holder.textSeats.setText(String.valueOf(reservationObj.getCount()));

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Parse the reservation date from the string
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                Date reservationDate = null;
                try {
                    reservationDate = dateFormat.parse(reservationObj.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                    return; // Handle the parsing error
                }

                // Get today's date
                Date today = new Date();

                // Calculate the difference in days
                long differenceInDays = (reservationDate.getTime() - today.getTime()) / (1000 * 60 * 60 * 24);

                Intent intent = new Intent(v.getContext(), UpdateReservation.class);

                // Check if the difference is less than 5 days
                if (differenceInDays < 5) {
                    intent.putExtra("Alert", true);
                }
                    // Continue with the edit action
                    intent.putExtra("ReservationId",reservationObj.getId());
                    intent.putExtra("ReservationDate",reservationObj.getDate());
                    intent.putExtra("ReservationCount",reservationObj.getCount());
                    intent.putExtra("ReservationFrom",reservationObj.getFrom());
                    intent.putExtra("ReservationTo",reservationObj.getTo());
                    intent.putExtra("ReservationTime",reservationObj.getTime());
                    v.getContext().startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textDate, textFrom, textTo, textSeats;
        Button btnEdit;

        public ViewHolder(View itemView) {
            super(itemView);

            textDate = itemView.findViewById(R.id.Tdate);
            textFrom = itemView.findViewById(R.id.Tfrom);
            textTo = itemView.findViewById(R.id.Tto);
            textSeats = itemView.findViewById(R.id.Tseats);

            btnEdit = (Button) itemView.findViewById(R.id.BTNedit);
        }
    }

}
