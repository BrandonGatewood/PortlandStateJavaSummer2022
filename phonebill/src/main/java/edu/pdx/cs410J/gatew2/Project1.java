package edu.pdx.cs410J.gatew2;

import com.google.common.annotations.VisibleForTesting;

import java.io.*;
import java.util.Arrays;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {
  /**
   * Prints out error messages or information about customer
   * and their phonebill.
   * @param args
   *        command line arguments
   */
  public static void main(String[] args) {
      if(args.length == 0)
        System.err.println("Missing command line arguments");
      else {
        String errorMessage = checkForInputOptions(args);
        System.err.println(errorMessage);
      }
  }


  /**
   * Checks if any options were given as an argument.
   * Program ends if '-README' option is given.
   * Program will print the new <code>PhoneBill</code> and
   * <code>PhoneCall</code> if '-print' option is given.
   *
   * @param args
   *        An array of <code>String</code> containing command line arguments
   */
  @VisibleForTesting
  static String checkForInputOptions(String[] args) {
    // Loop through args and check if README option was given
    for(String str:args) {
      // If '-README' option is given, then print README.txt.
      if(str.equals("-README")) {
        try (
                InputStream readme = Project1.class.getResourceAsStream("README.txt")
        ) {
          BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
          String read = reader.readLine();
          StringBuilder readMe = new StringBuilder();
          while(read != null) {
            readMe.append(read).append("\n");
            read = reader.readLine();
          }
          return readMe.toString();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }
    // If '-README' option WASN'T given, then check if '-print' option was given.
    for(String str:args) {
      // If '-print' option is given, then update the string array and validate its length.
      if(str.equals("-print")) {
        String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
        return validateArgLength(newArgs, true);
      }
    }
    // No options were given so, validate length of arguments.
    return validateArgLength(args, false);
  }

  /**
   * Validates that there are the correct amount
   * of command line arguments before validating
   * each argument.
   *
   * @param args
   *        An array of <code>String</code> containing command line arguments
   * @param print
   *        print flag to determine to print a description of the new phone call
   */
  @VisibleForTesting
  static String validateArgLength(String[] args, boolean print) {
    // Ignored args.length == 0, because main checked it before
    // calling checkForInputOptions method.
    if(args.length == 1) {
      return "Missing caller number (nnn-nnn-nnnn)";
    }
    else if(args.length == 2) {
      return "Missing callee number (nnn-nnn-nnnn)";
    }
    else if(args.length == 3) {
      return "Missing beginning date (mm/dd/yyyy)";
    }
    else if(args.length == 4) {
      return "Missing beginning time (hh:mm)";
    }
    else if(args.length == 5) {
      return "Missing ending date (mm/dd/yyyy)";
    }
    else if(args.length == 6) {
      return "Missing ending time (hh:mm)";
    }
    else if(args.length == 7) {
      return validateEachArgument(args, print);
    }
    else {
      return "Too many arguments";
    }
  }

  /**
   * Validate arguments before adding customers
   * phone call into their phone bill.
   *
   * - Customer name may contain characters and numbers.
   * - Caller and Callee number can only contain digits 0-9 and must
   *   be in the format nnn-nnn-nnnn.
   * - Begin and end date and time can only contain digits and must
   *   be in the format mm/dd/yyyy hh:mm.
   *
   * @param args
   *        An array of <code>String</code> containing command line arguments
   * @param print
   *        print flag to determine to print a description of the new phone call
   */
@VisibleForTesting
 static String validateEachArgument(String[] args, boolean print) {
    String customer = args[0];
    String callerNumber = args[1];
    String calleeNumber = args[2];
    String beginDate = args[3];
    String beginTime = args[4];
    String endDate = args[5];
    String endTime = args[6];

    if(!callerNumber.matches("(\\d{3})-(\\d{3})-(\\d{4})")) {
      //incorrect format
      return "Caller number can only be in the format: nnn-nnn-nnnn";
    }
    //validate callee number
    else if(!calleeNumber.matches("(\\d{3})-(\\d{3})-(\\d{4})")) {
      //incorrect format
      return "Callee number can only be in the format: nnn-nnn-nnnn";
    }
    //validate begin
    else if(!beginDate.matches("(\\d{1,2}/(\\d{1,2})/(\\d{4}))")) {
      //incorrect format
      return "Beginning date can only be in the format: mm/dd/yyyy";
    }
    else if(!beginTime.matches("((\\d{1,2}):(\\d{1,2}))")) {
        //incorrect format
        return "Beginning time can only be in the format: hh:mm";
    }
    //validate end
    else if(!endDate.matches("(\\d{1,2}/(\\d{1,2})/(\\d{4}))")) {
      //incorrect format
      return "Ending date can only be in the format: mm/dd/yyyy";
    }
    else if(!endTime.matches("((\\d{1,2}):(\\d{1,2}))")) {
        //incorrect format
        return "Ending time can only be in the format: hh:mm";
    }
    else {
        //All arguments are valid
        //append date and time substrings
        String begin = args[3] + " " + args[4];
        String end = args[5] + " " + args[6];
        String[] newArgs = {customer, callerNumber, calleeNumber, begin, end};
        //create new phone bill
        return phoneBill(newArgs, print);
    }
  }

  /**
   * Create a <code>PhoneBill</code> and <code>PhoneCall</code>
   * object with appropriate arguments. Then insert the new
   * phone call into the customers phone bill.
   *
   * @param args
   *        An array of <code>String</code> containing command line arguments
   * @param print
   *        print flag to determine to print a description of the new phone call
   */
  @VisibleForTesting
  static String phoneBill(String[] args, boolean print) {
    PhoneBill bill = new PhoneBill(args[0]);
    PhoneCall call = new PhoneCall(args[1], args[2], args[3],args[4]);

    bill.addPhoneCall(call);
    if(print)
      return bill + "\n" + call;
    else
      return "";
  }
}