package com.example.energymonitoring.Fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.energymonitoring.Adapter.RecycleViewDailyEnergyAdapter;
import com.example.energymonitoring.Adapter.RecyclerviewItemAdapter;
import com.example.energymonitoring.Model.DailyEnergy;
import com.example.energymonitoring.Model.Items;
import com.example.energymonitoring.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class homefragment extends Fragment {
    private RecyclerView recyclerView, dailyEnergyrecyclerView;
    private RecyclerviewItemAdapter mrecyclerviewItemAdapter;
    private RecycleViewDailyEnergyAdapter mrecycleViewDailyEnergyAdapter;
    private List<Items> itemsList, testList;
    private List<DailyEnergy>  dailyEnergyList;
    private TextView updateName;
    private Button Update, Cancel, btnDelete;
    private Button Test;
    private View.OnClickListener mDisconnectListener;
    private FirebaseUser user;
    private String uid;
    private LinearLayoutManager linearLayoutManager, alinearLayoutManager;
    public String a;
    DatabaseReference databaseReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.homefragment, container, false);
        //// for DataBase ////
        databaseReference = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://testauthen-15164-default-rtdb.firebaseio.com/");
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        /////////////////////
        itemsList = new ArrayList<>();
        dailyEnergyList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recycleView);
//        energyrecyclerView = view.findViewById(R.id.recycleView1);
//        arecyclerView = view.findViewById(R.id.recycleView1);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        mrecyclerviewItemAdapter = new RecyclerviewItemAdapter(itemsList, new RecyclerviewItemAdapter.IClickListener() {
            @Override
            public void onClickUpdateItems(Items items) {
                Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_dialog_update);
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setCancelable(true);
                Update = dialog.findViewById(R.id.btn_update_dialog_update);
                updateName = dialog.findViewById(R.id.tv_update_dialog_update);
//                updateName.setText(items.getName());

                dialog.show();
                Update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "click ok UPDATE", Toast.LENGTH_SHORT).show();
                        String newName = updateName.getText().toString().trim();
                        items.setName(newName);
//                        databaseReference.child(String.valueOf(items.getName())).updateChildren(items.toMap(),
                        databaseReference.child(uid).child("Device").child(items.getId()).child("Name").
                                setValue(String.valueOf(items.getName()));

//                        databaseReference.child(uid).child("Device").child(items.getId()).removeValue();
                    }
                });

            }

            @Override
            public void onClickDeleteItems(Items items) {
//                databaseReference.child(uid).child("Device").child(items.getId()).removeValue();
//
                //android:app
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Warning!");
                alertDialog.setMessage("Are you sure?");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseReference.child(uid).child("Device").child(items.getId()).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(getActivity(), "Delete success", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("Cancel", null);
                alertDialog.show();

            }

            @Override
            public void onRelay(Items items) {
                Toast.makeText(getActivity(), "Relay On", Toast.LENGTH_SHORT).show();
                databaseReference.child(uid).child("Device")
                        .child(items.getId()).child("Relay").setValue("on");

            }

            @Override
            public void offRelay(Items items) {
                Toast.makeText(getActivity(), "Relay Off", Toast.LENGTH_SHORT).show();
                databaseReference.child(uid).child("Device")
                        .child(items.getId()).child("Relay").setValue("off");

            }

            @Override
            public void seeMore(Items items) {
//                DailyEnergy dailyEnergy = new DailyEnergy("14042001", "1", "2");
//                dailyEnergyList.add(dailyEnergy);
                Toast.makeText(getActivity(), "Click seeMore", Toast.LENGTH_SHORT).show();
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_dialog_view_history_energy);
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setCancelable(true);


                dailyEnergyrecyclerView = dialog.findViewById(R.id.recycleView1);
                alinearLayoutManager = new LinearLayoutManager(getContext());
                dailyEnergyrecyclerView.setLayoutManager(alinearLayoutManager);
                dailyEnergyrecyclerView.setItemAnimator(new DefaultItemAnimator());
                mrecycleViewDailyEnergyAdapter = new RecycleViewDailyEnergyAdapter(dailyEnergyList);
                dailyEnergyrecyclerView.setAdapter(mrecycleViewDailyEnergyAdapter);
                DatabaseReference myReference = FirebaseDatabase.getInstance().getReference()
                        .child(uid).child("Device").child(items.getId()).child("Energy");
                ///X4YLHdqoGtM7yMtLSQpqPkf9lxx1/Device/1/Enegy/14-04-2022
                Toast.makeText(getActivity(), "id connect" + items.getId(), Toast.LENGTH_SHORT).show();
                dailyEnergyList.clear();
                myReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Log.d("onChildAdded", String.valueOf(snapshot));
                        DailyEnergy dailyEnergy = snapshot.getValue(DailyEnergy.class);
                        dailyEnergyList.add(dailyEnergy);
                        Toast.makeText(getActivity(), "onChildAdded", Toast.LENGTH_SHORT).show();
                        mrecycleViewDailyEnergyAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        DailyEnergy dailyEnergy = snapshot.getValue(DailyEnergy.class);
                        if(dailyEnergy == null || dailyEnergyList == null || dailyEnergyList.isEmpty() ){
                            return;
                        }
                        for(int i = 0; i < dailyEnergyList.size(); i++){
                            if(dailyEnergy.getDate() == dailyEnergyList.get(i).getDate()){
                                dailyEnergyList.set(i, dailyEnergy);
                            }
                        }
                        mrecycleViewDailyEnergyAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        DailyEnergy dailyEnergy = snapshot.getValue(DailyEnergy.class);
                        if(dailyEnergy == null || itemsList == null || itemsList.isEmpty() ){
                            return;
                        }
                        for(int i = 0; i < dailyEnergyList.size(); i++){
                            if(dailyEnergy.getDate() == dailyEnergyList.get(i).getDate()){
                                dailyEnergyList.remove(dailyEnergyList.get(i));
                                mrecycleViewDailyEnergyAdapter.notifyDataSetChanged();
                                break;
                            }
                        }
                        mrecycleViewDailyEnergyAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                dialog.show();
            }


        });

        //////
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mrecyclerviewItemAdapter);
        ///////////////////// create child device by uid
        DatabaseReference myReference = FirebaseDatabase.getInstance().getReference()
                .child(uid).child("Device");
        myReference.addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Items items = snapshot.getValue(Items.class);
                if(items != null){
                    Log.d("onChildAdded", String.valueOf(snapshot));
                    itemsList.add(items);
                    mrecyclerviewItemAdapter.notifyDataSetChanged();
                }
            }


            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Items items = snapshot.getValue(Items.class);
                a = items.getRelay();

                if(items == null || itemsList == null || itemsList.isEmpty() ){
                    return;
                }
                for(int i = 0; i < itemsList.size(); i++){
                    if(items.getId() == itemsList.get(i).getId()){
                        itemsList.set(i, items);
                    }
                }
                mrecyclerviewItemAdapter.notifyDataSetChanged();
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Items items = snapshot.getValue(Items.class);
                if(items == null || itemsList == null || itemsList.isEmpty() ){
                    return;
                }
                for(int i = 0; i < itemsList.size(); i++){
                    if(items.getId() == itemsList.get(i).getId()){
                        itemsList.remove(itemsList.get(i));
                        mrecyclerviewItemAdapter.notifyDataSetChanged();
                        break;
                    }
                }
                mrecyclerviewItemAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;



    }




}
