package edu.pdx.cs410J.hueb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;

public class ListAllApptsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_appts);

        Button viewAppts = findViewById(R.id.viewAllApptsButtonViewAppts);
        viewAppts.setOnClickListener(view -> viewAppts());

    }


    private void toast(String message) {
        Toast.makeText(ListAllApptsActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void viewAppts() {
        TextInputEditText owner = findViewById(R.id.text_input_owner2_view_appt);
        String ownerString = owner.getText().toString();

        if(ownerString.isEmpty()) {
            toast("Owner Name Is Required");
            return;
        }

        File contextDir = getApplicationContext().getDataDir();
        File file = new File(contextDir, ownerString+".txt");

        if (file.exists()) {
//            toast("EXISTS!");


            Intent intent = new Intent(this, ListViewActivity.class);
            intent.putExtra("OWNER", ownerString);
            startActivity(intent);

        } else {
            toast("Could Not Find Appointment Book For "+ownerString);
        }




    }


}