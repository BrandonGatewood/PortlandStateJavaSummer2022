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
  void forValidatingCommandLineArguments6() {
    //Given that there is 5+ command line arguments
    String args[] = {"Brandon", "905-214-4433", "905-213-4430", "1/1/2029 1:30", "TOOMANY"};
    validateArguments(args);

    //WHEN 5+ command line arguments are included
    //THEN output should be "Too many arguments"
    assertThat(outContent.toString(), equalTo("Too many arguments\n"));
  }
  @Test
  void forValidatingCommandLineArguments7() {
    //Given that there is 5+ command line arguments
    String args[] = {"Brandon", "905-214-4433", "905-213-4430", "1/1/2029 1:30", "TOOMANY", "905-213-4430", "1/1/2029 1:30"};
    validateArguments(args);

    //WHEN 5+ command line arguments are included
    //THEN output should be "Too many arguments"
    assertThat(outContent.toString(), equalTo("Too many arguments\n"));
  }
}
