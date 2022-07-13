package edu.pdx.cs410J.gatew2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AppointmentBookDumper;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.*;
import java.util.Collection;
import java.util.Vector;

public class TextDumper implements PhoneBillDumper<PhoneBill> {
  private final Writer writer;

  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  /**
   * Dumps a <code>PhoneBill</code> into an external .txt file.
   * With each <code>PhoneBill</code>, all <code>PhoneCall</code>
   * information is saved as well.
   * @param bill
   *        <code>PhoneBill</code> object.
   */
  @Override
  @VisibleForTesting
  public void dump(PhoneBill bill) {
    try (
            PrintWriter pw = new PrintWriter(this.writer)
    ) {
      // Get the collection of phone calls from bill
      Vector<PhoneCall> phoneCalls = new Vector<>(bill.getPhoneCalls());

      // Customer has no PhoneCalls
      if(phoneCalls.isEmpty()) {
        pw.println(bill.getCustomer() + "; ; ; ; ;");
      }
      else {
        // Loop through each phone call and save their information
        for (PhoneCall phoneCall : phoneCalls) {
          String callerNumber = phoneCall.getCaller();
          String calleeNumber = phoneCall.getCallee();
          String begin = phoneCall.getBeginTimeString();
          String end = phoneCall.getEndTimeString();

          pw.println(bill.getCustomer() + ";" + callerNumber + ";" + calleeNumber + ";" + begin + ";" + end + ";");
        }
      }
      pw.flush();
    }
  }
}
