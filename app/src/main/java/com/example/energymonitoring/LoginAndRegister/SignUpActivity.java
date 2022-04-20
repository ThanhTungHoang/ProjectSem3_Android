package com.example.energymonitoring.LoginAndRegister;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.energymonitoring.MainActivity;
import com.example.energymonitoring.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://testauthen-15164-default-rtdb.firebaseio.com/");
    private EditText editEmail, editPassword, editName;
    private Button btnSignUp;
    private ProgressDialog progressDialog;
    private Toolbar toolbarBtnReturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);
        initUI();
        initListener();

    }

    private void initListener() {
        toolbarBtnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickToolbarReturn();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignUp();
            }
        });
    }

    private void onClickToolbarReturn() {
        Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SignUpActivity.this, SingInActivity.class);
        startActivity(intent);

    }

    private void onClickSignUp() {
        String strName = editName.getText().toString().trim();
        String strEmail = editEmail.getText().toString().trim();
        String strPassword = editPassword.getText().toString().trim();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        progressDialog.show();
        auth.createUserWithEmailAndPassword(strEmail, strPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        // create key readtimedatabase
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = user.getUid();
                        Log.d("uid: ", uid);
                        databaseReference.child(uid).child("Name").setValue(strName);
                        databaseReference.child(uid).child("Email").setValue(strEmail);
                        databaseReference.child(uid).child("Device").setValue("null");

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("mesager", "createUserWithEmail:success");
                            Toast.makeText(SignUpActivity.this, "createUserWithEmail:success.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("mesager", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void initUI() {
        editName = findViewById(R.id.edit_name);
        editEmail = findViewById(R.id.edit_email);
        editPassword = findViewById(R.id.edit_password);
        btnSignUp = findViewById(R.id.btn_Signup);
        toolbarBtnReturn = findViewById(R.id.toolbar_register);
        progressDialog = new ProgressDialog(this);
    }
}