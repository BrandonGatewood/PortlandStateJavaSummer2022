package edu.pdx.cs410J.gatew2;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link PhoneBill} class.
 */

public class PhoneBillTest {
    @Test
    void forGetCustomer1() {
        //GIVEN that there is a customer named "Brandon
        PhoneBill bill = new PhoneBill("Brandon");

        //WHEN customer is requested
        //THEN customer is name is "Brandon"
        assertThat(bill.getCustomer(), equalTo("Brandon"));
    }
    @Test
    void forGetPhoneCalls1() {
        //GIVEN that customer is named "Brandon"
        PhoneBill bill = new PhoneBill("Brandon");
        //GIVEN that 0 calls were added into "Brandon"'s phone bill

        //WHEN phone calls is requested
        //THEN phone calls is 0
        assertThat(bill.getPhoneCalls().size(), equalTo(0));
    }
    @Test
    void forGetPhoneCalls2() {
        //GIVEN that customer is named "Brandon"
        PhoneBill bill = new PhoneBill("Brandon");
        //GIVEN that 1 call is added into "Brandon"'s phone bill
        PhoneCall call = new PhoneCall("808-324-0532", "905-328-4034", "1/3/2012 8:45", "1/3/2012 9:45");
        bill.addPhoneCall(call);

        //WHEN Phone calls is requested
        //THEN Phone calls is 1
        assertThat(bill.getPhoneCalls().size(), equalTo(1));
    }
    @Test
    void forGetPhoneCalls3() {
        //GIVEN that customer is named "Brandon"
        PhoneBill bill = new PhoneBill("Brandon");
        //GIVEN that 3 call is added into "Brandon"'s phone bill
        PhoneCall call1 = new PhoneCall("808-324-0532", "905-328-4034", "1/3/2012 8:45", "1/3/2012 9:45");
        bill.addPhoneCall(call1);
        PhoneCall call2 = new PhoneCall("808-324-0532", "905-328-4034", "1/5/2012 3:45", "1/5/2012 6:03");
        bill.addPhoneCall(call2);
        PhoneCall call3 = new PhoneCall("808-324-0532", "905-328-4034", "1/20/2012 2:06", "1/3/2012 2:40");
        bill.addPhoneCall(call3);

        //WHEN Phone calls is requested
        //Then Phone calls is 3
        assertThat(bill.getPhoneCalls().size(), equalTo(3));
    }
}
