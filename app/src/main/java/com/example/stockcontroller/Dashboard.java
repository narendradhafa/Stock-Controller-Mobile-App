package com.example.stockcontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    private Button btnAddItem;
    private TextView tvNamaGudang, tvItemCount;
    private ImageView imgProfile, imgSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnAddItem = findViewById(R.id.btn_dashboard_additem);
        imgProfile = findViewById(R.id.image_dashboard_profile);
        imgSort = findViewById(R.id.image_dashboard_sort);

        tvNamaGudang = findViewById(R.id.tv_dashboard_namagudang);
        tvItemCount = findViewById(R.id.tv_dashboard_itemcount);

        btnAddItem.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
        imgSort.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnAddItem) {

        }

        if (v == imgProfile) {
            Intent intent = new Intent(Dashboard.this, Profile.class);
            startActivity(intent);
        }

        if (v == imgSort) {

        }
    }
}