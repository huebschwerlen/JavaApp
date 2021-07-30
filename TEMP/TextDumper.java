package edu.pdx.cs410J.hueb;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;


public class TextDumper implements AppointmentBookDumper {



    protected final String fileName;
    private static PrintWriter err;


    /**
     * Creates a new <code>TextDumper</code>
     *
     *@param file
     *     The name of the file to dump to
     */
    public TextDumper(String file) {
        this.fileName = file;
    }



    /**
     * Dumps aptBook to file
     *
     *@param aptBook
     *      The appointment book to be dumped to file
     */
    @Override
    public void dump(AbstractAppointmentBook aptBook) throws IOException {

        err = new PrintWriter(System.err, true);

        try {

            File fi = new File(this.fileName);

            Writer writer = new FileWriter(fi,true);

            Collection<Appointment> appointments = aptBook.getAppointments();

            for (Appointment apt : appointments) {
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

            writer.flush();
            writer.close();

        } catch (IOException ex) {
            err.println("\nDirectory Entered Doesn't Seem To Exist\nPlease Try Again With Valid Existing Directory\n ");
            System.exit(1);
        }


    }

}

