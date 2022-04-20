package com.example.energymonitoring.LoginAndRegister;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.energymonitoring.MainActivity;
import com.example.energymonitoring.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SingInActivity extends AppCompatActivity {
    private TextView editEmail, editPassword;
    private Button btnSignIn, btnSignUp, btnForgotPw;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        initUI();
        initListener();
    }

    private void initListener() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingInActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignIn();
            }
        });
        btnForgotPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingInActivity.this, ForgotPassWord.class);
                startActivity(intent);
            }
        });
    }

    private void onClickSignIn() {
        String strEmail = editEmail.getText().toString().trim();
        String strPassword = editPassword.getText().toString().trim();
        auth = FirebaseAuth.getInstance();
        progressDialog.show();
        auth.signInWithEmailAndPassword(strEmail, strPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(SingInActivity.this, "signInWithEmail:success",Toast.LENGTH_SHORT).show();
                            Log.d("Messager", "signInWithEmail:success");
                            Intent intent = new Intent(SingInActivity.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Messager", "signInWithEmail:failure", task.getException());
                            Toast.makeText(SingInActivity.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void initUI() {
        btnSignUp = findViewById(R.id.button_signup);
        editEmail = findViewById(R.id.tv_sign_in_email);
        editPassword = findViewById(R.id.tv_sign_in_password);
        btnSignIn = findViewById(R.id.btn_Signin);
        progressDialog = new ProgressDialog(this);
        btnForgotPw = findViewById(R.id.button_forgot_password);

    }
}