package edu.pdx.cs410J.hueb;

///////// owner was 'word'   == =  description was 'description'

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link AppointmentBookServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class AppointmentBookServletTest {


  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////   ONE WAY   //////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  @Test
  void gettingAptBookReturnTextFormat() throws ServletException, IOException {
    String owner = "Dave";
    String description = "teach java";
    String beginTime = "12/11/1985 4:30 pm";
    String endTime = "12/11/1986 8:30 am";

    String description2 = "teach java2";
    String beginTime2 = "12/11/1922 4:30 pm";
    String endTime2 = "12/11/1923 8:30 am";

    //create apt book servlet
    AppointmentBookServlet servlet = new AppointmentBookServlet();
    //call createAptBook on servlet which return aptBoook "book"
    AppointmentBook book = servlet.createAppointmentBook(owner);
    //add apt to "book"
    book.addAppointment(new Appointment(description, beginTime, endTime));
    book.addAppointment(new Appointment(description2, beginTime2, endTime2));

    //using mockito to create mock objects
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter("owner")).thenReturn(owner);

    HttpServletResponse response = mock(HttpServletResponse.class);

    StringWriter sw = new StringWriter();
    //saying when getWriter method is called on mock response obj
    //then we want getWriter method to return this mock printWriter
    when(response.getWriter()).thenReturn(new PrintWriter(sw));

    servlet.doGet(request, response);

    verify(response).setStatus(HttpServletResponse.SC_OK);

    String text = sw.toString();
    assertThat(text,containsString(owner));
    assertThat(text,containsString(description));
    assertThat(text,containsString("12/11/85"));

    assertThat(text,containsString(owner));
    assertThat(text,containsString(description2));
    assertThat(text,containsString("12/11/22"));
  }



  @Test
  void addAppointment() throws ServletException, IOException {

    AppointmentBookServlet servlet = new AppointmentBookServlet();


    String owner = "Dave";
    String description = "teach java";
    String beginTime = "12/11/1985 4:30 pm";
    String endTime = "12/11/1986 8:30 am";


    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getParameter("owner")).thenReturn(owner);
    when(request.getParameter("description")).thenReturn(description);
    when(request.getParameter("beginTime")).thenReturn(beginTime);
    when(request.getParameter("endTime")).thenReturn(endTime);

    HttpServletResponse response = mock(HttpServletResponse.class);

    servlet.doPost(request, response);

    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
    ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
    verify(response).setStatus(statusCode.capture());

    assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));

    AppointmentBook book = servlet.getAppointmentBook(owner);
    assertThat(book, notNullValue());
    assertThat(book.getOwnerName(), equalTo(owner));

    //not safe
//    Appointment appointment = book.getAppointments().iterator().next();
    //alternative
    Collection<Appointment> appointments = book.getAppointments();
    assertThat(appointments, hasSize(1));

    Appointment appointment = appointments.iterator().next();
    assertThat(appointment.getDescription(), equalTo(description));

  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////   ANOTHER WAY   //////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  @Test
  void gettingAppointmentBookReturnsTextFormat2() throws ServletException, IOException {
    String owner = "Dave";
    String description = "Teach Java";
    String beginTime = "12/11/1985 4:30 pm";
    String endTime = "12/11/1986 8:30 am";

    AppointmentBookServlet servlet = new AppointmentBookServlet();
    AppointmentBook book = servlet.createAppointmentBook(owner);
    book.addAppointment(new Appointment(description,beginTime,endTime));

    Map<String, String> queryParams = Map.of("owner", owner);
    StringWriter sw = invokeServletMethod(queryParams, servlet::doGet);

    String text = sw.toString();
    assertThat(text, containsString(owner));
    assertThat(text, containsString(description));
    assertThat(text, containsString("12/11/85"));
  }

  private StringWriter invokeServletMethod(Map<String, String> params, ServletMethodInvoker invoker) throws IOException, ServletException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    params.forEach((key, value) -> when(request.getParameter(key)).thenReturn(value));

    HttpServletResponse response = mock(HttpServletResponse.class);

    StringWriter sw = new StringWriter();
    when(response.getWriter()).thenReturn(new PrintWriter(sw));

    invoker.invoke(request, response);

    verify(response).setStatus(HttpServletResponse.SC_OK);
    return sw;
  }

  @Test
  void addAppointment2() throws ServletException, IOException {
    AppointmentBookServlet servlet = new AppointmentBookServlet();

    String owner = "Dave";
    String description = "Teach Java";
    String beginTime = "12/11/1985 4:30 pm";
    String endTime = "12/11/1986 8:30 am";

    invokeServletMethod(Map.of("owner", owner, "description", description,
            "beginTime", beginTime, "endTime", endTime), servlet::doPost);

    AppointmentBook book = servlet.getAppointmentBook(owner);
    assertThat(book, notNullValue());
    assertThat(book.getOwnerName(), equalTo(owner));

    Collection<Appointment> appointments = book.getAppointments();
    assertThat(appointments, hasSize(1));

    Appointment appointment = appointments.iterator().next();
    assertThat(appointment.getDescription(), equalTo(description));
    assertThat(appointment.getBeginTimeString(), containsString("12/11/85"));


  }

  private interface ServletMethodInvoker {
    void invoke(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
  }







}
