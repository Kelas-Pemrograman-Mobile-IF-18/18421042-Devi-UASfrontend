package com.devianggraini.utsapilkasicatering.users;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.devianggraini.utsapilkasicatering.R;
import com.devianggraini.utsapilkasicatering.admin.MenuHome;
import com.devianggraini.utsapilkasicatering.server.BaseURL;
import com.devianggraini.utsapilkasicatering.session.PrefSetting;
import com.devianggraini.utsapilkasicatering.session.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.devianggraini.utsapilkasicatering.R.id;

public class Login extends AppCompatActivity {
Button btnlogin, btnregister;
EditText edtUserName, edtPassword;


    private RequestQueue mRequestQueue;
    ProgressDialog pDialog;

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mRequestQueue = Volley.newRequestQueue(this);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        btnlogin = (Button) findViewById(id.btnlogin);
        btnregister = (Button) findViewById(id.btnbregister);

        edtUserName = (EditText) findViewById(id.edtUserName);
        edtPassword = (EditText) findViewById(id.edtPassword);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreferances();

        session = new SessionManager(this);

        prefSetting.checkLogin(session, prefs);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
                finish();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUsername = edtUserName.getText().toString();
                String strPassword = edtPassword.getText().toString();

                if (strUsername.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "username tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if (strPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "password tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else {
                    login(strUsername, strPassword);
                }
            }
        });

    }

    public void login(String userName,  String password){

        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userName", userName);
        params.put("password", password);

        pDialog.setMessage("Mohon Tunggu.....");
        showDialog();

        JsonObjectRequest req = new JsonObjectRequest(BaseURL.login, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            String strMsg = response.getString("msg");
                            boolean status = response.getBoolean("error");
                            if (status == false){
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                String data = response.getString("data");
                                JSONObject jsonObject = new JSONObject(data);
                                String role = jsonObject.getString("role");
                                String _id = jsonObject.getString("_id");
                                String userName = jsonObject.getString("userName");
                                String no_telp = jsonObject.getString("no_telp");
                                String alamat = jsonObject.getString("alamat");
                                session.setLogin(true);
                                prefSetting.storeRegIdSharedPreferences(Login.this, _id, userName, no_telp, alamat, role, prefs);
                                if (role.equals("1")){
                                    Intent i = new Intent(Login.this, MenuHome.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Intent i = new Intent(Login.this, home_user.class);
                                    startActivity(i);
                                    finish();
                                }
                            } else {
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
        if (!pDialog.isShowing()){
            pDialog.show();
        }
    }

    private void hideDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
}