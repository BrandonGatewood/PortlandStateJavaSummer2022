package edu.pdx.cs410J.gatew2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Vector;

/**
 * Dumps <code>PhoneBill</code> and <code>PhoneCall</code>
 * information into an external .txt file.
 */
public class TextDumper implements PhoneBillDumper<PhoneBill> {
  private final Writer writer;

  /**
   * Assigns passed in <code>Writer</code> to <code>TextDumper</code>'s <code>Writer</code>.
   * @param writer
   *        Contains writing information for the given .txt file.
   */
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
