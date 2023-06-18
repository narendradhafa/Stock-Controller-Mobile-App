package com.example.stockcontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText etNama, etEmail, etTelepon, etPassword;
    private Button btnDaftar;
    private TextView btnMasuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNama = findViewById(R.id.et_signin_name);
        etEmail = findViewById(R.id.et_signin_email);
        etTelepon = findViewById(R.id.et_signin_notelp);
        etPassword = findViewById(R.id.et_signin_password);

        btnDaftar = findViewById(R.id.btn_signin);
        btnMasuk  = findViewById(R.id.tv_signin_login);

        btnDaftar.setOnClickListener(this);
        btnMasuk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnDaftar) {

        }

        if (v == btnMasuk) {

        }
    }
}