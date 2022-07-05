package edu.pdx.cs410J.gatew2;

import com.google.common.annotations.VisibleForTesting;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  @VisibleForTesting
  static boolean isValidPhoneNumber(String phoneNumber) {
    return true;
  }

  public static void main(String[] args) {
    /*
    PhoneCall call = new PhoneCall();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    System.err.println("Missing command line arguments");
    for (String arg : args) {
      System.out.println(arg);
    }
     */
    String errorMessage = validateCommandLine(args);

    if(errorMessage != null) {
      System.out.println(errorMessage);
    }
  }

  private static String validateCommandLine(String[] args) {
    if(args.length == 0) {
      return "Missing command line arguments";
    }
    else if(args.length == 1) {
      return "Missing caller number (nnn-nnn-nnnn)";
    }
    else if(args.length == 2) {
      return "Missing callee number (nnn-nnn-nnnn)";
    }
    else if(args.length == 3) {
      return "Missing beginning date and time (mm/dd/yyyy hh:mm)";
    }
    else if(args.length == 4) {
      return "Missing ending date and time (mm/dd/yyyy hh:mm)";
    }
    else if(args.length == 5) {
      String errorMessage = validateEachArgument(args);
      return errorMessage;
    }
    else {
      return "Too many arguments";
    }
  }

  private static String validateEachArgument(String[] args) {
    String customer = args[0];
    String callerNumber = args[1];
    String calleeNumber = args[2];
    String begin = args[3];
    String end = args[4];

    if(callerNumber.matches("(\\d{3})-(\\d{3})-(\\d{4})") == false) {
      //incorrect format
      return "Caller number can only be in the format: nnn-nnn-nnnn";
    }
    //validate callee number
    else if(calleeNumber.matches("(\\d{3})-(\\d{3})-(\\d{4})") == false) {
      //incorrect format
      return "Callee number can only be in the format: nnn-nnn-nnnn";
    }
    //validate begin
    else if(begin.matches("(\\d{1,2}/(\\d{1,2})/(\\d{4}) (\\d{1,2}):(\\d{1,2}))") == false) {
      //incorrect format
      return "Beginning date and time can only be in the format: mm/dd/yyyy hh:mm";
    }
    //validate end
    else if(end.matches("(\\d{1,2}/(\\d{1,2})/(\\d{4}) (\\d{1,2}):(\\d{1,2}))") == false) {
      //incorrect format
      return "Ending date and time can only be in the format: mm/dd/yyyy hh:mm";
    }
    else {
      //All arguments are valid
      //create new phone bill
      return phoneBill(args);
    }
  }

  private static String phoneBill(String[] args) {
    PhoneBill bill = new PhoneBill(args[0]);
    PhoneCall call = new PhoneCall(args[1], args[2], args[3],args[4]);

    bill.addPhoneCall(call);
    return bill.toString() + "\n" + call.toString();

  }
}