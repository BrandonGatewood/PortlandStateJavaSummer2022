package edu.pdx.cs410J.gatew2;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class TextDumperTest {

  @Test
  void appointmentBookOwnerIsDumpedInTextFormat() {
    String customer = "Test Phone Bill";
    PhoneBill bill = new PhoneBill(customer);

    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw);
    dumper.dump(bill);

    String text = sw.toString();
    assertThat(text, containsString(customer));
  }

  @Test
  void canParseTextWrittenByTextDumper(@TempDir File tempDir) throws IOException, ParserException {
    String customer = "Test Phone Bill";
    PhoneBill bill = new PhoneBill(customer);

    File textFile = new File(tempDir, "apptbook.txt");
    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(bill);

    TextParser parser = new TextParser(new FileReader(textFile));
    PhoneBill read = parser.parse();
    assertThat(read.getCustomer(), equalTo(customer));
  }
  @Test
  void testingDumpAbsolutePath() throws IOException, ParserException {
    String customer = "Test Phone Bill";
    PhoneBill bill = new PhoneBill(customer);
    PhoneCall call1 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/4432 1:30", "2/34/4323 1:30");
    PhoneCall call2 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/4432 1:30", "2/34/4323 1:30");
    PhoneCall call3 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/4432 1:30", "2/34/4323 1:30");

    bill.addPhoneCall(call1);
    bill.addPhoneCall(call2);
    bill.addPhoneCall(call3);
    File textFile = new File("/Users/brandongatewood/Desktop/Summer 2022/PortlandStateJavaSummer2022/phonebill/src/test/resources/edu/pdx/cs410J/gatew2/valid-phonebill.txt");

    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(bill);

    TextParser parser = new TextParser(new FileReader(textFile));
    PhoneBill read = parser.parse();
    assertThat(read.getCustomer(), equalTo(customer));
  }
  @Test
  void testingDumpCurrentDirectoryPath() throws IOException, ParserException {
    String customer = "Test Phone Bill";
    PhoneBill bill = new PhoneBill(customer);
    PhoneCall call1 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/4432 1:30", "2/34/4323 1:30");
    PhoneCall call2 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/4432 1:30", "2/34/4323 1:30");
    PhoneCall call3 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/4432 1:30", "2/34/4323 1:30");

    bill.addPhoneCall(call1);
    bill.addPhoneCall(call2);
    bill.addPhoneCall(call3);
    File textFile = new File("valid-phonebill.txt");

    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(bill);

    TextParser parser = new TextParser(new FileReader(textFile));
    PhoneBill read = parser.parse();
    assertThat(read.getCustomer(), equalTo(customer));
  }
  @Test
  void testingDumpSubDirectoryPath() throws IOException, ParserException {
    String customer = "Test Phone Bill";
    PhoneBill bill = new PhoneBill(customer);
    PhoneCall call1 = new PhoneCall("808-364-4323", "905-234-4323", "2/23/4432 1:30", "2/34/4323 1:30");
    PhoneCall call2 = new PhoneCall("808-314-4323", "905-234-4323", "2/23/4432 1:30", "2/34/4323 1:30");
    PhoneCall call3 = new PhoneCall("808-924-4323", "905-234-4323", "2/23/4432 1:30", "2/34/4323 1:30");

    bill.addPhoneCall(call1);
    bill.addPhoneCall(call2);
    bill.addPhoneCall(call3);
    File textFile = new File("src/test/resources/edu/pdx/cs410J/gatew2/valid-phonebill.txt");

    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(bill);

    TextParser parser = new TextParser(new FileReader(textFile));
    PhoneBill read = parser.parse();
    assertThat(read.getCustomer(), equalTo(customer));
  }
}

