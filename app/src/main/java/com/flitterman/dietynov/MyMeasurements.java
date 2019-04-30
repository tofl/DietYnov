package com.flitterman.dietynov;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyMeasurements extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Measurement> measurementList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        final String extra = intent.getStringExtra("tab");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_measurements);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyMeasurements.this, NewValue.class);
                intent.putExtra("type", extra);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (extra.equals("taille")) {
            Button taille = findViewById(R.id.button_taille);
            taille.setBackgroundColor(Color.GREEN);
        } else if (extra.equals("hanche")) {
            Button taille = findViewById(R.id.button_hanche);
            taille.setBackgroundColor(Color.GREEN);
        } else if (extra.equals("cuisse")) {
            Button taille = findViewById(R.id.button_cuisse);
            taille.setBackgroundColor(Color.GREEN);
        } else if (extra.equals("bras")) {
            Button taille = findViewById(R.id.button_bras);
            taille.setBackgroundColor(Color.GREEN);
        }


        MeasurementAdapter measurementAdapter = new MeasurementAdapter(this);
        measurementList = measurementAdapter.getData(extra);

        for (final Measurement measurement : measurementList) {
            Log.e("list", measurement.getDate());
        }


        MeasurementListAdapter measurementListAdapter = new MeasurementListAdapter(this, measurementList);
        measurementListAdapter.addAll(measurementList);

        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(measurementListAdapter);


    }

    public class MeasurementListAdapter extends ArrayAdapter<Measurement> {
        public MeasurementListAdapter(Context context, List<Measurement> measurementList) {
            super(context, 0, measurementList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Measurement measurement = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.measurements_row_layout, parent, false);
            }

            TextView date = (TextView) convertView.findViewById(R.id.date);
            TextView value = (TextView) convertView.findViewById(R.id.value);

            date.setText(measurement.getDate());
            value.setText(String.valueOf(measurement.getValue()) + " cm");

            return convertView;
        }
    }

    public void displayTaille(View v) {
        finish();
        Intent intent = new Intent(this, MyMeasurements.class);
        intent.putExtra("tab", "taille");
        startActivity(intent);
    }

    public void displayHanche(View v) {
        finish();
        Intent intent = new Intent(this, MyMeasurements.class);
        intent.putExtra("tab", "hanche");
        startActivity(intent);
    }

    public void displayCuisse(View v) {
        finish();
        Intent intent = new Intent(this, MyMeasurements.class);
        intent.putExtra("tab", "cuisse");
        startActivity(intent);
    }

    public void displayBras(View v) {
        finish();
        Intent intent = new Intent(this, MyMeasurements.class);
        intent.putExtra("tab", "bras");
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_measurements, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        } else if (id == R.id.all_recipes) {
            Intent intent = new Intent(this, Recipes.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
