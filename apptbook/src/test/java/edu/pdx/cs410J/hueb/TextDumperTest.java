package edu.pdx.cs410J.hueb;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link TextDumper} class.
 *
 */
public class TextDumperTest {
    String beginTime = "12/11/1985 11:50 am";
    String endTime = "12/11/1986 12:00 pm";

    @Test
    void createDumper() throws IOException {

        AppointmentBook ab = new AppointmentBook("Cheryl");
        Appointment appointment = new Appointment("descrip", beginTime, endTime);
        ab.addAppointment(appointment);


        String fileName = "testSuite.txt";
        TextDumper textDumper = new TextDumper(fileName);

        assertThat(textDumper.fileName, CoreMatchers.is(equalTo(fileName)));
        assertThat(textDumper.fileName.isEmpty(), CoreMatchers.is(equalTo(false)));
    }


    @Test
    void createDumperWithFile() {
        TextDumper textDumper = new TextDumper("Cheryl");
        assertThat(textDumper.fileName, CoreMatchers.is(equalTo("Cheryl")));
        assertThat(textDumper.fileName.isEmpty(), CoreMatchers.is(equalTo(false)));
    }

    @Test
    void createDumperWithNOFile() {
        TextDumper textDumper = new TextDumper("");
        assertThat(textDumper.fileName, CoreMatchers.is(equalTo("")));
        assertThat(textDumper.fileName.isEmpty(), CoreMatchers.is(equalTo(true)));
    }





}
