package com.example.a533.exercice_cours_3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity();
            }
        });
    }

    private void ChangeActivity()
    {
        Intent intent = new Intent(this, Main3Activity.class);
        intent.putExtra("String", ((EditText)findViewById(R.id.txtText)).getText().toString());
        startActivity(intent);
    }
}
