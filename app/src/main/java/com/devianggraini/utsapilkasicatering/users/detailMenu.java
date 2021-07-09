package com.devianggraini.utsapilkasicatering.users;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.devianggraini.utsapilkasicatering.R;
import com.devianggraini.utsapilkasicatering.server.BaseURL;
import com.devianggraini.utsapilkasicatering.session.PrefSetting;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class detailMenu extends AppCompatActivity {
    TextView kategoridetail, namamenu, idmakanan, harga, ratingg;
    EditText jumlah;
    Button btnpesan;

    ImageView imageMenu;
    String usename;

    ProgressDialog pDialog;

    private RequestQueue mRequestQueue;

    String strIdMenu, strNamaMenu, strHargamenu, strRatingMenu, strKategoriMenu, strGambarMenu, _id, strjumlah;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_menu);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        usename = PrefSetting.userName;
        mRequestQueue = Volley.newRequestQueue(this);

        kategoridetail = (TextView) findViewById(R.id.kategoridetail);
        namamenu = (TextView) findViewById(R.id.namamenu);
        idmakanan = (TextView) findViewById(R.id.idmakanan);
        harga = (TextView) findViewById(R.id.harga);
        ratingg = (TextView) findViewById(R.id.ratingg);
        jumlah = (EditText) findViewById(R.id.jumlahpesanan);
        btnpesan = (Button) findViewById(R.id.btnpesan);
        btnpesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                strIdMenu = idmakanan.getText().toString();
                strNamaMenu = namamenu.getText().toString();
                strHargamenu = harga.getText().toString();
                strRatingMenu = ratingg.getText().toString();
                strjumlah = jumlah.getText().toString();
                int Total = Integer.parseInt(strHargamenu) * Integer.parseInt(strjumlah);

                order(strNamaMenu, strHargamenu, strjumlah, Total);

            }
        });
        imageMenu = (ImageView) findViewById(R.id.imageMenu);

        Intent i = getIntent();
        strIdMenu = i.getStringExtra("idMenu");
        strNamaMenu = i.getStringExtra("namaMenu");
        strHargamenu = i.getStringExtra("hargaMenu");
        strRatingMenu = i.getStringExtra("ratingMenu");
        strGambarMenu = i.getStringExtra("gambar");
        strKategoriMenu = i.getStringExtra("kategori");
        _id = i.getStringExtra("_id");

        idmakanan.setText(strIdMenu);
        namamenu.setText(strNamaMenu);
        harga.setText(strHargamenu);
        ratingg.setText(strRatingMenu);
        kategoridetail.setText(strKategoriMenu);
        Picasso.get().load(BaseURL.baseUrl + "gambar/" + strGambarMenu)
                .into(imageMenu);
    }

    public void order(String namamenu, String harga, String jumlah, int total){

        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userName", usename);
        params.put("namamenu", namamenu);
        params.put("harga", harga);
        params.put("jumlah", jumlah);
        params.put("Status", "Sedang Proses");
        params.put("total", String.valueOf(total));


        pDialog.setMessage("Mohon Tunggu.....");
        showDialog();
        Log.e("param", String.valueOf(params));

        JsonObjectRequest req = new JsonObjectRequest(BaseURL.order, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            String strMsg = response.getString("msg");
                            boolean status= response.getBoolean("error");
                            if(status == false){
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(detailMenu.this, home_user.class);
                                startActivity(i);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });
        mRequestQueue.add(req);
    }
    private void showDialog(){
        if(!pDialog.isShowing()){
            pDialog.show();
        }
    }

    private void hideDialog(){
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
    }
}
