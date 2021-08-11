package edu.pdx.cs410J.hueb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class AddApptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appt);


        Button addAppt = findViewById(R.id.addApptButtonAddAppt);
        addAppt.setOnClickListener(view -> addAppointment());




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
        Toast.makeText(AddApptActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void addAppointment() {
        TextInputEditText owner = findViewById(R.id.text_input_owner2_add_appt);
        TextInputEditText desc = findViewById(R.id.text_input_desc2_add_appt);
        TextInputEditText bTime = findViewById(R.id.text_input_bTime2_add_appt);
        TextInputEditText eTime = findViewById(R.id.text_input_eTime2_add_appt);

        String ownerString = owner.getText().toString();
        String descString = desc.getText().toString();
        String bTimeString = bTime.getText().toString();
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


            try{
                Appointment appt = new Appointment(descString,bTimeString,eTimeString);
                AppointmentBook apptBook = new AppointmentBook(ownerString);
                apptBook.addAppointment(appt);


//
//            Appointment appt1 = new Appointment("test", "12/11/1985 1:00 pm", "12/11/1985 2:00 pm");
//            Appointment appt2 = new Appointment("test2", "12/11/1985 1:00 pm", "12/11/1985 2:00 pm");
//            AppointmentBook apptBook1 = new AppointmentBook("sam");
//            apptBook1.addAppointment(appt1);
//            apptBook1.addAppointment(appt2);



                //write to file by owners name
                File contextDirectory = getApplicationContext().getDataDir();
                File file = new File(contextDirectory, ownerString+".txt");
                try {
                    PrintWriter pw = new PrintWriter(new FileWriter(file, true));
                    TextDumper2 dumper = new TextDumper2(pw);
                    dumper.dump(apptBook);
//                PrettyPrinter prettyPrinter = new PrettyPrinter(pw);
//                prettyPrinter.dump(apptBook);

                    Toast.makeText(AddApptActivity.this, "Adding Appointment:\n" + appt.toString(), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    toast("While writing to file: " + e.getMessage());
                }



//            Intent intent = new Intent(this, MainActivity.class);
//            Bundle extras = new Bundle();
//            extras.putString("OWNER", ownerString);
//            extras.putString("DESC", descString);
//            extras.putString("BTIME", bTimeString);
//            extras.putString("ETIME", eTimeString);
//            intent.putExtras(extras);
//            startActivity(intent);


            } catch (IllegalArgumentException e){
                Toast.makeText(AddApptActivity.this, "Adding Appointment Failed\n" + e.getMessage(), Toast.LENGTH_LONG).show();
            }










        //            desc.getText().clear();
//            bTime.getText().clear();
//            eTime.getText().clear();







    }




}