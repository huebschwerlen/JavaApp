package edu.pdx.cs410J.hueb;

import edu.pdx.cs410J.AbstractAppointment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;


public class Appointment extends AbstractAppointment implements Comparable<Appointment> {


    public static final String USAGE_MESSAGE = "\nusage: java -jar target/apptbook-2021.0.0.jar [options] <args>\n\n" +
            "args are (in this order):\n" + "owner \t\t The person who owns the appointment book\n" +
            "description \t A description of the appointment\n" + "begin \t\t When the appointment begins (24-hour time)\n" +
            "end \t\t When the appointment ends (with am/pm)\n" + "\noptions are (options may appear in any order):\n" +
            "-pretty file \t Pretty print the appointment book to\n" +
            "\t\t a text file or standard out (file -)\n" +
            "-textFile file \t Where to read/write the appointment book\n" +
            "-print \t\t Prints a description of the new appointment\n" + "-README \t Prints a README for this project and exits\n" +
            "\n* Date and time should be in the format: mm/dd/yyyy hh:mm am/pm\n" +
            "* Owner and Description should be wrapped in quotes\n" + "* File should be filename with valid extension\n"
            + "* End Time Can Not Be Before Begin Time\n";

    private final String description;
    private final Date beginTime;
    private final Date endTime;

    /**
     * Creates a new <code>Appointment</code>
     */
    public Appointment() {
        this.description = null;
        this.beginTime = null;
        this.endTime = null;
    }

    /**
     * Creates a new <code>Appointment</code>
     *
     * @param description
     *        The description of the appointment
     * @param beginTime
     *        The start or begin time of the appointment.
     * @param endTime
     *        The end time of the appointment.
     */
    public Appointment(String description, String beginTime, String endTime) {
        Date endTime1;
        Date beginTime1;
//    super();
        this.description = description;
        if (description.isEmpty()){
            throw new IllegalArgumentException("Please Enter A Description for Appointment");
        }


        //these date conversions should take place outside of, and before the contructor call
        //and the contructor should just take dates as args, but then i would need to rewrite
        //a lot of tests, so, for this assignment, im going to be a little lazy
        try {
            beginTime1 = new SimpleDateFormat("MM/dd/yyyy hh:mm aa").parse(beginTime);
        } catch (ParseException e) {
            beginTime1 = null;
            throw new IllegalArgumentException("Date/Time Format Error\nUse: mm/dd/yyyy hh:mm am/pm");
//            System.err.println("\nParse of beginTime failed in Appointment Constuctor\n");
//            e.printStackTrace();
        }
        this.beginTime = beginTime1;

        try {
            endTime1 = new SimpleDateFormat("MM/dd/yyyy hh:mm aa").parse(endTime);
        } catch (ParseException e) {
            endTime1 = null;
//            System.err.println("\nParse of endTime failed in Appointment Constuctor\n");
//            e.printStackTrace();
            throw  new IllegalArgumentException("Date/Time Format Error\nUse: mm/dd/yyyy hh:mm am/pm");
        }
        this.endTime = endTime1;


        if(this.endTime.before(this.beginTime)) {
//            System.err.println("\n\nEnd Time Can Not Be Before Begin Time\nPlease Try Again\n\n");
//            System.err.println(USAGE_MESSAGE);
//            System.exit(1);
            throw  new IllegalArgumentException("End Time Before Begin Time");
        }

    }





    /**
     * Returns a <code>String</code> beginTime for this
     * <code>Appointment</code> .
     */
    @Override
    public String getBeginTimeString() {
//    DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        int f = DateFormat.SHORT;
        DateFormat df = DateFormat.getDateTimeInstance(f, f);
        return df.format(this.beginTime);
    }

    /**
     * Returns a <code>String</code> endTime for this
     * <code>Appointment</code> .
     */
    @Override
    public String getEndTimeString() {
//    DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        int f = DateFormat.SHORT;
        DateFormat df = DateFormat.getDateTimeInstance(f,f);
        return df.format(this.endTime);
    }


    /**
     * Returns a <code>Date</code> beginTime for this
     * <code>Appointment</code> .
     */
    @Override
    public Date getBeginTime() {
        return this.beginTime;
    }


    /**
     * Returns a <code>Date</code> endTime for this
     * <code>Appointment</code> .
     */
    @Override
    public Date getEndTime() {
        return this.endTime;
    }


    /**
     * Returns a <code>String</code> description for this
     * <code>Appointment</code> .
     */
    @Override
    public String getDescription() {
        return this.description;
    }


    @Override
    public int compareTo(Appointment a) {
        return Comparator.comparing(Appointment::getBeginTime)
                .thenComparing(Appointment::getEndTime)
                .thenComparing(Appointment::getDescription)
                .compare(this, a);

    }






}
