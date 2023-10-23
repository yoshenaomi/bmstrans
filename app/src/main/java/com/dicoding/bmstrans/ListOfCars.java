package com.dicoding.bmstrans;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.widget.ImageView;

public class ListOfCars extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener {

    private DrawerLayout drawerLayout;
    private ImageView ivMenu;
    private RecyclerView rvMenu;

    public static final String URLSELECT = "http://192.168.1.12/bmstrans/select.php";
    public static final String URLDELETE = "http://192.168.1.12/bmstrans/delete.php";
    public static final String URLINSERT = "http://192.168.1.12/bmstrans/insert.php";

    ListView list;
    SwipeRefreshLayout swipe;
    List<DataCars> itemList = new ArrayList<DataCars>();
    MobilAdapter adapter;
    LayoutInflater inflater;
    EditText tid,
            tjenis_mobil,
            tjenis_bensin,
            tharga_sewa;
    String vid,
            vjenis_mobil,
            vjenis_bensin,
            vharga_sewa;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_cars);

        drawerLayout = findViewById(R.id.drawer_layout);
        ivMenu = findViewById(R.id.ivMenu);
        rvMenu = findViewById(R.id.rvMenu);

        rvMenu.setLayoutManager(new LinearLayoutManager(
                this));

        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        list = (ListView) findViewById(R.id.list);

        adapter = new MobilAdapter(ListOfCars.this, itemList);
        list.setAdapter(adapter);

        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           itemList.clear();
                           adapter.notifyDataSetChanged();
                           callVolley();
                       }
                   }
        );
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //statement jika fab diklik
                dialogForm("","","","","Tambah");
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String idx = itemList.get(position).getId();
                final CharSequence[] pilihanAksi = {"Hapus"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(ListOfCars.this);
                dialog.setItems(pilihanAksi, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                //jika dipilih hapus
                                hapusData(idx);
                                break;
                        }

                    }
                }).show();
                return false;
            }
        });


    }

    public void dialogForm(
            String id,
            String jenis_mobil,
            String jenis_bensin,
            String harga_sewa,
            String button){

        AlertDialog.Builder dialogForm = new AlertDialog.Builder(ListOfCars.this);
        inflater = getLayoutInflater();

        View viewDialog = inflater.inflate(R.layout.form_cars, null);
        dialogForm.setView(viewDialog);
        dialogForm.setCancelable(true);
        dialogForm.setTitle("Add List of Cars");
//
//        tid = (EditText) viewDialog.findViewById(R.id.inId);
        tjenis_mobil = (EditText) viewDialog.findViewById(R.id.inJenisMobil);
        tjenis_bensin = (EditText) viewDialog.findViewById(R.id.inJenisBensin);
        tharga_sewa = (EditText) viewDialog.findViewById(R.id.inHargaSewa);

        if (id.isEmpty()){
//            tid.setText(null);
            tjenis_mobil.setText(null);
            tjenis_mobil.setText(null);
            tharga_sewa.setText(null);
        }else{
//            tid.setText(id);
            tjenis_mobil.setText(jenis_mobil);
            tjenis_bensin.setText(jenis_bensin);
            tharga_sewa.setText(harga_sewa);
        }

        dialogForm.setPositiveButton(button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                vid = tid.getText().toString();
                vjenis_mobil = tjenis_mobil.getText().toString();
                vjenis_bensin = tjenis_bensin.getText().toString();
                vharga_sewa = tharga_sewa.getText().toString();

                simpan();
                dialog.dismiss();
            }
        });

        dialogForm.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                tid.setText(null);
                tjenis_mobil.setText(null);
                tjenis_bensin.setText(null);
                tharga_sewa.setText(null);
            }
        });
        dialogForm.show();

    }
    public void simpan() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLINSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean success = jObj.getBoolean("success");
                            String message = jObj.getString("message");

                            if (success) {
                                // Data berhasil ditambahkan, perbarui tampilan
                                callVolley(); // Memanggil metode untuk memperbarui daftar data
                                Toast.makeText(
                                        ListOfCars.this,
                                        message,
                                        Toast.LENGTH_LONG).show();
                            } else {
                                // Gagal menambahkan data
                                Toast.makeText(
                                        ListOfCars.this,
                                        "Gagal: " + message,
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(
                                    ListOfCars.this,
                                    "Gagal: Terjadi kesalahan pada respons server",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(
                        ListOfCars.this,
                        "Gagal koneksi ke server, cek setingan koneksi Anda",
                        Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Membuat parameter yang akan dikirim ke server
                Map<String, String> params = new HashMap<String, String>();
                // Tidak perlu mengirim ID ke server
                // params.put("id", vid);
                params.put("jenis_mobil", vjenis_mobil);
                params.put("jenis_bensin", vjenis_bensin);
                params.put("harga_sewa", vharga_sewa);
                return params;
            }
        };

        // Mengirim permintaan ke server
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }


    public void hapusData(String id){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLDELETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(
                                ListOfCars.this,
                                response,
                                Toast.LENGTH_LONG).show();
                        callVolley();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(
                        ListOfCars.this,
                        "Gagal Koneksi Ke server, Cek setingan koneksi anda",
                        Toast.LENGTH_LONG).show();
            }
        })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();


                params.put("id", id);

                return params;
            }

        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
    @Override
    public void onRefresh() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        callVolley();

    }
    private void callVolley() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        // membuat request JSON
        JsonArrayRequest jArr = new JsonArrayRequest(URLSELECT, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataCars item = new DataCars();

                        item.setId(obj.getString("id"));
                        item.setJenis_mobil(obj.getString("jenis_mobil"));
                        item.setJenis_bensin(obj.getString("jenis_bensin"));
                        item.setHarga_sewa(obj.getString("harga_sewa"));

                        // menambah item ke array
                        itemList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // notifikasi adanya perubahan data pada adapter
                adapter.notifyDataSetChanged();

                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        });

        // menambah request ke request queue
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(jArr);

    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }
}