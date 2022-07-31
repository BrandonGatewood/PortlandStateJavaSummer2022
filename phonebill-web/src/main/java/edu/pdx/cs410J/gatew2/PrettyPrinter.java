package edu.pdx.cs410J.gatew2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 *  Pretty Printer will format a <code>PhoneCall</code>
 */
public class PrettyPrinter {
  /**
   * Returns a formatted String representing a <code>PhoneCall</code>.
   * Along with a duration of the call.
   *
   * @param aCall
   *        A <code>PhoneCall</code> object
   */
  public static String dump(PhoneCall aCall) throws ParseException {
    String begin = aCall.getBeginTimeString();
    String end = aCall.getEndTimeString();

    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy, hh:mm aa", Locale.US);
    Date beginDate = sdf.parse(begin);
    Date endDate = sdf.parse(end);
    long diff = endDate.getTime() - beginDate.getTime();
    long min = TimeUnit.MILLISECONDS.toMinutes(diff);

    return String.format("\tCall with: %s from %s - %s.\n\tDuration of call: %s minutes", aCall.getCallee(), begin, end, min);

  }
}
