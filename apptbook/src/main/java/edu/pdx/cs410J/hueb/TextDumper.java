package edu.pdx.cs410J.hueb;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

public class TextDumper implements AppointmentBookDumper {

    private final String fileName;
    private static PrintWriter err;
    private String filePath = "/Users/sam/Desktop/PortlandStateJavaSummer2021/apptbook/src/main/resources/edu/pdx/cs410J/hueb/";


    public TextDumper(String file) {
        this.fileName = file;
    }

    @Override
    public void dump(AbstractAppointmentBook aptBook) throws IOException {
        err = new PrintWriter(System.err, true);

        try {
//            Writer writer = new FileWriter(new File(String.valueOf(getClass().getResource(this.fileName))));

            File fi = new File(filePath+this.fileName);

//    File fi = new File("/resources/edu/pdx/cs410J/hueb/"+fileTEST);
//            String absolutePath = fi.getAbsolutePath();
//            String filePath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));

            Writer writer = new FileWriter(fi,true);

//          Writer writer = new FileWriter(new File("apptbook/src/main/resources/edu/pdx/cs410J/hueb/",this.fileName),true);
            Collection<Appointment> appointments = aptBook.getAppointments();

//            writer.write("\n");


            for (Appointment apt : appointments) {
                String beginTime = apt.getBeginTimeString();
                String endTime = apt.getEndTimeString();
                String[] bVals = beginTime.split(" ");
                String[] eVals = endTime.split(" ");



                writer.write(aptBook.getOwnerName());
                writer.write("--");
                writer.write(apt.getDescription());
                writer.write("--");
                writer.write(bVals[0]);
                writer.write("--");
                writer.write(bVals[1]);
                writer.write("--");

                writer.write(eVals[0]);
                writer.write("--");
                writer.write(eVals[1]);


                writer.write("\n");
            }

            writer.flush();
            writer.close();

        } catch (IOException ex) {
            err.println("DUMP FAIL ** " + ex);
        }


    }

//    @Override
//    public void dump(AbstractAppointmentBook abstractAppointmentBook) throws IOException {
//
//    }
}
