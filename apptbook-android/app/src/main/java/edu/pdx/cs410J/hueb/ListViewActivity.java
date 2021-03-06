package edu.pdx.cs410J.hueb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.Collection;
import java.util.Locale;

import edu.pdx.cs410J.ParserException;

public class ListViewActivity extends AppCompatActivity {
    private ArrayAdapter<Appointment> appts;
    private ArrayAdapter<String> apptStrings;
    private TextView ownerDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        String owner = getOwner();

        ownerDisplay = findViewById(R.id.apptBookOwnerListAllAppts);

        ownerDisplay.setText(owner.toUpperCase(Locale.ROOT) + "'S APPOINTMENTS: ");



        AppointmentBook appointmentBook;

        appointmentBook = parseFile(owner);


        File contextDirectory = getApplicationContext().getDataDir();
        File file = new File(contextDirectory, owner+"Pretty.txt");
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(file));
            PrettyPrinter prettyPrinter = new PrettyPrinter(pw);
            prettyPrinter.dump(appointmentBook);
        } catch (IOException e) {
            toast("While Pretty Printing to file: " + e.getMessage());
        }


        this.apptStrings = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while(line!=null) {
                this.apptStrings.add(line);
                line=br.readLine();
            }
        } catch (IOException e) {
            toast("While Reading Pretty file: " + e.getMessage());
        }

        ListView listOfAppts = findViewById(R.id.listAllAppts);
        listOfAppts.setAdapter(this.apptStrings);



//        Collection<Appointment> appointments = appointmentBook.getAppointments();





//        this.appts = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

//        for (Appointment apt : appointments){
//          this.appts.add(apt);
//        }

//        ListView listOfAppts = findViewById(R.id.listAllAppts);
//        listOfAppts.setAdapter(this.appts);
//        listOfAppts.add(appointments);
//        adapter.add(owner);





    }



    private void toast(String message) {
        Toast.makeText(ListViewActivity.this, message, Toast.LENGTH_LONG).show();
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



    private String getOwner() {
        String ownerString = getIntent().getStringExtra("OWNER");
        return ownerString;
    }


}