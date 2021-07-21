package edu.pdx.cs410J.hueb;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link PrettyPrinter} class.
 *
 */
public class PrettyPrinterTests {
    String beginTime = "12/11/1985 11:50 am";
    String endTime = "12/11/1986 12:00 pm";

    @Test
    void prettyWithDash() {
        Appointment apt = new Appointment("get stuff done", beginTime, endTime);
        AppointmentBook aptBook = new AppointmentBook("Cheryl");
        aptBook.addAppointment(apt);

        PrettyPrinter prettyPrint = new PrettyPrinter("-");
        try {
            prettyPrint.dump(aptBook);
        } catch (IOException e) {
            System.out.println("\ntext prettyWithDash pretty test failed \n");
//            e.printStackTrace();
        }

        assertThat(prettyPrint.fileName, CoreMatchers.is(equalTo("prettyOut.txt")));
        assertThat(prettyPrint.fileName.isEmpty(), CoreMatchers.is(equalTo(false)));
    }

    @Test
    void prettyWithNODashWithExt() {
        Appointment apt = new Appointment("get stuff done", beginTime, endTime);
        AppointmentBook aptBook = new AppointmentBook("Cheryl");
        aptBook.addAppointment(apt);

        PrettyPrinter prettyPrint = new PrettyPrinter("testPretty.txt");
        try {
            prettyPrint.dump(aptBook);
        } catch (IOException e) {
            System.out.println("\ntext prettyWithDash pretty test failed \n");
//            e.printStackTrace();
        }

        assertThat(prettyPrint.fileName, CoreMatchers.is(equalTo("testPretty.txt")));
        assertThat(prettyPrint.fileName.isEmpty(), CoreMatchers.is(equalTo(false)));
    }

    @Test
    void prettyWithNODashWithNoExt() {
        Appointment apt = new Appointment("get stuff done", beginTime, endTime);
        AppointmentBook aptBook = new AppointmentBook("Cheryl");
        aptBook.addAppointment(apt);

        PrettyPrinter prettyPrint = new PrettyPrinter("testPrettyNoExt");
        try {
            prettyPrint.dump(aptBook);
        } catch (IOException e) {
            System.out.println("\ntext prettyWithDash pretty test failed \n");
//            e.printStackTrace();
        }

        assertThat(prettyPrint.fileName, CoreMatchers.is(equalTo("testPrettyNoExt")));
        assertThat(prettyPrint.fileName.isEmpty(), CoreMatchers.is(equalTo(false)));
    }



    @AfterAll
    public static void cleanUpFile() {
        File f1 = new File("testPretty.txt");
        f1.delete();

        File f2 = new File("testPrettyNoExt");
        f2.delete();

//        File f3 = new File("testFilep3.txt");
//        f3.delete();
    }






    }
