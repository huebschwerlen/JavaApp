package edu.pdx.cs410J.hueb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {

  public static final String USAGE_MESSAGE = "usage: java -jar target/apptbook-2021.0.0.jar [options] <args>\n" +
          "args are (in this order):\n" + "owner \t The person who owns the appt book\n" +
          "description \t A description of the appointment\n" + "begin \t When the appt begins (24-hour time)\n" +
          "end \t When the appt ends (24-hour time)\n" + "options are (options may appear in any order):\n" +
          "-print \t Prints a description of the new appointment\n" + "-README \t Prints a README for this project and exits\n"+
          "* Date and time should be in the format: mm/dd/yyyy hh:mm\n" +
          "* Owner and Description should be wrapped in quotes\n";

  public static void main(String[] args) {

    //no command line args to pass first, premade test
    if (args.length == 0) {
      System.err.println("Missing command line arguments");
      System.exit(1);
    }

    //check for readme option
    for (String arg : args) {
      if (arg.equalsIgnoreCase("-README")) {
        readMeCheck(arg);
      }
    }

    //if no -README option and args.length is appropriate for possibilities
    if (args.length >= 6 && args.length <= 7) {
      //if 6 args validate format of end and begin time
      //and that description isnt empty(or not)
      //then add
      if (args.length == 6 && !args[0].equalsIgnoreCase("-print")) {
        boolean validDateTime6 = (validDate(args[2]) && validTime(args[3]) &&
                validDate(args[4]) && validTime(args[5]));

        String beginTime = args[2] + " " + args[3];
        String endTime = args[4] + " " + args[5];

        if (validDateTime6) {
          Appointment appointment = new Appointment();

          Appointment apt = new Appointment(args[1], beginTime, endTime);

          AppointmentBook aptBook = new AppointmentBook(args[0]);

          aptBook.addAppointment(apt);

          System.exit(0);

        } else {
          printErrorMessageAndExitWithUsage("Invalid Arguments");
        }
      }
      else if (args.length == 7 && args[0].equalsIgnoreCase("-print")) {
        //if 7 args see if first one is -print.caseinsensitive
        //if so set a flag to print description
        //then validate last and if all good
        //add apt and print

        boolean validDateTime7 = (validDate(args[3]) && validTime(args[4]) &&
                validDate(args[5]) && validTime(args[6]));

        String beginTime = args[3] + " " + args[4];
        String endTime = args[5] + " " + args[6];

        if (validDateTime7) {
          Appointment apt = new Appointment(args[2], beginTime, endTime);

          AppointmentBook aptBook = new AppointmentBook(args[1]);

          aptBook.addAppointment(apt);

          System.out.println(apt.toString());
//        System.out.println("Owner: " + aptBook.getOwnerName());
//        System.out.println("AptBook Empty: " + aptBook.getAppointments().isEmpty());

          System.exit(0);
        } else {
          printErrorMessageAndExitWithUsage("Invalid Arguments");
        }
      } else {
        printErrorMessageAndExitWithUsage("Invalid Arguments");
      }
    } else {
      printErrorMessageAndExitWithUsage("Invalid Arguments");
    }


  }


///////////////////////////// HELPERS ///////////////////////////////////

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
    InputStream readme = Project1.class.getResourceAsStream("README.txt");
    BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
    String line = reader.readLine();
    while((line=reader.readLine()) != null){
      System.out.println(line);
    }
//    System.out.println(line);
    System.exit(0);
  }

  private static void printErrorMessageAndExitWithUsage(String message) {
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



