package com.devianggraini.utsapilkasicatering.admin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.devianggraini.utsapilkasicatering.R;
import com.devianggraini.utsapilkasicatering.adapter.AdapterOrder;
import com.devianggraini.utsapilkasicatering.model.ModelOrder;
import com.devianggraini.utsapilkasicatering.server.BaseURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PesananUser extends AppCompatActivity {

    ProgressDialog pDialog;

    AdapterOrder adapter;
    ListView list;

    ArrayList<ModelOrder> newsList = new ArrayList<ModelOrder>();
    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan_user2);

        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list_order);
        newsList.clear();
        adapter = new AdapterOrder(PesananUser.this, newsList);
        list.setAdapter(adapter);
        getAllOrder();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(PesananUser.this, MenuHome.class);
        startActivity(i);
        finish();
    }

    private void getAllOrder() {
        // Pass second argument as "null" for GET requests
        newsList.clear();
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseURL.lihatDataTransaksi, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            if (status == false) {
                                Log.e("data pesanan = ", response.getString("data"));
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final ModelOrder order = new ModelOrder();
                                    final String _id = jsonObject.getString("_id");
                                    final String namaMenu = jsonObject.getString("namamenu");
                                    final String hargaMenu = jsonObject.getString("harga");
                                    final String jumlahMenu = jsonObject.getString("jumlah");
                                    final String Status = jsonObject.getString("Status");
                                    final String Total = jsonObject.getString("total");
                                    final String User = jsonObject.getString("dataUser");
                                    JSONArray DataUSer = new JSONArray(User);
                                    JSONObject FinalUser = DataUSer.getJSONObject(0);
                                    final String namaPembeli = FinalUser.getString("userName");
                                    Log.e("NamaPembeli", namaPembeli);
//                                    JSONArray DataUSer = new JSONArray(User);
//                                    Log.e("i", String.valueOf(i));
//                                    Log.e("length", String.valueOf(DataUSer.length()));
//                                    for (int j = 0; j < DataUSer.length(); j++) {
//                                        JSONObject FinalUser = DataUSer.getJSONObject(i);
//                                        final String namaPembeli = FinalUser.getString("nama");
//                                        Log.e("NamaPembeli", namaPembeli);
//                                    }
                                    order.set_id(_id);
                                    order.setUsername(namaPembeli);
                                    order.setNamaMenu(namaMenu);
                                    order.setHarga(hargaMenu);
                                    order.setJumlah(jumlahMenu);
                                    order.setStatus(Status);
                                    order.setTotal(Total);
//                                    order.set_Id(username);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                            if (newsList.get(position).getStatus().equals("Sedang Proses")) {

                                                AlertDialog.Builder builder = new AlertDialog.Builder(PesananUser.this);

                                                builder.setTitle("Konfirmasi");
                                                builder.setMessage("Yakin ingin konfirmasi transaksi ini ? ");

                                                builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {

                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // Do nothing but close the dialog
//                                                    hapusData();
                                                        updateData(newsList.get(position).get_id());
                                                        Toast.makeText(getApplicationContext(), "berhasil", Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        // Do nothing
                                                        dialog.dismiss();
                                                    }
                                                });

                                                AlertDialog alert = builder.create();
                                                alert.show();
                                            }


                                            // TODO Auto-generated method stub
//                                            Intent a = new Intent(HistoryTransaksiPembeli.this, EditBukuDanHapusActivity.class);
//                                            a.putExtra("namaBarang", newsList.get(position).getNamaBarang());
//                                            a.putExtra("_id", newsList.get(position).get_id());
//                                            a.putExtra("hargaBarang", newsList.get(position).getHargaBarang());
//                                            a.putExtra("stok", newsList.get(position).getStok());
//                                            a.putExtra("kategori", newsList.get(position).getKategori());
//                                            a.putExtra("image", newsList.get(position).getImage());
//                                            startActivity(a);
                                        }
                                    });
                                    newsList.add(order);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }
    public void updateData(String _id) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("Status", "Proses Pengiriman");
        pDialog.setMessage("Mohon Tunggu....");
        Log.e("ID", _id);
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, BaseURL.EditTransaksi + _id, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            hideDialog();
                            String strMsg = response.getString("msg");
                            boolean status = response.getBoolean("error");
                            if (status) {
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                getAllOrder();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });


// add the request object to the queue to be executed
        mRequestQueue.add(req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}