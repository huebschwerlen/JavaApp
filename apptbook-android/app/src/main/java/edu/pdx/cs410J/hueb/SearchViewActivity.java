package edu.pdx.cs410J.hueb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.pdx.cs410J.ParserException;

public class SearchViewActivity extends AppCompatActivity {
    private ArrayAdapter<Appointment> appts;
    private ArrayAdapter<String> apptStrings;
    private TextView ownerDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);


//        Intent intent = getIntent();
//        Bundle extras = intent.getExtras();
//        String owner = extras.getString("OWNER");
//        String beginTime = extras.getString("BTIME");
//        String endTime = extras.getString("ETIME");



        String owner = getIntent().getStringExtra("OWNER");
        String beginTime = getIntent().getStringExtra("BTIME");
        String endTime = getIntent().getStringExtra("ETIME");

        ownerDisplay = findViewById(R.id.apptBookOwnerSearchAllAppts);

        ownerDisplay.setText(owner.toUpperCase(Locale.ROOT) + "'S APPOINTMENTS: ");



        AppointmentBook appointmentBook;

        appointmentBook = parseFile(owner);


        Date beginTime1 = null;
        try {
            beginTime1 = new SimpleDateFormat("MM/dd/yyyy hh:mm aa").parse(beginTime);
        } catch (ParseException e) {
            toast("While Parsing BeginTime Pretty file: " + e.getMessage());
        }

        Date endTime1 = null;
        try {
            endTime1 = new SimpleDateFormat("MM/dd/yyyy hh:mm aa").parse(endTime);
        } catch (ParseException e) {
            toast("While Parsing EndTime Pretty file: " + e.getMessage());
        }


        AppointmentBook bookByTime = new AppointmentBook(owner);

        for (Appointment appt : appointmentBook.getAppointments()){

            Boolean between = appt.getBeginTime().after(beginTime1) && appt.getBeginTime().before(endTime1);
            Boolean equals =  appt.getBeginTime().equals(beginTime1) || appt.getBeginTime().equals(endTime1);
//                System.out.println("\n between: " + between + " equals: " + equals + "\n");

            if(between || equals ) {
                bookByTime.addAppointment(appt);
            }

        }



        File contextDirectory = getApplicationContext().getDataDir();
        File file = new File(contextDirectory, owner+"Search.txt");
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(file));
            PrettyPrinter prettyPrinter = new PrettyPrinter(pw);
            prettyPrinter.dump(bookByTime);
        } catch (IOException e) {
            toast("While Search Printing to file: " + e.getMessage());
        }


        this.apptStrings = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while(line!=null) {
                this.apptStrings.add(line);
                line=br.readLine();
            }
        } catch (IOException e) {
            toast("While Reading Search file: " + e.getMessage());
        }

        ListView listOfAppts = findViewById(R.id.searchAllAppts);
        listOfAppts.setAdapter(this.apptStrings);





    }



    private void toast(String message) {
        Toast.makeText(SearchViewActivity.this, message, Toast.LENGTH_LONG).show();
    }


    private AppointmentBook parseFile(String owner) {

        File contextDir = getApplicationContext().getDataDir();
        File file = new File(contextDir, owner+".txt");
        AppointmentBook apptBook = new AppointmentBook(owner);

        if (file.exists()) {
//            toast("EXISTS!");
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                TextParser tp = new TextParser(br);
                apptBook = tp.parse();
                return apptBook;

            } catch (FileNotFoundException | ParserException e) {
                toast("Couldnt Parse File " + e.getMessage());
                return apptBook;
            }
        } else {
            toast("Could Not Find Appointment Book For "+owner);
            return apptBook;
        }

    }

}