package com.dicoding.bmstrans;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SewaAdapter extends BaseAdapter {

    Activity activity;
    List<DataRents> items;
    private LayoutInflater inflater;

    public SewaAdapter(Activity activity, List<DataRents> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(
            int position,
            View convertView,
            ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(
                            Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate
                    (R.layout.list_rents, null);

        TextView id =
                (TextView) convertView.findViewById(R.id.id);
        TextView nomor =
                (TextView) convertView.findViewById(R.id.nomor);
        TextView cust_name =
                (TextView) convertView.findViewById(R.id.cust_name);
        TextView jenis_mobil =
                (TextView) convertView.findViewById(R.id.jenis_mobil);
        TextView no_mobil =
                (TextView) convertView.findViewById(R.id.no_mobil);
        TextView tgl_mulai =
                (TextView) convertView.findViewById(R.id.tgl_mulai);
        TextView tgl_selesai =
                (TextView) convertView.findViewById(R.id.tgl_selesai);
        TextView durasi_sewa =
                (TextView) convertView.findViewById(R.id.durasi_sewa);
        TextView tharga_sewa =
                (TextView) convertView.findViewById(R.id.tharga_sewa);


        DataRents data = items.get(position);

        id.setText(data.getId());
        nomor.setText(data.getNomor());
        cust_name.setText(data.getCust_name());
        jenis_mobil.setText(data.getJenis_mobil());
        no_mobil.setText(data.getNo_mobil());
        tgl_mulai.setText(data.getTgl_mulai());
        tgl_selesai.setText(data.getTgl_selesai());
        durasi_sewa.setText(data.getDurasi_sewa());
        tharga_sewa.setText(data.getTharga_sewa());

        return convertView;
    }

}
