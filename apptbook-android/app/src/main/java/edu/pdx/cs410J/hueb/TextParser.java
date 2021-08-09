package edu.pdx.cs410J.hueb;


import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TextParser implements AppointmentBookParser<AppointmentBook> {

    private final Reader reader;


    /**
     * Creates a new <code>TextParser</code>
     *
     *@param reader
     *      The name of the reader that is meant to be parsed
     */
    public TextParser(Reader reader) {
        this.reader = reader;
    }


    /**
     * Parses file at fileName and returns
     *      an appointment book with appointments added from
     *      external file
     */
    @Override
    public AppointmentBook parse() throws ParserException {

        BufferedReader br = new BufferedReader(this.reader);

        try {


            String line;
            String owner=null;
            ArrayList<String> data = new ArrayList<>();
            AppointmentBook aptBook = new AppointmentBook();

            while ((line = br.readLine()) != null) {
                data.add(line);
            }

            //keep track of file line num for err reporting
            int count = 0;

            for (String row : data) {
                String[] values = row.split("--");

                //keep track of file line num for err reporting
                count++;

                //make valid number of args
                if(values.length==6) {

                    //owner name of the first line read in
                    //is used to compare all following owner
                    //names
                    if (count==1) {
                        owner = values[0];
                    }

                    //make sure all owner names match owner name from first line in file
                    if (!owner.equalsIgnoreCase(values[0])) {
                        System.err.println("\nSorry, could not complete your request, please try again...\n");
                        System.exit(1);
                    }

                    boolean validDateTime6 = (validDate(values[2]) && validTime(values[3]) &&
                            validDate(values[4]) && validTime(values[5]));

                    //validate date and time formatting
                    if(validDateTime6) {
                        String beginTime = values[2] + " " + values[3];
                        String endTime = values[4] + " " + values[5];

//                        System.out.println("\n\n btime: " + beginTime + " etime: " + endTime);

                        Date beginTime1 = null;
                        Date endTime1 = null;

                        try {
                            beginTime1 = new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(beginTime);
                        } catch (ParseException e) {
                            System.err.println("\nbegin time format failed in text parser\n");
                            e.printStackTrace();
                        }
                        try {
                            endTime1 = new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(endTime);
                        } catch (ParseException e) {
                            System.err.println("\nend time format failed in text parser\n");
                            e.printStackTrace();
                        }

//                        System.out.println("\n\n btime1: " + beginTime1 + " etime1: " + endTime1);

                        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
                        String beginTime2 = df.format(beginTime1);
                        String endTime2 = df.format(endTime1);

//                        System.out.println("\n\n btime2: " + beginTime2 + " etime2: " + endTime2);

                        Appointment apt = new Appointment(values[1], beginTime2, endTime2);
                        aptBook.addAppointment(apt);

                    } else {
                        System.err.println("\nSorry, could not complete your request, please try again...\n");
                        System.exit(1);
                    }
                } else {
                    System.err.println("\nSorry, could not complete your request, please try again...\n");
                    System.exit(1);
                }

            }

            //set owner of aptbook being returned
            aptBook.setOwnerName(owner);

            return aptBook;

        } catch (UnsupportedEncodingException e) {
            System.err.println("\nUnsupportedEncoding found in TextParser\n");
//            System.exit(1);
//            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("\nIOexception found in TextParser\n");
//            System.exit(1);
//            e.printStackTrace();
        }

        return new AppointmentBook();

    }

    // mm/dd/yy
    private static boolean validDate(String arg) {
        boolean match = arg.matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/(\\d{4})");
//    System.out.println("DATE: " + arg + " " + match);
        return match;
    }

    // hh:mm
    private static boolean validTime(String arg) {
        boolean match = arg.matches("(0?[0-9]|1[0-9]|2[0-3]):(0?[0-9]|[0-5][0-9])");
//    System.out.println("TIME: " + arg + " " + match);
        return match;
    }




}