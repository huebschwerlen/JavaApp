package edu.pdx.cs410J.hueb;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.ParserException;
import org.hamcrest.CoreMatchers;
//import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
//import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link TextParser} class.
 *
 * You'll need to update these unit tests as you build out your program.
 */
public class TextParserTest {
    

    @Test
    void createParser() throws IOException {

        AppointmentBook ab = new AppointmentBook("Cheryl");
        Appointment appointment = new Appointment("descrip", "beginTime", "endTime");
        ab.addAppointment(appointment);


        String file = "tsuite.txt";
        TextParser textParser = new TextParser(file);

        assertThat(textParser.fileName, CoreMatchers.is(equalTo(file)));
    }

    @Test
    void createParser2() throws IOException {

        AppointmentBook ab = new AppointmentBook("Cheryl");
        Appointment appointment = new Appointment("descrip", "beginTime", "endTime");
        ab.addAppointment(appointment);


        String file = "tsuite.txt";
        TextParser textParser = new TextParser(file);

        assertThat(textParser.fileName.isEmpty(), CoreMatchers.is(equalTo(false)));
    }

    @Test
    @Disabled
    void parse() {

        AppointmentBook ab = new AppointmentBook();
        String file = "testParse.txt";
        TextParser textParser = new TextParser(file);

        try {
            ab = textParser.parse();
        } catch (ParserException e) {
            e.printStackTrace();
        }

        assertThat(ab.getOwnerName(), is(equalTo("Buffy Summers")));



    }

    @Test
    @Disabled
    void parse2() {

        AppointmentBook ab = new AppointmentBook();
        String file = "testParse.txt";
        TextParser textParser = new TextParser(file);

        try {
            ab = textParser.parse();
        } catch (ParserException e) {
            e.printStackTrace();
        }


        assertThat(ab.getAppointments().isEmpty(), CoreMatchers.is(equalTo(false)));
    }

    @Test
    @Disabled
    void parse3() {

        AppointmentBook ab = new AppointmentBook();
        String file = "testParse.txt";
        TextParser textParser = new TextParser(file);

        try {
            ab = textParser.parse();
        } catch (ParserException e) {
            e.printStackTrace();
        }

        assertThat(ab.getAppointments().size(), CoreMatchers.is(equalTo(1)));

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


        Appointment appointment = new Appointment("Slay Some Vampires", "12/11/1990 12:30", "12/11/1990 13:30");
        Collection<Appointment> aptList = ab.getAppointments();
        assertThat(ab.getAppointments().containsAll(aptList), CoreMatchers.is(equalTo(true)));

    }




//    @Rule
//    public final ExpectedException exception = ExpectedException.none();
//    @Test
//    void parseWithNoValidFile() throws ParserException {
//
//        AppointmentBook ab = new AppointmentBook();
//        String file = "testParseFALSE.txt";
//        TextParser textParser = new TextParser(file);
//        exception.expect(FileNotFoundException.class);
//        textParser.parse();
//
//    }


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
