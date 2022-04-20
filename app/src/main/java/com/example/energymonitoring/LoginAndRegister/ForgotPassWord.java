package com.example.energymonitoring.LoginAndRegister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.energymonitoring.R;


public class ForgotPassWord extends AppCompatActivity {
    private Toolbar toolbarBtnReturnForgotPw;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_forgot_password);
        toolbarBtnReturnForgotPw = findViewById(R.id.toolbar_forgot_pw);
        toolbarBtnReturnForgotPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ForgotPassWord.this, "click", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ForgotPassWord.this, SingInActivity.class);
                startActivity(intent);
            }
        });

    }

}
