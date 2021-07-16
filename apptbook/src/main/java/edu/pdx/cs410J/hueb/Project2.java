package edu.pdx.cs410J.hueb;

import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.Collection;

public class Project2 {


        public static final String USAGE_MESSAGE = "\nusage: java -jar target/apptbook-2021.0.0.jar [options] <args>\n\n" +
                "args are (in this order):\n" + "owner \t\t The person who owns the appt book\n" +
                "description \t A description of the appointment\n" + "begin \t\t When the appt begins (24-hour time)\n" +
                "end \t\t When the appt ends (24-hour time)\n" + "\noptions are (options may appear in any order):\n" +
                "-print \t\t Prints a description of the new appointment\n" + "-README \t Prints a README for this project and exits\n"+
                "\n* Date and time should be in the format: mm/dd/yyyy hh:mm\n" +
                "* Owner and Description should be wrapped in quotes\n";

//  public static void main(String[] args) {
//
//
//    String file = "TEST.txt";
//    String fileTo = "TEXT.txt";
//    TextParser tp = new TextParser(file);
//
//
////    File currentDir = new File("");
////    try {
////      System.out.println("\nCurrent Dir: " + currentDir.getCanonicalPath());
////    } catch (IOException e) {
////      e.printStackTrace();
////    }
//
//    File f = new File("apptbook/src/main/resources/edu/pdx/cs410J/hueb/"+file);
//    if (f.exists() && !f.isDirectory()) {
//      System.out.println("\nFile " + file + "WAS FOUND\n");
//    } else {
//      try {
//        f.createNewFile();
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//    }
//
//    //if file exists make sure owner matches
//    if (f.exists() && !f.isDirectory()) {
//      System.out.println("\nFile " + file + "WAS FOUND\n");
//      try {
//        //System.out.println("calling parse");
//        try {
//          AppointmentBook ab = tp.parse();
//          TextDumper td = new TextDumper(file);
//          td.dump(ab);
//        } catch (ParserException e) {
//          e.printStackTrace();
//        } catch (IOException e) {
//          e.printStackTrace();
//        }
//      } finally {
//        System.out.println("\nEND\n");
//      }
//    } else {
//      System.out.println("File not found");
//    }
//  }

