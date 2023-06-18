package com.example.stockcontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EditItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_StockController);
        setContentView(R.layout.activity_edit_item);
    }
}