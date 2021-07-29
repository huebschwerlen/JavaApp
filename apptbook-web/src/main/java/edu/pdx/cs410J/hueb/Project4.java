package edu.pdx.cs410J.hueb;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * The main class that parses the command line and communicates with the
 * Appointment Book server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String... args) {

        //no command line args to pass first
        if(args.length == 0) {
            usage(MISSING_ARGS);
        }

        //check for readme option
        for (String arg : args) {
            if (arg.equalsIgnoreCase("-README")) {
                readMeCheck(arg);
            }
        }



////////////////////////////////////////////////////////////////////////////////////
       //check arg lengths
////////////////////////////////////////////////////////////////////////////////////
        if(args.length >= 5 || args.length <= 13) {

            //vars
            String hostName = null;
            String portString = null;
            String owner = null;
            String description = null;
            String beginTime = null;
            String endTime = null;

            //set flags for search and print options
            ArrayList<String> options = new ArrayList<>(Arrays.asList(args));
            boolean search = options.stream().anyMatch("-search"::equalsIgnoreCase);
            boolean print = options.stream().anyMatch("-print"::equalsIgnoreCase);

            //get hostname and port
            int index = 0;
            int hostnameIndex = 0;
            int portIndex = 0;
            for (String o : options) {
                o.toLowerCase();
                if (o.equalsIgnoreCase("-host")) {
                    hostnameIndex = index;
                } else if (o.equalsIgnoreCase("-port")) {
                    portIndex = index;
                }
                System.out.println("\nArg: " + o + " at index: " + index);
                index++;
            }

            System.out.println("\nhost index: " + hostnameIndex + " port index: " + portIndex);
//            int hostnameIndex = options.indexOf("-host");
//            int portIndex = options.indexOf("-port");
            try{
                hostName = args[hostnameIndex + 1];
                portString = args[portIndex + 1];
            } catch (ArrayIndexOutOfBoundsException e) {
                usage("\n5) Please Check Hostname and/or Port");
            }

            //convert portstring into port type int
            int port;
            try {
                port = Integer.parseInt( portString );

            } catch (NumberFormatException ex) {
                usage("\nPort \"" + portString + "\" must be an integer");
                return;
            }



            System.out.println("\n-host: " + hostName + "\n-port: " + port + "\n");
            AppointmentBookRestClient client = new AppointmentBookRestClient(hostName, port);



////////////////////////////////////////////////////////////////////////////////////
            //Only acceptable scenarios :
////////////////////////////////////////////////////////////////////////////////////

            // 5 FIVE ARGS!! (-print makes no sense here as we are not adding new apt)
//        – Pretty print all appointments in an appointment book.

//        $ java -jar target/apptbook-client.jar -host localhost -port 12345 "Dave"
            // owner at args[4]

            if(args.length==5 && !print) {

                owner = args[4];

                try {
                    String message = client.getAppointments(owner);

                    if(message==null || message==""){
                        System.out.println("\nSorry, we could not find any appointments for " + owner + "\n");
                    } else {
                        System.out.println(message);
                    }



                } catch (IOException e) {
                    System.out.println("\nSomething Went Wrong With Finding All Appts For " + owner);
                    e.printStackTrace();
                    System.out.println("\nSomething Went Wrong With Finding All Appts For " + owner);
                }

                //find owners apt books and pretty print all apts

                //or return message is owner is not found

            } else if (args.length==12) {

                if (search && !print) {

                    boolean validDateTime12S = (validDate(args[6]) && validTime(args[7]) && validTimeAmPm(args[8]) &&
                            validDate(args[9]) && validTime(args[10]) && validTimeAmPm(args[11]));

                    if(validDateTime12S) {

                        owner = args[5];

                        beginTime = args[6] + " " + args[7] + " " + args[8];
                        endTime = args[9] + " " + args[10] + " " + args[11];


                        Date beginTime1 = null;
                        try {
                            beginTime1 = new SimpleDateFormat("MM/dd/yyyy hh:mm aa").parse(beginTime);
                        } catch (ParseException e) {
                            System.err.println("\n12S) Parse of Begin Time failed - Please Check your Date/Time Formatting\n");
//                            e.printStackTrace();
                        }

                        Date endTime1 = null;
                        try {
                            endTime1 = new SimpleDateFormat("MM/dd/yyyy hh:mm aa").parse(endTime);
                        } catch (ParseException e) {
                            System.err.println("\n12S) Parse of End Time failed - Please Check your Date/Time Formatting\n");
//                            e.printStackTrace();
                        }

                        if(endTime1.before(beginTime1)) {
                            usage("\n\n12S) End Time Can Not Be Before Begin Time\nPlease Try Again\n\n");
                        }



                        ///////////////////////////////////
                        //IMPLEMENT SEARCH for and PRETTY PRINT
                        // apts that begin between these times
                        ///////////////////////////////////
                        try {
                            String message = client.getAppointmentsByTime(owner, beginTime, endTime);
                            if(message==null || message==""){
                                System.out.println("\nSorry, we could not find any appointments that match your criteria.\n");
                            } else {
                                System.out.println("\nRetrieving appointments beginning" +
                                        "\nat or after: " + beginTime +
                                        "\nand " +
                                        "\nat or before: " + endTime + " \n........\n\n");
                                System.out.println(message);
                            }

                        } catch (IOException e) {
                            System.out.println("\nSomething Went Wrong With Finding All Appts Within Time Range For " + owner);
                            e.printStackTrace();
                            System.out.println("\nSomething Went Wrong With Finding All Appts Within Time Range For " + owner);
                        }




                    } else {
                        usage("\n12S)Please Check your Date/Time Formatting");
                    }


                } else if (!print && !search) {

                    boolean validDateTime12A = (validDate(args[6]) && validTime(args[7]) && validTimeAmPm(args[8]) &&
                            validDate(args[9]) && validTime(args[10]) && validTimeAmPm(args[11]));

                    if(validDateTime12A) {

                        owner = args[4];
                        description = args[5];

                        beginTime = args[6] + " " + args[7] + " " + args[8];
                        endTime = args[9] + " " + args[10] + " " + args[11];

                        Date beginTime1 = null;
                        try {
                            beginTime1 = new SimpleDateFormat("MM/dd/yyyy hh:mm aa").parse(beginTime);
                        } catch (ParseException e) {
                            System.err.println("\n12A) Parse of Begin Time failed - Please Check your Date/Time Formatting\n");
//                            e.printStackTrace();
                        }

                        Date endTime1 = null;
                        try {
                            endTime1 = new SimpleDateFormat("MM/dd/yyyy hh:mm aa").parse(endTime);
                        } catch (ParseException e) {
                            System.err.println("\n12A) Parse of End Time failed - Please Check your Date/Time Formatting\n");
//                            e.printStackTrace();
                        }

                        if(endTime1.before(beginTime1)) {
                            usage("\n\n12A) End Time Can Not Be Before Begin Time\nPlease Try Again\n\n");
                        }


                        ///////////////////////////////////
                        //IMPLEMENT ADD APPT, do NOT print it out
                        ///////////////////////////////////
                        try {
                            client.createAppointment(owner, description, beginTime, endTime);
                            System.out.println("\nCreated appointment. \nUse -print option next time " +
                                    "to see your appointment details after adding it.\n");


//                            Appointment appt = new Appointment(description, beginTime, endTime);
//                            System.out.println("\nTESTING appointment:\n");
//                            System.out.println(appt.toString() + "\n");


                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("\n12A) Something went wrong with adding your appointment\n");
                        }




                    } else {
                        usage("\n12A)Please Check your Date/Time Formatting");
                    }


                } else {
                    usage("\n12) Invalid Arguments");
                }


            } else if (args.length==13) {

                if (print) {

                    boolean validDateTime13 = (validDate(args[7]) && validTime(args[8]) && validTimeAmPm(args[9]) &&
                            validDate(args[10]) && validTime(args[11]) && validTimeAmPm(args[12]));

                    if(validDateTime13) {

                        owner = args[5];
                        description = args[6];

                        beginTime = args[7] + " " + args[8] + " " + args[9];
                        endTime = args[10] + " " + args[11] + " " + args[12];

                        Date beginTime1 = null;
                        try {
                            beginTime1 = new SimpleDateFormat("MM/dd/yyyy hh:mm aa").parse(beginTime);
                        } catch (ParseException e) {
                            System.err.println("\n13) Parse of Begin Time failed - Please Check your Date/Time Formatting\n");
//                            e.printStackTrace();
                        }

                        Date endTime1 = null;
                        try {
                            endTime1 = new SimpleDateFormat("MM/dd/yyyy hh:mm aa").parse(endTime);
                        } catch (ParseException e) {
                            System.err.println("\n13) Parse of End Time failed - Please Check your Date/Time Formatting\n");
//                            e.printStackTrace();
                        }

                        if(endTime1.before(beginTime1)) {
                            usage("\n\n13) End Time Can Not Be Before Begin Time\nPlease Try Again\n\n");
                        }

                        //IMPLEMENT add appt and pretty print new apt
                        try {
                            client.createAppointment(owner, description, beginTime, endTime);
                            Appointment appt = new Appointment(description, beginTime, endTime);
                            System.out.println("\nCreated appointment:\n");
                            System.out.println(appt.toString() + "\n");

                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("\n12A) Something went wrong with adding your appointment\n");
                        }


                    } else {
                        usage("\n13)Please Check your Date/Time Formatting");
                    }

                } else {
                    usage("\n13) Invalid Arguments, may be missing -print");
                }

            } else {
                usage("\n13) Invalid Arguments");
            }






        } else {
            usage("\n0) Invalid Number of Options / Arguments");
        }




//
//
//        String message;
//        try {
//            if (owner == null) {
//                // Print all owner/description pairs
//                Map<String, String> dictionary = client.getAllDictionaryEntries();
//                StringWriter sw = new StringWriter();
//                Messages.formatDictionaryEntries(new PrintWriter(sw, true), dictionary);
//                message = sw.toString();
//
//            } else if (description == null) {
//                // Print all dictionary entries
//                message = Messages.formatDictionaryEntry(owner, client.getAppointments(owner));
//
//            } else {
//                // Post the owner/description pair
//                client.createAppointment(owner, description, beginTime, endTime);
//                message = Messages.definedWordAs(owner, description);
//            }
//
//        } catch ( IOException ex ) {
//            error("While contacting server: " + ex);
//            return;
//        }
//
//        System.out.println(message);

        System.exit(0);
    }




