package com.example.stockcontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Explore extends AppCompatActivity implements View.OnClickListener {

    private Button btnDaftar, btnMasuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_StockController);
        setContentView(R.layout.activity_explore);

        btnDaftar = findViewById(R.id.btn_explore_signin);
        btnMasuk  = findViewById(R.id.btn_explore_login);

        btnDaftar.setOnClickListener(this);
        btnMasuk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnDaftar) {
            Intent intent = new Intent(Explore.this, Register.class);
            startActivity(intent);
        }

        if (v == btnMasuk) {
            Intent intent = new Intent(Explore.this, Login.class);
            startActivity(intent);
        }
    }
}