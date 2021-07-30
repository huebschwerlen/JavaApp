package edu.pdx.cs410J.hueb;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.*;
import java.util.Collection;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

public class PrettyPrinter implements AppointmentBookDumper {

    private final Writer writer;


    /**
     * Creates a new <code>PrettyPrinter</code>
     *
     *@param writer
     *     The name of the writer to write to
     */
    public PrettyPrinter(Writer writer) {
        this.writer = writer;
    }





    /**
     * pretty print dumps an aptBook to file or standard out if file is: ' - '
     *
     *@param aptBook
     *      The appointment book to be dumped to file
     */
    @Override
    public void dump(AbstractAppointmentBook aptBook) throws IOException {

            PrintWriter printWriter = new PrintWriter(this.writer);


            ////SORTING///////
            Collection<Appointment> appointments = aptBook.getAppointments();

            SortedSet<Appointment> sortedSet = new TreeSet<>();

            for (Appointment a : appointments) {
                sortedSet.add(a);
            }

            printWriter.println("\nAppointments for " + aptBook.getOwnerName() + ":");

            for (Appointment apt : sortedSet) {


                Long duration = getDateDiff(apt.getBeginTime(), apt.getEndTime(), TimeUnit.MINUTES);

                writer.write("\n");


                printWriter.printf("%-5s\n%-5s %3s %3s", apt.toString(), "Duration:", duration, "minutes");


                writer.write("\n");
            }

           writer.write("\n\n");

            writer.flush();
            writer.close();
    }

    /**
     * Get a diff between two dates
     * @param date1 the oldest date
     * @param date2 the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     */
    //Source: https://stackoverflow.com/questions/1555262/calculating-the-difference-between-two-java-date-instances
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }

}