        public static void main(String[] args) {
            String filePath = "/Users/sam/Desktop/PortlandStateJavaSummer2021/apptbook/src/main/resources/edu/pdx/cs410J/hueb/";

            String fileTEST = "TEST.txt";

            String absolutePath = new File("").getAbsolutePath();

//    System.out.println("Ab Path: " + absolutePath);

            String filePath2 = absolutePath+"/src/main/resources/edu/pdx/cs410J/hueb/";

            String t1 = filePath+fileTEST;
            String t2 = filePath2+fileTEST;
            if(t1.equals(t2)) {
//      System.out.println("SAME!!!");
            } else {
//                System.out.println("Filepaths are Different!" + "\nfp1: " + t1 + "\nfp2: " + t2);
            }


            filePath = filePath2;




//    TextParser tpp = new TextParser(fileTEST);
//    File fi = new File(filePath+fileTEST);
//    if (!fi.exists()) {
//      try {
//        System.out.println("file does not exist, trying to create");
//        fi.createNewFile();
//      } catch (IOException e) {
//        System.err.println("could not create file this time either");
//        e.printStackTrace();
//      }
//    } else {
//      System.out.println(fileTEST + " seems to exist");
//    }
//
//
//    if (fi.exists() && !fi.isDirectory()) {
//        System.out.println("\nFile " + fileTEST + " WAS FOUND\n");
//        try {
//          AppointmentBook ab = tpp.parse();
//          System.out.println(ab.toString());
//          TextDumper td = new TextDumper(fileTEST);
//          td.dump(ab);
//        } catch (ParserException e) {
//          e.printStackTrace();
//        } catch (IOException e) {
//          e.printStackTrace();
//        }
//    } else {
//      System.out.println("File not found");
//    }









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
            if (args.length >= 6 && args.length <= 9) {


                //if 6 args validate format of end and begin time, then add
                if (args.length == 6) {

                    if (!args[0].equalsIgnoreCase("-print")) {

                        boolean validDateTime6 = (validDate(args[2]) && validTime(args[3]) &&
                                validDate(args[4]) && validTime(args[5]));

                        String beginTime = args[2] + " " + args[3];
                        String endTime = args[4] + " " + args[5];

                        if (validDateTime6) {

                            Appointment apt = new Appointment(args[1], beginTime, endTime);

                            AppointmentBook aptBook = new AppointmentBook(args[0]);

                            aptBook.addAppointment(apt);

                            System.exit(0);

                        } else {
                            //if (validDateTime6)
                            printErrorMessageAndExitWithUsage("6\nInvalid Arguments - Please Check the Formatting of Your Dates and Times\n");
                        }


                    } else {
                        printErrorMessageAndExitWithUsage("6\nInvalid Arguments - Possibly May Have Wrong Combination of Arguments and Options\n");
                    }
                }


                else if (args.length == 7) {
                    //if 7 args see if first one is -print.caseinsensitive
                    //if so set a flag to print description
                    //then validate last and if all good
                    //add apt and print

                    if (args[0].equalsIgnoreCase("-print")) {

                        boolean validDateTime7 = (validDate(args[3]) && validTime(args[4]) &&
                                validDate(args[5]) && validTime(args[6]));

                        String beginTime = args[3] + " " + args[4];
                        String endTime = args[5] + " " + args[6];

                        if (validDateTime7) {
                            Appointment apt = new Appointment(args[2], beginTime, endTime);

                            AppointmentBook aptBook = new AppointmentBook(args[1]);

                            aptBook.addAppointment(apt);

                            System.out.println(apt.toString());
//          System.out.println(aptBook.toString());
//        System.out.println("Owner: " + aptBook.getOwnerName());
//        System.out.println("AptBook Empty: " + aptBook.getAppointments().isEmpty());

                            System.exit(0);
                        } else {
                            //if (validDateTime7)
                            printErrorMessageAndExitWithUsage("7\nInvalid Arguments - Please Check the Formatting of Your Dates and Times\n");
                        }

                    } else {
                        printErrorMessageAndExitWithUsage("7\nInvalid Arguments - Possibly May Have Wrong Combination of Arguments and Options\n");
                    }
                }


                else if (args.length == 8) {

                    if (args[0].equalsIgnoreCase("-textfile")) {


                        boolean validDateTime8 = (validDate(args[4]) && validTime(args[5]) &&
                                validDate(args[6]) && validTime(args[7]));

                        String beginTime = args[4] + " " + args[5];
                        String endTime = args[6] + " " + args[7];



                        if (validDateTime8) {
                            Appointment apt = new Appointment(args[3], beginTime, endTime);

                            AppointmentBook aptBook = new AppointmentBook(args[2]);

                            aptBook.addAppointment(apt);

//        System.out.println(apt.toString());
//        System.out.println(aptBook.toString());
//        System.out.println("Owner: " + aptBook.getOwnerName());
//        System.out.println("AptBook Empty: " + aptBook.getAppointments().isEmpty());

                            String file = args[1];


                            if (!file.contains(".")) {
                                printErrorMessageAndExitWithOutUsage("\nPlease make sure to include file extension with file name.\n"+
                                        "e.g. -textFile fileName.txt\n");
                            }


                            TextParser tp = new TextParser(file);


                            //check if file exists
                            File f = new File(filePath+file);
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
                                            printErrorMessageAndExitWithOutUsage("8\nDUMP Failed\n");
                                        }

                                        //if owners do NOT match exit with error message
                                    } else {
                                        System.err.println("\nCannot Add New Appointment to File. \nOwner Names Do NOT Match\n"+
                                                "Current Appointment Book Owner: " + ab.getOwnerName() + "\nOwner Entered: " + aptBook.getOwnerName() + "\n");
                                    }

                                    //System.out.println("calling tostring on ab");
                                    //System.out.println(ab.toString());
                                    Collection<Appointment> appointments = ab.getAppointments();

                                    for (Appointment apts : appointments) {
                                        //System.out.println("calling tostring on apt");
                                        //System.out.println(apts.toString());
                                    }


                                } catch (ParserException e) {
                                    printErrorMessageAndExitWithOutUsage("8\nParse Failed\n");
                                }
                                //if file does not exist, create file and dump to it
                            }


                            //if file doesnt exist or is directory
                            else {
                                System.out.println("\nFILE " + file + " Not Found\nCreating file: " + file +
                                        " in " + filePath+"\n");

                                File fi = new File(filePath+fileTEST);

                                try {

                                    //make sure filepaths match
                                    if(t1.equals(t2)) {
//                System.out.println("SAME!!!");
                                    } else {
                                        System.err.println("8\nFilepaths Do Not Match\n" + "\nfp1: " + t1 + "\nfp2: " + t2);
                                    }

                                    //create new file
                                    fi.createNewFile();


                                } catch (IOException e) {
                                    printErrorMessageAndExitWithOutUsage("8\nDUMP Failed\n");
                                }


                                try {
                                    //dump aptBook to file
                                    TextDumper td = new TextDumper(file);
                                    td.dump(aptBook);
                                    System.exit(0);

                                } catch (IOException e) {
                                    printErrorMessageAndExitWithOutUsage("8\nDUMP Failed\n");
                                }

                            }

                            System.exit(0);

                        } else {
                            //if (validDateTime8)
                            printErrorMessageAndExitWithUsage("8\nInvalid Arguments - Please Check the Formatting of Your Dates and Times\n");
                        }

                    } else {
                        printErrorMessageAndExitWithUsage("8\nInvalid Arguments - Possibly May Have Wrong Combination of Arguments and Options\n");
                    }
                }


                else if (args.length == 9) {



                    if (args[0].equalsIgnoreCase("-textfile") || args[1].equalsIgnoreCase("-textfile")
                            && args[0].equalsIgnoreCase("-print") || args[2].equalsIgnoreCase("-print")) {

                        boolean validDateTime9 = (validDate(args[5]) && validTime(args[6]) &&
                                validDate(args[7]) && validTime(args[8]));

                        String beginTime = args[5] + " " + args[6];
                        String endTime = args[7] + " " + args[8];

                        if (validDateTime9) {
                            Appointment apt = new Appointment(args[4], beginTime, endTime);

                            AppointmentBook aptBook = new AppointmentBook(args[3]);

                            aptBook.addAppointment(apt);

                            System.out.println(apt.toString());

//        System.out.println(aptBook.toString());
//        System.out.println("Owner: " + aptBook.getOwnerName());
//        System.out.println("AptBook Empty: " + aptBook.getAppointments().isEmpty());


                            //find which arg index -textfile string is at
                            //in order to find position of "file" name string'
                            int fileFlag = 0;
                            if (args[0].equalsIgnoreCase("-textfile")) {
                                fileFlag = 1;
                            } else {
                                fileFlag = 2;
                            }

                            String file = args[fileFlag];

                            if (!file.contains(".")) {
                                printErrorMessageAndExitWithOutUsage("\nPlease make sure to include file extension with file name.\n"+
                                        "e.g. -textFile fileName.txt\n");
                            }

                            TextParser tp = new TextParser(file);

                            //check if file exists
                            File f = new File(filePath+file);

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
                                            System.exit(0);

                                        } catch (IOException e) {
                                            printErrorMessageAndExitWithOutUsage("9\nDUMP Failed\n");
                                        }
                                        //if owners dont match exit with error message
                                    } else {
                                        System.err.println("\nCannot Add New Appointment to File. \nOwner Names Do NOT Match\n"+
                                                "Current Appointment Book Owner: " + ab.getOwnerName() + "\nOwner Entered: " + aptBook.getOwnerName() + "\n");
                                    }

                                    //System.out.println("calling tostring on ab");
                                    //System.out.println(ab.toString());
                                    Collection<Appointment> appointments = ab.getAppointments();

                                    for (Appointment apts : appointments) {
                                        //System.out.println("calling tostring on apt");
                                        //System.out.println(apts.toString());
                                    }
                                } catch (ParserException e) {
                                    printErrorMessageAndExitWithOutUsage("9\nPARSE Failed\n");
                                }


                                //if file does NOT exist, create file and dump to it
                            } else {
                                //CREATE FILE
                                System.out.println("\nFILE " + file + " Not Found\nCreating file: " + file +
                                        " in " + filePath + "\n");

                                File fi = new File(filePath+fileTEST);

                                try {

                                    if(t1.equals(t2)) {
//                  System.out.println("SAME!!!");
                                    } else {
                                        System.out.println("\nFilepaths Are Not the Same" + "\nfp1: " + t1 + "\nfp2: " + t2 + "\n");
                                    }

                                    fi.createNewFile();

                                } catch (IOException e) {
                                    printErrorMessageAndExitWithOutUsage("9\nCreate File Failed\n");
                                }
                                //dump aptBook to file
                                try {

                                    TextDumper td = new TextDumper(file);
                                    td.dump(aptBook);
                                    System.exit(0);

                                } catch (IOException e) {
                                    printErrorMessageAndExitWithOutUsage("9\nDUMP Failed\n");
                                }

                            }

                            System.exit(0);

                        } else {
                            //if (validDateTime9)
                            printErrorMessageAndExitWithUsage("9\nInvalid Arguments - Please Check the Formatting of Your Dates and Times\n");

                        }

                    } else {
                        printErrorMessageAndExitWithUsage("9\nInvalid Arguments - Possibly  Missing Options: -print and/or -textFile file\n");
                    }

                } else {
                    printErrorMessageAndExitWithUsage("9\nInvalid Arguments - Possibly May Have Wrong Combination of Arguments and Options\n");
                }

                //if (args.length >= 6 && args.length <= 9)
            } else {
                //if (args.length >= 6 && args.length <= 9)
                printErrorMessageAndExitWithUsage("9\nInvalid Arguments - Possibly Too Many or Too Few Arguments/Options Entered\n");
            }

            System.exit(0);

        }







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
            InputStream readme = edu.pdx.cs410J.hueb.Project2.class.getResourceAsStream("README.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
            String line = reader.readLine();
            while((line=reader.readLine()) != null){
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
            boolean match = arg.matches("(0?[0-9]|1[0-9]|2[0-3]):(0?[0-9]|[0-5][0-9])");
//    System.out.println("TIME: " + arg + " " + match);
            return match;
        }


























}
