package edu.pdx.cs410J.hueb;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;


public class TextDumper2 implements AppointmentBookDumper<AppointmentBook> {


    private final PrintWriter writer;


    /**
     * Creates a new <code>TextDumper</code>
     *
     *@param writer
     *     The printWriter to dump to
     */
//    public TextDumper2(String file) {
//        this.fileName = file;
//    }
    public TextDumper2(PrintWriter writer) {
        this.writer = writer;

    }



    /**
     * Dumps aptBook to file
     *
     *@param aptBook
     *      The appointment book to be dumped to file
     */
    @Override
    public void dump(AppointmentBook aptBook) throws IOException {


        for(Appointment apt : aptBook.getAppointments() ) {
//            writer.println(apt.toString());

            Date bTime = apt.getBeginTime();
            Date eTime = apt.getEndTime();

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");

            String beginTime = df.format(bTime);
            String endTime = df.format(eTime);

            String[] bVals = beginTime.split(" ");
            String[] eVals = endTime.split(" ");

            writer.write(aptBook.getOwnerName());
            writer.write("--");

            writer.write(apt.getDescription());
            writer.write("--");

            writer.write(bVals[0]);
            writer.write("--");
            writer.write(bVals[1]);
            writer.write("--");

            writer.write(eVals[0]);
            writer.write("--");
            writer.write(eVals[1]);

            writer.write("\n");

        }

    }
}


