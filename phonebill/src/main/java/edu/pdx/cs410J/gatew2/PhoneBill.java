package edu.pdx.cs410J.gatew2;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.Collection;
import java.util.Vector;

/**
 * This class represents a <code>PhoneBill</code>
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
  private final String customer;
  private final Vector<PhoneCall> phoneCalls;

  /**
   * Creates a new <code>PhoneBill</code>
   * @param customer
   *        The customers name
   */
  public PhoneBill(String customer) {
    this.customer = customer;
    this.phoneCalls = new Vector<PhoneCall>();
  }

  /**
   * Returns a <code>String</code> that
   * contains the customers name.
   */
  @Override
  public String getCustomer() {
    return this.customer;
  }

  /**
   * Adds a <code>PhoneCall</code> object
   * to customers list of calls.
   *
   * @param call
   *        <code>PhoneCall</code> object
   */
  @Override
  public void addPhoneCall(PhoneCall call) {
    phoneCalls.add(call);
  }

  /**
   * returns a collection of phone calls.
   */
  @Override
  public Collection<PhoneCall> getPhoneCalls() {
    return phoneCalls;
  }
}
