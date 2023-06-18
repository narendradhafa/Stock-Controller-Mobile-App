package com.example.stockcontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText etEmail, etPassword;
    private Button btnMasuk;
    private TextView btnDaftar, btnForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_StockController);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_login_email);
        etPassword = findViewById(R.id.et_login_password);

        btnMasuk  = findViewById(R.id.btn_login);
        btnDaftar = findViewById(R.id.tv_login_signin);
        btnForgotPassword = findViewById(R.id.tv_login_forgot_password);

        btnMasuk.setOnClickListener(this);
        btnDaftar.setOnClickListener(this);
        btnForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnMasuk) {

        }

        if (v == btnDaftar) {

        }

        if (v == btnForgotPassword) {

        }
    }
}