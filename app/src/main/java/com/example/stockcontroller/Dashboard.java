package com.example.stockcontroller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton btnAddItem;
    private TextView tvNamaGudang, tvItemCount;
    private ImageView imgProfile, imgSort;

    private RecyclerView recyclerView;
    private ArrayList<ModelBarang> list;
    private DatabaseReference dbref;
    private AdapterBarang adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_StockController);
        setContentView(R.layout.activity_dashboard);

        btnAddItem = findViewById(R.id.btn_dashboard_additem);
        imgProfile = findViewById(R.id.image_dashboard_profile);
        imgSort = findViewById(R.id.image_dashboard_sort);

        tvNamaGudang = findViewById(R.id.tv_dashboard_namagudang);
        tvItemCount = findViewById(R.id.tv_dashboard_itemcount);

        btnAddItem.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
        imgSort.setOnClickListener(this);

        recyclerView = findViewById(R.id.rv_dashboard_barang);
        dbref = FirebaseDatabase.getInstance().getReference().child("DataBarang");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterBarang(this,list);
        recyclerView.setAdapter(adapter);

        dbref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    ModelBarang modelBarang = dataSnapshot.getValue(ModelBarang.class);
                    list.add(modelBarang);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == btnAddItem) {
            // pindah ke activity add item
            Intent intent = new Intent(Dashboard.this, AddItem.class);
            startActivity(intent);
        }

        if (v == imgProfile) {
            // pindah ke activity profile
            Intent intent = new Intent(Dashboard.this, Profile.class);
            startActivity(intent);
        }

        if (v == imgSort) {

        }
    }
}