//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


/////////////////////////// HELPERS ///////////////////////////////////

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





    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }



    private static void readMeCheck(String arg) {
        try {
            readMe();
        } catch (IOException e) {
            usage("Problem With README.txt File");
        } catch (NullPointerException n) {
            usage("README.txt File Not Found");
        }
    }

    private static void readMe() throws IOException {
        InputStream readme = edu.pdx.cs410J.hueb.Project4.class.getResourceAsStream("README.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
        String line = reader.readLine();
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
//    System.out.println(line);
        System.exit(0);
    }




    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java edu.pdx.cs410J.<login-id>.Project4 [options] <args>");
        err.println("args are (in this order): ");
        err.println("  owner             The person who owns the appointment book");
        err.println("  description       A description of the appointment");
        err.println("  begin time        When the appointment begins");
        err.println("  end time          When the appointment begins");
        err.println("options are (options may appear in any order): ");
        err.println("  -host hostname    Host computer on which the server runs");
        err.println("  -port port        Port on which the server is listening");
        err.println("  -search           Appointments should be searched for");
        err.println("  -print            Prints a description of the new appointment");
        err.println("  -README           Prints a README for this project and exits");
        err.println();
        err.println("Appointment Book Application:");

        err.println("\n– Add an appointment to the server:");
        err.println("$ java -jar target/apptbook-client.jar -host localhost -port 12345 \\\n" +
                "\"Dave\" \"Teach Java Class\" 10/19/2021 6:00 pm 10/19/2021 9:30 pm");

        err.println("\n– Search for appointments that begin between two times:");
        err.println("$ java -jar target/apptbook-client.jar -host localhost -port 12345 \\\n" +
                "-search \"Dave\" 11/01/2021 12:00 am 11/30/2021 11:59 pm");

        err.println("\n– Print all appointments in an appointment book:");
        err.println("$ java -jar target/apptbook-client.jar -host localhost -port 12345 \"Dave\"");


        err.println("\nDate and time should be in the format: mm/dd/yyyy hh:mm am/pm\n" +
                "End Time Can Not Be Before Begin Time\n" +
                "\n" +
                "Owner and Descriptions should be wrapped in quotes.\n" +
                "\n" +
                "If the owner entered does not exist the application will attempt to\n" +
                "create a new appointment book for owner to save appointments to.\n" +
                "\n" +
                "When using -README option the README.txt file will print to the screen and quit the application.\n" +
                "To use the application to add an appointment, please remove the -README option from the command line.");

        err.println();

        System.exit(1);
    }
}