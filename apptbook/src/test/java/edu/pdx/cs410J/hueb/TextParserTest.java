package edu.pdx.cs410J.hueb;

import edu.pdx.cs410J.ParserException;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link TextParser} class.
 *
 */
public class TextParserTest {
    String beginTime = "12/11/1985 11:50 am";
    String endTime = "12/11/1986 12:00 pm";


    @Test
    void createParser() throws IOException {

        AppointmentBook ab = new AppointmentBook("Cheryl");
        Appointment appointment = new Appointment("descrip", beginTime, endTime);
        ab.addAppointment(appointment);


        String file = "tsuite.txt";
        TextParser textParser = new TextParser(file);

        assertThat(textParser.fileName, CoreMatchers.is(equalTo(file)));
    }

    @Test
    void createParser2() throws IOException {

        AppointmentBook ab = new AppointmentBook("Cheryl");
        Appointment appointment = new Appointment("descrip", beginTime, endTime);
        ab.addAppointment(appointment);


        String file = "tsuite.txt";
        TextParser textParser = new TextParser(file);

        assertThat(textParser.fileName.isEmpty(), CoreMatchers.is(equalTo(false)));
    }

    @Test
    void parse() {

        AppointmentBook ab = new AppointmentBook();
        String file = "testParse.txt";
        TextParser textParser = new TextParser(file);

        try {
            ab = textParser.parse();
        } catch (ParserException e) {
            e.printStackTrace();
        }

        assertThat(ab.getOwnerName(), is(equalTo(null)));



    }

    @Test
    void parse2() {

        AppointmentBook ab = new AppointmentBook();
        String file = "testParse.txt";
        TextParser textParser = new TextParser(file);

        try {
            ab = textParser.parse();
        } catch (ParserException e) {
            e.printStackTrace();
        }


        assertThat(ab.getAppointments().isEmpty(), CoreMatchers.is(equalTo(true)));
    }

    @Test
    void parse3() {

        AppointmentBook ab = new AppointmentBook();
        String file = "testParse.txt";
        TextParser textParser = new TextParser(file);

        try {
            ab = textParser.parse();
        } catch (ParserException e) {
            e.printStackTrace();
        }

        assertThat(ab.getAppointments().size(), CoreMatchers.is(equalTo(0)));

    }


    @Test
    void parse4() {

        AppointmentBook ab = new AppointmentBook();
        String file = "testParse.txt";
        TextParser textParser = new TextParser(file);

        try {
            ab = textParser.parse();
        } catch (ParserException e) {
            e.printStackTrace();
        }


        Appointment appointment = new Appointment("Slay Some Vampires", beginTime, endTime);
        Collection<Appointment> aptList = ab.getAppointments();
        assertThat(ab.getAppointments().containsAll(aptList), CoreMatchers.is(equalTo(true)));

    }


    @Test
    void createParserWithFile() {
        TextParser textParser = new TextParser("Cheryl");
        assertThat(textParser.fileName, CoreMatchers.is(equalTo("Cheryl")));
    }

    @Test
    void createParserWithFile2() {
        TextParser textParser = new TextParser("Cheryl");
        assertThat(textParser.fileName.isEmpty(), CoreMatchers.is(equalTo(false)));
    }

    @Test
    void createParserWithNOFile() {
        TextParser textParser = new TextParser("");
        assertThat(textParser.fileName, CoreMatchers.is(equalTo("")));;
    }

    @Test
    void createParserWithNOFile2() {
        TextParser textParser = new TextParser("");
        assertThat(textParser.fileName.isEmpty(), CoreMatchers.is(equalTo(true)));
    }

}
