package edu.pdx.cs410J.gatew2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project1} main class.
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
    assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments\n"));
  }

    /**
     * Tests that invoking the main method with Print option issues an error
     */
    @Test
    void testPrintOption() {
        MainMethodResult result = invokeMain(Project1.class, "-print", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03");
        assertThat(result.getTextWrittenToStandardError(), containsString("Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/1979 2:21 to 1/1/1979 5:03\n"));
    }
    @Test
    void testReadMeOption() {
        MainMethodResult result = invokeMain(Project1.class, "-README", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03");
        assertThat(result.getTextWrittenToStandardError(), containsString("This is a README file!"));
    }
    @Test
    void testReadMeOption1() {
        MainMethodResult result = invokeMain(Project1.class, "-print","-README", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03");
        assertThat(result.getTextWrittenToStandardError(), containsString("This is a README file!"));
    }
    @Test
    void testReadMeOption2() {
        MainMethodResult result = invokeMain(Project1.class, "-README", "-print", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03");
        assertThat(result.getTextWrittenToStandardError(), containsString("This is a README file!"));
    }

    /**
    * Tests that invoking the main method with one argument issues an error
    */
    @Test
    void testWithNotEnoughArguments1() {
      MainMethodResult result = invokeMain(Project1.class, "Brandon");
      assertThat(result.getTextWrittenToStandardError(), containsString("Missing caller number (nnn-nnn-nnnn)\n"));
    }

    /**
     * Tests that invoking the main method with two arguments issues an error
     */
    @Test
    void testWithNotEnoughArguments2() {
        MainMethodResult result = invokeMain(Project1.class, "Brandon f", "094-340-3443");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing callee number (nnn-nnn-nnnn)\n"));
    }

    /**
     * Tests that invoking the main method with three arguments issues an error
     */
    @Test
    void testWithNotEnoughArguments3() {
        MainMethodResult result = invokeMain(Project1.class, "Brandon f", "094-340-3443", "943-439-3432");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing beginning date (mm/dd/yyyy)\n"));
    }

    /**
     * Tests that invoking the main method with four arguments issues an error
     */
    @Test
    void testWithNotEnoughArguments4() {
        MainMethodResult result = invokeMain(Project1.class, "Brandon f", "094-340-3443", "943-439-3432", "1/1/1979");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing beginning time (hh:mm)\n"));
    }
    /**
     * Tests that invoking the main method with five arguments issues an error
     */
    @Test
    void testWithNotEnoughArguments5() {
        MainMethodResult result = invokeMain(Project1.class, "Brandon f", "094-340-3443", "943-439-3432", "1/1/1979", "1:30");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing ending date (mm/dd/yyyy)\n"));
    }

    /**
     * Tests that invoking the main method with six arguments issues an error
     */
    @Test
    void testWithNotEnoughArguments6() {
        MainMethodResult result = invokeMain(Project1.class, "Brandon f", "094-340-3443", "943-439-3432", "1/1/1979", "1:30", "1/1/1979");
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing ending time (hh:mm)\n"));
    }

    /**
     * Tests that invoking the main method with eight or more arguments issues an error
     */
    @Test
    void testWithTooManyEnoughArguments() {
        MainMethodResult result = invokeMain(Project1.class, "Brandon f", "094-340-3443", "943-439-3432", "1/1/1979", "2:21", "1/1/1979", "2:55", "1/1/1979 2:55");
        assertThat(result.getTextWrittenToStandardError(), containsString("Too many arguments\n"));
    }

}