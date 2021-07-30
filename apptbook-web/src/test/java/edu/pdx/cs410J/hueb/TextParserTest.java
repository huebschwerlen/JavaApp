package edu.pdx.cs410J.hueb;


import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;



public class TextParserTest {

    String beginTime = "12/11/1985 11:50 am";
    String endTime = "12/11/1986 12:00 pm";


    @Test
    void canInstan() throws ParserException {
        String text = "owner--desc--12/11/1985--11:00--12/11/1985--12:00";

        TextParser textParser = new TextParser(new StringReader(text));
        AppointmentBook appointmentBook = textParser.parse();

        assertThat(appointmentBook.getOwnerName(), containsString("owner"));

    }

    @Test
    void appointmentBookOwnerCanBeDumpedAndParsed() throws IOException, ParserException {
        String owner = "owner";
        AppointmentBook book = new AppointmentBook(owner);
        Appointment appointment = new Appointment("desc", beginTime, endTime);
        book.addAppointment(appointment);

        StringWriter sw = new StringWriter();
        PrintWriter printWriter = new PrintWriter(sw);
        TextDumper2 dumper = new TextDumper2(printWriter);
        dumper.dump(book);

        TextParser parser = new TextParser(new StringReader(sw.toString()));
        book = parser.parse();

        assertThat(book.getOwnerName(), containsString("owner"));
    }



}
