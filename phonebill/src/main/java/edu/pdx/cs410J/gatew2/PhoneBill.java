package edu.pdx.cs410J.gatew2;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.Collection;
import java.util.Vector;

/**
 * This class represens a <code>PhoneBill</code>
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
  /**
   * Creates a new <code>PhoneBill</code>
   * @param customer
   *        The customers name
   * @param phoneCalls
   *        A collection of phone calls
   */
  private final String customer;
  private final Vector<PhoneCall> phoneCalls;

  public PhoneBill(String customer) {
    this.customer = customer;
    this.phoneCalls = new Vector<>();
  }

  @Override
  public String getCustomer() {
    return this.customer;
  }

  @Override
  public void addPhoneCall(PhoneCall call) {
    phoneCalls.add(call);
  }

  @Override
  public Collection<PhoneCall> getPhoneCalls() {
    return phoneCalls;
  }
}
