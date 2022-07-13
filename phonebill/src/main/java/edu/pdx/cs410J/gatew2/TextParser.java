package edu.pdx.cs410J.gatew2;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

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
   * @throws ParserException
   *        Throws a ParserException if customer is null.
   */
  @Override
  public PhoneBill parse() throws ParserException {
    try (
      BufferedReader br = new BufferedReader(this.reader)
    ) {
      String callerNumber;
      String calleeNumber;
      String begin;
      String end;
      String toParse = br.readLine();

      if(toParse == null || toParse.isBlank()) {
        throw new ParserException("Missing customer");
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

            PhoneCall call = new PhoneCall(callerNumber, calleeNumber, begin, end);
            bill.addPhoneCall(call);
          }
          toParse = br.readLine();
        }
        return bill;
      }
    } catch (IOException e) {
      throw new ParserException("While parsing phone bill text", e);
    }
  }
}
