package com.example.energymonitoring.Adapter;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.energymonitoring.Model.DailyEnergy;
import com.example.energymonitoring.Model.Items;
import com.example.energymonitoring.R;

import java.util.List;

public class RecyclerviewItemAdapter extends RecyclerView.Adapter<RecyclerviewItemAdapter.MyViewHolder> {

    private List<Items> itemsList;
    private List<DailyEnergy> dailyEnergyList;
    private IClickListener mIClickListener;

    public interface IClickListener {
        void onClickUpdateItems(Items items);
        void onClickDeleteItems(Items items);
        void onRelay(Items items);
        void offRelay(Items items);
        void seeMore(Items items);
    }

    public RecyclerviewItemAdapter(List<Items> mItemList, IClickListener listener){
        this.itemsList = mItemList;
        this.mIClickListener = listener;

    }

    @Override
    public RecyclerviewItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerviewItemAdapter.MyViewHolder holder, final int position) {
        final Items item = itemsList.get(position);
        holder.Name.setText(toString().valueOf(item.getName())); // name device
        holder.Id.setText("ID: " +String.valueOf(item.getId()));
        holder.V.setText("V: " +String.valueOf(item.getV()));
        holder.A.setText("A: " +String.valueOf(item.getA()));
        holder.W.setText("W: " +String.valueOf(item.getW()));

        String stateSwt = String.valueOf(item.getRelay());
        if(stateSwt.equals("on")){
            holder.btnSwitch.setChecked(true);
        }
        if(stateSwt.equals("off")){
            holder.btnSwitch.setChecked(false);
        }
        holder.tvStateRelay.setText("Relay: " +String.valueOf(item.getRelay()));
        holder.btnSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mIClickListener.onRelay(item);
                    Toast.makeText(buttonView.getContext(), "on", Toast.LENGTH_SHORT).show();
                } else {
                    mIClickListener.offRelay(item);
                    Toast.makeText(buttonView.getContext(), "off", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                popupMenu.inflate(R.menu.popup_button_more);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.btn_edit_in_popup:
//                                Toast.makeText(view.getContext(), "click edit", Toast.LENGTH_SHORT).show();
                                mIClickListener.onClickUpdateItems(item);
                                return true;
                            case R.id.btn_delete_in_popup:
                                Toast.makeText(view.getContext(), "click delete", Toast.LENGTH_SHORT).show();
                                mIClickListener.onClickDeleteItems(item);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });
        holder.SeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickListener.seeMore(item);
            }
        });
    }



    @Override
    public int getItemCount() {
        return itemsList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView Name,Id, V, A, W;
        private LinearLayout itemLayout;
        private ImageButton imageButton;
        public Switch btnSwitch;
        public TextView tvStateRelay;
        public Button OnRelay, OffRelay, SeeMore;

        public MyViewHolder(View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.tvName);
            Id = itemView.findViewById(R.id.tvId);
            V = itemView.findViewById(R.id.tvV);
            A = itemView.findViewById(R.id.tvA);
            W = itemView.findViewById(R.id.tvW);
            tvStateRelay = itemView.findViewById(R.id.tv_get_state_relay);

            SeeMore = itemView.findViewById(R.id.btn_see_more);
            btnSwitch = itemView.findViewById(R.id.btnswt);
//            Boolean switchState = btnSwitch.isChecked();
//            btnSwitch.setChecked(true);
//            itemLayout =  itemView.findViewById(R.id.itemLayout);

            imageButton = itemView.findViewById(R.id.btn_more);
        }
    }
}
///"Theme.EnergyMonitoring"
