package edu.pdx.cs410J.gatew2;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A unit test for code in the <code>Project1</code> class.  This is different
 * from <code>Project1IT</code> which is an integration test (and can capture data
 * written to {@link System#out} and the like.
 */
class Project3Test {
  // Unit tests for checkForInputOptions(String[] args)
  @Test
  void checkForInputOptionsREADME0() throws IOException, ParserException {
    //GIVEN that the '-README' option is entered
    String[] args = {"-README", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03"};

    //WHEN '-README' option is entered
    //THEN output should be: README.txt
    assertThat(Project3.checkForInputOptions(args), containsString(String.valueOf(Files.readString(Path.of("src/main/resources/edu/pdx/cs410J/gatew2/README.txt")))));
  }

  @Test
  void checkForInputOptionsREADME1() throws IOException, ParserException {
    //GIVEN that the '-README' option is entered
    String[] args = {"-README", "-print", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03"};

    //WHEN '-README' option is entered
    //THEN output should be: README.txt
    assertThat(Project3.checkForInputOptions(args), containsString(String.valueOf(Files.readString(Path.of("src/main/resources/edu/pdx/cs410J/gatew2/README.txt")))));
  }

  @Test
  void checkForInputOptionsREADME2() throws IOException, ParserException {
    //GIVEN that the '-README' option is entered
    String[] args = {"-print", "-README", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03"};

    //WHEN '-README' option is entered
    //THEN output should be: README.txt
    assertThat(Project3.checkForInputOptions(args), containsString(String.valueOf(Files.readString(Path.of("src/main/resources/edu/pdx/cs410J/gatew2/README.txt")))));
  }
  @Test
  void checkForInputOptionsPrint0() throws IOException, ParserException {
    //GIVEN that the '-print' option is entered
    String[] args = {"-print", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "am", "1/1/1979", "5:03", "pm"};

    //WHEN '-print' option is entered
    //THEN output should be "Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/79, 2:21 AM to 1/1/79, 5:03 PM"
    assertThat(Project3.checkForInputOptions(args), equalTo("Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/79, 2:21 AM to 1/1/79, 5:03 PM"));
  }
  @Test
  void checkForInputOptionsTextFile0() throws IOException, ParserException {
    //GIVEN that the '-textFile' option is entered
    String[] args = {"-textFile", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03"};

    //WHEN '-textFile' option is entered with no destination file
    //THEN output should be "To use '-textFile' option, it must be in the order '-textFile' '.txt'."
    assertThat(Project3.checkForInputOptions(args), equalTo("To use '-textFile' option, it must be in the order '-textFile' '.txt'."));
  }
  @Test
  void checkForInputOptionsTextFile1(@TempDir File tempDir) throws IOException, ParserException {
    File textFile = new File(tempDir, "brandon.txt");

    //GIVEN that the '-textFile' option is entered with no 'print' option
    String[] args = {"-textFile", String.valueOf(textFile), "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "am", "1/1/1979", "5:03", "pm"};

    //WHEN '-textFile' option is entered with a destination file
    //THEN output should be ""
    assertThat(Project3.checkForInputOptions(args), equalTo(""));
  }
  @Test
  void checkForInputOptionsTextFile2(@TempDir File tempDir) throws IOException, ParserException {
    File textFile = new File(tempDir, "brandon.txt");

    //GIVEN that the '-textFile' and '-print' option is entered
    String[] args = {"-print", "-textFile", String.valueOf(textFile), "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "pm", "1/1/1979", "5:03", "pm"};

    //WHEN '-textFile' and '-print' option is entered
    //THEN output should be "Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/79, 2:21 PM to 1/1/79, 5:03 PM"
    assertThat(Project3.checkForInputOptions(args), equalTo("Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/79, 2:21 PM to 1/1/79, 5:03 PM"));
  }
  @Test
  void checkForInputOptionsTextFile3(@TempDir File tempDir) throws IOException, ParserException {
    String customer = "Brandon";
    PhoneBill bill = new PhoneBill(customer);
    PhoneCall call1 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/4432 1:30", "2/34/4323 1:30");
    PhoneCall call2 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/4432 1:30", "2/34/4323 1:30");
    PhoneCall call3 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/4432 1:30", "2/34/4323 1:30");

    bill.addPhoneCall(call1);
    bill.addPhoneCall(call2);
    bill.addPhoneCall(call3);

    File textFile = new File(tempDir, "brandon.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(bill);

    //GIVEN that the '-textFile' and '-print' option is entered
    String[] args = {"-textFile", String.valueOf(textFile), "-print", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "pm", "1/1/1979", "5:03", "pm"};

    //WHEN '-textFile' and '-print' option
    //THEN output should be "Brandon's phone bill with 4 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/79, 2:21 PM to 1/1/79, 5:03 PM"
    assertThat(Project3.checkForInputOptions(args), equalTo("Brandon's phone bill with 4 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/79, 2:21 PM to 1/1/79, 5:03 PM"));
  }
  @Test
  void checkForInputOptionsPrettyPrint0() throws IOException, ParserException {
    //GIVEN that the '-pretty' option is entered
    String[] args = {"-pretty", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03"};

    //WHEN '-pretty' option is entered with no destination file
    //THEN output should be "To use '-textFile' option, it must be in the order '-textFile' '.txt'."
    assertThat(Project3.checkForInputOptions(args), equalTo("To use '-pretty' option, it must be in the order '-pretty' '.txt'."));
  }
  @Test
  void checkForInputOptionsPrettyPrint1(@TempDir File tempDir) throws IOException, ParserException {
    File textFile = new File(tempDir, "brandon.txt");

    //GIVEN that the '-pretty' option is entered with no 'print' option
    String[] args = {"-pretty", String.valueOf(textFile), "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "am", "1/1/1979", "5:03", "pm"};

    //WHEN '-pretty' option is entered with a destination file
    //THEN output should be ""
    assertThat(Project3.checkForInputOptions(args), equalTo(""));
  }
  @Test
  void checkForInputOptionsPrettyPrint2(@TempDir File tempDir) throws IOException, ParserException {
    File textFile = new File(tempDir, "brandon.txt");

    //GIVEN that the '-pretty' and '-print' option is entered
    String[] args = {"-print", "-pretty", String.valueOf(textFile), "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "pm", "1/1/1979", "5:03", "pm"};

    //WHEN '-textFile' and '-print' option is entered
    //THEN output should be "Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/79, 2:21 PM to 1/1/79, 5:03 PM"
    assertThat(Project3.checkForInputOptions(args), equalTo("Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/79, 2:21 PM to 1/1/79, 5:03 PM"));
  }
  @Test
  void checkForInputOptionsPrettyPrint3(@TempDir File tempDir) throws IOException, ParserException {
    String customer = "Brandon";
    PhoneBill bill = new PhoneBill(customer);
    PhoneCall call1 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/4432 1:30", "2/34/4323 1:30");
    PhoneCall call2 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/4432 1:30", "2/34/4323 1:30");
    PhoneCall call3 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/4432 1:30", "2/34/4323 1:30");

    bill.addPhoneCall(call1);
    bill.addPhoneCall(call2);
    bill.addPhoneCall(call3);

    File textFile = new File(tempDir, "brandon.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(bill);

    //GIVEN that the '-textFile' and '-print' option is entered
    String[] args = {"-pretty", String.valueOf(textFile), "-print", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "pm", "1/1/1979", "5:03", "pm"};

    //WHEN '-pretty' and '-print' option
    //THEN output should be "Brandon's phone bill with 4 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/79, 2:21 PM to 1/1/79, 5:03 PM"
    assertThat(Project3.checkForInputOptions(args), equalTo("Brandon's phone bill with 4 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/79, 2:21 PM to 1/1/79, 5:03 PM"));
  }
  @Test
  void checkForInputOptionsNoREADMEOrPrintOrTextFile() throws IOException, ParserException {
    //Given that no '-README' or '-print' option is entered.
    String[] args = {"Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "pm", "1/1/1979", "5:03", "pm"};

    //WHEN no '-README' or '-print' option is entered
    //THEN output should be ""
    assertThat(Project3.checkForInputOptions(args), equalTo(""));
  }

  // Unit tests for validateArgLength(String[] args, boolean print)
  @Test
  void validateArgLength1() throws IOException, ParserException {
    //Given that there is 1 command line argument
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");

    //WHEN 1 command line arguments are included
    //THEN output should be "Missing caller number (nnn-nnn-nnnn)"
    assertThat(Project3.validateArgLength(args, false, null, null), equalTo("Missing caller number (nnn-nnn-nnnn)"));
  }
  @Test
  void validateArgLength2() throws IOException, ParserException {
    //Given that there are 2 command line arguments
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");

    //WHEN 2 command line arguments are included
    //THEN output should be "Missing callee number (nnn-nnn-nnnn)"
    assertThat(Project3.validateArgLength(args, false, null, null), equalTo("Missing callee number (nnn-nnn-nnnn)"));
  }
  @Test
  void validateArgLength3() throws IOException, ParserException {
    //Given that there are 3 command line arguments
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-4430");

    //WHEN 3 command line arguments are included
    //THEN output should be "Missing beginning date (mm/dd/yyyy)"
    assertThat(Project3.validateArgLength(args, false, null, null), equalTo("Missing beginning date (mm/dd/yyyy)"));
  }
  @Test
  void validateArgLength4() throws IOException, ParserException {
    //Given that there are 4 command line arguments
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-4430");
    args.add("1/1/2029");

    //WHEN 4 command line arguments are included
    //THEN output should be "Missing beginning time (hh:mm)"
    assertThat(Project3.validateArgLength(args, false, null, null), equalTo("Missing beginning time (hh:mm)"));
  }
  @Test
  void validateArgLength5() throws IOException, ParserException {
    //Given that there are 5 command line arguments
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-4430");
    args.add("1/1/2029");
    args.add("1:30");

    //WHEN 5 command line arguments are included
    //THEN output should be "Missing ending "am"/"pm""
    assertThat(Project3.validateArgLength(args, false, null, null), equalTo("Missing ending \"am\"/\"pm\""));
  }
  @Test
  void validateArgLength6() throws IOException, ParserException {
    //Given that there are 6 command line arguments
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-4430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("pm");

    //WHEN 6 command line arguments are included
    //THEN output should be "Missing ending date (mm/dd/yyyy)"
    assertThat(Project3.validateArgLength(args, false, null, null), equalTo("Missing ending date (mm/dd/yyyy)"));
  }
  @Test
  void validateArgLength7() throws IOException, ParserException {
    //Given that there are 7 command line arguments
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-4430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("pm");
    args.add("1/1/2029");

    //WHEN 7 command line arguments are included
    //THEN output should be "Missing ending time (hh:mm)"
    assertThat(Project3.validateArgLength(args, false, null, null), equalTo("Missing ending time (hh:mm)"));
  }
  @Test
  void validateArgLength8() throws IOException, ParserException {
    //Given that there are 8 command line arguments
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-4430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("pm");
    args.add("1/1/2029");
    args.add("1:30");

    //WHEN 8 command line arguments are included, with print flag = false
    //THEN output should be "Missing ending "am"/"pm""
    assertThat(Project3.validateArgLength(args, false, null, null), equalTo("Missing ending \"am\"/\"pm\""));

  }
  @Test
  void validateArgLength9() throws IOException, ParserException {
    //Given that there are 10+ command line arguments
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-4430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("pm");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("1:30");
    args.add("1/1/2029");
    args.add("1:30");

    //WHEN 10+ command line arguments are included
    //THEN output should be "Too many arguments"
    assertThat(Project3.validateArgLength(args, false, null, null), equalTo("Too many arguments"));
  }

  // Unit tests for validateEachArgument(String[] args, boolean print)
  @Test
  void validateEachArgumentCaller0() throws IOException, ParserException {
    //Given that caller number is "9052144433" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("9052144433");
    args.add("905-213-4430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("pm");
    args.add("1/1/2029");
    args.add("1:40");
    args.add("pm");

    //WHEN correct amount of command line arguments are included
    //THEN caller number must be validated.
    //WHEN caller number argument is invalid
    //THEN output should be "Caller number can only be in the format: nnn-nnn-nnnn."
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo("Caller number can only be in the format: nnn-nnn-nnnn."));
  }
  @Test
  void validateEachArgumentCaller1() throws IOException, ParserException {
    //Given that caller number is "905-2144433" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-2144433");
    args.add("905-213-4430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("pm");
    args.add("1/1/2029");
    args.add("1:40");
    args.add("pm");

    //WHEN correct amount of command line arguments are included
    //THEN caller number must be validated.
    //WHEN caller number argument is invalid
    //THEN output should be "Caller number can only be in the format: nnn-nnn-nnnn."
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo("Caller number can only be in the format: nnn-nnn-nnnn."));
  }
  @Test
  void validateEachArgumentCaller2() throws IOException, ParserException {
    //Given that caller number is "905-21-44433" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-21-44433");
    args.add("905-213-4430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("pm");
    args.add("1/1/2029");
    args.add("1:40");
    args.add("pm");

    //WHEN correct amount of command line arguments are included
    //THEN caller number must be validated.
    //WHEN caller number argument is invalid
    //THEN output should be "Caller number can only be in the format: nnn-nnn-nnnn."
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo("Caller number can only be in the format: nnn-nnn-nnnn."));
  }
  @Test
  void validateEachArgumentCaller3() throws IOException, ParserException {
    //Given that caller number is "90e2144433" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("90e2144433");
    args.add("905-213-4430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("pm");
    args.add("1/1/2029");
    args.add("1:40");
    args.add("pm");

    //WHEN correct amount of command line arguments are included
    //THEN caller number must be validated.
    //WHEN caller number argument is invalid
    //THEN output should be "Caller number can only be in the format: nnn-nnn-nnnn."
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo("Caller number can only be in the format: nnn-nnn-nnnn."));
  }
  @Test
  void validateEachArgumentCaller4() throws IOException, ParserException {
    //Given that caller number is "905-144-433" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-144-433");
    args.add("905-213-4430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("am");
    args.add("1/1/2029");
    args.add("1:40");
    args.add("am");

    //WHEN correct amount of command line arguments are included
    //THEN caller number must be validated.
    //WHEN caller number argument is invalid
    //THEN output should be "Caller number can only be in the format: nnn-nnn-nnnn."
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo("Caller number can only be in the format: nnn-nnn-nnnn."));
  }
  @Test
  void validateEachArgumentCallee0() throws IOException, ParserException {
    //Given that callee number is "9052134430" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("9052134430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("pm");
    args.add("1/1/2029");
    args.add("1:40");
    args.add("am");

    //WHEN correct amount of command line arguments are included
    //THEN callee number must be validated.
    //WHEN callee number argument is invalid
    //THEN output should be "Callee number can only be in the format: nnn-nnn-nnnn."
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo("Callee number can only be in the format: nnn-nnn-nnnn."));
  }
  @Test
  void validateEachArgumentCallee1() throws IOException, ParserException {
    //Given that callee number is "905a2134430" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905a2134430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("pm");
    args.add("1/1/2029");
    args.add("1:40");
    args.add("pm");

    //WHEN correct amount of command line arguments are included
    //THEN callee number must be validated.
    //WHEN callee number argument is invalid
    //THEN output should be "Callee number can only be in the format: nnn-nnn-nnnn."
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo("Callee number can only be in the format: nnn-nnn-nnnn."));
  }
  @Test
  void validateEachArgumentCallee2() throws IOException, ParserException {
    // GIVEN that callee number is "213-4430" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("213-4430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("pm");
    args.add("1/1/2029");
    args.add("1:40");
    args.add("pm");

    // WHEN correct amount of command line arguments are included
    // THEN callee number must be validated.
    // WHEN callee number argument is invalid
    // THEN output should be "Callee number can only be in the format: nnn-nnn-nnnn."
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo("Callee number can only be in the format: nnn-nnn-nnnn."));
  }
  @Test
  void validateEachArgumentCallee3() throws IOException, ParserException {
    // GIVEN that caller number is "4433" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("4430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("pm");
    args.add("1/1/2029");
    args.add("1:40");
    args.add("pm");

    // WHEN correct amount of command line arguments are included
    // THEN callee number must be validated.
    // WHEN callee number argument is invalid
    // THEN output should be "Callee number can only be in the format: nnn-nnn-nnnn."
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo("Callee number can only be in the format: nnn-nnn-nnnn."));
  }
  @Test
  void validateEachArgumentCallee4() throws IOException, ParserException {
    // GIVEN that caller number is "905-213-30" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-30");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("pm");
    args.add("1/1/2029");
    args.add("1:40");
    args.add("pm");

    // WHEN correct amount of command line arguments are included
    // THEN callee number must be validated.
    // WHEN callee number argument is invalid
    // THEN output should be "Callee number can only be in the format: nnn-nnn-nnnn."
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo("Callee number can only be in the format: nnn-nnn-nnnn."));
  }
  @Test
  void validateEachArgumentBeginDate0() throws IOException, ParserException {
    // GIVEN that beginning date "1-1/2029" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1-1/2029");
    args.add("1:30");
    args.add("pm");
    args.add("1/1/2029");
    args.add("1:40");
    args.add("pm");

    // WHEN correct amount of command line arguments are included
    // THEN beginning date must be validated.
    // WHEN beginning date argument is invalid
    // THEN output should be "Beginning date can only be in the format: mm/dd/yyyy.\nmonth and day can be from 1-69 or 01-69 BUT cannot be "0"."
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo("Beginning date can only be in the format: mm/dd/yyyy.\nmonth and day can be from 1-69 or 01-69 BUT cannot be \"0\"."));
  }

  @Test
  void validateEachArgumentBeginDate1() throws IOException, ParserException {
    // GIVEN that beginning date is "1-1-2029" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1-1-2029");
    args.add("1:30");
    args.add("am");
    args.add("1/1/2029");
    args.add("1:40");
    args.add("pm");

    // WHEN correct amount of command line arguments are included
    // THEN beginning date must be validated.
    // WHEN beginning date argument is invalid
    // THEN output should be "Beginning date can only be in the format: mm/dd/yyyy.\nmonth and day can be from 1-69 or 01-69 BUT cannot be "0"."
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo("Beginning date can only be in the format: mm/dd/yyyy.\nmonth and day can be from 1-69 or 01-69 BUT cannot be \"0\"."));
  }
  @Test
  void validateEachArgumentBeginDate2() throws IOException, ParserException {
    // GIVEN that beginning date is "1/34/2029" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("0/34/2029");
    args.add("1:30");
    args.add("am");
    args.add("1/1/2029");
    args.add("1:40");
    args.add("pm");

    // WHEN correct amount of command line arguments are included
    // THEN beginning date must be validated.
    // WHEN beginning date argument is invalid
    // THEN output should be "Beginning date can only be in the format: mm/dd/yyyy.\nmonth and day can be from 1-69 or 01-69 BUT cannot be "0"."
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo("Beginning date can only be in the format: mm/dd/yyyy.\nmonth and day can be from 1-69 or 01-69 BUT cannot be \"0\"."));
  }
  @Test
  void validateEachArgumentBeginDate3() throws IOException, ParserException {
    // GIVEN that beginning date is "13/31/2900" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("70/31/2900");
    args.add("1:30");
    args.add("am");
    args.add("1/1/2029");
    args.add("1:40");
    args.add("pm");

    // WHEN correct amount of command line arguments are included
    // THEN beginning date must be validated.
    // WHEN beginning date argument is invalid
    // THEN output should be "Beginning date can only be in the format: mm/dd/yyyy.\nmonth and day can be from 1-69 or 01-69 BUT cannot be "0"."
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo("Beginning date can only be in the format: mm/dd/yyyy.\nmonth and day can be from 1-69 or 01-69 BUT cannot be \"0\"."));
  }
  @Test
  void validateEachArgumentBeginDate4() throws IOException, ParserException {
    // GIVEN that beginning date is "12/31/2900" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("12/31/2012");
    args.add("1:30");
    args.add("am");
    args.add("1/1/2029");
    args.add("1:40");
    args.add("pm");

    // WHEN correct amount of command line arguments are included
    // THEN beginning date must be validated.
    // WHEN beginning date argument is valid
    // THEN output should be ""
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo(""));
  }
  @Test
  void validateEachArgumentBeginTime0() throws IOException, ParserException {
    // GIVEN that beginning time is "a:30" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1/1/2029");
    args.add("21:60");
    args.add("pm");
    args.add("1/1/2029");
    args.add("1:40");
    args.add("pm");

    //WHEN correct amount of command line arguments are included
    //THEN beginning time must be validated.
    //WHEN beginning time argument is invalid
    //THEN output should be "Beginning time can only be in the format: hh:mm.\nhour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69."
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo("Beginning time can only be in the format: hh:mm.\nhour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69."));
  }
  @Test
  void validateEachArgumentBeginTime1() throws IOException, ParserException {
    // GIVEN that beginning time is "12:4b" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1/1/2029");
    args.add("12:4b");
    args.add("pm");
    args.add("1/1/2029");
    args.add("1:40");
    args.add("pm");

    // WHEN correct amount of command line arguments are included
    // THEN beginning time must be validated.
    // WHEN beginning time argument is invalid
    // THEN output should be "Beginning time can only be in the format: hh:mm.\nhour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69."
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo("Beginning time can only be in the format: hh:mm.\nhour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69."));
  }
  @Test
  void validateEachArgumentBeginTime2() throws IOException, ParserException {
    // GIVEN that beginning time is "13:65" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1/1/2029");
    args.add("13:65");
    args.add("pm");
    args.add("1/1/2029");
    args.add("1:40");
    args.add("pm");

    // WHEN correct amount of command line arguments are included
    // THEN beginning time must be validated.
    // WHEN beginning time argument is invalid
    // THEN output should be "Beginning time can only be in the format: hh:mm.\nhour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69."
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo("Beginning time can only be in the format: hh:mm.\nhour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69."));
  }
  @Test
  void validateEachArgumentBeginTime3() throws IOException, ParserException {
    // GIVEN that beginning time is "0:59" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1/1/2029");
    args.add("0:59");
    args.add("pm");
    args.add("1/1/2029");
    args.add("1:40");
    args.add("pm");

    // WHEN correct amount of command line arguments are included
    // THEN beginning time must be validated.
    // WHEN beginning time argument is valid
    // THEN output should be ""
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo(""));
  }
  @Test
  void validateEachArgumentBeginAmPm0() throws ParserException, IOException {
    // GIVEN that beginning am/pm is "gm" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1/1/2029");
    args.add("0:59");
    args.add("gm");
    args.add("1/1/2029");
    args.add("1:40");
    args.add("pm");

    // WHEN correct amount of command line arguments are included
    // THEN beginning am/pm must be validated.
    // WHEN beginning am/pm argument is invalid
    // THEN output should be "Beginning "am"/"pm" can only be in the format: "am"/"pm"."
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo("Beginning \"am\"/\"pm\" can only be in the format: \"am\"/\"pm\"."));
  }
  @Test
  void validateEachArgumentBeginAmPm1() throws ParserException, IOException {
    // GIVEN that beginning am/pm is "123" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1/1/2029");
    args.add("0:59");
    args.add("123");
    args.add("1/1/2029");
    args.add("1:40");
    args.add("pm");

    // WHEN correct amount of command line arguments are included
    // THEN beginning am/pm must be validated.
    // WHEN beginning am/pm argument is invalid
    // THEN output should be "Beginning "am"/"pm" can only be in the format: "am"/"pm"."
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo("Beginning \"am\"/\"pm\" can only be in the format: \"am\"/\"pm\"."));
  }
  @Test
  void validateEachArgumentBeginAmPm2() throws ParserException, IOException {
    // GIVEN that beginning am/pm is "am" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1/1/2029");
    args.add("0:59");
    args.add("am");
    args.add("1/1/2029");
    args.add("1:40");
    args.add("pm");

    // WHEN correct amount of command line arguments are included
    // THEN beginning am/pm must be validated.
    // WHEN beginning am/pm argument is valid
    // THEN output should be "Beginning ""
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo(""));
  }
  @Test
  void validateEachArgumentEndDate0() throws IOException, ParserException {
    // GIVEN that ending date is "1/1-a029" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("pm");
    args.add("1/1-a029");
    args.add("1:40");
    args.add("pm");

    // WHEN correct amount of command line arguments are included
    // THEN ending date must be validated.
    // WHEN ending date argument is invalid
    // THEN output should be "Ending date can only be in the format: mm/dd/yyyy.\nmonth and day can be from 1-69 or 01-69 BUT cannot be "0"."
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo("Ending date can only be in the format: mm/dd/yyyy.\nmonth and day can be from 1-69 or 01-69 BUT cannot be \"0\"."));
  }
  @Test
  void validateEachArgumentEndDate1() throws IOException, ParserException {
    // GIVEN that ending date is "9/29/2012" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("pm");
    args.add("9/29/2012");
    args.add("1:40");
    args.add("pm");

    // WHEN correct amount of command line arguments are included
    // THEN ending date must be validated.
    // WHEN ending date argument is valid
    // THEN output should be ""
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo(""));
  }
  @Test
  void validateEachArgumentEndDate2() throws IOException, ParserException {
    // GIVEN that ending date is "09/30/2029" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("pm");
    args.add("09/30/2029");
    args.add("1:40");
    args.add("pm");

    // WHEN correct amount of command line arguments are included
    // THEN ending date must be validated.
    // WHEN ending date argument is valid
    // THEN output should be "Ending date can only be in the format: mm/dd/yyyy"
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo(""));
  }
  @Test
  void validateEachArgumentEndDate3() throws IOException, ParserException {
    // GIVEN that ending date is "01/01/2029" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("pm");
    args.add("01/01/2029");
    args.add("1:40");
    args.add("pm");

    // WHEN correct amount of command line arguments are included
    // THEN ending date must be validated.
    // WHEN ending date argument is valid
    // THEN output should be ""
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo(""));
  }
  @Test
  void validateEachArgumentEndDate4() throws IOException, ParserException {
    // GIVEN that ending date is "9/01/2029" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("pm");
    args.add("01/01/2029");
    args.add("1:40");
    args.add("pm");

    // WHEN correct amount of command line arguments are included
    // THEN ending date must be validated.
    // WHEN ending date argument is valid
    // THEN output should be ""
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo(""));
  }
  @Test
  void validateEachArgumentEndTime0() throws IOException, ParserException {
    //Given that ending time is "1" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("pm");
    args.add("1/1/2029");
    args.add("1");
    args.add("pm");

    //WHEN correct amount of command line arguments are included
    //THEN ending time must be validated.
    //WHEN ending time argument is invalid
    //THEN output should be "Ending time can only be in the format: hh:mm.\nhour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69."
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo("Ending time can only be in the format: hh:mm.\nhour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69."));
  }
  @Test
  void validateEachArgumentEndTime1() throws IOException, ParserException {
    // GIVEN that ending time is ":" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("pm");
    args.add("1/1/2029");
    args.add(":");
    args.add("am");

    // WHEN correct amount of command line arguments are included
    // THEN ending time must be validated.
    // WHEN ending time argument is invalid
    // THEN output should be "Ending time can only be in the format: hh:mm.\nhour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69."
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo("Ending time can only be in the format: hh:mm.\nhour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69."));
  }
  @Test
  void validateEachArgumentEndTime2() throws IOException, ParserException {
    // GIVEN that ending time is "01:65" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("pm");
    args.add("1/1/2029");
    args.add("01:70");
    args.add("am");

    // WHEN correct amount of command line arguments are included
    // THEN ending time must be validated.
    // WHEN ending time argument is invalid
    // THEN output should be "Ending time can only be in the format: hh:mm.\nhour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69."
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo("Ending time can only be in the format: hh:mm.\nhour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69."));
  }
  @Test
  void validateEachArgumentEndTime3() throws IOException, ParserException {
    // GIVEN that ending time is "01:09" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("pm");
    args.add("1/1/2029");
    args.add("01:09");
    args.add("am");

    // WHEN correct amount of command line arguments are included
    // THEN ending time must be validated.
    // WHEN ending time argument is invalid
    // THEN output should be ""
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo(""));
  }
  @Test
  void validateEachArgumentEndAmPm0() throws ParserException, IOException {
    // GIVEN that ending am/pm is "night" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1/1/2029");
    args.add("0:59");
    args.add("pm");
    args.add("1/1/2029");
    args.add("1:40");
    args.add("night");

    // WHEN correct amount of command line arguments are included
    // THEN ending am/pm must be validated.
    // WHEN ending am/pm argument is invalid
    // THEN output should be "Ending "am"/"pm" can only be in the format: "am"/"pm"."
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo("Ending \"am\"/\"pm\" can only be in the format: \"am\"/\"pm\"."));
  }
  @Test
  void validateEachArgumentEndAmPm1() throws ParserException, IOException {
    // GIVEN that ending am/pm is "pm" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1/1/2029");
    args.add("0:59");
    args.add("pm");
    args.add("1/1/2029");
    args.add("1:40");
    args.add("pm");

    // WHEN correct amount of command line arguments are included
    // THEN ending am/pm must be validated.
    // WHEN ending am/pm argument is valid
    // THEN output should be ""
    assertThat(Project3.validateEachArgument(args, false, null, null), equalTo(""));
  }

  // Unit tests for phoneBill(String[] args, boolean print)
  @Test
  void phoneBillPrint0() throws IOException, ParserException {
    //GIVEN that all arguments are valid
    String[] args = {"Brandon Gatewood", "905-214-4433", "905-213-3430", "1/1/2029 1:30 pm", "1/1/2029 1:03 pm"};

    //WHEN arguments have passed validation, and print flag = false
    //THEN output should be: ""
    assertThat(Project3.phoneBill(args, false, null, null), equalTo(""));
  }

  @Test
  void phoneBillPrint1() throws IOException, ParserException {
    //GIVEN that all arguments are valid
    String[] args = {"Brandon", "905-394-4432", "945-413-3430", "1/1/1979 2:21 am", "1/1/1979 5:03 am"};

    //WHEN arguments have passed validation, and print flag = true
    //THEN output should be: "Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/79, 2:21 AM to 1/1/79, 5:03 AM"
    assertThat(Project3.phoneBill(args, true, null, null), equalTo("Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/79, 2:21 AM to 1/1/79, 5:03 AM"));
  }
  @Test
  void phoneBillEmptyFile(@TempDir File tempDir) throws ParserException, IOException {
    //GIVEN that all arguments are valid
    String[] args = {"Brandon", "905-394-4432", "945-413-3430", "1/1/1979 2:21 pm", "1/1/1979 5:03 pm"};
    File textFile = new File(tempDir, "emptyFile.txt");

    //WHEN arguments have passed validation, and a file path is given
    //THEN output should be: "Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/79, 2:21 PM to 1/1/79 5:03 PM"
    assertThat(Project3.phoneBill(args, true, String.valueOf(textFile), null), equalTo("Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/79, 2:21 PM to 1/1/79, 5:03 PM"));
  }
  @Test
  void phoneBillFilledFile0(@TempDir File tempDir) throws ParserException, IOException {
    String customer = "Brandon";
    PhoneBill bill = new PhoneBill(customer);
    PhoneCall call1 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/4432 1:30", "2/34/4323 1:30");
    PhoneCall call2 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/4432 1:30", "2/34/4323 1:30");
    PhoneCall call3 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/4432 1:30", "2/34/4323 1:30");

    bill.addPhoneCall(call1);
    bill.addPhoneCall(call2);
    bill.addPhoneCall(call3);

    File textFile = new File(tempDir, "emptyFile.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(bill);

    //GIVEN that all arguments are valid
    String[] args = {"Brandon", "905-394-4432", "945-413-3430", "1/1/1979 2:21 pm", "1/1/1979 5:03 pm"};

    //WHEN arguments have passed validation, and a file path is given
    //THEN output should be: "Brandon's phone bill with 4 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/79, 2:21 PM to 1/1/79, 5:03 PM"
    assertThat(Project3.phoneBill(args, true, String.valueOf(textFile), null), equalTo("Brandon's phone bill with 4 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/79, 2:21 PM to 1/1/79, 5:03 PM"));
  }
  @Test
  void phoneBillFilledFile1(@TempDir File tempDir) throws ParserException, IOException {
    String customer = "Brandon";
    PhoneBill bill = new PhoneBill(customer);
    PhoneCall call1 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/4432 1:30", "2/34/4323 1:30");
    PhoneCall call2 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/4432 1:30", "2/34/4323 1:30");
    PhoneCall call3 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/4432 1:30", "2/34/4323 1:30");

    bill.addPhoneCall(call1);
    bill.addPhoneCall(call2);
    bill.addPhoneCall(call3);

    File textFile = new File(tempDir, "emptyFile.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(bill);

    //GIVEN that both customer names are the same
    String[] args = {customer, "905-394-4432", "945-413-3430", "1/1/1979 2:21 pm", "1/1/1979 5:03 pm"};

    //WHEN both customer names are the same
    //THEN output should be: "Brandon's phone bill with 4 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/79, 2:21 PM to 1/1/79, 5:03 PM"
    assertThat(Project3.phoneBill(args, true, String.valueOf(textFile), null), equalTo("Brandon's phone bill with 4 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/79, 2:21 PM to 1/1/79, 5:03 PM"));
  }
  @Test
  void phoneBillFilledFile2(@TempDir File tempDir) throws ParserException, IOException {
    String customer = "Brandon";
    PhoneBill bill = new PhoneBill(customer);
    PhoneCall call1 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/4432 1:30", "2/34/4323 1:30");
    PhoneCall call2 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/4432 1:30", "2/34/4323 1:30");
    PhoneCall call3 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/4432 1:30", "2/34/4323 1:30");

    bill.addPhoneCall(call1);
    bill.addPhoneCall(call2);
    bill.addPhoneCall(call3);

    File textFile = new File(tempDir, "emptyFile.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(bill);

    // GIVEN that customer name passed in the command line is different from the customer name in the .txt file
    String[] args = {"Bob", "905-394-4432", "945-413-3430", "1/1/1979 2:21 pm", "1/1/1979 5:03 pm"};

    // WHEN Customer names are different from each other.
    // THEN output should be: "Customer name passed in the command line doesnt match the customer name in the .txt file."
    assertThat(Project3.phoneBill(args, true, String.valueOf(textFile), null), equalTo("Customer name passed in the command line doesnt match the customer name in the .txt file."));
  }
  @Test
  void phoneBillTextFileIncorrectFile0(@TempDir File tempDir) throws ParserException, IOException {
    // GIVEN that textFile is formatted incorrectly
    String[] args = {"Brandon", "905-394-4432", "945-413-3430", "1/1/1979 2:21 pm", "1/1/1979 5:03 pm"};
    File textFile = new File(tempDir, "emptyFile.txt");

    FileWriter writer = new FileWriter(textFile);
    writer.write("in.correct.format.clu.cdd.");
    writer.close();

    // WHEN the textFile is formatted incorrectly
    // THEN output should be: "The .txt file is formatted incorrectly. The correct format: customer;caller;callee;begin;end;"
    assertThat(Project3.phoneBill(args, true, String.valueOf(textFile), null), equalTo("The .txt file is formatted incorrectly. The correct format: customer;caller;callee;begin;end;"));
  }
  @Test
  void phoneBillTextFileIncorrectFile1(@TempDir File tempDir) throws ParserException, IOException {
    // GIVEN that textFile is formatted incorrectly
    String[] args = {"Brandon", "905-394-4432", "945-413-3430", "1/1/1979 2:21 pm", "1/1/1979 5:03 pm"};
    File textFile = new File(tempDir, "emptyFile.txt");

    FileWriter writer = new FileWriter(textFile);
    writer.write("Brandon;808-224-1234;808-324-6789;12/32/2012 1:30 pm;12/31/2012 1:35 pm");
    writer.close();

    // WHEN the textFile is formatted incorrectly
    // THEN output should be: "File cannot be parsed.. Beginning date can only be in the format: mm/dd/yy, hh:mm AM/PM."
    assertThat(Project3.phoneBill(args, true, String.valueOf(textFile), null), equalTo("File cannot be parsed.. Beginning date can only be in the format: mm/dd/yy, hh:mm AM/PM."));
  }
  @Test
  void phoneBillTextFileIncorrectFile2(@TempDir File tempDir) throws ParserException, IOException {
    // GIVEN that textFile is formatted incorrectly
    String[] args = {"Brandon", "905-394-4432", "945-413-3430", "1/1/1979 2:21 pm", "1/1/1979 5:03 pm"};
    File textFile = new File(tempDir, "emptyFile.txt");

    FileWriter writer = new FileWriter(textFile);
    writer.write("Brandon;808-224-1234;808-324-6789;01/31/202 01:30 pm;12/31/2012 1:35 pm");
    writer.close();

    // WHEN the textFile is formatted incorrectly
    // THEN output should be: "File cannot be parsed.. Beginning date can only be in the format: mm/dd/yy, hh:mm AM/PM."
    assertThat(Project3.phoneBill(args, true, String.valueOf(textFile), null), equalTo("File cannot be parsed.. Beginning date can only be in the format: mm/dd/yy, hh:mm AM/PM."));
  }
  @Test
  void phoneBillTextFileIncorrectFile3(@TempDir File tempDir) throws ParserException, IOException {
    // GIVEN that textFile is formatted incorrectly
    String[] args = {"Brandon", "905-394-4432", "945-413-3430", "1/1/1979 2:21 pm", "1/1/1979 5:03 pm"};
    File textFile = new File(tempDir, "emptyFile.txt");

    FileWriter writer = new FileWriter(textFile);
    writer.write("Brandon;808-24-1234;808-324-6789;01/31/22, 01:30 PM;12/31/2012, 1:35 PM");
    writer.close();

    // WHEN the textFile is formatted incorrectly
    // THEN output should be: "File cannot be parsed.. Caller number can only be in the format: nnn-nnn-nnnn."
    assertThat(Project3.phoneBill(args, true, String.valueOf(textFile), null), equalTo("File cannot be parsed.. Caller number can only be in the format: nnn-nnn-nnnn."));
  }
  @Test
  void phoneBillTextFileIncorrectFile4(@TempDir File tempDir) throws ParserException, IOException {
    // GIVEN that textFile is formatted incorrectly
    String[] args = {"Brandon", "905-394-4432", "945-413-3430", "1/1/1979 2:21 pm", "1/1/1979 5:03 pm"};
    File textFile = new File(tempDir, "emptyFile.txt");

    FileWriter writer = new FileWriter(textFile);
    writer.write("Brandon;808-242-1234;808-324-679;01/31/22, 01:30 PM;12/31/2012, 1:35 PM");
    writer.close();

    // WHEN the textFile is formatted incorrectly
    // THEN output should be: "File cannot be parsed.. Callee number can only be in the format: nnn-nnn-nnnn."
    assertThat(Project3.phoneBill(args, true, String.valueOf(textFile), null), equalTo("File cannot be parsed.. Callee number can only be in the format: nnn-nnn-nnnn."));
  }
  @Test
  void phoneBillIncorrectFile1(@TempDir File tempDir) throws ParserException, IOException {
    // GIVEN that textFile is formatted incorrectly
    String[] args = {"Brandon", "905-394-4432", "945-413-3430", "1/1/1979 2:21 pm", "1/1/1979 5:03 pm"};
    File textFile = new File(tempDir, "emptyFile.txt");

    FileWriter writer = new FileWriter(textFile);
    writer.write("in.correct.format.clu.cdd.");
    writer.close();

    // WHEN the textFile is formatted incorrectly
    // THEN output should be: "The .txt file is formatted incorrectly. The correct format: customer;caller;callee;begin;end;"
    assertThat(Project3.phoneBill(args, true, String.valueOf(textFile), null), equalTo("The .txt file is formatted incorrectly. The correct format: customer;caller;callee;begin;end;"));
  }
  @Test
  void phoneBillPrintPretty0(@TempDir File tempDir) throws ParserException, IOException {
    String customer = "Brandon";
    PhoneBill bill = new PhoneBill(customer);
    PhoneCall call1 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/2013 1:30 am", "2/23/2013 1:50 pm");
    PhoneCall call2 = new PhoneCall("808-324-4323", "905-234-4323", "2/25/2013 2:50 pm", "2/25/2013 3:30 pm");
    PhoneCall call3 = new PhoneCall("808-324-4323", "905-234-4323", "1/26/2012 1:30 pm", "1/26/2012 1:59 pm");

    bill.addPhoneCall(call1);
    bill.addPhoneCall(call2);
    bill.addPhoneCall(call3);

    File textFile = new File(tempDir, "emptyFile.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(bill);

    //GIVEN that all arguments are valid
    String[] args = {customer, "905-394-4432", "945-413-3430", "5/1/2014 2:21 pm", "5/1/2014 5:03 pm"};

    //WHEN arguments have passed validation, and a file path is given
    //THEN output should be: "Brandon's phone bill with 4 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 5/1/14, 2:21 PM to 5/1/14, 5:03 PM"
    assertThat(Project3.phoneBill(args, true, null, String.valueOf(textFile)), equalTo("Brandon's phone bill with 4 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 5/1/14, 2:21 PM to 5/1/14, 5:03 PM"));
  }
  @Test
  void phoneBillPrintPretty1(@TempDir File tempDir) throws ParserException, IOException {
    String customer = "Brandon";
    PhoneBill bill = new PhoneBill(customer);
    PhoneCall call1 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/2013 1:30 am", "2/23/2013 1:50 pm");
    PhoneCall call2 = new PhoneCall("808-324-4323", "905-234-4323", "2/25/2013 2:50 pm", "2/25/2013 3:30 pm");
    PhoneCall call3 = new PhoneCall("808-324-4323", "905-234-4323", "1/26/2012 1:30 pm", "1/26/2012 1:59 pm");

    bill.addPhoneCall(call1);
    bill.addPhoneCall(call2);
    bill.addPhoneCall(call3);

    File textFile = new File(tempDir, "emptyFile.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(bill);

    // GIVEN that customer name passed in the command line is different from the customer name in the .txt file
    String[] args = {"Joe1", "905-394-4432", "945-413-3430", "5/1/2014 2:21 pm", "5/1/2014 5:03 pm"};

    // WHEN Customer names are different from each other.
    // THEN output should be: "Customer name passed in the command line doesnt match the customer name in the .txt file."
    assertThat(Project3.phoneBill(args, true, null, String.valueOf(textFile)), equalTo("Customer name passed in the command line doesnt match the customer name in the .txt file."));
  }
  @Test
  void phoneBillPrintPrettyIncorrectFile0(@TempDir File tempDir) throws ParserException, IOException {
    // GIVEN that textFile is formatted incorrectly
    String[] args = {"Brandon", "905-394-4432", "945-413-3430", "1/1/1979 2:21 pm", "1/1/1979 5:03 pm"};
    File textFile = new File(tempDir, "emptyFile.txt");

    FileWriter writer = new FileWriter(textFile);
    writer.write("Brandon;808-224-1234;808-324-6789;01/31/12, 01:30 PM;1/31/2012, 1:5 pm");
    writer.close();

    // WHEN the textFile is formatted incorrectly
    // THEN output should be: "File cannot be parsed.. Ending date can only be in the format: mm/dd/yy hh:mm AM/PM."
    assertThat(Project3.phoneBill(args, true, null, String.valueOf(textFile)), equalTo("File cannot be parsed.. Ending date can only be in the format: mm/dd/yy, hh:mm AM/PM."));
  }
  @Test
  void phoneBillPrintPrettyIncorrectFile1(@TempDir File tempDir) throws ParserException, IOException {
    // GIVEN that textFile is formatted incorrectly
    String[] args = {"Brandon", "905-394-4432", "945-413-3430", "1/1/1979 2:21 pm", "1/1/1979 5:03 pm"};
    File textFile = new File(tempDir, "emptyFile.txt");

    FileWriter writer = new FileWriter(textFile);
    writer.write("in.correct.format.clu.cdd.");
    writer.close();

    // WHEN the textFile is formatted incorrectly
    // THEN output should be: "The .txt file is formatted incorrectly. The correct format: customer;caller;callee;begin;end;"
    assertThat(Project3.phoneBill(args, true, null, String.valueOf(textFile)), equalTo("The .txt file is formatted incorrectly. The correct format: customer;caller;callee;begin;end;"));
  }
}
