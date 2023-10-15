package com.example.ead;

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
import com.example.ead.models.Schedule;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private Context context;
    private List<Schedule> list;

    public ScheduleAdapter(Context context, List<Schedule> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.select_schedule_card, parent, false);
        return new ScheduleAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ScheduleAdapter.ViewHolder holder, int position) {
        Schedule scheduleObj = list.get(position);

        holder.textDate.setText(scheduleObj.getScheduleDate());
        holder.textFrom.setText(String.valueOf(scheduleObj.getDeparture()));
        holder.textTo.setText(String.valueOf(scheduleObj.getDestination()));
        holder.textTime.setText(String.valueOf(scheduleObj.getStartTime()));
        holder.textSeats.setText(String.valueOf(scheduleObj.getAvailableSeatCount()));

        holder.btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),CreateReservation.class);
                intent.putExtra("Date",scheduleObj.getScheduleDate());
                intent.putExtra("From",scheduleObj.getDeparture());
                intent.putExtra("To",scheduleObj.getDestination());
                intent.putExtra("Time",scheduleObj.getStartTime());
                intent.putExtra("Seats",scheduleObj.getAvailableSeatCount());
                intent.putExtra("Sid",scheduleObj.get_id());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textDate, textFrom, textTo, textSeats, textTime;
        Button btnReserve;

        public ViewHolder(View itemView) {
            super(itemView);

            textDate = itemView.findViewById(R.id.Adate);
            textFrom = itemView.findViewById(R.id.Afrom);
            textTo = itemView.findViewById(R.id.Ato);
            textTime = itemView.findViewById(R.id.Atime);
            textSeats = itemView.findViewById(R.id.Aseats);

            btnReserve = (Button) itemView.findViewById(R.id.BTNReserve);
        }
    }

}
