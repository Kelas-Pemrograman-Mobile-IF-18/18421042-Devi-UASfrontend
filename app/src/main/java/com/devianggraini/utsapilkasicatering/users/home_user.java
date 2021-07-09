package com.devianggraini.utsapilkasicatering.users;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.devianggraini.utsapilkasicatering.R;
import com.devianggraini.utsapilkasicatering.session.PrefSetting;
import com.devianggraini.utsapilkasicatering.session.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class home_user extends AppCompatActivity {

    CardView cardAppetizer, cardMainCourse, cardDessert, cardDrinks;
    FloatingActionButton exitt;
    Button user, keranjang;
    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreferances();

        session = new SessionManager(home_user.this);

        prefSetting.isLogin(session, prefs);

        cardAppetizer = (CardView) findViewById(R.id.cardAppetizer);
        cardMainCourse = (CardView) findViewById(R.id.cardMainCourse);
        cardDessert = (CardView) findViewById(R.id.cardDessert);
        cardDrinks = (CardView) findViewById(R.id.cardDrinks);
        exitt = (FloatingActionButton) findViewById(R.id.exitt);
        user = (Button) findViewById(R.id.user);
        keranjang = (Button) findViewById(R.id.keranjang);

        exitt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                session.setSessid(0);
                Intent i = new Intent(home_user.this, Login.class);
                startActivity(i);
                finish();
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(home_user.this, User_Profile.class);
                startActivity(i);
                finish();
            }
        });

        keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(home_user.this, PesananUser.class);
                startActivity(i);
                finish();
            }
        });

        cardAppetizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(home_user.this, menu_appetizer.class);
                startActivity(i);
                finish();
            }
        });

        cardMainCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(home_user.this, menu_mainCourse.class);
                startActivity(i);
                finish();
            }
        });

        cardDessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(home_user.this, menu_dessert.class);
                startActivity(i);
                finish();
            }
        });

        cardDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(home_user.this, menu_drink.class);
                startActivity(i);
                finish();
            }
        });
    }
}