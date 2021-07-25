package edu.pdx.cs410J.hueb;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet provides a REST API for working with an
 * <code>AppointmentBook</code>.
 */
public class AppointmentBookServlet extends HttpServlet
{
    static final String OWNER_PARAMETER = "owner";
    static final String DESCRIPTION_PARAMETER = "description";
    static final String BEGINTIME_PARAMETER = "beginTime";
    static final String ENDTIME_PARAMETER = "endTime";

    private final Map<String, AppointmentBook> books = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the appointment book contents
     * of the owner specified in the "owner" HTTP parameter to the HTTP response.
     * If the"owner" parameter is not specified the client will receive an error.
     * If there is no "owner" match the client will receive a message alerting them.
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String owner = getParameter( OWNER_PARAMETER, request );

        String beginTime = getParameter(BEGINTIME_PARAMETER, request);
        String endTime = getParameter(ENDTIME_PARAMETER, request);

        if (owner == null) {
            missingRequiredParameter(response, OWNER_PARAMETER);
        } else if (owner != null && beginTime != null && endTime != null)  {
            writeAppointmentBookByTime(owner, beginTime, endTime, response);
        } else {
            writeAppointmentBook(owner, response);
        }
    }


    /**
     * Writes the appointments that begin within time range
     * from appointmentBook of owner to the HTTP response.
     *
     * The text of the message is formatted with TextDumper2
     */
    private void writeAppointmentBookByTime(String owner, String beginTime, String endTime, HttpServletResponse response) throws IOException {
        AppointmentBook book = this.books.get(owner);



        if (book == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            Date beginTime1 = null;
            try {
                beginTime1 = new SimpleDateFormat("MM/dd/yyyy hh:mm aa").parse(beginTime);
            } catch (ParseException e) {
                System.err.println("\nSRVLT) Parse of Begin Time failed - Please Check your Date/Time Formatting\n");
//                            e.printStackTrace();
            }

            Date endTime1 = null;
            try {
                endTime1 = new SimpleDateFormat("MM/dd/yyyy hh:mm aa").parse(endTime);
            } catch (ParseException e) {
                System.err.println("\nSRVLT) Parse of End Time failed - Please Check your Date/Time Formatting\n");
//                            e.printStackTrace();
            }


            AppointmentBook bookByTime = new AppointmentBook(owner);

            for (Appointment appt : book.getAppointments()){

                Boolean between = appt.getBeginTime().after(beginTime1) && appt.getBeginTime().before(endTime1);
                Boolean equals =  appt.getBeginTime().equals(beginTime1) || appt.getBeginTime().equals(endTime1);
//                System.out.println("\n between: " + between + " equals: " + equals + "\n");

                if(between || equals ) {
                    bookByTime.addAppointment(appt);
                }

            }

            if (bookByTime == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {

                PrintWriter pw = response.getWriter();

                TextDumper2 dumper = new TextDumper2(pw);
                dumper.dump(bookByTime);
                pw.flush();

                response.setStatus(HttpServletResponse.SC_OK);

            }


        }
    }

    /**
     * Writes the appointmentBook of owner to the HTTP response.
     *
     * The text of the message is formatted with TextDumper2
     */
    private void writeAppointmentBook(String owner, HttpServletResponse response) throws IOException {
        AppointmentBook book = this.books.get(owner);
        if (book == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        } else {
            PrintWriter pw = response.getWriter();

            TextDumper2 dumper = new TextDumper2(pw);
            dumper.dump(book);
            pw.flush();

            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    /**
     * Handles an HTTP POST request by storing the appointment entry for the
     * "owner" and "appointment" request parameters.  It writes the appointment
     * entry to the HTTP response.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );



        String owner = getParameter(OWNER_PARAMETER, request );
        if (owner == null) {
            missingRequiredParameter(response, OWNER_PARAMETER);
            return;
        }

        String description = getParameter(DESCRIPTION_PARAMETER, request );
        if ( description == null) {
            missingRequiredParameter( response, DESCRIPTION_PARAMETER );
            return;
        }

        String beginTime = getParameter(BEGINTIME_PARAMETER, request );
        if ( beginTime == null) {
            missingRequiredParameter( response, BEGINTIME_PARAMETER );
            return;
        }

        String endTime= getParameter(ENDTIME_PARAMETER, request );
        if ( endTime == null) {
            missingRequiredParameter( response, ENDTIME_PARAMETER );
            return;
        }


        AppointmentBook book = this.books.get(owner);
        if (book == null) {
            book = createAppointmentBook(owner);
        }

        Appointment appointment = new Appointment(description,beginTime,endTime);
        book.addAppointment(appointment);

        response.setStatus( HttpServletResponse.SC_OK);

        }




    /**
     * Handles an HTTP DELETE request by removing all appointmentBooks.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        this.books.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allAptBooksDeleted());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }



    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
        throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }







    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
      String value = request.getParameter(name);
      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }
    }




    //conveys to the reader that this method
    //isn't private because it's used in a test
    @VisibleForTesting
     AppointmentBook getAppointmentBook(String owner) {
        return this.books.get(owner);
    }




    /**
     * Returns the value of the book of given owner.
     *
     * @param owner
     *      The owner of the appointmentBook
     * @return <code>AppointmentBook</code> of owner
     *         <code>null</code> or is the empty string
     */
    public AppointmentBook createAppointmentBook(String owner) {
        AppointmentBook book = new AppointmentBook(owner);
        this.books.put(owner,book);
        return book;
    }
}
