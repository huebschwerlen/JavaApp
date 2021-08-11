package edu.pdx.cs410J.hueb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);



        Button readMe = findViewById(R.id.readMeButton);
        readMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "View Appt!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(HelpActivity.this, ReadMeActivity.class);
                startActivity(intent);
            }
        });

    }
}