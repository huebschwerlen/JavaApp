package edu.pdx.cs410J.hueb;

import java.util.ArrayList;
import java.util.List;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {

  public static void main(String[] args) {
//    Appointment appointment = new Appointment();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    Appointment apt = new Appointment("dscrip", "btime", "etime");
    Appointment apt2 = new Appointment("dscrip2", "btime2", "etime2");
    AppointmentBook aptBook = new AppointmentBook("Jess");
//    aptBook.addAppointment(apt);

    System.out.println(aptBook.getOwnerName());

//    aptBook.addAppointment(apt2);

    System.out.println(aptBook.getAppointments().isEmpty());

    System.out.println(apt);

    System.err.println("Missing command line arguments");
    for (String arg : args) {
      System.out.println(arg);
    }
    System.exit(1);
  }

}