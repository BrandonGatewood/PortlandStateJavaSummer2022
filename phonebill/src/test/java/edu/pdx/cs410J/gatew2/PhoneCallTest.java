package edu.pdx.cs410J.gatew2;

import org.junit.jupiter.api.Test;

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
  void forPhoneCallGetCaller1() {
    //GIVEN that there is a caller number "808-324-0532"
    PhoneCall call = new PhoneCall("808-324-0532", "905-328-4034", "10/9/1997 10:20", "10/9/1997 10:30");

    //WHEN "808-324-0532" is requested
    //THEN call number is "808-324-0532"
    assertThat(call.getCaller(), equalTo("808-324-0532"));
  }
  @Test
  void forPhoneCallGetCallee1() {
    //GIVEN that there is a caller number "905-328-4034"
    PhoneCall call = new PhoneCall("808-324-0532", "905-328-4034", "1/3/2012 8:45", "1/3/2012 9:45");

    //WHEN "905-328-4034" is requested
    //THEN callee number is "905-328-4034"
    assertThat(call.getCallee(), equalTo("905-328-4034"));
  }
  @Test
  void forPhoneCallGetBeginTimeString1() {
    //GIVEN that there is a beginning time "1/3/2012 8:45"
    PhoneCall call = new PhoneCall("808-324-0532", "905-328-4034", "1/3/2012 8:45", "1/3/2012 9:45");

    //WHEN beginning time is requested
    //THEN beginning time is "1/3/2012 8:45"
    assertThat(call.getBeginTimeString(), equalTo("1/3/2012 8:45"));
  }
  @Test
  void forPhoneCallGetEndTimeString1() {
    //GIVEN that there is an end time "1/3/2012 9:45"
    PhoneCall call = new PhoneCall("808-324-0532", "905-328-4034", "1/3/2012 8:45", "1/3/2012 9:45");

    //WHEN end time is requested
    //THEN end time is "1/3/2012 9:45"
    assertThat(call.getEndTimeString(), equalTo("1/3/2012 9:45"));
  }
}
