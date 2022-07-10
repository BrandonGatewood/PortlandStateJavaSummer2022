package edu.pdx.cs410J.gatew2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.*;

import javax.swing.plaf.FileChooserUI;
import java.io.*;

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
  void checkForInputOptionsREADME0() {
    //GIVEN that the '-README' option is entered
    String[] args = {"-README", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03"};

    //WHEN '-README' option is entered
    //THEN output should be: "Brandon Gatewood\nProject 1\n\nThis project represents a phone bill application. It keeps track of a customer and their phone calls. Each phone call is saved into a list for their phone bill.\n\nTo use run this project on the command, all args are mandatory:\njava -jar target/phonebill-2022.0.0.jar [options] <args>\n\noptions:\n-print, -README\n\nargs:\ncustomer, caller number, callee number, begin date, begin time, end date, end time.\n"
    assertThat(Project1.checkForInputOptions(args), equalTo("Brandon Gatewood\nProject 1\n\nThis project represents a phone bill application. It keeps track of a customer and their phone calls. Each phone call is saved into a list for their phone bill.\n\nTo use run this project on the command, all args are mandatory:\njava -jar target/phonebill-2022.0.0.jar [options] <args>\n\noptions:\n-print, -README\n\nargs:\ncustomer, caller number, callee number, begin date, begin time, end date, end time.\n"));
  }

  @Test
  void checkForInputOptionsREADME1() {
    //GIVEN that the '-README' option is entered
    String[] args = {"-README", "-print", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03"};

    //WHEN '-README' option is entered
    //THEN output should be: "Brandon Gatewood\nProject 1\n\nThis project represents a phone bill application. It keeps track of a customer and their phone calls. Each phone call is saved into a list for their phone bill.\n\nTo use run this project on the command, all args are mandatory:\njava -jar target/phonebill-2022.0.0.jar [options] <args>\n\noptions:\n-print, -README\n\nargs:\ncustomer, caller number, callee number, begin date, begin time, end date, end time.\n"
    assertThat(Project1.checkForInputOptions(args), equalTo("Brandon Gatewood\nProject 1\n\nThis project represents a phone bill application. It keeps track of a customer and their phone calls. Each phone call is saved into a list for their phone bill.\n\nTo use run this project on the command, all args are mandatory:\njava -jar target/phonebill-2022.0.0.jar [options] <args>\n\noptions:\n-print, -README\n\nargs:\ncustomer, caller number, callee number, begin date, begin time, end date, end time.\n"));
  }

  @Test
  void checkForInputOptionsREADME2() {
    //GIVEN that the '-README' option is entered
    String[] args = {"-print", "-README", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03"};

    //WHEN '-README' option is entered
    //THEN output should be: "Brandon Gatewood\nProject 1\n\nThis project represents a phone bill application. It keeps track of a customer and their phone calls. Each phone call is saved into a list for their phone bill.\n\nTo use run this project on the command, all args are mandatory:\njava -jar target/phonebill-2022.0.0.jar [options] <args>\n\noptions:\n-print, -README\n\nargs:\ncustomer, caller number, callee number, begin date, begin time, end date, end time.\n"
    assertThat(Project1.checkForInputOptions(args), equalTo("Brandon Gatewood\nProject 1\n\nThis project represents a phone bill application. It keeps track of a customer and their phone calls. Each phone call is saved into a list for their phone bill.\n\nTo use run this project on the command, all args are mandatory:\njava -jar target/phonebill-2022.0.0.jar [options] <args>\n\noptions:\n-print, -README\n\nargs:\ncustomer, caller number, callee number, begin date, begin time, end date, end time.\n"));
  }

  @Test
  void checkForInputOptionsPrint0() {
    //GIVEN that the '-print' option is entered
    String[] args = {"-print", "Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03"};

    //WHEN '-print' option is entered
    //THEN output should be "Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/1979 2:21 to 1/1/1979 5:03"
    assertThat(Project1.checkForInputOptions(args), equalTo("Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/1979 2:21 to 1/1/1979 5:03"));
  }

  @Test
  void checkForInputOptionsNoREADMEOrPrint0() {
    //Given that no '-README' or '-print' option is entered.
    String[] args = {"Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03"};

    //WHEN no '-README' or '-print' option is entered
    //THEN output should be ""
    assertThat(Project1.checkForInputOptions(args), equalTo(""));
  }

  // Unit tests for validateArgLength(String[] args, boolean print)
  @Test
  void validateArgLength1() {
    //Given that there is 1 command line argument
    String[] args = {"Brandon Gatewood"};

    //WHEN 1 command line arguments are included
    //THEN output should be "Missing caller number (nnn-nnn-nnnn)"
    assertThat(Project1.validateArgLength(args, false), equalTo("Missing caller number (nnn-nnn-nnnn)"));
  }
  @Test
  void validateArgLength2() {
    //Given that there are 2 command line arguments
    String[] args = {"Brandon", "905-214-4433"};

    //WHEN 2 command line arguments are included
    //THEN output should be "Missing callee number (nnn-nnn-nnnn)"
    assertThat(Project1.validateArgLength(args, false), equalTo("Missing callee number (nnn-nnn-nnnn)"));
  }
  @Test
  void validateArgLength3() {
    //Given that there are 3 command line arguments
    String[] args = {"Brandon", "905-214-4433", "905-213-4430"};

    //WHEN 3 command line arguments are included
    //THEN output should be "Missing beginning date (mm/dd/yyyy)"
    assertThat(Project1.validateArgLength(args, false), equalTo("Missing beginning date (mm/dd/yyyy)"));
  }
  @Test
  void validateArgLength4() {
    //Given that there are 4 command line arguments
    String[] args = {"Brandon", "905-214-4433", "905-213-4430", "1/1/2029"};

    //WHEN 4 command line arguments are included
    //THEN output should be "Missing beginning time (hh:mm)"
    assertThat(Project1.validateArgLength(args, false), equalTo("Missing beginning time (hh:mm)"));
  }
  @Test
  void validateArgLength5() {
    //Given that there are 5 command line arguments
    String[] args = {"Brandon", "905-214-4433", "905-213-4430", "1/1/2029", "1:30"};

    //WHEN 5 command line arguments are included
    //THEN output should be "Missing ending date (mm/dd/yyyy)"
    assertThat(Project1.validateArgLength(args, false), equalTo("Missing ending date (mm/dd/yyyy)"));
  }
  @Test
  void validateArgLength6() {
    //Given that there are 6 command line arguments
    String[] args = {"Brandon", "905-214-4433", "905-213-4430", "1/1/2029", "1:30", "1/1/2029"};

    //WHEN 6 command line arguments are included
    //THEN output should be "Missing ending time (hh:mm)"
    assertThat(Project1.validateArgLength(args, false), equalTo("Missing ending time (hh:mm)"));
  }
  @Test
  void validateArgLength7() {
    //Given that there are 7 command line arguments
    String[] args = {"Brandon", "905-214-4433", "905-213-4430", "1/1/2029", "1:30", "1/1/2029", "1:30"};

    //WHEN 7 command line arguments are included, with print flag = false
    //THEN output should be ""
    assertThat(Project1.validateArgLength(args, false), equalTo(""));

  }
  @Test
  void validateArgLength8() {
    //Given that there are 8+ command line arguments
    String[] args = {"Brandon", "905-214-4433", "905-213-4430", "1/1/2029", "1:30", "1/1/2029", "dsf", "1:30", "Brandon"};

    //WHEN 8+ command line arguments are included
    //THEN output should be "Too many arguments"
    assertThat(Project1.validateArgLength(args, false), equalTo("Too many arguments"));
  }

  // Unit tests for validateEachArgument(String[] args, boolean print)
  @Test
  void validateEachArgumentCaller0() {
    //Given that caller number is "9052144433" and correct amount of args
    String[] args = {"Brandon Gatewood", "9052144433", "905-213-4430", "1/1/2029", "1:30", "1/1/2029", "1:40"};

    //WHEN correct amount of command line arguments are included
    //THEN caller number must be validated.
    //WHEN caller number argument is invalid
    //THEN output should be "Caller number can only be in the format: nnn-nnn-nnnn"
    assertThat(Project1.validateEachArgument(args, false), equalTo("Caller number can only be in the format: nnn-nnn-nnnn"));
  }
  @Test
  void validateEachArgumentCaller1() {
    //Given that caller number is "905-2144433" and correct amount of args
    String[] args = {"Brandon Gatewood", "905-2144433", "905-213-4430", "1/1/2029", "1:30", "1/1/2029", "1:40"};

    //WHEN correct amount of command line arguments are included
    //THEN caller number must be validated.
    //WHEN caller number argument is invalid
    //THEN output should be "Caller number can only be in the format: nnn-nnn-nnnn"
    assertThat(Project1.validateEachArgument(args, false), equalTo("Caller number can only be in the format: nnn-nnn-nnnn"));
  }
  @Test
  void validateEachArgumentCaller2() {
    //Given that caller number is "905-2144433" and correct amount of args
    String[] args = {"Brandon Gatewood", "905-2144433", "905-213-4430", "1/1/2029", "1:30", "1/1/2029", "1:40"};

    //WHEN correct amount of command line arguments are included
    //THEN caller number must be validated.
    //WHEN caller number argument is invalid
    //THEN output should be "Caller number can only be in the format: nnn-nnn-nnnn"
    assertThat(Project1.validateEachArgument(args, false), equalTo("Caller number can only be in the format: nnn-nnn-nnnn"));
  }
  @Test
  void validateEachArgumentCaller3() {
    //Given that caller number is "90e2144433" and correct amount of args
    String[] args = {"Brandon Gatewood", "90e2144433", "905-213-4430", "1/1/2029", "1:30", "1/1/2029", "1:40"};

    //WHEN correct amount of command line arguments are included
    //THEN caller number must be validated.
    //WHEN caller number argument is invalid
    //THEN output should be "Caller number can only be in the format: nnn-nnn-nnnn"
    assertThat(Project1.validateEachArgument(args, false), equalTo("Caller number can only be in the format: nnn-nnn-nnnn"));
  }
  @Test
  void validateEachArgumentCaller4() {
    //Given that caller number is "905-144-433" and correct amount of args
    String[] args = {"Brandon Gatewood", "905-144-433", "905-213-4430", "1/1/2029", "1:30", "1/1/2029", "1:40"};

    //WHEN correct amount of command line arguments are included
    //THEN caller number must be validated.
    //WHEN caller number argument is invalid
    //THEN output should be "Caller number can only be in the format: nnn-nnn-nnnn"
    assertThat(Project1.validateEachArgument(args, false), equalTo("Caller number can only be in the format: nnn-nnn-nnnn"));
  }
  @Test
  void validateEachArgumentCallee0() {
    //Given that callee number is "9052134430" and correct amount of args
    String[] args = {"Brandon Gatewood", "905-214-4433", "9052134430", "1/1/2029", "1:30", "1/1/2029", "1:40"};

    //WHEN correct amount of command line arguments are included
    //THEN callee number must be validated.
    //WHEN callee number argument is invalid
    //THEN output should be "Callee number can only be in the format: nnn-nnn-nnnn"
    assertThat(Project1.validateEachArgument(args, false), equalTo("Callee number can only be in the format: nnn-nnn-nnnn"));
  }
  @Test
  void validateEachArgumentCallee1() {
    //Given that callee number is "905a2134430" and correct amount of args
    String[] args = {"Brandon Gatewood", "905-214-4433", "905a2134430", "1/1/2029", "1:30", "1/1/2029", "1:40"};

    //WHEN correct amount of command line arguments are included
    //THEN callee number must be validated.
    //WHEN callee number argument is invalid
    //THEN output should be "Callee number can only be in the format: nnn-nnn-nnnn"
    assertThat(Project1.validateEachArgument(args, false), equalTo("Callee number can only be in the format: nnn-nnn-nnnn"));
  }
  @Test
  void validateEachArgumentCallee2() {
    //Given that callee number is "213-4430" and correct amount of args
    String[] args = {"Brandon Gatewood", "905-214-4433", "213-4430", "1/1/2029", "1:30", "1/1/2029", "1:40"};

    //WHEN correct amount of command line arguments are included
    //THEN callee number must be validated.
    //WHEN callee number argument is invalid
    //THEN output should be "Callee number can only be in the format: nnn-nnn-nnnn"
    assertThat(Project1.validateEachArgument(args, false), equalTo("Callee number can only be in the format: nnn-nnn-nnnn"));
  }
  @Test
  void validateEachArgumentCallee3() {
    //Given that caller number is "4433" and correct amount of args
    String[] args = {"Brandon Gatewood", "905-214-4433", "4430", "1/1/2029", "1:30", "1/1/2029", "1:40"};

    //WHEN correct amount of command line arguments are included
    //THEN callee number must be validated.
    //WHEN callee number argument is invalid
    //THEN output should be "Callee number can only be in the format: nnn-nnn-nnnn"
    assertThat(Project1.validateEachArgument(args, false), equalTo("Callee number can only be in the format: nnn-nnn-nnnn"));
  }
  @Test
  void validateEachArgumentCallee4() {
    //Given that caller number is "905-213-30" and correct amount of args
    String[] args = {"Brandon Gatewood", "905-214-4433", "905-213-30", "1/1/2029", "1:30", "1/1/2029", "1:40"};

    //WHEN correct amount of command line arguments are included
    //THEN callee number must be validated.
    //WHEN callee number argument is invalid
    //THEN output should be "Callee number can only be in the format: nnn-nnn-nnnn"
    assertThat(Project1.validateEachArgument(args, false), equalTo("Callee number can only be in the format: nnn-nnn-nnnn"));
  }
  @Test
  void validateEachArgumentBeginDate0() {
    //Given that beginning date "1-1/2029" and correct amount of args
    String[] args = {"Brandon Gatewood", "905-214-4433", "905-213-3430", "1-1/2029", "1:30", "1/1/2029", "1:40"};

    //WHEN correct amount of command line arguments are included
    //THEN beginning date must be validated.
    //WHEN beginning date argument is invalid
    //THEN output should be "Beginning date can only be in the format: mm/dd/yyyy"
    assertThat(Project1.validateEachArgument(args, false), equalTo("Beginning date can only be in the format: mm/dd/yyyy"));
  }

  @Test
  void validateEachArgumentBeginDate1() {
    //Given that beginning date is "1-1-2029" and correct amount of args
    String[] args = {"Brandon Gatewood", "905-214-4433", "905-213-3430", "1-1-2029", "1:30", "1/1/2029", "1:40"};

    //WHEN correct amount of command line arguments are included
    //THEN beginning date must be validated.
    //WHEN beginning date argument is invalid
    //THEN output should be "Beginning date can only be in the format: mm/dd/yyyy"
    assertThat(Project1.validateEachArgument(args, false), equalTo("Beginning date can only be in the format: mm/dd/yyyy"));
  }
  @Test
  void validateEachArgumentBeginTime0() {
    //Given that beginning time is "a:30" and correct amount of args
    String[] args = {"Brandon Gatewood", "905-214-4433", "905-213-3430", "1/1/2029", "a:30", "1/1/2029", "1:40"};

    //WHEN correct amount of command line arguments are included
    //THEN beginning time must be validated.
    //WHEN beginning time argument is invalid
    //THEN output should be "Beginning time can only be in the format: hh:mm"
    assertThat(Project1.validateEachArgument(args, false), equalTo("Beginning time can only be in the format: hh:mm"));
  }
  @Test
  void validateEachArgumentBeginTime1() {
    //Given that beginning time is "12:4b" and correct amount of args
    String[] args = {"Brandon Gatewood", "905-214-4433", "905-213-3430", "1/1/2029", "12:4b", "1/1/2029", "1:40"};

    //WHEN correct amount of command line arguments are included
    //THEN beginning time must be validated.
    //WHEN beginning time argument is invalid
    //THEN output should be "Beginning time can only be in the format: hh:mm"
    assertThat(Project1.validateEachArgument(args, false), equalTo("Beginning time can only be in the format: hh:mm"));
  }
  @Test
  void validateEachArgumentEndDate0() {
    //Given that ending date is "1/1-a029" and correct amount of args
    String[] args = {"Brandon Gatewood", "905-214-4433", "905-213-3430", "1/1/2029", "1:30", "1/1-a029", "1:00"};

    //WHEN correct amount of command line arguments are included
    //THEN ending date must be validated.
    //WHEN ending date argument is invalid
    //THEN output should be "Ending date can only be in the format: mm/dd/yyyy"
    assertThat(Project1.validateEachArgument(args, false), equalTo("Ending date can only be in the format: mm/dd/yyyy"));
  }
  @Test
  void validateEachArgumentEndDate1() {
    //Given that ending date is "11/2029" and correct amount of args
    String[] args = {"Brandon Gatewood", "905-214-4433", "905-213-3430", "1/1/2029", "1:30", "11/2029", "2:30"};

    //WHEN correct amount of command line arguments are included
    //THEN ending date must be validated.
    //WHEN ending date argument is invalid
    //THEN output should be "Ending date can only be in the format: mm/dd/yyyy"
    assertThat(Project1.validateEachArgument(args, false), equalTo("Ending date can only be in the format: mm/dd/yyyy"));
  }
  @Test
  void validateEachArgumentEndTime0() {
    //Given that ending time is "1" and correct amount of args
    String[] args = {"Brandon Gatewood", "905-214-4433", "905-213-3430", "1/1/2029", "1:30", "1/1/2029", "1"};

    //WHEN correct amount of command line arguments are included
    //THEN ending time must be validated.
    //WHEN ending time argument is invalid
    //THEN output should be "Ending time can only be in the format: hh:mm"
    assertThat(Project1.validateEachArgument(args, false), equalTo("Ending time can only be in the format: hh:mm"));
  }
  @Test
  void validateEachArgumentEndTime1() {
    //Given that ending time is ":" and correct amount of args
    String[] args = {"Brandon Gatewood", "905-214-4433", "905-213-3430", "1/1/2029", "1:30", "1/1/2029", ":"};

    //WHEN correct amount of command line arguments are included
    //THEN ending time must be validated.
    //WHEN ending time argument is invalid
    //THEN output should be "Ending time can only be in the format: hh:mm"
    assertThat(Project1.validateEachArgument(args, false), equalTo("Ending time can only be in the format: hh:mm"));
  }

  // Unit tests for phoneBill(String[] args, boolean print)
  @Test
  void phoneBill0() {
    //GIVEN that all arguments are valid
    String[] args = {"Brandon Gatewood", "905-214-4433", "905-213-3430", "1/1/2029", "1:30", "1/1/2029", "1:03"};

    //WHEN arguments have passed validation, and print flag = false
    //THEN output should be: ""
    assertThat(Project1.phoneBill(args, false), equalTo(""));
  }

  @Test
  void phoneBill1() {
    //GIVEN that all arguments are valid
    String[] args = {"Brandon", "905-394-4432", "945-413-3430", "1/1/1979", "2:21", "1/1/1979", "5:03"};

    //WHEN arguments have passed validation, and print flag = true
    //THEN output should be: "Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/1979 to 2:21"
    assertThat(Project1.phoneBill(args, true), equalTo("Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/1979 to 2:21"));
  }

}
