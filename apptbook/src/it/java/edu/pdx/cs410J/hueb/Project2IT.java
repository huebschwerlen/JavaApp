package edu.pdx.cs410J.hueb;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.StringContains.containsString;

/**
 * Integration tests for the {@link Project2} main class.
 */
class Project2IT extends InvokeMainTestCase {

    /**
     * Invokes the main method of {@link Project2} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project2.class, args );
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain(Project2.class);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Missing Command Line Arguments"));
    }

    @Test
    void testWithReadMe() {
        MainMethodResult result = invokeMain(Project2.class, "-README");
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testWorkingWithPrint() {
        MainMethodResult result = invokeMain(Project2.class, "-pRiNt", "Brian Griffin", "Go play some golf", "7/15/2021", "14:39", "06/2/2021", "1:03");
        String message = "\nGo play some golf from 7/15/2021 14:39 until 06/2/2021 1:03\n\n";
        assertThat(result.getTextWrittenToStandardOut(), equalTo(message));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testWorkingWithNoPrint() {
        MainMethodResult result = invokeMain(Project2.class, "Brian Griffin", "Go play some golf", "7/15/2021", "14:39", "06/2/2021", "1:03");
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testInvalidDateWithPrint() {
        MainMethodResult result = invokeMain(Project2.class, "-pRiNt", "Brian Griffin", "Go play some golf", "27/15/2021", "14:39", "06/2/2021", "1:03");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testInvalidTimeWithPrint() {
        MainMethodResult result = invokeMain(Project2.class, "-pRiNt", "Brian Griffin", "Go play some golf", "07/15/2021", "24:39", "06/2/2021", "1:03");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testTooFewArgs6() {
        MainMethodResult result = invokeMain(Project2.class, "-pRiNt", "Brian Griffin", "Go play some golf", "07/15/2021", "24:39", "06/2/2021");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testTooFewArgs5() {
        MainMethodResult result = invokeMain(Project2.class, "-pRiNt", "Brian Griffin", "Go play some golf", "07/15/2021", "24:39");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArgs() {
        MainMethodResult result = invokeMain(Project2.class, "-pRiNt", "Brian Griffin", "Go play some golf", "07/15/2021", "4:39", "06/2/2021", "1:03", "help");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArgs1() {
        MainMethodResult result = invokeMain(Project2.class, "-pRiNt", "Brian Griffin", "Go play some golf", "07/15/2021", "4:39", "06/2/2021", "1:03", "help", "jerry");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArgs2() {
        MainMethodResult result = invokeMain(Project2.class, "-pRiNt", "-filetext", "Brian Griffin", "Go play some golf", "07/15/2021", "4:39", "06/2/2021", "1:03", "help", "jerry");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArgs3() {
        MainMethodResult result = invokeMain(Project2.class, "-pRiNt", "-filetext", "file", "Brian Griffin", "Go play some golf", "07/15/2021", "4:39", "06/2/2021", "1:03", "help", "jerry");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }


    @Test
    @Disabled
    void testcorrectArgsNoFileExt() {
        MainMethodResult result = invokeMain(Project2.class, "-pRiNt", "-textfile", "file", "Brian Griffin", "Go play some golf", "07/15/2021", "4:39", "06/2/2021", "1:03");
        String message = "file extension";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    @Disabled
    void testcorrectArgsWithFileExt() {
        MainMethodResult result = invokeMain(Project2.class, "-pRiNt", "-textfile", "testFileP2.txt", "Brian Griffin", "Go play some golf", "07/15/2021", "4:39", "06/2/2021", "1:03");
        String message = "golf";
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testIncorrectArgs4() {
        MainMethodResult result = invokeMain(Project2.class, "-Readme");
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testIncorrectArgs5() {
        MainMethodResult result = invokeMain(Project2.class, "-pRiNt");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArgs6() {
        MainMethodResult result = invokeMain(Project2.class, "-pRiNt", "-textfile");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArgs7() {
        MainMethodResult result = invokeMain(Project2.class, "-pRiNt", "-textfile", "testFileP2");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArgs8() {
        MainMethodResult result = invokeMain(Project2.class, "-pRiNt", "-textfile", "testFileP2.txt");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArgs9() {
        MainMethodResult result = invokeMain(Project2.class, "-pRiNt", "-textfile", "testFileP2.txt", "Buffy Summers");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }


    @Test
    void testIncorrectArgs10() {
        MainMethodResult result = invokeMain(Project2.class, "-pRiNt", "-textfile", "testFileP2.txt", "Buffy Summers", "Slay Vampires");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArg11() {
        MainMethodResult result = invokeMain(Project2.class, "-pRiNt", "-textfile", "testFileP2.txt", "Buffy Summers", "Slay Vampires","12/11/1985");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArg12() {
        MainMethodResult result = invokeMain(Project2.class, "-pRiNt", "-textfile", "testFileP2.txt", "Buffy Summers", "Slay Vampires","12/11/1985","12:11");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArg13() {
        MainMethodResult result = invokeMain(Project2.class, "-pRiNt", "-textfile", "testFileP2.txt", "Buffy Summers", "Slay Vampires","12/11/1985","12:11","12/11/1986");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testcorrectArgs() {
        MainMethodResult result = invokeMain(Project2.class, "-pRiNt", "-textfile", "testFileP2.txt", "Buffy Summers", "Slay Vampires","12/11/1985","12:11","12/11/1986","12:12");
        String message = "Slay Vampires";
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testcorrectArgs2() {
        MainMethodResult result = invokeMain(Project2.class, "-pRiNt", "-textfile", "testFileP2.txt", "Buffy Summers", "Slay Vampires","12/11/1985","12:11","12/11/1986","12:12");
        String message = "Slay Vampires";
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testcorrectArgs3() {
        MainMethodResult result = invokeMain(Project2.class, "-textfile", "testFileP2.txt", "-print", "Buffy Summers", "Slay Vampires","12/11/1985","12:11","12/11/1986","12:12");
        String message = "Slay Vampires";
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    @Disabled
    void testcorrectArgsWrongROder() {
        MainMethodResult result = invokeMain(Project2.class, "-textfile", "-print", "testFileP2.txt", "Buffy Summers", "Slay Vampires","12/11/1985","12:11","12/11/1986","12:12");
        String message = "extension";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }


    @Test
    void testcorrectArg4() {
        MainMethodResult result = invokeMain(Project2.class, "-textfile", "testFileP2.txt", "Buffy Summers", "Slay Vampires","12/11/1985","12:11","12/11/1986","12:12");
        String message = "";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(""));
        assertThat(result.getExitCode(), equalTo(0));
    }




}