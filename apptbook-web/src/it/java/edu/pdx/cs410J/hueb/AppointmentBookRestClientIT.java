package edu.pdx.cs410J.hueb;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Integration test that tests the REST calls made by {@link AppointmentBookRestClient}
 */
@TestMethodOrder(MethodName.class)
class AppointmentBookRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private AppointmentBookRestClient newAppointmentBookRestClient() {
    int port = Integer.parseInt(PORT);
    return new AppointmentBookRestClient(HOSTNAME, port);
  }

  @Test
  void test0RemoveAllAptBooks() throws IOException {
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    client.removeAllAptBooks();
  }

  @Test
  void test1EmptyServerContainsNoDictionaryEntries() throws IOException {
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    Map<String, String> dictionary = client.getAllDictionaryEntries();
    assertThat(dictionary.size(), equalTo(0));
  }

  @Test
  void test2CreateAptBookWithOneApt() throws IOException, ParserException {
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    String owner = "Dave";
    String description = "teach java";
    String beginTime = "12/11/1985 4:30 pm";
    String endTime = "12/11/1986 8:30 am";

    client.createAppointment(owner, description, beginTime, endTime);

    AppointmentBook book = client.getAppointments(owner);
    assertThat(book.getOwnerName(), equalTo(owner));

    Appointment appointment = book.getAppointments().iterator().next();
    assertThat(appointment.getDescription(), equalTo(description));

//    String aptBookText = client.getAppointments(owner);
//    assertThat(aptBookText, containsString(owner));
//    assertThat(aptBookText, containsString(description));
  }

  @Test
  void test4MissingRequiredParameterReturnsPreconditionFailed() throws IOException {
    //validates that when you post with no params you get a 412 stat code
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    //postToMyUrl is a public method in AptBookRestClient specifically
    //for testing this scenario
    HttpRequestHelper.Response response = client.postToMyURL(Map.of());
    assertThat(response.getContent(), containsString("required parameter"));
    assertThat(response.getCode(), equalTo(HttpURLConnection.HTTP_PRECON_FAILED));
  }

}
