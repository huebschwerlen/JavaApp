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
 * Integration tests for the {@link Project1} main class.
 */
class Project1IT extends InvokeMainTestCase {

  /**
   * Invokes the main method of {@link Project1} with the given arguments.
   */
  private MainMethodResult invokeMain(String... args) {
    return invokeMain( Project1.class, args );
  }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain(Project1.class);
    assertThat(result.getExitCode(), equalTo(1));
    assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Missing Command Line Arguments"));
  }

  @Test
  void testWithReadMe() {
    MainMethodResult result = invokeMain(Project1.class, "-README");
    assertThat(result.getExitCode(), equalTo(0));
  }

  @Test
  void testWorkingWithPrint() {
    MainMethodResult result = invokeMain(Project1.class, "-pRiNt", "Brian Griffin", "Go play some golf", "7/15/2021", "14:39", "06/2/2021", "1:03");
    String message = "Go play some golf from 7/15/2021 14:39 until 06/2/2021 1:03\n";
    assertThat(result.getTextWrittenToStandardOut(), equalTo(message));
    assertThat(result.getExitCode(), equalTo(0));
  }

  @Test
  void testWorkingWithNoPrint() {
    MainMethodResult result = invokeMain(Project1.class, "Brian Griffin", "Go play some golf", "7/15/2021", "14:39", "06/2/2021", "1:03");
    assertThat(result.getExitCode(), equalTo(0));
  }

  @Test
  void testInvalidDateWithPrint() {
    MainMethodResult result = invokeMain(Project1.class, "-pRiNt", "Brian Griffin", "Go play some golf", "27/15/2021", "14:39", "06/2/2021", "1:03");
    String message = "Invalid Arguments";
    assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  void testInvalidTimeWithPrint() {
    MainMethodResult result = invokeMain(Project1.class, "-pRiNt", "Brian Griffin", "Go play some golf", "07/15/2021", "24:39", "06/2/2021", "1:03");
    String message = "Invalid Arguments";
    assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  void testTooFewArgs6() {
    MainMethodResult result = invokeMain(Project1.class, "-pRiNt", "Brian Griffin", "Go play some golf", "07/15/2021", "24:39", "06/2/2021");
    String message = "Invalid Arguments";
    assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  void testTooFewArgs5() {
    MainMethodResult result = invokeMain(Project1.class, "-pRiNt", "Brian Griffin", "Go play some golf", "07/15/2021", "24:39");
    String message = "Invalid Arguments";
    assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  void testIncorrectArgs() {
    MainMethodResult result = invokeMain(Project1.class, "-pRiNt", "Brian Griffin", "Go play some golf", "07/15/2021", "4:39", "06/2/2021", "1:03", "help");
    String message = "Invalid Arguments";
    assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
    assertThat(result.getExitCode(), equalTo(1));
  }



  @Test
  void testIncorrectArgs1() {
    MainMethodResult result = invokeMain(Project1.class, "-pRiNt", "Brian Griffin", "Go play some golf", "07/15/2021", "4:39", "06/2/2021", "1:03", "help", "jerry");
    String message = "Invalid Arguments";
    assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  void testIncorrectArgs2() {
    MainMethodResult result = invokeMain(Project1.class, "-pRiNt", "-filetext", "Brian Griffin", "Go play some golf", "07/15/2021", "4:39", "06/2/2021", "1:03", "help", "jerry");
    String message = "Invalid Arguments";
    assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
    assertThat(result.getExitCode(), equalTo(1));
  }


  @Test
  void testIncorrectArgs3() {
    MainMethodResult result = invokeMain(Project1.class, "-pRiNt", "-filetext", "file", "Brian Griffin", "Go play some golf", "07/15/2021", "4:39", "06/2/2021", "1:03", "help", "jerry");
    String message = "Invalid Arguments";
    assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
    assertThat(result.getExitCode(), equalTo(1));
  }


  @Test
  void testIncorrectArgsNoFileExt() {
    MainMethodResult result = invokeMain(Project1.class, "-pRiNt", "-filetext", "file", "Brian Griffin", "Go play some golf", "07/15/2021", "4:39", "06/2/2021", "1:03");
    String message = "Invalid Arguments";
    assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  void testIncorrectArgsWithFileExt() {
    MainMethodResult result = invokeMain(Project1.class, "-pRiNt", "-filetext", "file.txt", "Brian Griffin", "Go play some golf", "07/15/2021", "4:39", "06/2/2021", "1:03");
    String message = "Invalid Arguments";
    assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(message));
    assertThat(result.getExitCode(), equalTo(1));
  }


}