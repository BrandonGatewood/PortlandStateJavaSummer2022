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
      return validateEachArgument(args);
    }
    else {
      return "Too many arguments";
    }
  }

  private static String validateEachArgument(String[] args) {

    return null;
  }

}