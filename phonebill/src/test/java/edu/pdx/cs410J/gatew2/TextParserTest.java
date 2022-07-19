package edu.pdx.cs410J.gatew2;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextParserTest {

  @Test
  void validTextFileCanBeParsedCurrentDirectoryPath() throws ParserException, FileNotFoundException {
    InputStream in = new FileInputStream("valid-phonebill.txt");
    assertThat(in, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(in));
    PhoneBill bill = parser.parse();
    assertThat(bill.getCustomer(), equalTo("Test Phone Bill"));
  }
  @Test
  void validTextFileCanBeParsedSubDirectoryPath() throws ParserException, FileNotFoundException {
    InputStream in = new FileInputStream("src/test/resources/edu/pdx/cs410J/gatew2/valid-phonebill.txt");
    assertThat(in, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(in));
    PhoneBill bill = parser.parse();
    assertThat(bill.getCustomer(), equalTo("Test Phone Bill"));
  }
  @Test
  void invalidTextFileCurrentDirectoryPath() throws FileNotFoundException, ParserException {
    InputStream in = new FileInputStream("empty-phonebill.txt");
    assertThat(in, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(in));
    PhoneBill bill = parser.parse();
    assertThat(bill.getCustomer(), equalTo("MISSING CUSTOMER NAME!!"));
  }

  @Test
  void invalidTextFileSubDirectoryPath() throws FileNotFoundException, ParserException {
    InputStream in = new FileInputStream("src/test/resources/edu/pdx/cs410J/gatew2/empty-phonebill.txt");
    assertThat(in, notNullValue());

    TextParser parser = new TextParser(new InputStreamReader(in));
    PhoneBill bill = parser.parse();
    assertThat(bill.getCustomer(), equalTo("MISSING CUSTOMER NAME!!"));
  }
}
