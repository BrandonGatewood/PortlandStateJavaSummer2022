package edu.pdx.cs410J.gatew2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Tests the functionality in the {@link Project3} main class.
 */
class Project3IT extends InvokeMainTestCase {

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
      String usage = "Usage: java -jar target/phonebill-2022.0.0.jar [-README] [-print] [-textFile file] [-pretty file] customer caller callee beginDate beginTime am/pm endDate endTime am/pm\n";
      String option1 = "\t-README: Prints a README for this project and exits\n";
      String option2 = "\t-print: Prints a description of the new phone call\n";
      String option3 = "\t-textFile file:  Where to read/write the phone bill\n";
      String option4 = "\t-pretty file: Pretty print the phone bill to a text file\n";
      String option5 = "\t-pretty -: Pretty print the phone bill to Standard out\n";

      MainMethodResult result = invokeMain(Project3.class);
      assertThat(result.getTextWrittenToStandardError(), containsString(usage + option1 + option2 + option3 + option4 + option5));
  }

    /**
     * Tests that invoking the main method with Print option issues an error
     */
    @Test
    void testPrintOption() {
        //GIVEN that the '-print' option is entered
        MainMethodResult result = invokeMain(Project3.class, "-print", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "pm", "1/1/1979", "5:03", "pm");

        //WHEN '-print' option is entered
        //THEN output should be "Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/79, 2:21 PM to 1/1/79, 5:03 PM"
        assertThat(result.getTextWrittenToStandardError(), containsString("Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/79, 2:21 PM to 1/1/79, 5:03 PM"));
    }
    @Test
    void testReadMeOption() throws IOException {
        //GIVEN that the '-README' option is entered
        MainMethodResult result = invokeMain(Project3.class, "-README", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "pm", "1/1/1979", "5:03", "pm");

        //WHEN '-README' option is entered
        //THEN output should be: README.txt
        assertThat(result.getTextWrittenToStandardError(), containsString(String.valueOf(Files.readString(Path.of("src/main/resources/edu/pdx/cs410J/gatew2/README.txt")))));
    }
    @Test
    void testReadMeOption1() throws IOException {
        //GIVEN that the '-README' option is entered with the '-print' option.
        MainMethodResult result = invokeMain(Project3.class, "-print","-README", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "pm", "1/1/1979", "5:03", "pm");

        //WHEN a '-print' option is entered before the '-README' option
        //THEN output should be: README.txt
        assertThat(result.getTextWrittenToStandardError(), containsString(String.valueOf(Files.readString(Path.of("src/main/resources/edu/pdx/cs410J/gatew2/README.txt")))));
    }
    @Test
    void testReadMeOption2() throws IOException {
        //GIVEN that the '-README' option is entered
        MainMethodResult result = invokeMain(Project3.class, "-README", "-print", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "pm", "1/1/1979", "5:03", "pm");

        //WHEN a '-README' option is entered
        //THEN output should be: README.txt
        assertThat(result.getTextWrittenToStandardError(), containsString(String.valueOf(Files.readString(Path.of("src/main/resources/edu/pdx/cs410J/gatew2/README.txt")))));
    }
    @Test
    void testTextFileOption0() {
        //GIVEN that the '-textFile' option is entered with no destination file.
        MainMethodResult result = invokeMain(Project3.class, "-textFile", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "pm", "1/1/1979", "5:03", "pm");

        //WHEN a '-textFile' option is entered with no destination file
        //THEN output should be: "To use '-textFile' option, it must be in the order '-textFile' '.txt'."
        assertThat(result.getTextWrittenToStandardError(), containsString("To use '-textFile' option, it must be in the order '-textFile' '.txt'."));
    }

    @Test
    void testTextFileOption1(@TempDir File tempDir) {
        File textFile = new File(tempDir, "apptbook.txt");
        //GIVEN that the '-textFile' option is entered with a destination file.
        MainMethodResult result = invokeMain(Project3.class, "-textFile", String.valueOf(textFile), "Brandon", "905-394-4432", "945-413-3430", "1/1/2012", "2:21", "pm", "1/1/2012", "5:03", "pm");

        //WHEN a '-textFile' option is entered with a destination file
        //THEN output should be: ""
        assertThat(result.getTextWrittenToStandardError(), containsString(""));
    }

    @Test
    void testPrintAndTextFileOption0(@TempDir File tempDir) {
        File textFile = new File(tempDir, "apptbook.txt");
        //GIVEN that the '-textFile' and '-print' option is entered with a destination file.
        MainMethodResult result = invokeMain(Project3.class, "-print", "-textFile", String.valueOf(textFile),"Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "pm", "1/1/1979", "5:03", "pm");

        //WHEN a '-textFile' option is entered with no destination file
        //THEN output should be: "Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/79, 2:21 PM to 1/1/79, 5:03 PM"
        assertThat(result.getTextWrittenToStandardError(), containsString("Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/79, 2:21 PM to 1/1/79, 5:03 PM"));
    }
    @Test
    void testPrintAndTextFileOption1(@TempDir File tempDir) {
        File textFile = new File(tempDir, "apptbook.txt");
        //GIVEN that the '-textFile' and '-print' option is entered with a destination file in the wrong order.
        MainMethodResult result = invokeMain(Project3.class, "-textFile", "-print", String.valueOf(textFile), "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "pm", "1/1/1979", "5:03", "pm");

        //WHEN a '-textFile' option is entered with no destination file in the wrong order
        //THEN output should be: "To use '-textFile' option, it must be in the order '-textFile' '.txt'."
        assertThat(result.getTextWrittenToStandardError(), containsString("To use '-textFile' option, it must be in the order '-textFile' '.txt'."));
    }
    @Test
    void testREADMEANDPrintAndTextFileOption(@TempDir File tempDir) throws IOException {
        File textFile = new File(tempDir, "apptbook.txt");
        //GIVEN that ALL options are entered
        MainMethodResult result = invokeMain(Project3.class, "-README", "-print", "-textFile", String.valueOf(textFile), "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "pm", "1/1/1979", "5:03", "pm");

        //WHEN ALL options are entered
        //THEN output should be: README.txt
        assertThat(result.getTextWrittenToStandardError(), containsString(String.valueOf(Files.readString(Path.of("src/main/resources/edu/pdx/cs410J/gatew2/README.txt")))));
    }
    @Test
    void testEmptyPrintPretty(@TempDir File tempDir) {
        File textFile = new File(tempDir, "apptbook.txt");
        //GIVEN that ALL options are entered
        MainMethodResult result = invokeMain(Project3.class, "-pretty", String.valueOf(textFile), "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "pm", "1/1/1979", "5:03", "pm");

        //WHEN ALL options are entered
        //THEN output should be: ""
        assertThat(result.getTextWrittenToStandardError(), containsString(""));
    }
    @Test
    void testEmptyPrintPretty1(@TempDir File tempDir) {
        File textFile = new File(tempDir, "apptbook.txt");
        //GIVEN that ALL options are entered
        MainMethodResult result = invokeMain(Project3.class, "-textFile", String.valueOf(textFile), "-pretty", "-", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "pm", "1/1/1979", "5:03", "pm");

        //WHEN ALL options are entered
        //THEN output should be: ""
        assertThat(result.getTextWrittenToStandardError(), containsString(""));
    }
    @Test
    void testPrintPretty0(@TempDir File tempDir) throws IOException {
        String customer = "Test Phone Bill";
        PhoneBill bill = new PhoneBill(customer);
        PhoneCall call1 = new PhoneCall("808-924-4323", "905-234-4323", "2/23/2013 1:30 am", "2/23/2013 1:50 pm");
        PhoneCall call2 = new PhoneCall("808-924-4323", "705-214-4323", "6/15/2012 2:50 pm", "2/25/2013 3:30 pm");
        bill.addPhoneCall(call1);
        bill.addPhoneCall(call2);
        File textFile = new File(tempDir, "emptyFile.txt");
        TextDumper dumper = new TextDumper(new FileWriter(textFile));
        dumper.dump(bill);

        //GIVEN that ALL options are entered
        MainMethodResult result = invokeMain(Project3.class, "-pretty", String.valueOf(textFile), "Test Phone Bill", "808-924-4323", "945-413-3430", "1/1/2019", "2:21", "pm", "1/1/2019", "5:03", "pm");

        //WHEN ALL options are entered
        //THEN output should be: ""
        assertThat(result.getTextWrittenToStandardError(), containsString(""));
    }
    @Test
    void testPrintPretty1(@TempDir File tempDir) throws IOException {
        String customer = "Test Phone Bill";
        PhoneBill bill = new PhoneBill(customer);
        PhoneCall call1 = new PhoneCall("808-924-4323", "905-234-4323", "2/23/2013 1:30 am", "2/23/2013 1:50 pm");
        PhoneCall call2 = new PhoneCall("808-924-4323", "705-214-4323", "6/15/2012 2:50 pm", "2/25/2013 3:30 pm");
        PhoneCall call3 = new PhoneCall("808-924-4323", "705-214-4323", "7/15/2012 2:50 pm", "7/25/2013 3:30 pm");


        bill.addPhoneCall(call1);
        bill.addPhoneCall(call2);
        bill.addPhoneCall(call3);

        File textFile = new File(tempDir, "emptyFile.txt");
        TextDumper dumper = new TextDumper(new FileWriter(textFile));
        dumper.dump(bill);

        //GIVEN that ALL options are entered
        MainMethodResult result = invokeMain(Project3.class, "-pretty", "-", "Test Phone Bill", "808-924-4323", "945-413-3430", "1/1/2019", "2:21", "pm", "1/1/2019", "5:03", "pm");

        //WHEN ALL options are entered
        //THEN output should be: "No .txt file to Pretty print to standard out."
        assertThat(result.getTextWrittenToStandardError(), containsString("No .txt file to Pretty print to standard out."));
    }

    @Test
    void testPrintPretty2(@TempDir File tempDir) throws IOException {
        String customer = "Test Phone Bill";
        PhoneBill bill = new PhoneBill(customer);
        PhoneCall call1 = new PhoneCall("808-924-4323", "905-234-4323", "2/23/2013 1:30 am", "2/23/2013 1:50 pm");
        PhoneCall call2 = new PhoneCall("808-924-4323", "705-214-4323", "6/15/2012 2:50 pm", "2/25/2013 3:30 pm");
        PhoneCall call3 = new PhoneCall("808-924-4323", "705-214-4323", "7/15/2012 2:50 pm", "7/25/2013 3:30 pm");


        bill.addPhoneCall(call1);
        bill.addPhoneCall(call2);
        bill.addPhoneCall(call3);

        File textFile = new File(tempDir, "emptyFile.txt");
        TextDumper dumper = new TextDumper(new FileWriter(textFile));
        dumper.dump(bill);

        //GIVEN that ALL options are entered
        MainMethodResult result = invokeMain(Project3.class, "-textFile", String.valueOf(textFile), "-pretty", "-", "Test Phone Bill", "808-924-4323", "945-413-3430", "1/1/2019", "2:21", "pm", "1/1/2019", "5:03", "pm");

        //WHEN ALL options are entered
        //THEN output should be: ""
        assertThat(result.getTextWrittenToStandardError(), containsString(""));
    }
    /**
    * Tests that invoking the main method with one argument issues an error
    */
    @Test
    void testWithNotEnoughArguments1() {
        //GIVEN that there is 1 argument
        MainMethodResult result = invokeMain(Project3.class, "Brandon");

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
        MainMethodResult result = invokeMain(Project3.class, "Brandon f", "094-340-3443");

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
        MainMethodResult result = invokeMain(Project3.class, "Brandon f", "094-340-3443", "943-439-3432");

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
        MainMethodResult result = invokeMain(Project3.class, "Brandon f", "094-340-3443", "943-439-3432", "1/1/1979");

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
        MainMethodResult result = invokeMain(Project3.class, "Brandon f", "094-340-3443", "943-439-3432", "1/1/1979", "1:30");

        //WHEN there is only 5 arguments
        //THEN output should be "Missing ending \"am\"/\"pm'\""
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing ending \"am\"/\"pm\""));
    }

    /**
     * Tests that invoking the main method with six arguments issues an error
     */
    @Test
    void testWithNotEnoughArguments6() {
        //GIVEN that there are 6 arguments
        MainMethodResult result = invokeMain(Project3.class, "Brandon f", "094-340-3443", "943-439-3432", "1/1/1979", "1:30", "1/1/1979");

        //WHEN there is only 6 arguments
        //THEN output should be "Missing ending date (mm/dd/yyyy)"
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing ending date (mm/dd/yyyy)"));
    }
    /**
     * Tests that invoking the main method with seven arguments issues an error
     */
    @Test
    void testWithNotEnoughArguments7() {
        //GIVEN that there are 7 arguments
        MainMethodResult result = invokeMain(Project3.class, "Brandon f", "094-340-3443", "943-439-3432", "1/1/1979", "1:30", "1/1/1979", "1:30");

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
        MainMethodResult result = invokeMain(Project3.class, "Brandon f", "094-340-3443", "943-439-3432", "1/1/1979", "2:21", "1/1/1979", "2:55", "1/1/1979 2:55", "Brad", "-print", "hi");

        //WHEN there is too many arguments
        //THEN output should be "Too many arguments\n"
        assertThat(result.getTextWrittenToStandardError(), containsString("Too many arguments\n"));
    }

}