package edu.pdx.cs410J.hueb;


import org.junit.jupiter.api.Test;

import java.io.StringWriter;

/**
 * Unit tests for the {@link PrettyPrinter} class.
 *
 */
public class PrettyPrinterTest {

    @Test
    void canInstantiate() {
//        String owner = "Owner";
//        AppointmentBook book = new AppointmentBook(owner);

        StringWriter sw = new StringWriter();
        PrettyPrinter pp = new PrettyPrinter(sw);
    }




}
