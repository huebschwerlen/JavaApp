package edu.pdx.cs410J.hueb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;

public class SearchApptsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_appts);


        Button searchAppts = findViewById(R.id.searchApptsButtonSearchAppts);
        searchAppts.setOnClickListener(view -> searchAppts());
    }

    // mm/dd/yy
    private static boolean validDate(String arg) {
        boolean match = arg.matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/(\\d{4})");
//    System.out.println("DATE: " + arg + " " + match);
        return match;
    }

    // hh:mm
    private static boolean validTime(String arg) {
        boolean match = arg.matches("(0?[1-9]|1[0-2]):(0?[0-9]|[0-5][0-9])");
//    System.out.println("TIME: " + arg + " " + match);
        return match;
    }

    // am / pm
    private static boolean validTimeAmPm(String arg) {
        boolean match = arg.matches("([a,A,p,P][m,M])");
//    System.out.println("TIME: " + arg + " " + match);
        return match;
    }


    private void toast(String message) {
        Toast.makeText(SearchApptsActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void searchAppts() {
        TextInputEditText owner = findViewById(R.id.text_input_owner2_search_appt);
        String ownerString = owner.getText().toString();

        TextInputEditText bTime = findViewById(R.id.text_input_btime2_search_appt);
        String bTimeString = bTime.getText().toString();

        TextInputEditText eTime = findViewById(R.id.text_input_etime2_search_appt);
        String eTimeString = eTime.getText().toString();

        String[] bTimeArr = bTimeString.split(" ", 3);
        boolean validaBeginTime = (validDate(bTimeArr[0]) && validTime(bTimeArr[1]) && validTimeAmPm(bTimeArr[2]));

        String[] eTimeArr = eTimeString.split(" ", 3);
        boolean validaEndTime = (validDate(eTimeArr[0]) && validTime(eTimeArr[1]) && validTimeAmPm(eTimeArr[2]));

        if(ownerString.isEmpty()) {
            toast("Owner Name Is Required");
            return;
        }

        if(!validaBeginTime) {
            toast("Incorrect Begin Time Format\nUse: mm/dd/yyyy hh:mm am/pm");
            return;
        }

        if(!validaEndTime) {
            toast("Incorrect End Time Format\nUse: mm/dd/yyyy hh:mm am/pm");
            return;
        }





        File contextDir = getApplicationContext().getDataDir();
        File file = new File(contextDir, ownerString+".txt");

        if (file.exists()) {
//            toast("EXISTS!");


            Intent intent = new Intent(this, SearchViewActivity.class);


//            Bundle bundle = new Bundle();
//            bundle.putString("OWNER", ownerString);
//            bundle.putString("BTIME", bTimeString);
//            bundle.putString("ETIME", eTimeString);
//
//            intent.putExtras(bundle);

            intent.putExtra("OWNER", ownerString);
            intent.putExtra("BTIME", bTimeString);
            intent.putExtra("ETIME", eTimeString);
            startActivity(intent);

        } else {
            toast("Could Not Find Appointment Book For "+ownerString);
        }


    }
}