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

  public static final String USAGE_MESSAGE = "usage: java edu.pdx.cs410J.hueb.Project1 [options] <args>";

  public static void main(String[] args) {

    //no command line args to pass first, premade test
    if (args.length == 0) {
      System.err.println("Missing command line arguments");
      System.exit(1);
    }

    //check for readme option
    for (String arg : args) {
      if (arg.equals("-README")) {
        readMeCheck(arg);
      }
    }


    if (args.length >= 3 && args.length <= 4) {
      //if 3 args validate format of end and begin time
      //and that description isnt empty(or not)
      //then add
      System.out.println("3 or 4 args");

      //if 4 args see if first one is -print.caseinsensitive
      //if so set a flag to print description
      //then validate last 3 and if all good
      //add apt and print



    } else {
      printErrorMessageAndExitWithUsage("Invalid Type of Arguments");
    }


  }

  private static void readMeCheck(String arg) {
      try {
        readMe();
      } catch (IOException e) {
        printErrorMessageAndExitWithOutUsage("README.txt File Not Found");
      }
  }

  private static void readMe() throws IOException {
    InputStream readme = Project1.class.getResourceAsStream("README.txt");
    BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
    String line = reader.readLine();
    System.out.println(line);
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

}


//    Appointment appointment = new Appointment();

//    Appointment apt = new Appointment("dscrip", "btime", "etime");

//    Appointment apt2 = new Appointment("dscrip2", "btime2", "etime2");

//    AppointmentBook aptBook = new AppointmentBook("Jess");

//    aptBook.addAppointment(apt);

//    System.out.println(aptBook.getOwnerName());

//    aptBook.addAppointment(apt2);

//    System.out.println(aptBook.getAppointments().isEmpty());

//    System.out.println(apt);
