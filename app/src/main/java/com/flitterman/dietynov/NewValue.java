package com.flitterman.dietynov;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NewValue extends AppCompatActivity {

    String extra;
    EditText value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_value);

        value = findViewById(R.id.editText_val);

        Intent intent = getIntent();
        extra = intent.getStringExtra("type");

        Log.e("extra", extra);

        TextView label = findViewById(R.id.label);

        if (extra.equals("weight")) {
            label.setText("Poids :");
        } else if (extra.equals("taille")) {
            label.setText("Taille :");
        } else if (extra.equals("hanche")) {
            label.setText("Hanche :");
        } else if (extra.equals("cuisse")) {
            label.setText("Cuisse :");
        } else if (extra.equals("bras")) {
            label.setText("Bras :");
        }
    }

    public void validate(View v) {

        if (extra.equals("weight")) {
            weightAdapter helper = new weightAdapter(this);
            helper.insertData(Integer.parseInt(value.getText().toString()));

            finish();
            Intent intent = new Intent(this, MyWeight.class);
            startActivity(intent);

        } else {
            MeasurementAdapter helper = new MeasurementAdapter(this);
            helper.insertData(extra, Integer.parseInt(value.getText().toString()));

            finish();
            Intent intent = new Intent(this, MyMeasurements.class);
            intent.putExtra("tab", extra);
            startActivity(intent);
        }

    }
}
