package com.devianggraini.utsapilkasicatering.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.devianggraini.utsapilkasicatering.R;
import com.devianggraini.utsapilkasicatering.adapter.AdapterMenu;
import com.devianggraini.utsapilkasicatering.model.ModelMakanan;
import com.devianggraini.utsapilkasicatering.server.BaseURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityDataMenu extends AppCompatActivity {
    ProgressDialog pDialog;

    AdapterMenu adapter;
    ListView list;

    ArrayList<ModelMakanan> newsList = new ArrayList<ModelMakanan>();
    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_menu);

        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);
        newsList.clear();
        adapter = new AdapterMenu(ActivityDataMenu.this, newsList);
        list.setAdapter(adapter);
        getAllMakanan();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ActivityDataMenu.this, MenuHome.class);
        startActivity(i);
        finish();
    }

    private void getAllMakanan() {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseURL.dataMenu, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            if (status == false) {
                                Log.d("data makanan = ", response.toString());
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final ModelMakanan makanan = new ModelMakanan();
                                    final String _id = jsonObject.getString("_id");
                                    final String namaMenu = jsonObject.getString("namaMenu");
                                    final String idMenu = jsonObject.getString("idMenu");
                                    final String hargaMenu = jsonObject.getString("hargaMenu");
                                    final String ratingMenu = jsonObject.getString("ratingMenu");
                                    final String gambar = jsonObject.getString("gambar");
                                    final String Kategori = jsonObject.getString("kategori");
                                    makanan.setIdMenu(idMenu);
                                    makanan.setNamaMenu(namaMenu);
                                    makanan.setHargaMenu(hargaMenu);
                                    makanan.setRatingMenu(ratingMenu);
                                    makanan.setGambar(gambar);
                                    makanan.setKategori(Kategori);
                                    makanan.set_id(_id);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                            Intent a = new Intent(ActivityDataMenu.this, ActivityEditMenudanHapusMenu.class);
                                            a.putExtra("idMenu", newsList.get(position).getIdMenu());
                                            a.putExtra("_id", newsList.get(position).get_id());
                                            a.putExtra("namaMenu", newsList.get(position).getNamaMenu());
                                            a.putExtra("hargaMenu", newsList.get(position).getHargaMenu());
                                            a.putExtra("ratingMenu", newsList.get(position).getRatingMenu());
                                            a.putExtra("kategori", newsList.get(position).getKategori());
                                            a.putExtra("gambar", newsList.get(position).getGambar());
                                            startActivity(a);
                                        }
                                    });
                                    newsList.add(makanan);
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

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    }