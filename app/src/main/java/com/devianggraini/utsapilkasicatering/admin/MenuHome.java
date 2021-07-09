 package com.devianggraini.utsapilkasicatering.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.devianggraini.utsapilkasicatering.R;
import com.devianggraini.utsapilkasicatering.session.PrefSetting;
import com.devianggraini.utsapilkasicatering.session.SessionManager;
import com.devianggraini.utsapilkasicatering.users.Login;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

 public class MenuHome extends AppCompatActivity {

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;
    CardView cardExit, cardDataMenu, cardInputmenu, cardProfile, cardPesanan;
     FloatingActionButton exitt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_admin);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreferances();

        session = new SessionManager(MenuHome.this);

        prefSetting.isLogin(session, prefs);

        exitt = (FloatingActionButton) findViewById(R.id.exitt);
        cardDataMenu = (CardView) findViewById(R.id.cardDataMenu);
        cardInputmenu = (CardView) findViewById(R.id.cardInput);
        cardProfile = (CardView) findViewById(R.id.cardProfile);
        cardPesanan = (CardView) findViewById(R.id.cardPesanan);


        exitt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                session.setSessid(0);
                Intent i = new Intent(MenuHome.this, Login.class);
                startActivity(i);
                finish();
            }
        });

        cardDataMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuHome.this, ActivityDataMenu.class);
                startActivity(i);
                finish();
            }
        });

        cardInputmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuHome.this, InputDataMenu.class);
                startActivity(i);
                finish();
            }
        });

        cardProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuHome.this, Profile.class);
                startActivity(i);
                finish();
            }
        });

        cardPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuHome.this, PesananUser.class);
                startActivity(i);
                finish();
            }
        });
    }
}