package edu.pdx.cs410J.gatew2;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link PhoneCall} class.
 *
 * You'll need to update these unit tests as you build out your program.
 */
public class PhoneCallTest {
  @Test
  void forPhoneCallGetCaller() {
    //GIVEN that there is a caller number "808-324-0532"
    PhoneCall call = new PhoneCall("808-324-0532", "905-328-4034", "10/9/1997 10:20", "10/9/1997 10:30");

    //WHEN "808-324-0532" is requested
    //THEN call number is "808-324-0532"
    assertThat(call.getCaller(), equalTo("808-324-0532"));
  }
  @Test
  void forPhoneCallGetCallee() {
    //GIVEN that there is a caller number "905-328-4034"
    PhoneCall call = new PhoneCall("808-324-0532", "905-328-4034", "1/3/2012 8:45", "1/3/2012 9:45");

    //WHEN "905-328-4034" is requested
    //THEN callee number is "905-328-4034"
    assertThat(call.getCallee(), equalTo("905-328-4034"));
  }
  @Test
  void forPhoneCallGetBeginTimeString() {
    //GIVEN that there is a beginning time "1/3/2012, 8:45 am"
    PhoneCall call = new PhoneCall("808-324-0532", "905-328-4034", "1/3/2012 8:45 am", "1/3/2012 9:45 am");

    //WHEN beginning time is requested
    //THEN beginning time is "1/3/2012, 8:45 AM"
    assertThat(call.getBeginTimeString(), equalTo("1/3/12, 8:45 AM"));
  }
  @Test
  void forPhoneCallGetBeginTime() {
    //GIVEN that there is an end time "1/3/2012 6:45 pm"
    String beginDate = "1/3/2012 6:45 pm";
    PhoneCall call = new PhoneCall("808-324-0532", "905-328-4034", beginDate, "1/3/2012 8:45 pm");

    Date newDate = new Date(beginDate);
    //WHEN end time is requested
    //THEN end time is "1/3/12, 9:45 PM"
    assertThat(call.getBeginTime(), equalTo(newDate));
  }
  @Test
  void forPhoneCallGetEndTimeString() {
    //GIVEN that there is an end time "1/3/2012 9:45 pm"
    PhoneCall call = new PhoneCall("808-324-0532", "905-328-4034", "1/3/2012 8:45 pm", "1/3/2012 9:45 pm");

    //WHEN end time is requested
    //THEN end time is "1/3/12, 9:45 pm"
    assertThat(call.getEndTimeString(), equalTo("1/3/12, 9:45 PM"));
  }
  @Test
  void forPhoneCallGetEndTime() {
    //GIVEN that there is an end time "1/3/2012 9:45 pm"
    String endDate = "1/3/2012 9:45 pm";
    PhoneCall call = new PhoneCall("808-324-0532", "905-328-4034", "1/3/2012 8:45 pm", endDate);

    Date newDate = new Date(endDate);
    //WHEN end time is requested
    //THEN end time is "1/3/12, 9:45 PM"
    assertThat(call.getEndTime(), equalTo(newDate));
  }
  @Test
  void forPhoneCallFormatDateString() {
    // GIVEN that there is a date "1/3/2012 9:45 pm"
    PhoneCall call = new PhoneCall("808-324-0532", "905-328-4034", "1/3/2012 8:45 pm", "1/3/2012 9:45 pm");

    String aDateString = "1/3/2012 9:45 pm";
    Date newDate = new Date(aDateString);

    // WHEN format date to string is requested
    // THEN the output is: "1/3/12, 9:45 PM"
    assertThat(call.formatDateString(newDate), equalTo("1/3/12, 9:45 PM"));
  }
  @Test
  void forCompareTo0() {
    // GIVEN that both calls have different begin dates.
    PhoneCall call1 = new PhoneCall("808-324-0532", "905-328-4034", "1/3/2012 8:45 pm", "1/3/2012 9:45 pm");
    PhoneCall call2 = new PhoneCall("808-324-0532", "905-328-4034", "12/3/2012 8:45 pm", "1/3/2012 9:45 pm");

    int s = call1.compareTo(call2);

    // WHEN both calls have different begin dates
    // THEN the output should be: -3
    assertThat(s, equalTo(-3));
  }
  @Test
  void forCompareTo1() {
    // GIVEN that both calls have the same begin dates and different caller numbers.
    PhoneCall call1 = new PhoneCall("808-324-0532", "905-328-4034", "1/3/2012 8:45 pm", "1/3/2012 9:45 pm");
    PhoneCall call2 = new PhoneCall("108-324-0532", "905-328-4034", "1/3/2012 8:45 pm", "1/3/2012 9:45 pm");

    int s = call1.compareTo(call2);

    // WHEN both calls have the same begin dates and different caller numbers
    // THEN the output should be: 7
    assertThat(s, equalTo(7));
  }
  @Test
  void forCompareTo2() {
    // GIVEN that both calls have the same begin dates and caller numbers.
    PhoneCall call1 = new PhoneCall("808-324-0532", "905-328-4034", "1/3/2012 8:45 pm", "1/3/2012 9:45 pm");
    PhoneCall call2 = new PhoneCall("808-324-0532", "905-328-4034", "1/3/2012 8:45 pm", "1/3/2012 9:45 pm");

    int s = call1.compareTo(call2);

    // WHEN both calls have the same begin dates and caller numbers
    // THEN the output should be: 0
    assertThat(s, equalTo(0));
  }
  @Test
  void forGetEndTimeStringForTextFiles() {
    //GIVEN that there is an end time "1/3/2012 9:45 pm"
    String endDate = "1/3/2012 9:45 pm";
    PhoneCall call = new PhoneCall("808-324-0532", "905-328-4034", "1/3/2012 8:45 pm", endDate);

    //WHEN end time is requested
    //THEN end time is "1/3/2012 9:45 pm"
    assertThat(call.getEndTimeStringForTextFiles(), equalTo(endDate));
  }
  @Test
  void forGetBeginTimeStringForTextFiles() {
    //GIVEN that there is an end time "1/3/2012 9:45 pm"
    String beginDate = "1/3/2012 8:45 pm";
    PhoneCall call = new PhoneCall("808-324-0532", "905-328-4034", beginDate, "1/3/2012 9:45 pm");

    //WHEN end time is requested
    //THEN end time is "1/3/2012 8:45 pm"
    assertThat(call.getBeginTimeStringForTextFiles(), equalTo(beginDate));

  }
}
