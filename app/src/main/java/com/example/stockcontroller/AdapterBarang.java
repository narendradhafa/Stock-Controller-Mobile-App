package com.example.stockcontroller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterBarang extends RecyclerView.Adapter<AdapterBarang.MyViewHolder> implements View.OnClickListener {

    Context context;
    ArrayList<ModelBarang> list;

    public AdapterBarang(Context context, ArrayList<ModelBarang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.barangentry,parent, false);
        v.setOnClickListener(this);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ModelBarang modelBarang = list.get(position);
        holder.namaBarang.setText(modelBarang.getNama_barang());
        holder.jumlahBarang.setText(modelBarang.getJum_barang());
        holder.satuanBarang.setText(modelBarang.getSatuan_barang());
        holder.hargaBarang.setText(modelBarang.getHarga_barang());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context,ItemDetail.class);
        context.startActivity(intent);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView namaBarang, jumlahBarang, satuanBarang, hargaBarang;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            namaBarang = itemView.findViewById(R.id.txt_nama_barang);
            jumlahBarang = itemView.findViewById(R.id.txt_jumlah);
            satuanBarang = itemView.findViewById(R.id.txt_satuan);
            hargaBarang = itemView.findViewById(R.id.txt_harga);

        }
    }
}
