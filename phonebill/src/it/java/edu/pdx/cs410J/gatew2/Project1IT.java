package edu.pdx.cs410J.gatew2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
    String message = "Please include command line arguments. [options] <args>.";
    String options = "Options include: '-README', '-print', and \"'-textFile' '.txt'\". NOTE: Each Customer will have their own .txt file and will not check if each customer name in the file are the same.";
    String cmdLineArgs = "Args must be in the order: customer name, caller number (nnn-nnn-nnnn), callee number (nnn-nnn-nnnn), begin date (dd/dd/dddd), begin time (dd:dd), end date (dd/dd/dddd), and end time (dd:dd).";

    MainMethodResult result = invokeMain(Project1.class);
    assertThat(result.getTextWrittenToStandardError(), containsString(message + "\n" + options + "\n" + cmdLineArgs));
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
    void testReadMeOption() throws IOException {
        //GIVEN that the '-README' option is entered
        MainMethodResult result = invokeMain(Project1.class, "-README", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03");

        //WHEN '-README' option is entered
        //THEN output should be: README.txt
        assertThat(result.getTextWrittenToStandardError(), containsString(String.valueOf(Files.readString(Path.of("src/main/resources/edu/pdx/cs410J/gatew2/README.txt")))));
    }
    @Test
    void testReadMeOption1() throws IOException {
        //GIVEN that the '-README' option is entered with the '-print' option.
        MainMethodResult result = invokeMain(Project1.class, "-print","-README", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03");

        //WHEN a '-print' option is entered before the '-README' option
        //THEN output should be: README.txt
        assertThat(result.getTextWrittenToStandardError(), containsString(String.valueOf(Files.readString(Path.of("src/main/resources/edu/pdx/cs410J/gatew2/README.txt")))));
    }
    @Test
    void testReadMeOption2() throws IOException {
        //GIVEN that the '-README' option is entered
        MainMethodResult result = invokeMain(Project1.class, "-README", "-print", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03");

        //WHEN a '-README' option is entered
        //THEN output should be: README.txt
        assertThat(result.getTextWrittenToStandardError(), containsString(String.valueOf(Files.readString(Path.of("src/main/resources/edu/pdx/cs410J/gatew2/README.txt")))));
    }
    @Test
    void testTextFileOption0() {
        //GIVEN that the '-textFile' option is entered with no destination file.
        MainMethodResult result = invokeMain(Project1.class, "-textFile", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03");

        //WHEN a '-textFile' option is entered with no destination file
        //THEN output should be: "To use '-textFile' option, it must be in the order '-textFile' '.txt'."
        assertThat(result.getTextWrittenToStandardError(), containsString("To use '-textFile' option, it must be in the order '-textFile' '.txt'."));
    }
    @Test
    void testTextFileOption1(@TempDir File tempDir) {
        File textFile = new File(tempDir, "apptbook.txt");
        //GIVEN that a destination file is entered with no '-textFile' option
        MainMethodResult result = invokeMain(Project1.class, String.valueOf(textFile),"Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03");

        //WHEN a '-textFile' option is entered with no destination file
        //THEN output should be: "To use '-textFile' option, it must be in the order '-textFile' '.txt'."
        assertThat(result.getTextWrittenToStandardError(), containsString("To use '-textFile' option, it must be in the order '-textFile' '.txt'."));
    }
    @Test
    void testTextFileOption2(@TempDir File tempDir) {
        File textFile = new File(tempDir, "apptbook.txt");
        //GIVEN that the '-textFile' option is entered with a destination file.
        MainMethodResult result = invokeMain(Project1.class, "-textFile", String.valueOf(textFile), "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03");

        //WHEN a '-textFile' option is entered with a destination file
        //THEN output should be: ""
        assertThat(result.getTextWrittenToStandardError(), containsString(""));
    }
    @Test
    void testTextFileOption3(@TempDir File tempDir) {
        File textFile = new File(tempDir, "apptbook.txt");
        //GIVEN that the '-textFile' option is entered with a destination file in the wrong order.
        MainMethodResult result = invokeMain(Project1.class, String.valueOf(textFile), "-textFile", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03");

        //WHEN a '-textFile' option is entered with a destination file in the wrong order
        //THEN output should be: "To use '-textFile' option, it must be in the order '-textFile' '.txt'."
        assertThat(result.getTextWrittenToStandardError(), containsString("To use '-textFile' option, it must be in the order '-textFile' '.txt'."));
    }
    @Test
    void testPrintAndTextFileOption0(@TempDir File tempDir) {
        File textFile = new File(tempDir, "apptbook.txt");
        //GIVEN that the '-textFile' and '-print' option is entered with a destination file.
        MainMethodResult result = invokeMain(Project1.class, "-print", "-textFile", String.valueOf(textFile),"Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03");

        //WHEN a '-textFile' option is entered with no destination file
        //THEN output should be: "Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/1979 2:21 to 1/1/1979 5:03"
        assertThat(result.getTextWrittenToStandardError(), containsString("Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/1979 2:21 to 1/1/1979 5:03"));
    }
    @Test
    void testPrintAndTextFileOption1(@TempDir File tempDir) {
        File textFile = new File(tempDir, "apptbook.txt");
        //GIVEN that the '-textFile' and '-print' option is entered with a destination file in the wrong order.
        MainMethodResult result = invokeMain(Project1.class, "-textFile", "-print", String.valueOf(textFile), "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03");

        //WHEN a '-textFile' option is entered with no destination file in the wrong order
        //THEN output should be: "To use '-textFile' option, it must be in the order '-textFile' '.txt'."
        assertThat(result.getTextWrittenToStandardError(), containsString("To use '-textFile' option, it must be in the order '-textFile' '.txt'."));
    }
    @Test
    void testPrintAndTextFileOption2(@TempDir File tempDir) {
        File textFile = new File(tempDir, "apptbook.txt");
        //GIVEN that the '-textFile' and '-print' option is entered with a destination file in the wrong order.
        MainMethodResult result = invokeMain(Project1.class, String.valueOf(textFile), "-print", "-textFile", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03");

        //WHEN a '-textFile' option is entered with no destination file in the wrong order
        //THEN output should be: "To use '-textFile' option, it must be in the order '-textFile' '.txt'."
        assertThat(result.getTextWrittenToStandardError(), containsString("To use '-textFile' option, it must be in the order '-textFile' '.txt'."));
    }
    @Test
    void testREADMEANDPrintAndTextFileOption(@TempDir File tempDir) throws IOException {
        File textFile = new File(tempDir, "apptbook.txt");
        //GIVEN that ALL options are entered
        MainMethodResult result = invokeMain(Project1.class, "-README", "-print", "-textFile", String.valueOf(textFile), "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03");

        //WHEN ALL options are entered
        //THEN output should be: README.txt
        assertThat(result.getTextWrittenToStandardError(), containsString(String.valueOf(Files.readString(Path.of("src/main/resources/edu/pdx/cs410J/gatew2/README.txt")))));
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