package edu.pdx.cs410J.hueb;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link Appointment} class.
 *
 * You'll need to update these unit tests as you build out your program.
 */
public class AppointmentTest {

  @Test
  void getBeginTimeStringNeedsToBeImplemented() {
    Appointment appointment = new Appointment("descrip", "beginTime", "endTime");
    assertThat(appointment.getBeginTimeString(), is(equalTo("beginTime")));
  }

  @Test
  void getEndTimeStringNeedsToBeImplemented() {
    Appointment appointment = new Appointment("descrip", "beginTime", "endTime");
    assertThat(appointment.getEndTimeString(), is(equalTo("endTime")));
  }

  @Test
  void getDescriptionNeedsToBeImplemented() {
    Appointment appointment = new Appointment("descrip", "beginTime", "endTime");
    assertThat(appointment.getDescription(), is(equalTo("descrip")));
  }

  @Test
  void toStringTest() {
    Appointment appointment = new Appointment("descrip", "beginTime", "endTime");
    assertThat(appointment.toString(), is(equalTo("descrip from beginTime until endTime")));
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
