package com.dicoding.bmstrans;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
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

public class RentOfCars
        extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener {

    private DrawerLayout drawerLayout;
    private ImageView ivMenu;
    private RecyclerView rvMenu;

    public static final String URLSELECT = "http://192.168.1.12/bmstrans/selectrent.php";
    public static final String URLDELETE = "http://192.168.1.12/bmstrans/deleterent.php";
    public static final String URLINSERT = "http://192.168.1.12/bmstrans/insertrent.php";

    ListView list;
    SwipeRefreshLayout swipe;
    List<DataRents> itemList = new ArrayList<DataRents>();
    SewaAdapter adapter;
    LayoutInflater inflater;

    EditText tid,
            tnomor,
            tcust_name,
            tno_mobil,
            tjenis_mobil,
            ttgl_mulai,
            ttgl_selesai,
            tdurasi_sewa,
            ttharga_sewa;

    String vid,
            vnomor,
            vcust_name,
            vno_mobil,
            vjenis_mobil,
            vtgl_mulai,
            vtgl_selesai,
            vdurasi_sewa,
            vtharga_sewa;

    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_of_cars);

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

        adapter = new SewaAdapter(RentOfCars.this, itemList);
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
                dialogForm("",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "Tambah");
            }
        });

        list.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(
                    AdapterView<?> parent,
                    View view,
                    int position,
                    long id) {

                final String idx = itemList.get(position).getId();
                final CharSequence[] pilihanAksi = {"Hapus"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(
                        RentOfCars.this);
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

    @SuppressLint("MissingInflatedId")
    public void dialogForm(
            String id,
            String nomor,
            String cust_name,
            String jenis_mobil,
            String no_mobil,
            String tgl_mulai,
            String tgl_selesai,
            String durasi_sewa,
            String tharga_sewa,
            String button) {

        AlertDialog.Builder dialogForm = new AlertDialog.Builder(
                RentOfCars.this);
        inflater = getLayoutInflater();

        View viewDialog = inflater.inflate(
                R.layout.form_rents, null);
        dialogForm.setView(viewDialog);
        dialogForm.setCancelable(true);
        dialogForm.setTitle("Add List of Rents");

        tnomor = (EditText) viewDialog.findViewById(R.id.etNumberDataCar);
        tjenis_mobil = (EditText) viewDialog.findViewById(R.id.etNameCar);
        tno_mobil = (EditText) viewDialog.findViewById(R.id.etCarLicenseNumber);
        tcust_name = (EditText) viewDialog.findViewById(R.id.etCustomerName);
        ttgl_mulai = (EditText) viewDialog.findViewById(R.id.etRentStartDate);
        ttgl_selesai = (EditText) viewDialog.findViewById(R.id.etRentEndDate);
        tdurasi_sewa = (EditText) viewDialog.findViewById(R.id.etRentDuration);
        ttharga_sewa = (EditText) viewDialog.findViewById(R.id.etRentFee);

        if (id.isEmpty()) {
            tnomor.setText(null);
            tjenis_mobil.setText(null);
            tcust_name.setText(null);
            tno_mobil.setText(null);
            ttgl_mulai.setText(null);
            ttgl_selesai.setText(null);
            tdurasi_sewa.setText(null);
            ttharga_sewa.setText(null);
        } else {
            tjenis_mobil.setText(jenis_mobil);
            tnomor.setText(nomor);
            tcust_name.setText(cust_name);
            tno_mobil.setText(no_mobil);
            ttgl_mulai.setText(tgl_mulai);
            ttgl_selesai.setText(tgl_selesai);
            tdurasi_sewa.setText(durasi_sewa);
            ttharga_sewa.setText(tharga_sewa);
        }

        dialogForm.setPositiveButton(button,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(
                            DialogInterface dialog,
                            int which) {
                        vjenis_mobil = tjenis_mobil.getText().toString();
                        vnomor = tnomor.getText().toString();
                        vcust_name = tcust_name.getText().toString();

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
                tnomor.setText(null);
                tcust_name.setText(null);
                tno_mobil.setText(null);
                ttgl_mulai.setText(null);
                ttgl_selesai.setText(null);
                tdurasi_sewa.setText(null);
                ttharga_sewa.setText(null);
            }
        });
        dialogForm.show();
    }

    public void simpan() {
        // Mendapatkan nilai dari EditText dan menyimpannya dalam variabel
        vjenis_mobil = tjenis_mobil.getText().toString();
        vnomor = tnomor.getText().toString();
        vcust_name = tcust_name.getText().toString();
        vno_mobil = tno_mobil.getText().toString();
        vtgl_mulai = ttgl_mulai.getText().toString();
        vtgl_selesai = ttgl_selesai.getText().toString();
        vdurasi_sewa = tdurasi_sewa.getText().toString();
        vtharga_sewa = ttharga_sewa.getText().toString();

        if (vno_mobil != null && !vno_mobil.isEmpty() &&
                vtgl_mulai != null && !vtgl_mulai.isEmpty() &&
                vtgl_selesai != null && !vtgl_selesai.isEmpty() &&
                vdurasi_sewa != null && !vdurasi_sewa.isEmpty() &&
                vtharga_sewa != null && !vtharga_sewa.isEmpty()) {
            // Semua variabel memiliki nilai yang valid
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
                                            RentOfCars.this,
                                            message,
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    // Gagal menambahkan data
                                    Toast.makeText(
                                            RentOfCars.this,
                                            "Gagal: " + message,
                                            Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(
                                        RentOfCars.this,
                                        "Gagal: Terjadi kesalahan pada respons server",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(
                            RentOfCars.this,
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
                    params.put("nomor", vnomor);
                    params.put("cust_name", vcust_name);
                    params.put("no_mobil", vno_mobil);
                    params.put("tgl_mulai", vtgl_mulai);
                    params.put("tgl_selesai", vtgl_selesai);
                    params.put("jenis_mobil", vjenis_mobil);
                    params.put("durasi_sewa", vdurasi_sewa);
                    params.put("tharga_sewa", vtharga_sewa);
                    return params;
                }
            };

            // Mengirim permintaan ke server
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(stringRequest);
        } else {
            // Tampilkan pesan kesalahan karena ada nilai yang kosong atau null
            Toast.makeText(
                    RentOfCars.this,
                    "Semua kolom harus diisi",
                    Toast.LENGTH_LONG).show();
        }
    }

//
//    public void simpan() {
//        StringRequest stringRequest = new StringRequest(
//                Request.Method.POST, URLINSERT,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jObj = new JSONObject(response);
//                            boolean success = jObj.getBoolean("success");
//                            String message = jObj.getString("message");
//
//                            if (success) {
//                                // Data berhasil ditambahkan, perbarui tampilan
//                                callVolley(); // Memanggil metode untuk memperbarui daftar data
//                                Toast.makeText(
//                                        RentOfCars.this,
//                                        message,
//                                        Toast.LENGTH_LONG).show();
//                            } else {
//                                // Gagal menambahkan data
//                                Toast.makeText(
//                                        RentOfCars.this,
//                                        "Gagal: " + message,
//                                        Toast.LENGTH_LONG).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(
//                                    RentOfCars.this,
//                                    "Gagal: Terjadi kesalahan pada respons server",
//                                    Toast.LENGTH_LONG).show();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(
//                        RentOfCars.this,
//                        "Gagal koneksi ke server, cek setingan koneksi Anda",
//                        Toast.LENGTH_LONG).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                // Membuat parameter yang akan dikirim ke server
//                Map<String, String> params = new HashMap<String, String>();
//                // Tidak perlu mengirim ID ke server
//                // params.put("id", vid);
//                params.put("nomor", vnomor);
//                params.put("cust_name", vcust_name);
//                params.put("no_mobil", vno_mobil);
//                params.put("tgl_mulai", vtgl_mulai);
//                params.put("tgl_selesai", vtgl_selesai);
//                params.put("jenis_mobil", vjenis_mobil);
//                params.put("durasi_sewa", vdurasi_sewa);
//                params.put("tharga_sewa", vtharga_sewa);
//                return params;
//            }
//        };
//
//        // Mengirim permintaan ke server
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        queue.add(stringRequest);
//    }
//    public void simpan() {
//        vjenis_mobil = tjenis_mobil.getText().toString();
//        vnomor = tnomor.getText().toString();
//        vcust_name = tcust_name.getText().toString();
//        vno_mobil = tno_mobil.getText().toString();
//        vtgl_mulai = ttgl_mulai.getText().toString();
//        vtgl_selesai = ttgl_selesai.getText().toString();
//        vdurasi_sewa = tdurasi_sewa.getText().toString();
//        vtharga_sewa = ttharga_sewa.getText().toString();
//
//        if (vjenis_mobil.isEmpty() || vnomor.isEmpty() || vcust_name.isEmpty() || vno_mobil.isEmpty() ||
//                vtgl_mulai.isEmpty() || vtgl_selesai.isEmpty() || vdurasi_sewa.isEmpty() || vtharga_sewa.isEmpty()) {
//            Toast.makeText(RentOfCars.this, "Semua kolom harus diisi", Toast.LENGTH_LONG).show();
//        } else {
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLINSERT,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            try {
//                                JSONObject jObj = new JSONObject(response);
//                                boolean success = jObj.getBoolean("success");
//                                String message = jObj.getString("message");
//
//                                if (success) {
//                                    // Data berhasil ditambahkan, perbarui tampilan
//                                    callVolley(); // Memanggil metode untuk memperbarui daftar data
//                                    Toast.makeText(
//                                            RentOfCars.this,
//                                            message,
//                                            Toast.LENGTH_LONG).show();
//                                } else {
//                                    // Gagal menambahkan data
//                                    Toast.makeText(
//                                            RentOfCars.this,
//                                            "Gagal: " + message,
//                                            Toast.LENGTH_LONG).show();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                                Toast.makeText(
//                                        RentOfCars.this,
//                                        "Gagal: Terjadi kesalahan pada respons server",
//                                        Toast.LENGTH_LONG).show();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(
//                            RentOfCars.this,
//                            "Gagal koneksi ke server, cek setingan koneksi Anda",
//                            Toast.LENGTH_LONG).show();
//                }
//            }) {
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    // Membuat parameter yang akan dikirim ke server
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("jenis_mobil", vjenis_mobil);
//                    params.put("nomor", vnomor);
//                    params.put("cust_name", vcust_name);
//                    params.put("no_mobil", vno_mobil);
//                    params.put("tgl_mulai", vtgl_mulai);
//                    params.put("tgl_selesai", vtgl_selesai);
//                    params.put("durasi_sewa", vdurasi_sewa);
//                    params.put("tharga_sewa", vtharga_sewa);
//                    return params;
//                }
//            };
//
//            // Mengirim permintaan ke server
//            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//            queue.add(stringRequest);
//        }
//    }


    public void hapusData(String id){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLDELETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(
                                RentOfCars.this,
                                response,
                                Toast.LENGTH_LONG).show();
                        callVolley();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(
                        RentOfCars.this,
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

                        DataRents item = new DataRents();

                        item.setId(obj.getString("id"));
                        item.setJenis_mobil(obj.getString("jenis_mobil"));
                        item.setNomor(obj.getString("nomor"));
                        item.setCust_name(obj.getString("cust_name"));
                        item.setNo_mobil(obj.getString("no_mobil"));
                        item.setTgl_mulai(obj.getString("tgl_mulai"));
                        item.setTgl_selesai(obj.getString("tgl_selesai"));
                        item.setDurasi_sewa(obj.getString("durasi_sewa"));
                        item.setTharga_sewa(obj.getString("tharga_sewa"));

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