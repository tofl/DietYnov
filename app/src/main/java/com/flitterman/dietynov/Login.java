package com.flitterman.dietynov;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    private EditText editSize;
    private EditText editBirthDate;
    private EditText editWeight;
    private RadioGroup radioGroup;
    private RadioButton radioButtonFemale;
    private TextView wrongSize;
    private TextView wrongWeight;
    //private RadioButton radioButtonMale;

    settingsAdapter helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editSize = findViewById(R.id.editSize);
        editBirthDate = findViewById(R.id.editBirthDate);
        editWeight = findViewById(R.id.editWeight);
        radioGroup = findViewById(R.id.radioGroup);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);
        //radioButtonMale = findViewById(R.id.radioButtonMale);
        wrongSize = findViewById(R.id.wrongSize);
        wrongWeight = findViewById(R.id.wrongWeight);
    }

    public void validate(View v) {
        int size = Integer.parseInt(editSize.getText().toString());
        String birthdate = editBirthDate.getText().toString();
        int weight = Integer.parseInt(editWeight.getText().toString());

        int selectedId = radioGroup.getCheckedRadioButtonId();
        String sexe;

        if (selectedId == radioButtonFemale.getId()) {
            sexe = "f";
        } else {
            sexe = "m";
        }

        int wrongElements = 0;

        if (size < 20 || size > 250) {
            wrongSize.setVisibility(View.VISIBLE);
            wrongElements += 1;
        } else {
            wrongSize.setVisibility(View.GONE);
        }


        if (weight < 20 || weight > 700) {
            wrongWeight.setVisibility(View.VISIBLE);
            wrongElements += 1;
        } else {
            wrongWeight.setVisibility(View.GONE);
        }

        if (wrongElements > 0) {
            return;
        }

        // Sauvegarder dans la base de données :
        helper = new settingsAdapter(this);
        helper.insertData(size, birthdate, sexe, weight);

        Intent intent = new Intent(this, Home.class);
        startActivity(intent);

    }
}
