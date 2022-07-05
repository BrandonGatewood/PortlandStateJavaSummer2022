package edu.pdx.cs410J.gatew2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A unit test for code in the <code>Project1</code> class.  This is different
 * from <code>Project1IT</code> which is an integration test (and can capture data
 * written to {@link System#out} and the like.
 */
class Project1Test {

  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project1.class.getResourceAsStream("README.txt")
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString("This is a README file!"));
    }
  }
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;

  @BeforeEach
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }
  @AfterEach
  public void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

  void validateArguments(String[] copyArgs) {
    Project1 proj1 = new Project1();
    String args[] = new String[copyArgs.length];
    for(int i = 0; i < copyArgs.length; ++i) {
      args[i] = copyArgs[i];
    }

    proj1.main(args);
  }

  @Test
  void forValidatingCommandLineArguments0() {
    //Given that there is no command line arguments
    String args[] = new String[0];
    validateArguments(args);

    //WHEN no command line arguments are included
    //THEN output should be "Missing command line argument"
    assertThat(outContent.toString(), equalTo("Missing command line arguments\n"));
  }
  @Test
  void forValidatingCommandLineArguments1() {
    //Given that there is 1 command line arguments
    String args[] = {"Brandon"};
    validateArguments(args);

    //WHEN 1 command line arguments are included
    //THEN output should be "Missing caller number (nnn-nnn-nnnn)"
    assertThat(outContent.toString(), equalTo("Missing caller number (nnn-nnn-nnnn)\n"));
  }
  @Test
  void forValidatingCommandLineArguments2() {
    //Given that there is 1 command line arguments
    String args[] = {"Brandon Gatewood"};
    validateArguments(args);

    //WHEN 1 command line arguments are included
    //THEN output should be "Missing caller number (nnn-nnn-nnnn)"
    assertThat(outContent.toString(), equalTo("Missing caller number (nnn-nnn-nnnn)\n"));
  }
  @Test
  void forValidatingCommandLineArguments3() {
    //Given that there is 2 command line arguments
    String args[] = {"Brandon", "905-214-4433"};
    validateArguments(args);

    //WHEN 2 command line arguments are included
    //THEN output should be "Missing callee number (nnn-nnn-nnnn)"
    assertThat(outContent.toString(), equalTo("Missing callee number (nnn-nnn-nnnn)\n"));
  }

  @Test
  void forValidatingCommandLineArguments4() {
    //Given that there is 3 command line arguments
    String args[] = {"Brandon", "905-214-4433", "905-213-4430"};
    validateArguments(args);

    //WHEN 3 command line arguments are included
    //THEN output should be "Missing beginning date and time"
    assertThat(outContent.toString(), equalTo("Missing beginning date and time (mm/dd/yyyy hh:mm)\n"));
  }
  @Test
  void forValidatingCommandLineArguments5() {
    //Given that there is 4 command line arguments
    String args[] = {"Brandon", "905-214-4433", "905-213-4430", "1/1/2029 1:30"};
    validateArguments(args);

    //WHEN 4 command line arguments are included
    //THEN output should be "Missing ending date and time"
    assertThat(outContent.toString(), equalTo("Missing ending date and time (mm/dd/yyyy hh:mm)\n"));
  }
  @Test
  void forValidatingCommandLineArguments7() {
    //Given that there is 6+ command line arguments
    String args[] = {"Brandon", "905-214-4433", "905-213-4430", "1/1/2029 1:30", "1/1/2029 1:30", "905-213-4430", "1/1/2029 1:30"};
    validateArguments(args);

    //WHEN 5+ command line arguments are included
    //THEN output should be "Too many arguments"
    assertThat(outContent.toString(), equalTo("Too many arguments\n"));
  }
  @Test
  void forValidatingCallerArgument0() {
    //Given that caller number is "9052144433" and correct amount of args
    String args[] = {"Brandon Gatewood", "9052144433", "905-213-4430", "1/1/2029 1:30", "1/1/2029 1:40"};
    validateArguments(args);

    //WHEN correct amount of command line arguments are included
    //THEN caller number must be validated.
    //WHEN caller number argument is invalid
    //THEN output should be "Caller number can only be in the format: nnn-nnn-nnnn"
    assertThat(outContent.toString(), equalTo("Caller number can only be in the format: nnn-nnn-nnnn\n"));
  }
  @Test
  void forValidatingCallerArgument1() {
    //Given that caller number is "905-2144433" and correct amount of args
    String args[] = {"Brandon Gatewood", "905-2144433", "905-213-4430", "1/1/2029 1:30", "1/1/2029 1:40"};
    validateArguments(args);

    //WHEN correct amount of command line arguments are included
    //THEN caller number must be validated.
    //WHEN caller number argument is invalid
    //THEN output should be "Caller number can only be in the format: nnn-nnn-nnnn"
    assertThat(outContent.toString(), equalTo("Caller number can only be in the format: nnn-nnn-nnnn\n"));
  }
  @Test
  void forValidatingCallerArgument2() {
    //Given that caller number is "905-2144433" and correct amount of args
    String args[] = {"Brandon Gatewood", "905-2144433", "905-213-4430", "1/1/2029 1:30", "1/1/2029 1:40"};
    validateArguments(args);

    //WHEN correct amount of command line arguments are included
    //THEN caller number must be validated.
    //WHEN caller number argument is invalid
    //THEN output should be "Caller number can only be in the format: nnn-nnn-nnnn"
    assertThat(outContent.toString(), equalTo("Caller number can only be in the format: nnn-nnn-nnnn\n"));
  }
  @Test
  void forValidatingCallerArgument3() {
    //Given that caller number is "90e2144433" and correct amount of args
    String args[] = {"Brandon Gatewood", "90e2144433", "905-213-4430", "1/1/2029 1:30", "1/1/2029 1:40"};
    validateArguments(args);

    //WHEN correct amount of command line arguments are included
    //THEN caller number must be validated.
    //WHEN caller number argument is invalid
    //THEN output should be "Caller number can only be in the format: nnn-nnn-nnnn"
    assertThat(outContent.toString(), equalTo("Caller number can only be in the format: nnn-nnn-nnnn\n"));
  }
  @Test
  void forValidatingCallerArgument4() {
    //Given that caller number is "905-144-433" and correct amount of args
    String args[] = {"Brandon Gatewood", "905-144-433", "905-213-4430", "1/1/2029 1:30", "1/1/2029 1:40"};
    validateArguments(args);

    //WHEN correct amount of command line arguments are included
    //THEN caller number must be validated.
    //WHEN caller number argument is invalid
    //THEN output should be "Caller number can only be in the format: nnn-nnn-nnnn"
    assertThat(outContent.toString(), equalTo("Caller number can only be in the format: nnn-nnn-nnnn\n"));
  }
  @Test
  void forValidatingCalleeArgument0() {
    //Given that callee number is "9052134430" and correct amount of args
    String args[] = {"Brandon Gatewood", "905-214-4433", "9052134430", "1/1/2029 1:30", "1/1/2029 1:40"};
    validateArguments(args);

    //WHEN correct amount of command line arguments are included
    //THEN callee number must be validated.
    //WHEN callee number argument is invalid
    //THEN output should be "Callee number can only be in the format: nnn-nnn-nnnn"
    assertThat(outContent.toString(), equalTo("Callee number can only be in the format: nnn-nnn-nnnn\n"));
  }
  @Test
  void forValidatingCalleeArgument1() {
    //Given that callee number is "905a2134430" and correct amount of args
    String args[] = {"Brandon Gatewood", "905-214-4433", "905a2134430", "1/1/2029 1:30", "1/1/2029 1:40"};
    validateArguments(args);

    //WHEN correct amount of command line arguments are included
    //THEN callee number must be validated.
    //WHEN callee number argument is invalid
    //THEN output should be "Callee number can only be in the format: nnn-nnn-nnnn"
    assertThat(outContent.toString(), equalTo("Callee number can only be in the format: nnn-nnn-nnnn\n"));
  }
  @Test
  void forValidatingCalleeArgument2() {
    //Given that callee number is "213-4430" and correct amount of args
    String args[] = {"Brandon Gatewood", "905-214-4433", "213-4430", "1/1/2029 1:30", "1/1/2029 1:40"};
    validateArguments(args);

    //WHEN correct amount of command line arguments are included
    //THEN callee number must be validated.
    //WHEN callee number argument is invalid
    //THEN output should be "Callee number can only be in the format: nnn-nnn-nnnn"
    assertThat(outContent.toString(), equalTo("Callee number can only be in the format: nnn-nnn-nnnn\n"));
  }
  @Test
  void forValidatingCalleeArgument3() {
    //Given that caller number is "4433" and correct amount of args
    String args[] = {"Brandon Gatewood", "905-214-4433", "4430", "1/1/2029 1:30", "1/1/2029 1:40"};
    validateArguments(args);

    //WHEN correct amount of command line arguments are included
    //THEN callee number must be validated.
    //WHEN callee number argument is invalid
    //THEN output should be "Callee number can only be in the format: nnn-nnn-nnnn"
    assertThat(outContent.toString(), equalTo("Callee number can only be in the format: nnn-nnn-nnnn\n"));
  }
  @Test
  void forValidatingCalleeArgument4() {
    //Given that caller number is "905-213-30" and correct amount of args
    String args[] = {"Brandon Gatewood", "905-214-4433", "905-213-30", "1/1/2029 1:30", "1/1/2029 1:40"};
    validateArguments(args);

    //WHEN correct amount of command line arguments are included
    //THEN callee number must be validated.
    //WHEN callee number argument is invalid
    //THEN output should be "Callee number can only be in the format: nnn-nnn-nnnn"
    assertThat(outContent.toString(), equalTo("Callee number can only be in the format: nnn-nnn-nnnn\n"));
  }
  @Test
  void forValidatingBeginArgument0() {
    //Given that beginning date and time is "1-1/2029 1:30" and correct amount of args
    String args[] = {"Brandon Gatewood", "905-214-4433", "905-213-3430", "1-1/2029 1:30", "1/1/2029 1:40"};
    validateArguments(args);

    //WHEN correct amount of command line arguments are included
    //THEN beginning date and time must be validated.
    //WHEN beginning date and time argument is invalid
    //THEN output should be "Beginning date and time can only be in the format: mm/dd/yyyy hh:mm"
    assertThat(outContent.toString(), equalTo("Beginning date and time can only be in the format: mm/dd/yyyy hh:mm\n"));
  }
  @Test
  void forValidatingBeginArgument1() {
    //Given that beginning date and time is "1-1-2029 1:30" and correct amount of args
    String args[] = {"Brandon Gatewood", "905-214-4433", "905-213-3430", "1-1-2029 1:30", "1/1/2029 1:40"};
    validateArguments(args);

    //WHEN correct amount of command line arguments are included
    //THEN beginning date and time must be validated.
    //WHEN beginning date and time argument is invalid
    //THEN output should be "Beginning date and time can only be in the format: mm/dd/yyyy hh:mm"
    assertThat(outContent.toString(), equalTo("Beginning date and time can only be in the format: mm/dd/yyyy hh:mm\n"));
  }
  @Test
  void forValidatingBeginArgument2() {
    //Given that beginning date and time is "1/1/20291:30" and correct amount of args
    String args[] = {"Brandon Gatewood", "905-214-4433", "905-213-3430", "1/1/20291:30", "1/1/2029 1:40"};
    validateArguments(args);

    //WHEN correct amount of command line arguments are included
    //THEN beginning date and time must be validated.
    //WHEN beginning date and time argument is invalid
    //THEN output should be "Beginning date and time can only be in the format: mm/dd/yyyy hh:mm"
    assertThat(outContent.toString(), equalTo("Beginning date and time can only be in the format: mm/dd/yyyy hh:mm\n"));
  }
  @Test
  void forValidatingBeginArgument3() {
    //Given that beginning date and time is "1/1/2029 a:30" and correct amount of args
    String args[] = {"Brandon Gatewood", "905-214-4433", "905-213-3430", "1/1/2029 a:30", "1/1/2029 1:40"};
    validateArguments(args);

    //WHEN correct amount of command line arguments are included
    //THEN beginning date and time must be validated.
    //WHEN beginning date and time argument is invalid
    //THEN output should be "Beginning date and time can only be in the format: mm/dd/yyyy hh:mm"
    assertThat(outContent.toString(), equalTo("Beginning date and time can only be in the format: mm/dd/yyyy hh:mm\n"));
  }
  @Test
  void forValidatingEndArgument0() {
    //Given that ending date and time is "1/1/2029 1" and correct amount of args
    String args[] = {"Brandon Gatewood", "905-214-4433", "905-213-3430", "1/1/2029 1:30", "1/1/2029 1"};
    validateArguments(args);

    //WHEN correct amount of command line arguments are included
    //THEN ending date and time must be validated.
    //WHEN ending date and time argument is invalid
    //THEN output should be "Ending date and time can only be in the format: mm/dd/yyyy hh:mm"
    assertThat(outContent.toString(), equalTo("Ending date and time can only be in the format: mm/dd/yyyy hh:mm\n"));
  }
  @Test
  void forValidatingEndArgument1() {
    //Given that ending date and time is "1/1/2029 1:" and correct amount of args
    String args[] = {"Brandon Gatewood", "905-214-4433", "905-213-3430", "1/1/2029 1:30", "1/1/2029 1:"};
    validateArguments(args);

    //WHEN correct amount of command line arguments are included
    //THEN ending date and time must be validated.
    //WHEN ending date and time argument is invalid
    //THEN output should be "Ending date and time can only be in the format: mm/dd/yyyy hh:mm"
    assertThat(outContent.toString(), equalTo("Ending date and time can only be in the format: mm/dd/yyyy hh:mm\n"));
  }
  @Test
  void forValidatingEndArgument2() {
    //Given that ending date and time is "1/1/2029 1" and correct amount of args
    String args[] = {"Brandon Gatewood", "905-214-4433", "905-213-3430", "1/1/2029 1:30", "1/1/2029 1:03^3"};
    validateArguments(args);

    //WHEN correct amount of command line arguments are included
    //THEN ending date and time must be validated.
    //WHEN ending date and time argument is invalid
    //THEN output should be "Ending date and time can only be in the format: mm/dd/yyyy hh:mm"
    assertThat(outContent.toString(), equalTo("Ending date and time can only be in the format: mm/dd/yyyy hh:mm\n"));
  }
  @Test
  void forAllArgumentsValid0() {
    //GIVEN that all arguments are valid
    String args[] = {"Brandon Gatewood", "905-214-4433", "905-213-3430", "1/1/2029 1:30", "1/1/2029 1:03"};
    validateArguments(args);

    //WHEN arguments have passed validation
    //THEN output should be:
    //"Brandon Gatewood's phone bill with 1 phone calls\n"
    //"Phone call from 905-214-4433 to 905-213-3430 from  1/1/2029 1:30 to 1/1/2029 1:03"
    assertThat(outContent.toString(), equalTo("Brandon Gatewood's phone bill with 1 phone calls\nPhone call from 905-214-4433 to 905-213-3430 from 1/1/2029 1:30 to 1/1/2029 1:03\n"));
  }
  @Test
  void forAllArgumentsValid1() {
    //GIVEN that all arguments are valid
    String args[] = {"Brandon", "905-394-4432", "945-413-3430", "1/1/1979 2:21", "1/1/1979 5:03"};
    validateArguments(args);

    //WHEN arguments have passed validation
    //THEN output should be:
    //"Brandon's phone bill with 1 phone calls\n"
    //"Phone call from 905-394-4432 to 945-413-3430 from 1/1/1979 2:21 to 1/1/1979 5:03"
    assertThat(outContent.toString(), equalTo("Brandon's phone bill with 1 phone calls\nPhone call from 905-394-4432 to 945-413-3430 from 1/1/1979 2:21 to 1/1/1979 5:03\n"));
  }
}
