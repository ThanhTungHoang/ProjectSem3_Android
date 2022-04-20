package com.example.energymonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.energymonitoring.Fragment.addDeviceFragment;
import com.example.energymonitoring.Fragment.homefragment;
import com.example.energymonitoring.LoginAndRegister.SingInActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public  static long sizeDevice;
    private static final int Fragment_Home = 0;
    private static final int addDevice_Fragment = 1;
    private static final int Fragment_History = 2;
    private int mCurrentFragment = Fragment_Home;
    private ImageView imgAvatar;
    private TextView tvName, tvEmail;
    private NavigationView mnavigationView;
    private DrawerLayout mdrawerLayout;
    /////////
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private String uid;
    private  Button btnMenu;
    ///////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //// for Firebase
        databaseReference = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://testauthen-15164-default-rtdb.firebaseio.com/");
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();

        ///

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DatabaseReference a = databaseReference.child(uid).child("Email");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String count = dataSnapshot.getValue(String.class);
                Log.d("hih", count);
                getSupportActionBar().setTitle(count);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        a.addListenerForSingleValueEvent(valueEventListener);


        initUI();


        mdrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mdrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mdrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        // bat su kien trong navigation view
        mnavigationView.setNavigationItemSelectedListener(this);
        ///
        // vao mo home luon
        replaceFragment(new homefragment());
        mnavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        ///////


        showInfor();

    }


    private void showInfor() {
        // get name
        DatabaseReference a = databaseReference.child(uid).child("Name");
        ValueEventListener getName = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                Log.d("hih", name);
                getSupportActionBar().setTitle("Hi, " +name +"!");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        a.addListenerForSingleValueEvent(getName);
        // get size device
        DatabaseReference usersRef = databaseReference.child(uid).child("Device");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                //get size Device
                tvName.setVisibility(View.VISIBLE);
                tvName.setText((int) count +" Device");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        usersRef.addListenerForSingleValueEvent(valueEventListener);

        if(user == null){
            return;
        }else{
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            tvEmail.setText(email);
            Glide.with(this).load(photoUrl).error(R.drawable.ic_person).into(imgAvatar);

        }
    }

    private void initUI() {
        mnavigationView = findViewById(R.id.navigation_view);
        tvName = mnavigationView.getHeaderView(0).findViewById(R.id.tv_name);
        tvEmail = mnavigationView.getHeaderView(0).findViewById(R.id.tv_email);
        imgAvatar = mnavigationView.getHeaderView(0).findViewById(R.id.img_avatar);
        ///

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, "click navigatio", Toast.LENGTH_SHORT).show();
        TextView tvName = findViewById(R.id.tv_name);
        ////////
        DatabaseReference usersRef = databaseReference.child(uid).child("Device");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count = dataSnapshot.getChildrenCount();
                //get size Device
                tvName.setVisibility(View.VISIBLE);
                tvName.setText((int) count +" Device");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        usersRef.addListenerForSingleValueEvent(valueEventListener);
        //////

        int id = item.getItemId();
        if( id == R.id.nav_home){
            if( mCurrentFragment != Fragment_Home) {
                replaceFragment(new homefragment());
                mCurrentFragment = Fragment_Home;

            }

        }
        else if(id == R.id.nav_addDevice) {
            if( mCurrentFragment != addDevice_Fragment) {
                replaceFragment(new addDeviceFragment());
                mCurrentFragment = addDevice_Fragment;
            }

        }else if(id == R.id.nav_sign_out){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, SingInActivity.class);
            startActivity(intent);
            finish();
        }
        mdrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if( mdrawerLayout.isDrawerOpen(GravityCompat.START)){
            mdrawerLayout.isDrawerOpen(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();

    }


}