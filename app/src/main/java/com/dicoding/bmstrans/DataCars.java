package com.dicoding.bmstrans;

public class DataCars {
    private String id,
            jenis_mobil,
            jenis_bensin,
            harga_sewa;

    public DataCars() {
    }

    public DataCars(
            String id,
            String jenis_mobil,
            String jenis_bensin,
            String harga_sewa){

        this.id = id;
        this.jenis_mobil = jenis_mobil;
        this.jenis_bensin = jenis_bensin;
        this.harga_sewa = harga_sewa;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJenis_mobil() {
        return jenis_mobil;
    }

    public void setJenis_mobil(String jenis_mobil) {
        this.jenis_mobil = jenis_mobil;
    }

    public String getJenis_bensin() {
        return jenis_bensin;
    }

    public void setJenis_bensin(String jenis_bensin) {
        this.jenis_bensin = jenis_bensin;
    }

    public String getHarga_sewa() {
        return harga_sewa;
    }

    public void setHarga_sewa(String harga_sewa) {
        this.harga_sewa = harga_sewa;
    }
}
