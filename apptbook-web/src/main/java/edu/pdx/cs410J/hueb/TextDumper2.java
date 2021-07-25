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

        writer.println("\nOwner: " + aptBook.getOwnerName());
        writer.println();
        for(Appointment appointment : aptBook.getAppointments() ) {
            writer.println(appointment.toString());
        }

    }
}


