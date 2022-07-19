package edu.pdx.cs410J.gatew2;

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
  void canParseTextWrittenByTextDumper(@TempDir File tempDir) throws IOException {
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
  void testingDumpCurrentDirectoryPath() throws IOException {
    String customer = "Test Phone Bill";
    PhoneBill bill = new PhoneBill(customer);
    PhoneCall call1 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/2014 1:30 pm", "2/23/2014 1:50 pm");
    PhoneCall call2 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/2014 2:30 pm", "2/23/2014 3:30 pm");
    PhoneCall call3 = new PhoneCall("808-324-4323", "905-234-4323", "2/23/2014 3:30 pm", "2/23/2014 5:30 pm");

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
  void testingDumpSubDirectoryPath() throws IOException {
    String customer = "Test Phone Bill";
    PhoneBill bill = new PhoneBill(customer);
    PhoneCall call1 = new PhoneCall("808-364-4323", "905-234-4323", "2/23/2014 1:30 pm", "2/23/2014 1:30 pm");
    PhoneCall call2 = new PhoneCall("808-314-4323", "905-234-4323", "2/23/2015 1:30 pm", "2/23/2015 1:45 pm");
    PhoneCall call3 = new PhoneCall("808-924-4323", "905-234-4323", "2/23/2015 7:30 pm", "2/23/2015 7:50 pm");

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
  @Test
  void testingBadDates() throws IOException {
    String customer = "Test Phone Bill";
    PhoneBill bill = new PhoneBill(customer);
    PhoneCall call1 = new PhoneCall("808-364-4323", "905-234-4323", "2/23/2014 1:30 pm", "2/23/2014 1:30 pm");
    PhoneCall call2 = new PhoneCall("808-314-4323", "905-234-4323", "2/23/2015 1:30 pm", "2/23/2015 1:45 pm");
    PhoneCall call3 = new PhoneCall("808-924-4323", "905-234-4323", "2/23/2015 7:30 pm", "2/23/2015 7:00 pm");

    bill.addPhoneCall(call1);
    bill.addPhoneCall(call2);
    bill.addPhoneCall(call3);
    File textFile = new File("src/test/resources/edu/pdx/cs410J/gatew2/valid-phonebill.txt");

    TextDumper dumper = new TextDumper(new FileWriter(textFile));
    dumper.dump(bill);

    TextParser parser = new TextParser(new FileReader(textFile));
    PhoneBill read = parser.parse();
    assertThat(read.getCustomer(), equalTo("File cannot be parsed.. Call end time cannot be before begin time."));
  }
}

