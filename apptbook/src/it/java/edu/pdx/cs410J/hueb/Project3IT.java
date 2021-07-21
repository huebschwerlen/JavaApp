package edu.pdx.cs410J.hueb;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Integration tests for the {@link Project3} main class.
 */

class Project3IT extends InvokeMainTestCase{

    String beginTime = "12/11/1985 11:50 am";
    String endTime = "12/11/1986 12:00 pm";



    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain( Project3.class, args );
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain(Project3.class);
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Missing Command Line Arguments"));
    }

    @Test
    void testWithReadMe() {
        MainMethodResult result = invokeMain(Project3.class, "-README");
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testWorkingWithPrint() {
        MainMethodResult result = invokeMain(Project3.class, "-pRiNt", "Brian Griffin", "Go play some golf", "7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "\nGo play some golf from 7/15/20, 4:39 AM until 6/2/21, 1:03 PM\n\n";
        assertThat(result.getTextWrittenToStandardOut(), equalTo(message));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testWorkingWithNoPrint() {
        MainMethodResult result = invokeMain(Project3.class, "Brian Griffin", "Go play some golf", "7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testInvalidDateWithPrint() {
        MainMethodResult result = invokeMain(Project3.class, "-pRiNt", "Brian Griffin", "Go play some golf", "27/15/2021", "14:39", "06/2/2021", "1:03");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testInvalidTimeWithPrint() {
        MainMethodResult result = invokeMain(Project3.class, "-pRiNt", "Brian Griffin", "Go play some golf", "07/15/2021", "24:39", "06/2/2021", "1:03");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testTooFewArgs6() {
        MainMethodResult result = invokeMain(Project3.class, "-pRiNt", "Brian Griffin", "Go play some golf", "07/15/2021", "24:39", "06/2/2021");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testTooFewArgs5() {
        MainMethodResult result = invokeMain(Project3.class, "-pRiNt", "Brian Griffin", "Go play some golf", "07/15/2021", "24:39");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArgs() {
        MainMethodResult result = invokeMain(Project3.class, "-pRiNt", "Brian Griffin", "Go play some golf", "07/15/2021", "4:39", "06/2/2021", "1:03", "help");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArgs1() {
        MainMethodResult result = invokeMain(Project3.class, "-pRiNt", "Brian Griffin", "Go play some golf", "07/15/2021", "4:39", "06/2/2021", "1:03", "help", "jerry");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArgs2() {
        MainMethodResult result = invokeMain(Project3.class, "-pRiNt", "-filetext", "Brian Griffin", "Go play some golf", "07/15/2021", "4:39", "06/2/2021", "1:03", "help", "jerry");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArgs3() {
        MainMethodResult result = invokeMain(Project3.class, "-pRiNt", "-filetext", "testFileP3.txt", "Brian Griffin", "Go play some golf", "07/15/2021", "4:39", "06/2/2021", "1:03", "help", "jerry");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }


    @Test
    void testcorrectArgsNoFileExt() {
        MainMethodResult result = invokeMain(Project3.class, "-pRiNt", "-textfile", "testFileP3.txt", "Brian Griffin", "Go play some golf", "7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "\nGo play some golf from 7/15/20, 4:39 AM until 6/2/21, 1:03 PM\n\n";
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testcorrectArgsWithFileExt() {
        MainMethodResult result = invokeMain(Project3.class, "-pRiNt", "-textfile", "testFileP3.txt", "Brian Griffin" , "Go play some golf", "7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "play some golf from 7/15/20, 4:39 AM until 6/2/21, 1:03 PM\n\n";
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testIncorrectArgs4() {
        MainMethodResult result = invokeMain(Project3.class, "-Readme");
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testIncorrectArgs5() {
        MainMethodResult result = invokeMain(Project3.class, "-pRiNt");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArgs6() {
        MainMethodResult result = invokeMain(Project3.class, "-pRiNt", "-textfile");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArgs7() {
        MainMethodResult result = invokeMain(Project3.class, "-pRiNt", "-textfile", "testFileP2");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArgs8() {
        MainMethodResult result = invokeMain(Project3.class, "-pRiNt", "-textfile", "testFileP2.txt");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArgs9() {
        MainMethodResult result = invokeMain(Project3.class, "-pRiNt", "-textfile", "testFileP2.txt", "Buffy Summers");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }


    @Test
    void testIncorrectArgs10() {
        MainMethodResult result = invokeMain(Project3.class, "-pRiNt", "-textfile", "testFileP2.txt", "Buffy Summers", "Slay Vampires");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArg11() {
        MainMethodResult result = invokeMain(Project3.class, "-pRiNt", "-textfile", "testFileP2.txt", "Buffy Summers", "Slay Vampires","12/11/1985");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArg12() {
        MainMethodResult result = invokeMain(Project3.class, "-pRiNt", "-textfile", "testFileP2.txt", "Buffy Summers", "Slay Vampires","12/11/1985","12:11");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArg13() {
        MainMethodResult result = invokeMain(Project3.class, "-pRiNt", "-textfile", "testFileP2.txt", "Buffy Summers", "Slay Vampires","12/11/1985","12:11","12/11/1986");
        String message = "Invalid Arguments";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testcorrectArgs() {
        MainMethodResult result = invokeMain(Project3.class, "-pRiNt", "-textfile", "testFileP4.txt", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "Slay Vampires";
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testcorrectArgs2() {
        MainMethodResult result = invokeMain(Project3.class, "-pRiNt", "-textfile", "testFileP2.txt", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "";
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testcorrectArgs3() {
        MainMethodResult result = invokeMain(Project3.class, "-textfile", "testFileP2.txt", "-print", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "";
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testcorrectArgsWrongROder() {
        MainMethodResult result = invokeMain(Project3.class, "-textfile", "-print", "testFileP2.txt", "Buffy Summers", "Slay Vampires","12/11/1985","12:11","12/11/1986","12:12");
        String message = "extension";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }


    @Test
    void testcorrectArg4() {
        MainMethodResult result = invokeMain(Project3.class, "-textfile", "testFileP2.txt", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(""));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testcorrectArg5() {
        MainMethodResult result = invokeMain(Project3.class, "-textfile", "testFileP2.txt", "-print", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(""));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testcorrectArg5Half() {
        MainMethodResult result = invokeMain(Project3.class, "-print","-textfile", "testFileP2.txt", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(""));
        assertThat(result.getExitCode(), equalTo(0));
    }


    @Test
    void testcorrectArg6() {
        MainMethodResult result = invokeMain(Project3.class, "-pretty", "testFileP2.txt", "-print", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Slay"));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testcorrectArg7() {
        MainMethodResult result = invokeMain(Project3.class, "-pretty", "testFileP2.txt", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(""));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testcorrectArg8() {
        MainMethodResult result = invokeMain(Project3.class, "-print","-pretty", "testFileP2.txt", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Slay"));
        assertThat(result.getExitCode(), equalTo(0));
    }


    @Test
    void testcorrectArg9() {
        MainMethodResult result = invokeMain(Project3.class, "-print","-pretty", "testFileP2.txt", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Slay"));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testcorrectArg10() {
        MainMethodResult result = invokeMain(Project3.class, "-textFile","testFileP2.txt","-pretty", "testFileP2.txt", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
//        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Slay"));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testcorrectArg11() {
        MainMethodResult result = invokeMain(Project3.class, "-pretty", "testFileP2.txt", "-textFile","testFileP2.txt", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
//        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Slay"));
        assertThat(result.getExitCode(), equalTo(0));
    }


    @Test
    void testcorrectArg12() {
        MainMethodResult result = invokeMain(Project3.class, "-print","-pretty", "testFileP2.txt", "-textFile","testFileP2.txt", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Slay"));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testcorrectArg13() {
        MainMethodResult result = invokeMain(Project3.class, "-pretty", "testFileP2.txt", "-print", "-textFile","testFileP2.txt", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Slay"));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testcorrectArg14() {
        MainMethodResult result = invokeMain(Project3.class, "-pretty", "testFileP2.txt",  "-textFile","testFileP2.txt", "-print","Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Slay"));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testcorrectArg15() {
        MainMethodResult result = invokeMain(Project3.class, "-print","-pretty", "-", "-textFile","testFileP2.txt", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Slay"));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testcorrectArg16() {
        MainMethodResult result = invokeMain(Project3.class, "-pretty", "-", "-print", "-textFile","testFileP2.txt", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Slay"));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testcorrectArg17() {
        MainMethodResult result = invokeMain(Project3.class, "-pretty", "-",  "-textFile","testFileP2.txt", "-print","Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Slay"));
        assertThat(result.getExitCode(), equalTo(0));
    }


    @Test
    void testcorrectArg18() {
        MainMethodResult result = invokeMain(Project3.class, "-pretty", "-", "-print", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Slay"));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testcorrectArg19() {
        MainMethodResult result = invokeMain(Project3.class, "-pretty", "-", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Slay"));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testcorrectArg20() {
        MainMethodResult result = invokeMain(Project3.class, "-print","-pretty", "-", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Slay"));
        assertThat(result.getExitCode(), equalTo(0));
    }


    @Test
    void testcorrectArg21() {
        MainMethodResult result = invokeMain(Project3.class, "-print","-pretty", "-", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Slay"));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testcorrectArg22() {
        MainMethodResult result = invokeMain(Project3.class, "-textFile","testFileP2.txt","-pretty", "-", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Slay"));
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    void testcorrectArg23() {
        MainMethodResult result = invokeMain(Project3.class, "-pretty", "-", "-textFile","testFileP2.txt", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("Slay"));
        assertThat(result.getExitCode(), equalTo(0));
    }




    @Test
    void testIncorrectArg7() {
        MainMethodResult result = invokeMain(Project3.class, "-pretty", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "Invalid";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArg8() {
        MainMethodResult result = invokeMain(Project3.class, "-print","-pretty", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "Invalid";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }


    @Test
    void testIncorrectArg9() {
        MainMethodResult result = invokeMain(Project3.class, "-print","-pretty", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "Invalid";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArg10() {
        MainMethodResult result = invokeMain(Project3.class, "-textFile","-pretty", "testFileP2.txt", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "Invalid";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArg14() {
        MainMethodResult result = invokeMain(Project3.class, "-pretty", "testFileP2.txt", "-textFile", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "Invalid";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }


    @Test
    void testIncorrectArg15() {
        MainMethodResult result = invokeMain(Project3.class, "-print","-pretty", "-textFile","testFileP2.txt", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "Invalid";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArg16() {
        MainMethodResult result = invokeMain(Project3.class, "-pretty", "-print", "-textFile","testFileP2.txt", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "Invalid";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArg17() {
        MainMethodResult result = invokeMain(Project3.class, "-pretty",  "-textFile","testFileP2.txt", "-print","Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "Invalid";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArg18() {
        MainMethodResult result = invokeMain(Project3.class, "-print","-pretty", "-", "-textFile", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "Invalid";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArg19() {
        MainMethodResult result = invokeMain(Project3.class, "-pretty", "-print", "-textFile","testFileP2.txt", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "Invalid";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArg20() {
        MainMethodResult result = invokeMain(Project3.class, "-pretty",  "-textFile","testFileP2.txt", "-print","Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "Invalid";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }


    @Test
    void testIncorrectArg21() {
        MainMethodResult result = invokeMain(Project3.class, "-pretty", "-", "-print", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "a", "06/2/2021", "1:03", "pm");
        String message = "Invalid";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArg22() {
        MainMethodResult result = invokeMain(Project3.class, "-pretty", "-", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "p");
        String message = "Invalid";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArg23() {
        MainMethodResult result = invokeMain(Project3.class, "-print","-pretty", "-", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "31:03", "pm");
        String message = "Invalid";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }


    @Test
    void testIncorrectArg24() {
        MainMethodResult result = invokeMain(Project3.class, "-print","-pretty", "-", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "06/2/2021", "1:03", "pm");
        String message = "Invalid";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArg25() {
        MainMethodResult result = invokeMain(Project3.class, "-textFile","testFileP2.txt","-pretty", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "Invalid";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    void testIncorrectArg26() {
        MainMethodResult result = invokeMain(Project3.class, "-pretty", "-textFile","testFileP2.txt", "Buffy Summers", "Slay Vampires","7/15/2020", "4:39", "am", "06/2/2021", "1:03", "pm");
        String message = "Invalid";
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
        assertThat(result.getExitCode(), equalTo(1));
    }





    @AfterAll
    public static void cleanUpFile() {
        File f1 = new File("file.txt");
        f1.delete();

        File f2 = new File("testFilep2.txt");
        f2.delete();

        File f3 = new File("testFilep3.txt");
        f3.delete();

        File f4 = new File("testFilep4.txt");
        f4.delete();

        File f5 = new File("-textFile");
        f5.delete();
        File f6 = new File("-pretty");
        f6.delete();
        File f7 = new File("-print");
        f7.delete();
    }

    
}
