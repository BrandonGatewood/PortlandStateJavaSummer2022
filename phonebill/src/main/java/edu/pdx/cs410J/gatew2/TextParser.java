package edu.pdx.cs410J.gatew2;

import edu.pdx.cs410J.PhoneBillParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Parses <code>PhoneBill</code> and <code>PhoneCall</code> information from
 * an external .txt file.
 */
public class TextParser implements PhoneBillParser<PhoneBill> {
  private final Reader reader;

  /**
   * Assigns passed in <code>Reader</code> to <code>TextParser</code>'s <code>Reader</code>.
   * @param reader
   *        Contains reading information for the given .txt file.
   */
  public TextParser(Reader reader) {
    this.reader = reader;
  }

  /**
   * Parses an external .txt file, which contains a
   * <code>PhoneBill</code> and all the customers
   * <code>PhoneCall</code>'s.
   * @return
   *        Returns a <code>PhoneBill</code> object.
   */
  @Override
  public PhoneBill parse() {
    try (
      BufferedReader br = new BufferedReader(this.reader)
    ) {
      String callerNumber;
      String calleeNumber;
      String begin;
      String end;
      String toParse = br.readLine();

      if(toParse == null || toParse.isBlank()) {
        return new PhoneBill("MISSING CUSTOMER NAME!!");
      }
      else if(!toParse.contains(";")){
        return new PhoneBill("The .txt file is formatted incorrectly. The correct format: customer;caller;callee;begin;end;");
      }
      else  {
        int i = toParse.indexOf(';');
        String customer = toParse.substring(0, i);

        PhoneBill bill = new PhoneBill(customer);
        while(toParse != null) {
          String[] phoneCallInformation = toParse.split(";");
          // If information in String array is " " then ignore it.
          if(!phoneCallInformation[1].equals(" ")) {
            callerNumber = phoneCallInformation[1];
            calleeNumber = phoneCallInformation[2];
            begin = phoneCallInformation[3];
            end = phoneCallInformation[4];

            // Validate each String
            String canParse = validateParsing(callerNumber, calleeNumber, begin, end);

            if(!canParse.equals("FILE CAN BE PARSED")) {
              return new PhoneBill(canParse);
            }

            PhoneCall call = new PhoneCall(callerNumber, calleeNumber, begin, end);
            bill.addPhoneCall(call);
          }
          toParse = br.readLine();
        }
        return bill;
      }
    } catch (IOException | ParseException e) {
      return new PhoneBill("The .txt file is formatted incorrectly. The correct format: customer;caller;callee;begin;end;");
    }
  }

  /**
   * Validates the caller, callee, begin date, and end date for each
   * <code>PhoneCall</code> in the .txt file.
   *
   * @param caller
   *        The caller's phone number
   * @param callee
   *        The callee's phone number
   * @param begin
   *        The beginning time and date of the phone call
   * @param end
   *        The ending time and date of the phone call
   */
  public String validateParsing(String caller, String callee, String begin, String end) throws ParseException {
    String parsed = "File cannot be parsed.. ";

    // Validate caller number
    if(!caller.matches("(\\d{3})-(\\d{3})-(\\d{4})")) {
      // Incorrect format
      return parsed + "Caller number can only be in the format: nnn-nnn-nnnn.";
    }
    // Validate callee number
    else if(!callee.matches("(\\d{3})-(\\d{3})-(\\d{4})")) {
      // Incorrect format
      return parsed + "Callee number can only be in the format: nnn-nnn-nnnn.";
    }
    // Validate begin date
    else if(!begin.matches("(([1-9]|0[1-9]|[1-6]\\d)/([1-9]|0[1-9]|[1-6]\\d)/(\\d{4}) ((\\d|0\\d|1[0-2]):(\\d|0\\d|[1-6]\\d)) (am|AM|pm|PM))")) {

      // Incorrect format
      return parsed + "Beginning date can only be in the format: mm/dd/yyyy hh:mm am/pm.";
    }
    // Validate end date
    else if(!end.matches("(([1-9]|0[1-9]|[1-6]\\d)/([1-9]|0[1-9]|[1-6]\\d)/(\\d{4}) ((\\d|0\\d|1[0-2]):(\\d|0\\d|[1-6]\\d)) (am|AM|pm|PM))")) {
      // Incorrect format
      return parsed + "Ending date can only be in the format: mm/dd/yyyy hh:mm am/pm.";
    }

    // Check if end date is before begin date.
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);

    Date dateBegin = sdf.parse(begin);
    Date dateEnd = sdf.parse(end);

    if(dateBegin.compareTo(dateEnd) > 0) {
      return parsed + "Call end time cannot be before begin time.";
    }
    return "FILE CAN BE PARSED";
  }
}
