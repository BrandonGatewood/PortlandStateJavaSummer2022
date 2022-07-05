package edu.pdx.cs410J.gatew2;

import edu.pdx.cs410J.AbstractPhoneCall;

/**
 * This class represents a <code>PhoneCall</code>.
 */
public class PhoneCall extends AbstractPhoneCall {
  private final String caller;
  private final String callee;
  private final String begin;
  private final String end;

  /**
   * Creates a new <code>PhoneCall</code>
   *
   * @param caller
   *        The caller's phone number
   * @param callee
   *        The callee's phone number
   * @param begin
   *        The beginning time and date of the phone call
   * @param end
   *        The end time and date of the phone call
   */
  PhoneCall(String caller, String callee, String begin, String end) {
    this.caller = caller;
    this.callee = callee;
    this.begin = begin;
    this.end = end;
  }

  /**
   * Returns a <code>String</code> that
   * contains the callers phone number.
   */
  @Override
  public String getCaller() {
    return caller;
  }

  /**
   * Returns a <code>String</code> that
   * contains the callees phone number.
   */
  @Override
  public String getCallee() {
    return callee;
  }

  /**
   * Returns a <code>String</code> that
   * the beginning date and time of the
   * phone call.
   */
  @Override
  public String getBeginTimeString() {
    return begin;
  }

  /**
   * Returns a <code>String</code> that
   * the ending date and time of the
   * phone call.
   */
  @Override
  public String getEndTimeString() {
    return end;
  }
}
