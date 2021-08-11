package edu.pdx.cs410J.hueb;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import edu.pdx.cs410J.hueb.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Appointment appointment = new Appointment("this is a desc", "12/11/1985 5:30 pm", "12/11/1985 6:30 pm");
//                Snackbar.make(view, appointment.toString(), Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        Button addAppt = findViewById(R.id.addApptButtonMain);
        addAppt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Adding Appt!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, AddApptActivity.class);
                startActivity(intent);
            }
        });


        Button searchAppts = findViewById(R.id.searchApptsButtonMain);
        searchAppts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "Searching Appt!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, SearchApptsActivity.class);
                startActivity(intent);
            }
        });


        Button viewAllAppts = findViewById(R.id.viewAllApptsButtonMain);
        viewAllAppts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "View Appt!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, ListAllApptsActivity.class);
                startActivity(intent);
            }
        });


        Button help = findViewById(R.id.helpButton);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this, "View Appt!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
            }
        });




    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


}