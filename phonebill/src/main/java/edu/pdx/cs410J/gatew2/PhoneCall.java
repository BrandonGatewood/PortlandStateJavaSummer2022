package edu.pdx.cs410J.gatew2;

import edu.pdx.cs410J.AbstractPhoneCall;

/**
 * This class represents a <code>PhoneCall</code>.
 */
public class PhoneCall extends AbstractPhoneCall {
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
  private final String caller;
  private final String callee;
  private final String begin;
  private final String end;

  PhoneCall() {
    caller = null;
    callee = null;
    begin = null;
    end = null;
  }
  PhoneCall(String caller, String callee, String begin, String end) {
    this.caller = caller;
    this.callee = callee;
    this.begin = begin;
    this.end = end;
  }
  @Override
  public String getCaller() {
    return caller;
  }

  @Override
  public String getCallee() {
    return callee;
  }

  @Override
  public String getBeginTimeString() {
    return begin;
  }

  @Override
  public String getEndTimeString() {
    return end;
  }
}
