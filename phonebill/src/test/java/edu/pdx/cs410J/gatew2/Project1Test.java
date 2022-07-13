package edu.pdx.cs410J.gatew2;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import javax.swing.plaf.FileChooserUI;
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
class Project1Test {
  // Unit tests for checkForInputOptions(String[] args)
  @Test
  void checkForInputOptionsREADME0() throws IOException, ParserException {
    //GIVEN that the '-README' option is entered
    String[] args = {"-README", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03"};

    //WHEN '-README' option is entered
    //THEN output should be: README.txt
    assertThat(Project1.checkForInputOptions(args), containsString(String.valueOf(Files.readString(Path.of("/Users/brandongatewood/Desktop/Summer 2022/PortlandStateJavaSummer2022/phonebill/src/main/resources/edu/pdx/cs410J/gatew2/README.txt")))));
  }

  @Test
  void checkForInputOptionsREADME1() throws IOException, ParserException {
    //GIVEN that the '-README' option is entered
    String[] args = {"-README", "-print", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03"};

    //WHEN '-README' option is entered
    //THEN output should be: README.txt
    assertThat(Project1.checkForInputOptions(args), containsString(String.valueOf(Files.readString(Path.of("/Users/brandongatewood/Desktop/Summer 2022/PortlandStateJavaSummer2022/phonebill/src/main/resources/edu/pdx/cs410J/gatew2/README.txt")))));
  }

  @Test
  void checkForInputOptionsREADME2() throws IOException, ParserException {
    //GIVEN that the '-README' option is entered
    String[] args = {"-print", "-README", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03"};

    //WHEN '-README' option is entered
    //THEN output should be: README.txt
    assertThat(Project1.checkForInputOptions(args), containsString(String.valueOf(Files.readString(Path.of("/Users/brandongatewood/Desktop/Summer 2022/PortlandStateJavaSummer2022/phonebill/src/main/resources/edu/pdx/cs410J/gatew2/README.txt")))));
  }
  @Test
  void checkForInputOptionsPrint0() throws IOException, ParserException {
    //GIVEN that the '-print' option is entered
    String[] args = {"-print", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03"};

    //WHEN '-print' option is entered
    //THEN output should be "Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/1979 2:21 to 1/1/1979 5:03"
    assertThat(Project1.checkForInputOptions(args), equalTo("Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/1979 2:21 to 1/1/1979 5:03"));
  }
  @Test
  void checkForInputOptionsTextFile0() throws IOException, ParserException {
    //GIVEN that the '-textFile' option is entered
    String[] args = {"-textFile", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03"};

    //WHEN '-textFile' option is entered with no destination file
    //THEN output should be "To use '-textFile' option, it must be in the order '-textFile' '.txt'."
    assertThat(Project1.checkForInputOptions(args), equalTo("To use '-textFile' option, it must be in the order '-textFile' '.txt'."));
  }
  @Test
  void checkForInputOptionsTextFile1(@TempDir File tempDir) throws IOException, ParserException {
    File textFile = new File(tempDir, "brandon.txt");

    //GIVEN that the '-textFile' option is entered with no 'print' option
    String[] args = {"-textFile", String.valueOf(textFile), "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03"};

    //WHEN '-textFile' option is entered with a destination file
    //THEN output should be ""
    assertThat(Project1.checkForInputOptions(args), equalTo(""));
  }
  @Test
  void checkForInputOptionsTextFile2(@TempDir File tempDir) throws IOException, ParserException {
    File textFile = new File(tempDir, "brandon.txt");

    //GIVEN that the '-textFile' and '-print' option is entered
    String[] args = {"-print", "-textFile", String.valueOf(textFile), "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03"};

    //WHEN '-textFile' and '-print' option is entered
    //THEN output should be "Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/1979 2:21 to 1/1/1979 5:03"
    assertThat(Project1.checkForInputOptions(args), equalTo("Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/1979 2:21 to 1/1/1979 5:03"));
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
    String[] args = {"-textFile", String.valueOf(textFile), "-print", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03"};

    //WHEN '-textFile' and '-print' option
    //THEN output should be "Brandon's phone bill with 4 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/1979 2:21 to 1/1/1979 5:03"
    assertThat(Project1.checkForInputOptions(args), equalTo("Brandon's phone bill with 4 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/1979 2:21 to 1/1/1979 5:03"));
  }
  @Test
  void checkForInputOptionsNoREADMEOrPrintOrTextFile() throws IOException, ParserException {
    //Given that no '-README' or '-print' option is entered.
    String[] args = {"Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03"};

    //WHEN no '-README' or '-print' option is entered
    //THEN output should be ""
    assertThat(Project1.checkForInputOptions(args), equalTo(""));
  }

  // Unit tests for validateArgLength(String[] args, boolean print)
  @Test
  void validateArgLength1() throws IOException, ParserException {
    //Given that there is 1 command line argument
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");

    //WHEN 1 command line arguments are included
    //THEN output should be "Missing caller number (nnn-nnn-nnnn)"
    assertThat(Project1.validateArgLength(args, false, ""), equalTo("Missing caller number (nnn-nnn-nnnn)"));
  }
  @Test
  void validateArgLength2() throws IOException, ParserException {
    //Given that there are 2 command line arguments
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");

    //WHEN 2 command line arguments are included
    //THEN output should be "Missing callee number (nnn-nnn-nnnn)"
    assertThat(Project1.validateArgLength(args, false, ""), equalTo("Missing callee number (nnn-nnn-nnnn)"));
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
    assertThat(Project1.validateArgLength(args, false, ""), equalTo("Missing beginning date (mm/dd/yyyy)"));
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
    assertThat(Project1.validateArgLength(args, false, ""), equalTo("Missing beginning time (hh:mm)"));
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
    //THEN output should be "Missing ending date (mm/dd/yyyy)"
    assertThat(Project1.validateArgLength(args, false, ""), equalTo("Missing ending date (mm/dd/yyyy)"));
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
    args.add("1/1/2029");

    //WHEN 6 command line arguments are included
    //THEN output should be "Missing ending time (hh:mm)"
    assertThat(Project1.validateArgLength(args, false, ""), equalTo("Missing ending time (hh:mm)"));
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
    args.add("1/1/2029");
    args.add("1:30");

    //WHEN 7 command line arguments are included, with print flag = false
    //THEN output should be ""
    assertThat(Project1.validateArgLength(args, false, null), equalTo(""));

  }
  @Test
  void validateArgLength8() throws IOException, ParserException {
    //Given that there are 8+ command line arguments
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-4430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("1:30");
    args.add("1/1/2029");
    args.add("1:30");

    //WHEN 8+ command line arguments are included
    //THEN output should be "Too many arguments"
    assertThat(Project1.validateArgLength(args, false, ""), equalTo("Too many arguments"));
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
    args.add("1/1/2029");
    args.add("1:40");

    //WHEN correct amount of command line arguments are included
    //THEN caller number must be validated.
    //WHEN caller number argument is invalid
    //THEN output should be "Caller number can only be in the format: nnn-nnn-nnnn"
    assertThat(Project1.validateEachArgument(args, false, ""), equalTo("Caller number can only be in the format: nnn-nnn-nnnn"));
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
    args.add("1/1/2029");
    args.add("1:40");

    //WHEN correct amount of command line arguments are included
    //THEN caller number must be validated.
    //WHEN caller number argument is invalid
    //THEN output should be "Caller number can only be in the format: nnn-nnn-nnnn"
    assertThat(Project1.validateEachArgument(args, false, ""), equalTo("Caller number can only be in the format: nnn-nnn-nnnn"));
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
    args.add("1/1/2029");
    args.add("1:40");

    //WHEN correct amount of command line arguments are included
    //THEN caller number must be validated.
    //WHEN caller number argument is invalid
    //THEN output should be "Caller number can only be in the format: nnn-nnn-nnnn"
    assertThat(Project1.validateEachArgument(args, false, ""), equalTo("Caller number can only be in the format: nnn-nnn-nnnn"));
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
    args.add("1/1/2029");
    args.add("1:40");

    //WHEN correct amount of command line arguments are included
    //THEN caller number must be validated.
    //WHEN caller number argument is invalid
    //THEN output should be "Caller number can only be in the format: nnn-nnn-nnnn"
    assertThat(Project1.validateEachArgument(args, false, ""), equalTo("Caller number can only be in the format: nnn-nnn-nnnn"));
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
    args.add("1/1/2029");
    args.add("1:40");

    //WHEN correct amount of command line arguments are included
    //THEN caller number must be validated.
    //WHEN caller number argument is invalid
    //THEN output should be "Caller number can only be in the format: nnn-nnn-nnnn"
    assertThat(Project1.validateEachArgument(args, false, ""), equalTo("Caller number can only be in the format: nnn-nnn-nnnn"));
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
    args.add("1/1/2029");
    args.add("1:40");

    //WHEN correct amount of command line arguments are included
    //THEN callee number must be validated.
    //WHEN callee number argument is invalid
    //THEN output should be "Callee number can only be in the format: nnn-nnn-nnnn"
    assertThat(Project1.validateEachArgument(args, false, ""), equalTo("Callee number can only be in the format: nnn-nnn-nnnn"));
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
    args.add("1/1/2029");
    args.add("1:40");

    //WHEN correct amount of command line arguments are included
    //THEN callee number must be validated.
    //WHEN callee number argument is invalid
    //THEN output should be "Callee number can only be in the format: nnn-nnn-nnnn"
    assertThat(Project1.validateEachArgument(args, false, ""), equalTo("Callee number can only be in the format: nnn-nnn-nnnn"));
  }
  @Test
  void validateEachArgumentCallee2() throws IOException, ParserException {
    //Given that callee number is "213-4430" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("213-4430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("1/1/2029");
    args.add("1:40");

    //WHEN correct amount of command line arguments are included
    //THEN callee number must be validated.
    //WHEN callee number argument is invalid
    //THEN output should be "Callee number can only be in the format: nnn-nnn-nnnn"
    assertThat(Project1.validateEachArgument(args, false, ""), equalTo("Callee number can only be in the format: nnn-nnn-nnnn"));
  }
  @Test
  void validateEachArgumentCallee3() throws IOException, ParserException {
    //Given that caller number is "4433" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("4430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("1/1/2029");
    args.add("1:40");

    //WHEN correct amount of command line arguments are included
    //THEN callee number must be validated.
    //WHEN callee number argument is invalid
    //THEN output should be "Callee number can only be in the format: nnn-nnn-nnnn"
    assertThat(Project1.validateEachArgument(args, false, ""), equalTo("Callee number can only be in the format: nnn-nnn-nnnn"));
  }
  @Test
  void validateEachArgumentCallee4() throws IOException, ParserException {
    //Given that caller number is "905-213-30" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-30");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("1/1/2029");
    args.add("1:40");

    //WHEN correct amount of command line arguments are included
    //THEN callee number must be validated.
    //WHEN callee number argument is invalid
    //THEN output should be "Callee number can only be in the format: nnn-nnn-nnnn"
    assertThat(Project1.validateEachArgument(args, false, ""), equalTo("Callee number can only be in the format: nnn-nnn-nnnn"));
  }
  @Test
  void validateEachArgumentBeginDate0() throws IOException, ParserException {
    //Given that beginning date "1-1/2029" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1-1/2029");
    args.add("1:30");
    args.add("1/1/2029");
    args.add("1:40");

    //WHEN correct amount of command line arguments are included
    //THEN beginning date must be validated.
    //WHEN beginning date argument is invalid
    //THEN output should be "Beginning date can only be in the format: mm/dd/yyyy"
    assertThat(Project1.validateEachArgument(args, false, ""), equalTo("Beginning date can only be in the format: mm/dd/yyyy"));
  }

  @Test
  void validateEachArgumentBeginDate1() throws IOException, ParserException {
    //Given that beginning date is "1-1-2029" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1-1-2029");
    args.add("1:30");
    args.add("1/1/2029");
    args.add("1:40");

    //WHEN correct amount of command line arguments are included
    //THEN beginning date must be validated.
    //WHEN beginning date argument is invalid
    //THEN output should be "Beginning date can only be in the format: mm/dd/yyyy"
    assertThat(Project1.validateEachArgument(args, false, ""), equalTo("Beginning date can only be in the format: mm/dd/yyyy"));
  }
  @Test
  void validateEachArgumentBeginTime0() throws IOException, ParserException {
    //Given that beginning time is "a:30" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1/1/2029");
    args.add("a:30");
    args.add("1/1/2029");
    args.add("1:40");

    //WHEN correct amount of command line arguments are included
    //THEN beginning time must be validated.
    //WHEN beginning time argument is invalid
    //THEN output should be "Beginning time can only be in the format: hh:mm"
    assertThat(Project1.validateEachArgument(args, false, ""), equalTo("Beginning time can only be in the format: hh:mm"));
  }
  @Test
  void validateEachArgumentBeginTime1() throws IOException, ParserException {
    //Given that beginning time is "12:4b" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1/1/2029");
    args.add("12:4b");
    args.add("1/1/2029");
    args.add("1:40");

    //WHEN correct amount of command line arguments are included
    //THEN beginning time must be validated.
    //WHEN beginning time argument is invalid
    //THEN output should be "Beginning time can only be in the format: hh:mm"
    assertThat(Project1.validateEachArgument(args, false, ""), equalTo("Beginning time can only be in the format: hh:mm"));
  }
  @Test
  void validateEachArgumentEndDate0() throws IOException, ParserException {
    //Given that ending date is "1/1-a029" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("1/1-a029");
    args.add("1:40");

    //WHEN correct amount of command line arguments are included
    //THEN ending date must be validated.
    //WHEN ending date argument is invalid
    //THEN output should be "Ending date can only be in the format: mm/dd/yyyy"
    assertThat(Project1.validateEachArgument(args, false, ""), equalTo("Ending date can only be in the format: mm/dd/yyyy"));
  }
  @Test
  void validateEachArgumentEndDate1() throws IOException, ParserException {
    //Given that ending date is "11/2029" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("11/2029");
    args.add("1:40");

    //WHEN correct amount of command line arguments are included
    //THEN ending date must be validated.
    //WHEN ending date argument is invalid
    //THEN output should be "Ending date can only be in the format: mm/dd/yyyy"
    assertThat(Project1.validateEachArgument(args, false, ""), equalTo("Ending date can only be in the format: mm/dd/yyyy"));
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
    args.add("1/1/2029");
    args.add("1");

    //WHEN correct amount of command line arguments are included
    //THEN ending time must be validated.
    //WHEN ending time argument is invalid
    //THEN output should be "Ending time can only be in the format: hh:mm"
    assertThat(Project1.validateEachArgument(args, false, ""), equalTo("Ending time can only be in the format: hh:mm"));
  }
  @Test
  void validateEachArgumentEndTime1() throws IOException, ParserException {
    //Given that ending time is ":" and correct amount of args
    ArrayList<String> args = new ArrayList<>();
    args.add("Brandon Gatewood");
    args.add("905-214-4433");
    args.add("905-213-3430");
    args.add("1/1/2029");
    args.add("1:30");
    args.add("1/1/2029");
    args.add(":");

    //WHEN correct amount of command line arguments are included
    //THEN ending time must be validated.
    //WHEN ending time argument is invalid
    //THEN output should be "Ending time can only be in the format: hh:mm"
    assertThat(Project1.validateEachArgument(args, false, ""), equalTo("Ending time can only be in the format: hh:mm"));
  }

  // Unit tests for phoneBill(String[] args, boolean print)
  @Test
  void phoneBillPrint0() throws IOException, ParserException {
    //GIVEN that all arguments are valid
    String[] args = {"Brandon Gatewood", "905-214-4433", "905-213-3430", "1/1/2029 1:30", "1/1/2029 1:03"};

    //WHEN arguments have passed validation, and print flag = false
    //THEN output should be: ""
    assertThat(Project1.phoneBill(args, false, null), equalTo(""));
  }

  @Test
  void phoneBillPrint1() throws IOException, ParserException {
    //GIVEN that all arguments are valid
    String[] args = {"Brandon", "905-394-4432", "945-413-3430", "1/1/1979 2:21", "1/1/1979 5:03"};

    //WHEN arguments have passed validation, and print flag = true
    //THEN output should be: "Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/1979 to 2:21"
    assertThat(Project1.phoneBill(args, true, null), equalTo("Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/1979 2:21 to 1/1/1979 5:03"));
  }
  @Test
  void phoneBillEmptyFile(@TempDir File tempDir) throws ParserException, IOException {
    //GIVEN that all arguments are valid
    String[] args = {"Brandon", "905-394-4432", "945-413-3430", "1/1/1979 2:21", "1/1/1979 5:03"};
    File textFile = new File(tempDir, "emptyFile.txt");
    //WHEN arguments have passed validation, and a file path is given
    //THEN output should be: "Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/1979 to 2:21"
    assertThat(Project1.phoneBill(args, true, String.valueOf(textFile)), equalTo("Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/1979 2:21 to 1/1/1979 5:03"));
  }
  @Test
  void phoneBillFilledFile(@TempDir File tempDir) throws ParserException, IOException {
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
    String[] args = {"Brandon", "905-394-4432", "945-413-3430", "1/1/1979 2:21", "1/1/1979 5:03"};

    //WHEN arguments have passed validation, and a file path is given
    //THEN output should be: "Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/1979 to 2:21"
    assertThat(Project1.phoneBill(args, true, String.valueOf(textFile)), equalTo("Brandon's phone bill with 4 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/1979 2:21 to 1/1/1979 5:03"));
  }
}
