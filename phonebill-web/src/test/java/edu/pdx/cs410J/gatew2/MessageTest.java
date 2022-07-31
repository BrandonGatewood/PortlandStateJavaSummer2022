package edu.pdx.cs410J.gatew2;

import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MessageTest {
    @Test
    void missingRequiredParameter() {
        assertThat(Messages.missingRequiredParameter("Customer"), equalTo("The required parameter \"Customer\" is missing"));
    }
    @Test
    void noPhoneBillForCustomer() {
        assertThat(Messages.noPhoneBillForCustomer("Brandon"), equalTo("No phone bill for customer named Brandon."));
    }
    @Test
    void addedCall() {
        assertThat(Messages.addedCall("PhoneCall"), equalTo("Newly added PhoneCall"));
    }
    @Test
    void printPrettyPhoneCall() throws ParseException {
        PhoneCall aCall = new PhoneCall("123-456-7893", "123-456-7890", "12/2/2012 1:40 pm", "12/2/2012 1:50 pm");
        assertThat(Messages.printPrettyPhoneCall(aCall), equalTo("\tCall with: 123-456-7890 from 12/2/12, 1:40 PM - 12/2/12, 1:50 PM.\n\tDuration of call: 10 minutes"));
    }
}
