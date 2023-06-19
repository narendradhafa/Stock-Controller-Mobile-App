package com.example.stockcontroller;

public class ModelBarang {

    private String namaBarang;
    private String jumlahBarang;
    private String satuanBarang;
    private String hargaBarang;

    public ModelBarang(String namaBarang, String jumlahBarang, String satuanBarang, String hargaBarang) {
        this.namaBarang = namaBarang;
        this.jumlahBarang = jumlahBarang;
        this.satuanBarang = satuanBarang;
        this.hargaBarang = hargaBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(String jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public String getSatuanBarang() {
        return satuanBarang;
    }

    public void setSatuanBarang(String satuanBarang) {
        this.satuanBarang = satuanBarang;
    }

    public String getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(String hargaBarang) {
        this.hargaBarang = hargaBarang;
    }
}
