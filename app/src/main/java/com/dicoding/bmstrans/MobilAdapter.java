package com.dicoding.bmstrans;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MobilAdapter extends BaseAdapter {

    Activity activity;
    List<DataCars> items;
    private LayoutInflater inflater;

    public MobilAdapter(Activity activity, List<DataCars> items) {
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
                    (R.layout.list_cars, null);

        TextView id =
                (TextView) convertView.findViewById(R.id.id);
        TextView jenis_mobil =
                (TextView) convertView.findViewById(R.id.jenis_mobil);
        TextView jenis_bensin =
                (TextView) convertView.findViewById(R.id.jenis_bensin);
        TextView harga_sewa =
                (TextView) convertView.findViewById(R.id.harga_sewa);

        DataCars data = items.get(position);

        id.setText(data.getId());
        jenis_mobil.setText(data.getJenis_mobil());
        jenis_bensin.setText(data.getJenis_bensin());
        harga_sewa.setText(data.getHarga_sewa());

        return convertView;
    }

}
