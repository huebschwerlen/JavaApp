package edu.pdx.cs410J.hueb;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class AppointmentBookRestClient extends HttpRequestHelper {
  private static final String WEB_APP = "apptbook";
  private static final String SERVLET = "appointments";

  private final String url;


  /**
   * Creates a client to the appointment book REST service running on the given host and port
   *
   * @param hostName The name of the host
   * @param port     The port
   */
  public AppointmentBookRestClient(String hostName, int port) {
    this.url = String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET);
  }

  /**
   * Returns all dictionary entries from the server
   */
  public Map<String, String> getAllDictionaryEntries() throws IOException {
    Response response = get(this.url, Map.of());
    return Messages.parseDictionary(response.getContent());
  }

  /**
   * Returns the appts for the given owner
   */
  public String getAppointments(String owner) throws IOException {
    Response response = get(this.url, Map.of("owner", owner));
    throwExceptionIfNotOkayHttpStatus(response);
    return response.getContent();
  }

  /**
   * Returns the appts for the given owner that begin within time range
   */
  public String getAppointmentsByTime(String owner, String beginTime, String endTime) throws IOException {
    Response response = get(this.url, Map.of("owner",owner, "beginTime", beginTime, "endTime", endTime));
    throwExceptionIfNotOkayHttpStatus(response);
    return response.getContent();
  }


  /**
   * Creates the appt and adds to owners appt book
   */
  public void createAppointment(String owner, String description, String beginTime, String endTime) throws IOException {
    Response response = postToMyURL(Map.of("owner", owner, "description",
            description, "beginTime", beginTime, "endTime", endTime));
    throwExceptionIfNotOkayHttpStatus(response);
  }

  @VisibleForTesting
  Response postToMyURL(Map<String, String> appointmentInfo) throws IOException {
    return post(this.url, appointmentInfo);
  }

  public void removeAllAptBooks() throws IOException {
    Response response = delete(this.url, Map.of());
    throwExceptionIfNotOkayHttpStatus(response);
  }

  private Response throwExceptionIfNotOkayHttpStatus(Response response) {
    int code = response.getCode();
    if (code != HTTP_OK) {
      String message = response.getContent();
      throw new RestException(code, message);
    }
    return response;
  }


}
