package com.example.stockcontroller;

public class ModelBarang {

    private String nama_barang;
    private String jum_barang;
    private String satuan_barang;
    private String harga_barang;



    public ModelBarang() {
        this.nama_barang = nama_barang;
        this.jum_barang = jum_barang;
        this.satuan_barang = satuan_barang;
        this.harga_barang = harga_barang;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getJum_barang() {
        return jum_barang;
    }

    public void setJum_barang(String jum_barang) {
        this.jum_barang = jum_barang;
    }

    public String getSatuan_barang() {
        return satuan_barang;
    }

    public void setSatuan_barang(String satuan_barang) {
        this.satuan_barang = satuan_barang;
    }

    public String getHarga_barang() {
        return harga_barang;
    }

    public void setHarga_barang(String harga_barang) {
        this.harga_barang = harga_barang;
    }
}
