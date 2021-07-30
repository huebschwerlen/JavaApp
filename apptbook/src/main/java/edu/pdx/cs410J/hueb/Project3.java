package edu.pdx.cs410J.hueb;

import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class Project3 {


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


    public static void main(String[] args) {


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //no command line args to pass first, premade test
        if (args.length == 0) {
            printErrorMessageAndExitWithUsage("\nMissing Command Line Arguments\n");
        }


        //check for readme option
        for (String arg : args) {
            if (arg.equalsIgnoreCase("-README")) {
                readMeCheck(arg);
            }
        }

        //if no -README option and args.length is appropriate for possibilities
        if (args.length >= 8 && args.length <= 13) {


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////      ARGS LEN 8             ///////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///only valid option is the 6min args


            //if 6 args validate format of end and begin time, then add
            if (args.length == 8) {

                if (!args[0].equalsIgnoreCase("-print")) {

                    boolean validDateTime8 = (validDate(args[2]) && validTime(args[3]) &&
                            validTimeAmPm(args[4]) && validDate(args[5]) && validTime(args[6]) && validTimeAmPm(args[7]));

                    String beginTime = args[2] + " " + args[3] + " " + args[4];
                    String endTime = args[5] + " " + args[6] + " " + args[7];

                    if (validDateTime8) {

                        Appointment apt = new Appointment(args[1], beginTime, endTime);

                        AppointmentBook aptBook = new AppointmentBook(args[0]);

                        aptBook.addAppointment(apt);

                        System.exit(0);

                    } else {
                        //if (validDateTime8)
                        printErrorMessageAndExitWithUsage("8\nInvalid Arguments - Please Check the Formatting of Your Dates and Times\n");
                    }


                } else {
                    printErrorMessageAndExitWithUsage("8\nInvalid Arguments - Possibly May Have Wrong Number/Combination of Arguments and Options\n");
                }
            }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////      ARGS LEN 9             ///////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///only valid option is -print + 6min args

            else if (args.length == 9) {

                //if 7 args see if first one is -print.caseinsensitive
                //if so set a flag to print description
                //then validate last and if all good
                //add apt and print

                if (args[0].equalsIgnoreCase("-print")) {

                    boolean validDateTime9 = (validDate(args[3]) && validTime(args[4]) && validTimeAmPm(args[5]) &&
                            validDate(args[6]) && validTime(args[7]) && validTimeAmPm(args[8]));

                    String beginTime = args[3] + " " + args[4] + " " + args[5];
                    String endTime = args[6] + " " + args[7] + " " + args[8];

                    if (validDateTime9) {
                        Appointment apt = new Appointment(args[2], beginTime, endTime);

                        AppointmentBook aptBook = new AppointmentBook(args[1]);

                        aptBook.addAppointment(apt);

                        System.out.println("\n" + apt.toString() + "\n");

                        System.exit(0);
                    } else {
                        //if (validDateTime7)
                        printErrorMessageAndExitWithUsage("9\nInvalid Arguments - Please Check the Formatting of Your Dates and Times\n");
                    }

                } else {
                    printErrorMessageAndExitWithUsage("9\nInvalid Arguments - Possibly May Have Wrong Number/Combination of Arguments and Options\n");
                }
            }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////      ARGS LEN 10            ///////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            else if (args.length == 10) {

                if (args[0].equalsIgnoreCase("-textfile") || args[0].equalsIgnoreCase("-pretty")) {

                    boolean validDateTime10 = (validDate(args[4]) && validTime(args[5]) && validTimeAmPm(args[6]) &&
                            validDate(args[7]) && validTime(args[8]) && validTimeAmPm(args[9]));

                    String beginTime = args[4] + " " + args[5] + " " + args[6];
                    String endTime = args[7] + " " + args[8] + " " + args[9];

                    if (validDateTime10 && args[0].equalsIgnoreCase("-textfile")) {

                        Appointment apt = new Appointment(args[3], beginTime, endTime);

                        AppointmentBook aptBook = new AppointmentBook(args[2]);

                        aptBook.addAppointment(apt);

                        String file = args[1];

                        TextParser tp = new TextParser(file);

                        //check if file exists
                        File f = new File(file);

                        //if file exists make sure owner matches
                        if (f.exists() && !f.isDirectory()) {
//              System.out.println("\nFile " + file + " WAS FOUND\n");

                            try {

                                //System.out.println("calling parse");
                                AppointmentBook ab = tp.parse();

                                //make sure owner names match
                                if (ab.getOwnerName().equalsIgnoreCase(aptBook.getOwnerName())) {

                                    //dump aptBook to file
                                    try {

                                        TextDumper td = new TextDumper(file);
                                        td.dump(aptBook);

                                    } catch (IOException e) {
                                        printErrorMessageAndExitWithOutUsage("10\nDUMP Failed\n");
                                    }

                                    //if owners do NOT match exit with error message
                                } else {
                                    System.err.println("\nCannot Add New Appointment to File. \nOwner Names Do NOT Match\n" +
                                            "Current Appointment Book Owner: " + ab.getOwnerName() + "\nOwner Entered: " + aptBook.getOwnerName() + "\n");
                                }

                                Collection<Appointment> appointments = ab.getAppointments();

                            } catch (ParserException e) {
                                printErrorMessageAndExitWithOutUsage("10\nParse Failed\n");
                            }
                            //if file does not exist, create file and dump to it
                        }

                        //if file doesnt exist or is directory
                        else {
                            System.out.println("\nFILE " + file + " Not Found\nAttempting to create file: " + file + "\n");

                            try {

                                //dump aptBook to file
                                TextDumper td = new TextDumper(file);
                                td.dump(aptBook);
                                System.exit(0);

                            } catch (IOException e) {
                                printErrorMessageAndExitWithOutUsage("10\nFile DUMP Failed\n");
                            }

                        }

                        System.exit(0);

                    } else if (validDateTime10 && args[0].equalsIgnoreCase("-pretty")) {

                        Appointment apt = new Appointment(args[3], beginTime, endTime);

                        AppointmentBook aptBook = new AppointmentBook(args[2]);

                        aptBook.addAppointment(apt);

                        String file = args[1];

                        PrettyPrinter prettyPrinter10 = new PrettyPrinter(file);

                        try {
                            prettyPrinter10.dump(aptBook);
                        } catch (IOException e) {
                            printErrorMessageAndExitWithOutUsage("10\nPretty DUMP Failed\n");
//                            e.printStackTrace();
                        }


                    } else {
                        //if (validDateTime8)
                        printErrorMessageAndExitWithUsage("10\nInvalid Arguments - Please Check the Formatting of Your Dates and Times\n");
                    }

                } else {
                    printErrorMessageAndExitWithUsage("10\nInvalid Arguments - Possibly May Have Wrong Combination of Arguments and Options\n");
                }
            }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////      ARGS LEN 11            ///////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ////only valid option are:    -textfile file -print + 8min args         ||        -print -textfile file + 8min args
            ////only valid option are:    -pretty file -print + 8min args         ||        -print -pretty -  + 8min args


            else if (args.length == 11) {

                boolean txtFileorPretty = (args[0].equalsIgnoreCase("-textfile") || args[1].equalsIgnoreCase("-textfile") ||
                        (args[0].equalsIgnoreCase("-pretty") || (args[1].equalsIgnoreCase("-pretty"))));

                if (txtFileorPretty && (args[0].equalsIgnoreCase("-print") || args[2].equalsIgnoreCase("-print"))) {

                    boolean validDateTime11 = (validDate(args[5]) && validTime(args[6]) && validTimeAmPm(args[7]) &&
                            validDate(args[8]) && validTime(args[9]) && validTimeAmPm(args[10]));

                    String beginTime = args[5] + " " + args[6] + " " + args[7];
                    String endTime = args[8] + " " + args[9] + " " + args[10];

                    if (validDateTime11 && (args[0].equalsIgnoreCase("-textfile") || args[1].equalsIgnoreCase("-textfile"))) {
//                        System.out.println("\nGOT HERE 1\n");
                        Appointment apt = new Appointment(args[4], beginTime, endTime);
//                        System.out.println("\nGOT HERE 2\n");
                        AppointmentBook aptBook = new AppointmentBook(args[3]);
//                        System.out.println("\nGOT HERE 3\n");
                        aptBook.addAppointment(apt);
//                        System.out.println("\nGOT HERE 4\n");

                        //find which arg index -textfile string is at
                        //in order to find position of "file" name string'
                        int fileFlag = 0;
                        if (args[0].equalsIgnoreCase("-textfile")) {
                            fileFlag = 1;
                        } else {
                            fileFlag = 2;
                        }

                        String file = args[fileFlag];

                        TextParser tp = new TextParser(file);

                        //check if file exists
                        File f = new File(file);

                        //if file exists make sure owner matches
                        if (f.exists() && !f.isDirectory()) {

                            try {
                                //System.out.println("calling parse");
//                                System.out.println("\nGOT HERE 5\n");

                                AppointmentBook ab = tp.parse();

//                                System.out.println("\nGOT HERE 6\n");
//                                System.out.println("AB owner: "+ab.getOwnerName() + " aptBook owner: "+aptBook.getOwnerName());

                                //make sure owner names match
                                if (ab.getOwnerName().equalsIgnoreCase(aptBook.getOwnerName())) {
                                    //dump aptBook to file
                                    try {

                                        TextDumper td = new TextDumper(file);
//                                        System.out.println("\nGOT HERE 7\n");
                                        td.dump(aptBook);
//                                        System.out.println("\nGOT HERE 8\n");

                                        System.out.println("\n" + apt.toString() + "\n");
                                        System.exit(0);

                                    } catch (IOException e) {
                                        printErrorMessageAndExitWithOutUsage("\n11) DUMP Failed\n");
                                    }
                                    //if owners dont match exit with error message
                                } else {
                                    System.err.println("\nCannot Add New Appointment to File. \nOwner Names Do NOT Match\n" +
                                            "Current Appointment Book Owner: " + ab.getOwnerName() + "\nOwner Entered: " + aptBook.getOwnerName() + "\n");
                                }

                                Collection<Appointment> appointments = ab.getAppointments();

                            } catch (ParserException e) {
                                printErrorMessageAndExitWithOutUsage("\n11)PARSE Failed\n");
                            }


                            //if file does NOT exist, create file and dump to it
                        } else {
                            //CREATE FILE
                            System.out.println("\nFILE " + file + " Not Found\nAttempting to create file: " + file + "\n");


                            //dump aptBook to file
                            try {
//                                System.out.println("\nGOT HERE 9\n");
                                TextDumper td = new TextDumper(file);
//                                System.out.println("\nGOT HERE 10\n");
                                td.dump(aptBook);

//                                System.out.println("\nGOT HERE 11\n");

                                System.out.println("\n" + apt.toString() + "\n");
                                System.exit(0);

                            } catch (IOException e) {
                                printErrorMessageAndExitWithOutUsage("\n11) DUMP Failed\n");
                            }

                        }

                        System.exit(0);

                    } else if (validDateTime11 && (args[0].equalsIgnoreCase("-pretty") || args[1].equalsIgnoreCase("-pretty"))) {

                        Appointment apt = new Appointment(args[4], beginTime, endTime);

                        AppointmentBook aptBook = new AppointmentBook(args[3]);

                        aptBook.addAppointment(apt);

                        //find which arg index -pretty string is at
                        //in order to find position of "file" name string'
                        int fileFlag = 0;
                        if (args[0].equalsIgnoreCase("-pretty")) {
                            fileFlag = 1;
                        } else {
                            fileFlag = 2;
                        }

                        String file = args[fileFlag];

                        PrettyPrinter prettyPrinter11 = new PrettyPrinter(file);

                        try {
                            prettyPrinter11.dump(aptBook);
                        } catch (IOException e) {
                            printErrorMessageAndExitWithOutUsage("11\nPretty DUMP Failed\n");
//                            e.printStackTrace();
                        }

                        System.out.println(apt.toString());

                    } else {
                        //if (validDateTime11)
                        printErrorMessageAndExitWithUsage("\n11) Invalid Arguments - Please Check the Formatting of Your Dates and Times\n");

                    }

                } else {
                    printErrorMessageAndExitWithUsage("\n11) Invalid Arguments - Possibly  Missing or Malformed Options: -print and/or -textFile file\n");
                }

            } else if (args.length == 12) {

                ArrayList<String> options = new ArrayList<>(Arrays.asList(args));
                boolean textFileOptionTrue = options.stream().anyMatch("-textfile"::equalsIgnoreCase);
                boolean prettyOptionTrue = options.stream().anyMatch("-pretty"::equalsIgnoreCase);
                boolean printOptionFalse = options.stream().anyMatch("-print"::equalsIgnoreCase);

                if ((!printOptionFalse) && textFileOptionTrue && prettyOptionTrue) {

                    boolean validDateTime12 = (validDate(args[6]) && validTime(args[7]) && validTimeAmPm(args[8]) &&
                            validDate(args[9]) && validTime(args[10]) && validTimeAmPm(args[11]));

                    String beginTime = args[6] + " " + args[7] + " " + args[8];
                    String endTime = args[9] + " " + args[10] + " " + args[11];

                    if (validDateTime12) {

                        for (String o : options) {
                            o.toLowerCase();
                        }
                        int textFileIndex = options.indexOf("-textfile");
                        int prettyIndex = options.indexOf("-pretty");

                        String textFileFile = args[textFileIndex + 1];
                        String prettyFileFile = args[prettyIndex + 1];



                        //1. Parse textFileFile to new aptBook
                        Appointment apt = new Appointment(args[5], beginTime, endTime);

                        AppointmentBook aptBook = new AppointmentBook(args[4]);

                        aptBook.addAppointment(apt);

                        TextParser tp = new TextParser(textFileFile);

                        //check if file exists
                        File f = new File(textFileFile);

                        //if file exists make sure owner matches
                        if (f.exists() && !f.isDirectory()) {

                            try {

                                AppointmentBook ab = tp.parse();

                                //make sure owner names match
                                if (ab.getOwnerName().equalsIgnoreCase(aptBook.getOwnerName())) {
                                    //dump aptBook to file
                                    try {

                                        TextDumper td = new TextDumper(textFileFile);
                                        td.dump(aptBook);


                                        System.out.println("\n...pretty printing now...\n");

                                        ab.addAppointment(apt);
                                        PrettyPrinter prettyPrinterExistingFile = new PrettyPrinter(prettyFileFile);
                                        prettyPrinterExistingFile.dump(ab);

                                        System.exit(0);

                                    } catch (IOException e) {
                                        printErrorMessageAndExitWithOutUsage("\n12) DUMP Failed\n");
                                    }
                                    //if owners dont match exit with error message
                                } else {
                                    System.err.println("\nCannot Add New Appointment to File. \nOwner Names Do NOT Match\n" +
                                            "Current Appointment Book Owner: " + ab.getOwnerName() + "\nOwner Entered: " + aptBook.getOwnerName() + "\n");
                                }

                                Collection<Appointment> appointments = ab.getAppointments();

                            } catch (ParserException e) {
                                printErrorMessageAndExitWithOutUsage("\n12)PARSE Failed\n");
                            }


                            //if file does NOT exist, create file and dump to it
                        } else {
                            //CREATE FILE
                            System.out.println("\nFILE " + textFileFile + " Not Found\nAttempting to create file: " + textFileFile + "\n");


                            //dump aptBook to file
                            try {
                                TextDumper td = new TextDumper(textFileFile);
                                td.dump(aptBook);


                                System.out.println("\n...pretty printing now...\n");

                                PrettyPrinter prettyPrinterExistingFile = new PrettyPrinter(prettyFileFile);
                                prettyPrinterExistingFile.dump(aptBook);

                                System.exit(0);

                            } catch (IOException e) {
                                printErrorMessageAndExitWithOutUsage("\n12) DUMP Failed\n");
                            }

                        }

                        System.exit(0);

                    } else {
                        printErrorMessageAndExitWithUsage("\n12) Invalid Arguments - Please Check the Formatting of Your Dates and Times\n");
                    }
                } else {
                    printErrorMessageAndExitWithUsage("\n12) Invalid Arguments - Possibly May Have Wrong Combination of Arguments and Options\n");
                }

            } else if (args.length == 13) {

                //for correct options with 13 args -print needs to be in index 0 || 2 || 4
                boolean printOptionTrue = (args[0].equalsIgnoreCase("-print") || args[2].equalsIgnoreCase("-print")
                        || args[4].equalsIgnoreCase("-print"));

                ArrayList<String> options = new ArrayList<>(Arrays.asList(args));
                boolean textFileOptionTrue = options.stream().anyMatch("-textfile"::equalsIgnoreCase);
                boolean prettyOptionTrue = options.stream().anyMatch("-pretty"::equalsIgnoreCase);

                if (printOptionTrue && textFileOptionTrue && prettyOptionTrue) {

                    boolean validDateTime13 = (validDate(args[7]) && validTime(args[8]) && validTimeAmPm(args[9]) &&
                            validDate(args[10]) && validTime(args[11]) && validTimeAmPm(args[12]));

                    String beginTime = args[7] + " " + args[8] + " " + args[9];
                    String endTime = args[10] + " " + args[11] + " " + args[12];

                    if (validDateTime13) {

                        for (String o : options) {
                            o.toLowerCase();
                        }
                        int textFileIndex = options.indexOf("-textfile");
                        int prettyIndex = options.indexOf("-pretty");

                        String textFileFile = args[textFileIndex + 1];
                        String prettyFileFile = args[prettyIndex + 1];

//                        System.out.println("\n-textfile is at index: " + textFileIndex + "\n-pretty is at index: " +
//                                prettyIndex + "\ntext file's file is: " + textFileFile + "\nand, pretty's file is: " +
//                                prettyFileFile + "\n");

                        //1. Parse textFileFile to new aptBook
                        Appointment apt = new Appointment(args[6], beginTime, endTime);

                        AppointmentBook aptBook = new AppointmentBook(args[5]);

                        aptBook.addAppointment(apt);

                        TextParser tp = new TextParser(textFileFile);

                        //check if file exists
                        File f = new File(textFileFile);

                        //if file exists make sure owner matches
                        if (f.exists() && !f.isDirectory()) {

                            try {

                                AppointmentBook ab = tp.parse();

                                //make sure owner names match
                                if (ab.getOwnerName().equalsIgnoreCase(aptBook.getOwnerName())) {
                                    //dump aptBook to file
                                    try {

                                        TextDumper td = new TextDumper(textFileFile);
                                        td.dump(aptBook);

                                        System.out.println("\nNewly Added Appointment:");
                                        System.out.println("\n" + apt.toString() + "\n\n");
                                        System.out.println("...pretty printing now...\n");

                                        ab.addAppointment(apt);
                                        PrettyPrinter prettyPrinterExistingFile = new PrettyPrinter(prettyFileFile);
                                        prettyPrinterExistingFile.dump(ab);

                                        System.exit(0);

                                    } catch (IOException e) {
                                        printErrorMessageAndExitWithOutUsage("\n13) DUMP Failed\n");
                                    }
                                    //if owners dont match exit with error message
                                } else {
                                    System.err.println("\nCannot Add New Appointment to File. \nOwner Names Do NOT Match\n" +
                                            "Current Appointment Book Owner: " + ab.getOwnerName() + "\nOwner Entered: " + aptBook.getOwnerName() + "\n");
                                }

                                Collection<Appointment> appointments = ab.getAppointments();

                            } catch (ParserException e) {
                                printErrorMessageAndExitWithOutUsage("\n13)PARSE Failed\n");
                            }


                            //if file does NOT exist, create file and dump to it
                        } else {
                            //CREATE FILE
                            System.out.println("\nFILE " + textFileFile + " Not Found\nAttempting to create file: " + textFileFile + "\n");


                            //dump aptBook to file
                            try {
                                TextDumper td = new TextDumper(textFileFile);
                                td.dump(aptBook);

                                System.out.println("\nNewly Added Appointment:");
                                System.out.println("\n" + apt.toString() + "\n\n");
                                System.out.println("...pretty printing now...\n");

                                PrettyPrinter prettyPrinterExistingFile = new PrettyPrinter(prettyFileFile);
                                prettyPrinterExistingFile.dump(aptBook);

                                System.exit(0);

                            } catch (IOException e) {
                                printErrorMessageAndExitWithOutUsage("\n13) DUMP Failed\n");
                            }

                        }

                        System.exit(0);

                    } else {
                        printErrorMessageAndExitWithUsage("\n13) Invalid Arguments - Please Check the Formatting of Your Dates and Times\n");
                    }
                } else {
                    printErrorMessageAndExitWithUsage("\n13) Invalid Arguments - Possibly May Have Wrong Combination of Arguments and Options\n");
                }
            } else {
                printErrorMessageAndExitWithUsage("\n13) Invalid Arguments - Possibly May Have Wrong Combination of Arguments and Options\n");
            }

            //if (args.length >= 8 && args.length <= 13)
        } else {
            //if (args.length >= 8 && args.length <= 13)
            printErrorMessageAndExitWithUsage("\n13) Invalid Arguments - Possibly Too Many or Too Few Arguments/Options Entered\n");
        }

        System.exit(0);


    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


/////////////////////////// HELPERS ///////////////////////////////////

    private static void readMeCheck(String arg) {
        try {
            readMe();
        } catch (IOException e) {
            printErrorMessageAndExitWithOutUsage("Problem With README.txt File");
        } catch (NullPointerException n) {
            printErrorMessageAndExitWithOutUsage("README.txt File Not Found");
        }
    }

    private static void readMe() throws IOException {
        InputStream readme = edu.pdx.cs410J.hueb.Project3.class.getResourceAsStream("README.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
        String line = reader.readLine();
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
//    System.out.println(line);
        System.exit(0);
    }

    protected static void printErrorMessageAndExitWithUsage(String message) {
        System.err.println(message);
        System.err.println(USAGE_MESSAGE);
        System.exit(1);
    }

    private static void printErrorMessageAndExitWithOutUsage(String message) {
        System.err.println(message);
        System.exit(1);
    }


    /////////////////////  MOVE TO STUDENT CLASS FOR PROJ 2 //////////////////////

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
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
