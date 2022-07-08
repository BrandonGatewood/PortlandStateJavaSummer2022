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
        //GIVEN that the '-print' option is entered
        MainMethodResult result = invokeMain(Project1.class, "-print", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03");

        //WHEN '-print' option is entered
        //THEN output should be "Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/1979 2:21 to 1/1/1979 5:03"
        assertThat(result.getTextWrittenToStandardError(), containsString("Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/1979 2:21 to 1/1/1979 5:03\n"));
    }
    @Test
    void testReadMeOption() {
        //GIVEN that the '-README' option is entered
        MainMethodResult result = invokeMain(Project1.class, "-README", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03");

        //WHEN '-README' option is entered
        //THEN output should be: "Brandon Gatewood\nProject 1\n\nThis project represents a phone bill application. It keeps track of a customer and their phone calls. Each phone call is saved into a list for their phone bill.\n\nTo use run this project on the command, all args are mandatory:\njava -jar target/phonebill-2022.0.0.jar [options] <args>\n\noptions:\n-print, -README\n\nargs:\ncustomer, caller number, callee number, begin date, begin time, end date, end time.\n"
        assertThat(result.getTextWrittenToStandardError(), containsString("Brandon Gatewood\nProject 1\n\nThis project represents a phone bill application. It keeps track of a customer and their phone calls. Each phone call is saved into a list for their phone bill.\n\nTo use run this project on the command, all args are mandatory:\njava -jar target/phonebill-2022.0.0.jar [options] <args>\n\noptions:\n-print, -README\n\nargs:\ncustomer, caller number, callee number, begin date, begin time, end date, end time.\n"));
    }
    @Test
    void testReadMeOption1() {
        //GIVEN that the print flag is entered before the README flag
        MainMethodResult result = invokeMain(Project1.class, "-print","-README", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03");

        //WHEN a print flag is entered before the README flag
        //THEN output should be: "Brandon Gatewood\nProject 1\n\nThis project represents a phone bill application. It keeps track of a customer and their phone calls. Each phone call is saved into a list for their phone bill.\n\nTo use run this project on the command, all args are mandatory:\njava -jar target/phonebill-2022.0.0.jar [options] <args>\n\noptions:\n-print, -README\n\nargs:\ncustomer, caller number, callee number, begin date, begin time, end date, end time.\n"
        assertThat(result.getTextWrittenToStandardError(), containsString("Brandon Gatewood\nProject 1\n\nThis project represents a phone bill application. It keeps track of a customer and their phone calls. Each phone call is saved into a list for their phone bill.\n\nTo use run this project on the command, all args are mandatory:\njava -jar target/phonebill-2022.0.0.jar [options] <args>\n\noptions:\n-print, -README\n\nargs:\ncustomer, caller number, callee number, begin date, begin time, end date, end time.\n"));
    }
    @Test
    void testReadMeOption2() {
        //GIVEN that the README flag is entered
        MainMethodResult result = invokeMain(Project1.class, "-README", "-print", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03");

        //WHEN a README flag is entered
        //THEN output should be: "Brandon Gatewood\nProject 1\n\nThis project represents a phone bill application. It keeps track of a customer and their phone calls. Each phone call is saved into a list for their phone bill.\n\nTo use run this project on the command, all args are mandatory:\njava -jar target/phonebill-2022.0.0.jar [options] <args>\n\noptions:\n-print, -README\n\nargs:\ncustomer, caller number, callee number, begin date, begin time, end date, end time.\n"
        assertThat(result.getTextWrittenToStandardError(), containsString("Brandon Gatewood\nProject 1\n\nThis project represents a phone bill application. It keeps track of a customer and their phone calls. Each phone call is saved into a list for their phone bill.\n\nTo use run this project on the command, all args are mandatory:\njava -jar target/phonebill-2022.0.0.jar [options] <args>\n\noptions:\n-print, -README\n\nargs:\ncustomer, caller number, callee number, begin date, begin time, end date, end time.\n"));
    }

    /**
    * Tests that invoking the main method with one argument issues an error
    */
    @Test
    void testWithNotEnoughArguments1() {
        //GIVEN that there is 1 argument
        MainMethodResult result = invokeMain(Project1.class, "Brandon");

        //WHEN there is only 1 argument
        //THEN output should be "Missing caller number (nnn-nnn-nnnn)\n"
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing caller number (nnn-nnn-nnnn)\n"));
    }

    /**
     * Tests that invoking the main method with two arguments issues an error
     */
    @Test
    void testWithNotEnoughArguments2() {
        //GIVEN that there are 2 arguments
        MainMethodResult result = invokeMain(Project1.class, "Brandon f", "094-340-3443");

        //WHEN there is only 2 arguments
        //THEN output should be "Missing callee number (nnn-nnn-nnnn)\n"
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing callee number (nnn-nnn-nnnn)\n"));
    }

    /**
     * Tests that invoking the main method with three arguments issues an error
     */
    @Test
    void testWithNotEnoughArguments3() {
        //GIVEN that there are 3 arguments
        MainMethodResult result = invokeMain(Project1.class, "Brandon f", "094-340-3443", "943-439-3432");

        //WHEN there is only 3 arguments
        //THEN output should be "Missing beginning date (mm/dd/yyyy)\n"
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing beginning date (mm/dd/yyyy)\n"));
    }

    /**
     * Tests that invoking the main method with four arguments issues an error
     */
    @Test
    void testWithNotEnoughArguments4() {
        //GIVEN that there are 4 arguments
        MainMethodResult result = invokeMain(Project1.class, "Brandon f", "094-340-3443", "943-439-3432", "1/1/1979");

        //WHEN there is only 4 arguments
        //THEN output should be "Missing beginning time (hh:mm)\n"
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing beginning time (hh:mm)\n"));
    }
    /**
     * Tests that invoking the main method with five arguments issues an error
     */
    @Test
    void testWithNotEnoughArguments5() {
        //GIVEN that there are 5 arguments
        MainMethodResult result = invokeMain(Project1.class, "Brandon f", "094-340-3443", "943-439-3432", "1/1/1979", "1:30");

        //WHEN there is only 5 arguments
        //THEN output should be "Missing ending date (mm/dd/yyyy)\n"
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing ending date (mm/dd/yyyy)\n"));
    }

    /**
     * Tests that invoking the main method with six arguments issues an error
     */
    @Test
    void testWithNotEnoughArguments6() {
        //GIVEN that there are 6 arguments
        MainMethodResult result = invokeMain(Project1.class, "Brandon f", "094-340-3443", "943-439-3432", "1/1/1979", "1:30", "1/1/1979");

        //WHEN there is only 6 arguments
        //THEN output should be "Missing ending time (hh:mm)\n"
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing ending time (hh:mm)\n"));
    }
    /**
     * Tests that invoking the main method with seven arguments issues an error
     */
    @Test
    void testWithNotEnoughArguments7() {
        //GIVEN that there are 7 arguments
        MainMethodResult result = invokeMain(Project1.class, "Brandon f", "094-340-3443", "943-439-3432", "1/1/1979", "1:30", "1/1/1979", "1:30");

        //WHEN there is 7 arguments
        //THEN output should be ""
        assertThat(result.getTextWrittenToStandardError(), containsString(""));
    }

    /**
     * Tests that invoking the main method with eight or more arguments issues an error
     */
    @Test
    void testWithTooManyEnoughArguments() {
        //GIVEN that there are too many arguments
        MainMethodResult result = invokeMain(Project1.class, "Brandon f", "094-340-3443", "943-439-3432", "1/1/1979", "2:21", "1/1/1979", "2:55", "1/1/1979 2:55");

        //WHEN there is too many arguments
        //THEN output should be "Too many arguments\n"
        assertThat(result.getTextWrittenToStandardError(), containsString("Too many arguments\n"));
    }

}