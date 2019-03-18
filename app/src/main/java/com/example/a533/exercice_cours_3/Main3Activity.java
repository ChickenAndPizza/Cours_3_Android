package com.example.a533.exercice_cours_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    String activiy2String;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            activiy2String = "N/A";
        } else {
            activiy2String = extras.getString("String");
        }
        Toast();
    }

    private void Toast()
    {
        Toast.makeText(this, activiy2String,
                Toast.LENGTH_SHORT).show();
    }
}
