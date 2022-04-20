package com.example.energymonitoring.Adapter;



import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.energymonitoring.Model.DailyEnergy;
import com.example.energymonitoring.R;

import java.util.List;

public class RecycleViewDailyEnergyAdapter extends RecyclerView.Adapter<RecycleViewDailyEnergyAdapter.MyViewHolder> {
    private List<DailyEnergy> DailyEnergyList;



    public RecycleViewDailyEnergyAdapter(List<DailyEnergy> mDailyEnergyList){
        this.DailyEnergyList = mDailyEnergyList;
    }

    @Override
    public RecycleViewDailyEnergyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_energy_row,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecycleViewDailyEnergyAdapter.MyViewHolder holder, final int position) {
        final DailyEnergy dailyEnergy = DailyEnergyList.get(position);
        holder.Date.setText("  Date: " +String.valueOf(dailyEnergy.getDate()));
        holder.kWh.setText("  Equal to: " +String.valueOf(dailyEnergy.getKwh())+ " Kwh");
        holder.VND.setText("  Total: " +String.valueOf(dailyEnergy.getVnd())+ " VND");

    }

    @Override
    public int getItemCount() {
        return DailyEnergyList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView Date, kWh, VND;
        public Button Return;


        public MyViewHolder(View itemView) {
            super(itemView);
            Date = itemView.findViewById(R.id.setDate);
            kWh = itemView.findViewById(R.id.setkWh);
            VND = itemView.findViewById(R.id.setVND);
//            Return = itemView.findViewById(R.id.btn_return);

        }
    }
}
