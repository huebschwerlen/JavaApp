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

    protected String fileName;
    private static PrintWriter err;


    /**
     * Creates a new <code>PrettyPrinter</code>
     *
     *@param file
     *     The name of the file to dump to
     */
    public PrettyPrinter(String file) {
        this.fileName = file;
    }




    /**
     * pretty print dumps an aptBook to file or standard out if file is: ' - '
     *
     *@param aptBook
     *      The appointment book to be dumped to file
     */
    @Override
    public void dump(AbstractAppointmentBook aptBook) throws IOException {

            err = new PrintWriter(System.err, true);

            try {

                File fi;

                if(this.fileName.equalsIgnoreCase("-")){

                    this.fileName = "prettyOut.txt";

                }

                fi = new File(this.fileName);

                Writer writer = new FileWriter(fi);

                PrintWriter printWriter = new PrintWriter(writer);

                Collection<Appointment> appointments = aptBook.getAppointments();

                SortedSet<Appointment> sortedSet = new TreeSet<>();

                for (Appointment a : appointments) {
                    sortedSet.add(a);
                }

                writer.write(aptBook.getOwnerName() + "'s Appointment Book: \n");

                for (Appointment apt : sortedSet) {


                    Long duration = getDateDiff(apt.getBeginTime(), apt.getEndTime(), TimeUnit.MINUTES);

                    writer.write("\n");

//                    writer.write("\n"+apt.toString());

                    printWriter.printf("%-5s\n%-5s %3s %3s", apt.toString(), "Duration:", duration, "minutes");

//                    writer.write(" Duration: " + duration + " minutes");

                    writer.write("\n");
                }

                writer.flush();
                writer.close();

                if(this.fileName.equalsIgnoreCase("prettyOut.txt")) {
                    BufferedReader br = new BufferedReader(new FileReader(this.fileName));
                    String line;
                    System.out.println("\n");
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                    System.out.println("\n");

                    if(fi.delete()) {
//                        System.out.println("Deleted file");
                    } else {
//                        System.out.println("did NOT Delete file");
                    }
                }

            } catch (IOException ex) {
                err.println("\nDirectory Entered Doesn't Seem To Exist\nPlease Try Again With Valid Existing Directory\n ");
                System.exit(1);
            }

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


