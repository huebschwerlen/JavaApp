package edu.pdx.cs410J.hueb;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link AppointmentBook} class.
 *
 */
public class AppointmentBookTest {
    String beginTime = "12/11/1985 11:50 am";
    String endTime = "12/11/1986 12:00 pm";

    @Test
    void createAptBookNoOwner() {
        AppointmentBook ab = new AppointmentBook();
        assertThat(ab.getOwnerName(), CoreMatchers.is(nullValue()));
        assertThat(ab.getAppointments().isEmpty(), CoreMatchers.is(equalTo(true)));
    }

    @Test
    void createAptBookWithOwner() {
        AppointmentBook ab = new AppointmentBook("Cheryl");
        assertThat(ab.getOwnerName(), CoreMatchers.is(equalTo("Cheryl")));
        assertThat(ab.getAppointments().isEmpty(), CoreMatchers.is(equalTo(true)));
    }

    @Test
    void createAptBookWithOwnerAddApt() {
        AppointmentBook ab = new AppointmentBook("Cheryl");
        Appointment appointment = new Appointment("descrip", beginTime, endTime);

        assertThat(ab.getOwnerName(), CoreMatchers.is(equalTo("Cheryl")));
        assertThat(ab.getAppointments().isEmpty(), CoreMatchers.is(equalTo(true)));

        ab.addAppointment(appointment);
        assertThat(ab.getAppointments().isEmpty(), CoreMatchers.is(equalTo(false)));

        ArrayList<Appointment> aptList = new ArrayList<>();
        aptList.add(appointment);

        assertThat(ab.getAppointments(), is(equalTo(aptList)));
    }



}
