package edu.pdx.cs410J.hueb;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class TextDumperTest {
    String beginTime = "12/11/1985 11:50 am";
    String endTime = "12/11/1986 12:00 pm";


    @Test
    void dumperDumpsAppointmentBookOwner() throws IOException {
        String owner = "Owner";
        AppointmentBook book = new AppointmentBook(owner);
        Appointment appointment = new Appointment("desc", beginTime, endTime);
        book.addAppointment(appointment);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        TextDumper2 dumper = new TextDumper2(printWriter);
        dumper.dump(book);

        printWriter.flush();

        String dumpedText = stringWriter.toString();
        assertThat(dumpedText, containsString(owner));

    }



}
