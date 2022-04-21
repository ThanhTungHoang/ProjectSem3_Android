package com.example.energymonitoring.Fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.energymonitoring.Adapter.RecyclerviewItemAdapter;
import com.example.energymonitoring.Model.Items;
import com.example.energymonitoring.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class addDeviceFragment extends Fragment implements View.OnClickListener {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://testauthen-15164-default-rtdb.firebaseio.com/");


    EditText InputName, InputId;
    TextView tvName;

    Button InputBtn;
    private String Inputname, Inputid;;

    private RecyclerView recyclerView;
    private RecyclerviewItemAdapter recyclerviewItemAdapter;
    private List<Items> itemsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_device_fragment, container, false);
        InputBtn = view.findViewById(R.id.input_btn);
        InputName = view.findViewById(R.id.input_name);
        InputId = view.findViewById(R.id.input_id);
        tvName = view.findViewById(R.id.tv_name);
//        InputBtn.setOnClickListener(this);
        InputBtn.setOnClickListener(this);


        return view;
    }
    @Override
    public void onClick(View v) {
        Inputname = InputName.getText().toString();
        Inputid = InputId.getText().toString();
        Log.d("name: ", Inputname);
        Log.d("id: ", Inputid);
        if(Inputname.isEmpty() || Inputid.isEmpty()){
            Toast.makeText(getContext(), "Name or id is empty", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(), "Add device Successful", Toast.LENGTH_SHORT).show();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            databaseReference.child(uid)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        sizeDevice = snapshot.getChildrenCount();
                            //get size Device
                            if(snapshot.hasChild(uid)){
                            }else {
//                    databaseReference.child(uid).child("Device")
//                            .child(Inputid).child("Name").setValue(Inputname);
                                databaseReference.child(uid).child("Device")
                                        .child(Inputid).child("Id").setValue(Inputid);
                                databaseReference.child(uid).child("Device")
                                        .child(Inputid).child("Name").setValue(Inputname);
                                databaseReference.child(uid).child("Device")
                                        .child(Inputid).child("V").setValue("0");
                                databaseReference.child(uid).child("Device")
                                        .child(Inputid).child("W").setValue("0");
                                databaseReference.child(uid).child("Device")
                                        .child(Inputid).child("A").setValue("0");
                                databaseReference.child(uid).child("Device")
                                        .child(Inputid).child("Relay").setValue("off");
                                databaseReference.child(uid).child("Device")
                                        .child(Inputid).child("kWh").setValue("0");
                                databaseReference.child(uid).child("Device")
                                        .child(Inputid).child("Vnd").setValue("0");
                                databaseReference.child(uid).child("Device")
                                        .child(Inputid).child("Energy");
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
        }

    }
}
