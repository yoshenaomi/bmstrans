package com.dicoding.bmstrans;

public class DataRents {
    private String id,
            nomor,
            cust_name,
            jenis_mobil,
            no_mobil,
            tgl_mulai,
            tgl_selesai,
            durasi_sewa,
            tharga_sewa;

    public DataRents() {
    }

    public DataRents(
            String id,
            String nomor,
            String cust_name,
            String jenis_mobil,
            String no_mobil,
            String tgl_mulai,
            String tgl_selesai,
            String durasi_sewa,
            String tharga_sewa){

        this.id = id;
        this.nomor = nomor;
        this.cust_name = cust_name;
        this.jenis_mobil = jenis_mobil;
        this.no_mobil = no_mobil;
        this.tgl_mulai = tgl_mulai;
        this.tgl_selesai = tgl_selesai;
        this.durasi_sewa = durasi_sewa;
        this.tharga_sewa = tharga_sewa;

    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getJenis_mobil() {

        return jenis_mobil;
    }

    public void setJenis_mobil(String jenis_mobil) {

        this.jenis_mobil = jenis_mobil;
    }

    public String getNo_mobil() {

        return no_mobil;
    }

    public void setNo_mobil(String no_mobil) {

        this.no_mobil = no_mobil;
    }

    public String getTgl_mulai() {

        return tgl_mulai;
    }

    public void setTgl_mulai(String tgl_mulai) {

        this.tgl_mulai = tgl_mulai;
    }

    public String getTgl_selesai() {

        return tgl_selesai;
    }

    public void setTgl_selesai(String tgl_selesai) {

        this.tgl_selesai = tgl_selesai;
    }

    public String getDurasi_sewa() {

        return durasi_sewa;
    }

    public void setDurasi_sewa(String durasi_sewa) {

        this.durasi_sewa = durasi_sewa;
    }

    public String getTharga_sewa() {

        return tharga_sewa;
    }

    public void setTharga_sewa(String tharga_sewa) {

        this.tharga_sewa = tharga_sewa;
    }

}
