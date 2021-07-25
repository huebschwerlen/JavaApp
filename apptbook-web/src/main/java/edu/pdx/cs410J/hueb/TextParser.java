package edu.pdx.cs410J.hueb;


import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TextParser implements AppointmentBookParser {


    protected final String fileName;
    private final String absolutePath = new File("").getAbsolutePath();
    private String filePath = absolutePath+"/src/main/resources/edu/pdx/cs410J/hueb/";
//    private String filePath = "/Users/sam/Desktop/PortlandStateJavaSummer2021/apptbook/src/main/resources/edu/pdx/cs410J/hueb/";

    /**
     * Creates a new <code>TextParser</code>
     *
     *@param file
     *      The name of the file that is meant to be parsed
     */
    public TextParser(String file) {
        this.fileName = file;
    }


    /**
     * Parses file at fileName and returns
     *      an appointment book with appointments added from
     *      external file
     */
    @Override
    public AppointmentBook parse() throws ParserException {

        try {
//            BufferedReader br = new BufferedReader(new FileReader(filePath+this.fileName));
            BufferedReader br = new BufferedReader(new FileReader(this.fileName));

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
                        System.err.println("\nLine " + count + " in your external appointment book storage file: " + this.fileName +
                                "\nMay have an owner name that doesn't match other owner names in the file.\n " +
                                "\nRemember each line in your storage file represents an appointment in the format: " +
                                "\nowner--description--begin date<mm/dd/yyyy>--begin time<hh:mm>--end date<mm/dd/yyyy>--end time<hh:mm>" +
                                "\nexample) Buffy Summers--Slay Vampires--07/31/1992--11:30--12/11/2001--16:30"
                                +"\n\nExiting...\n\n");
                        System.exit(1);
                    }

                    boolean validDateTime6 = (validDate(values[2]) && validTime(values[3]) &&
                            validDate(values[4]) && validTime(values[5]));

                    //validate date and time formatting
                    if(validDateTime6) {
                        String beginTime = values[2] + " " + values[3];
                        String endTime = values[4] + " " + values[5];

                        Date beginTime1 = null;
                        Date endTime1 = null;

                        try {
                            beginTime1 = new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(beginTime);
                        } catch (ParseException e) {
                            System.err.println("\nbegin time format failed in text parser\n");
                            e.printStackTrace();
                        }
                        try {
                            endTime1 = new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(beginTime);
                        } catch (ParseException e) {
                            System.err.println("\nend time format failed in text parser\n");
                            e.printStackTrace();
                        }

                        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
                        String beginTime2 = df.format(beginTime1);
                        String endTime2 = df.format(endTime1);

                        Appointment apt = new Appointment(values[1], beginTime2, endTime2);
                        aptBook.addAppointment(apt);

                    } else {
                        System.err.println("\nLine " + count + " in your external appointment book storage file: " + this.fileName + " may be malformatted.\nPlease Check and Try Again"+
                                "\nRemember each line in your storage file represents an appointment in the format:" +
                                "\nowner--description--begin date<mm/dd/yyyy>--begin time<hh:mm>--end date<mm/dd/yyyy>--end time<hh:mm>" +
                                "\nexample) Buffy Summers--Slay Vampires--07/31/1992--11:30--12/11/2001--16:30"
                                +"\nExiting...\n");
                        System.exit(1);
                    }
                } else {
//                    System.out.println("Line from External Storage File May Be Malformatted and was Not Loaded to Apt Book: " + Arrays.toString(values));
                    System.err.println("\nLine " + count + " in your external appointment book storage file: " + this.fileName + " may be malformatted.\nPlease Check and Try Again"+
                            "\nRemember each line in your storage file represents an appointment in the format:" +
                            "\nowner--description--begin date<mm/dd/yyyy>--begin time<hh:mm>--end date<mm/dd/yyyy>--end time<hh:mm>" +
                            "\nexample) Buffy Summers--Slay Vampires--07/31/1992--11:30--12/11/2001--16:30"
                            +"\nExiting...\n");
                    System.exit(1);
                }

            }

            //set owner of aptbook being returned
            aptBook.setOwnerName(owner);

            return aptBook;

        } catch (FileNotFoundException e) {
            System.err.println("FileNotFound: " + this.fileName + "\nPlease try again with new file name\n");
//            System.exit(1);
//            e.printStackTrace();
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