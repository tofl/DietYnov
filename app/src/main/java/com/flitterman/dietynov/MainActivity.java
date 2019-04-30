package com.flitterman.dietynov;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    boolean loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if user has an account
        // If he doesn't, send him to the account activity
        // Otherwise, send him to the homepage

        settingsAdapter helper = new settingsAdapter(this);

        if (!helper.userExists()) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }
    }
}
