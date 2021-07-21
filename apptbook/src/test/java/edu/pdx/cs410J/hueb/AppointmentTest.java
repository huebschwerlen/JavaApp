package edu.pdx.cs410J.hueb;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Appointment} class.
 *
 */
public class AppointmentTest {

  String beginTime = "12/11/1985 11:50 am";
  String endTime = "12/11/1986 12:00 pm";

  @Test
  void getBeginTimeStringNeedsToBeImplemented() {
    Appointment appointment = new Appointment("descrip", beginTime, endTime);
//    assertThat(appointment.getBeginTimeString(), is(equalTo("beginTime")));
  }

  @Test
  void getEndTimeStringNeedsToBeImplemented() {
    Appointment appointment = new Appointment("descrip", beginTime, endTime);
//    assertThat(appointment.getEndTimeString(), is(equalTo("endTime")));
  }

  @Test
  void getDescriptionNeedsToBeImplemented() {
    Appointment appointment = new Appointment("descrip", beginTime, endTime);
    assertThat(appointment.getDescription(), is(equalTo("descrip")));
  }

  @Test
  void toStringTest() {
    Appointment appointment = new Appointment("descrip", beginTime, endTime);
//    assertThat(appointment.toString(), is(equalTo("descrip from beginTime until endTime")));
  }

  @Test
  void initiallyAllAppointmentsHaveTheSameDescription() {
    Appointment appointment = new Appointment();
    assertThat(appointment.getDescription(), is(nullValue()));
  }

  @Test
  void forProject1ItIsOkayIfGetBeginTimeReturnsNull() {
    Appointment appointment = new Appointment();
    assertThat(appointment.getBeginTime(), is(nullValue()));
    assertThat(appointment.getEndTime(), is(nullValue()));
    assertThat(appointment.getDescription(), is(nullValue()));
  }

}
