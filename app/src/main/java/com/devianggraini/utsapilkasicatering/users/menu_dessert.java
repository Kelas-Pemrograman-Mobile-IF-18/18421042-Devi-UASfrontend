package com.devianggraini.utsapilkasicatering.users;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class menu_dessert extends AppCompatActivity {
    ProgressDialog pDialog;

    AdapterMenu adapter;
    ListView list;

    ArrayList<ModelMakanan> newsList = new ArrayList<ModelMakanan>();
    private RequestQueue mRequestQueue;

    FloatingActionButton floatingExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_dessert);

        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);

        floatingExit = (FloatingActionButton) findViewById(R.id.exit);

        newsList.clear();
        adapter = new AdapterMenu(menu_dessert.this, newsList);
        list.setAdapter(adapter);
        getAllMenu();


        floatingExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(menu_dessert.this, home_user.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void getAllMenu() {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseURL.dataKategoriMenuDest, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            if (status == false) {
                                Log.d("data menu = ", response.toString());
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final ModelMakanan menu = new ModelMakanan();
                                    final String _id = jsonObject.getString("_id");
                                    final String namaMenu = jsonObject.getString("namaMenu");
                                    final String idMenu= jsonObject.getString("idMenu");
                                    final String HargaMenu = jsonObject.getString("hargaMenu");
                                    final String Ratingmenu = jsonObject.getString("ratingMenu");
                                    final String Kategori = jsonObject.getString("kategori");
                                    final String gambar = jsonObject.getString("gambar");
                                    menu.setIdMenu(idMenu);
                                    menu.setNamaMenu(namaMenu);
                                    menu.setHargaMenu(HargaMenu);
                                    menu.setRatingMenu(Ratingmenu);
                                    menu.setKategori(Kategori);
                                    menu.setGambar(gambar);
                                    menu.set_id(_id);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                            Intent a = new Intent(menu_dessert.this, detailMenu.class);
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
                                    newsList.add(menu);
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