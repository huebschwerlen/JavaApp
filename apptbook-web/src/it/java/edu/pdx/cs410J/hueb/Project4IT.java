package edu.pdx.cs410J.hueb;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * An integration test for {@link Project4} that invokes its main method with
 * various arguments
 */
@TestMethodOrder(MethodName.class)
class Project4IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "1234");

    @Test
    void test0RemoveAllMappings() throws IOException {
      AppointmentBookRestClient client = new AppointmentBookRestClient(HOSTNAME, Integer.parseInt(PORT));
      client.removeAllAptBooks();
    }

    @Test
    void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project4.class );
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_ARGS));
    }

    @Test
    void test2EmptyServer() {
        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT );
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(1));
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(""));
    }

//    @Test
//    void test3NoDefinitionsThrowsAppointmentBookRestException() {
//        String word = "WORD";
//        try {
//            invokeMain(Project4.class, HOSTNAME, PORT, word);
//            fail("Expected a RestException to be thrown");
//
//        } catch (UncaughtExceptionInMain ex) {
//            RestException cause = (RestException) ex.getCause();
//            assertThat(cause.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_NOT_FOUND));
//        }
//    }

    @Test
    void test4AddAppoinment() {
        String beginTime = "12/11/1985 11:50 am";
        String endTime = "12/11/1986 12:00 pm";
        String owner = "owner";
        String desc = "desc";

        MainMethodResult result = invokeMain( Project4.class, "-host", "localhost", "-port", "8080", "owner", "desc", "12/11/1985", "11:50", "am", "12/12/1985", "11:55", "pm" );
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(0));
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, containsString("-print"));

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, owner, desc, "12/11/1985", "11:50", "am", "12/11/1985", "11:50", "am" );
        out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString("-print"));

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, owner, desc, "12/11/1985", "11:50", "am", "12/11/1985", "11:50", "am" );
        out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString("-print"));
    }




    @Test
    void test5GetAllAppts() {
        String beginTime = "12/11/1985 11:50 am";
        String endTime = "12/11/1986 12:00 pm";
        String owner = "owner";
        String desc = "desc";

        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, owner );
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(0));
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString("owner"));

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, owner );
        out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString("owner"));

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, owner);
        out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString("owner"));
    }


    @Test
    void test6AddSecondAppoinmentWithPrint() {
        String beginTime = "12/11/1985 11:50 am";
        String endTime = "12/11/1986 12:00 pm";
        String owner = "owner";
        String desc = "desc";

        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-print", owner, desc, "12/11/1985", "11:50", "am", "12/11/1985", "11:50", "am" );
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(0));
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString("desc"));

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-print", owner, desc, "12/11/1985", "11:50", "am", "12/11/1985", "11:50", "am" );
        out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString("desc"));

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-print", owner, desc, "12/11/1985", "11:50", "am", "12/11/1985", "11:50", "am" );
        out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString("desc"));
    }


    @Test
    void test7SearchAppts() {
        String beginTime = "12/11/1985 11:50 am";
        String endTime = "12/11/1986 12:00 pm";
        String owner = "owner";
        String desc = "desc";

        MainMethodResult result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-search", owner, "12/11/1985", "11:50", "am", "12/11/1985", "11:50", "am" );
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(0));
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString("begin"));

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-search", owner, "12/11/1985", "11:50", "am", "12/11/1985", "11:50", "am" );
        out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString("begin"));

        result = invokeMain( Project4.class, "-host", HOSTNAME, "-port", PORT, "-search", owner, "12/11/1985", "11:50", "am", "12/11/1985", "11:50", "am" );
        out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString("begin"));
    }



}