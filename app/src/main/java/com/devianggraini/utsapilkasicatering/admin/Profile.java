package com.devianggraini.utsapilkasicatering.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.devianggraini.utsapilkasicatering.R;
import com.devianggraini.utsapilkasicatering.session.PrefSetting;

public class Profile extends AppCompatActivity {
    TextView textnama, username, alamat, noTelp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        textnama = (TextView) findViewById(R.id.textnama);
        username = (TextView) findViewById(R.id.username);
        alamat = (TextView) findViewById(R.id.alamat);
        noTelp = (TextView) findViewById(R.id.noTelp);


        textnama.setText(PrefSetting.userName);
        username.setText(PrefSetting.userName);
        alamat.setText(PrefSetting.alamat);
        noTelp.setText(PrefSetting.no_telp);


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Profile.this, MenuHome.class);
        startActivity(i);
        finish();
    }

}