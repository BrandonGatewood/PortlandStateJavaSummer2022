package edu.pdx.cs410J.gatew2;

import com.google.common.annotations.VisibleForTesting;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project3 {
  /**
   * Prints out error messages or information about customer
   * and their <code>PhoneBill</code>.
   * @param args
   *        command line arguments
   */
  public static void main(String[] args) throws IOException, ParseException {
      if(args.length == 0) {
          String usage = "Usage: java -jar target/phonebill-2022.0.0.jar [-README] [-print] [-textFile file] [-pretty file] customer caller callee beginDate beginTime am/pm endDate endTime am/pm\n";
          String option1 = "\t-README: Prints a README for this project and exits\n";
          String option2 = "\t-print: Prints a description of the new phone call\n";
          String option3 = "\t-textFile file:  Where to read/write the phone bill\n";
          String option4 = "\t-pretty file: Pretty print the phone bill to a text file\n";
          String option5 = "\t-pretty -: Pretty print the phone bill to Standard out\n";

          // No arguments entered, so display a "how to use" message.
          System.err.println(usage + option1 + option2 + option3 + option4 + option5);
      }
    else {
        String errorMessage = checkForInputOptions(args);
        System.err.println(errorMessage);
      }
  }

  /**
   * Checks if any options were given as an argument.
   *
   * If '-README' option is given, then the program ends.
   *
   * If "'-textFile' '.txt'" option is given, then the program
   * will parse the .txt file, create a <code>PhoneBill</code>,
   * add the new <code>PhoneCall</code> given from the command line
   * into the new <code>PhoneBill</code>, and then dump the <code>PhoneBill</code>
   * and <code>PhoneCall</code>s back into the .txt file.
   *
   * If '-print' option is given, then the program will
   * will print the new <code>PhoneBill</code> and
   * <code>PhoneCall</code>.
   *
   * @param args
   *        An array of <code>String</code> containing command line arguments
   */
  @VisibleForTesting
  static String checkForInputOptions(String[] args) throws IOException, ParseException {
    // Loop through args and check if README option was given
    for(String str:args) {
      // If '-README' option is given, then print README.txt.
      if(str.equals("-README")) {
        try (
                InputStream readme = Project3.class.getResourceAsStream("README.txt")
        ) {
            assert readme != null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
          String read = reader.readLine();
          StringBuilder readMe = new StringBuilder();
          while(read != null) {
            readMe.append(read).append("\n");
            read = reader.readLine();
          }
          return readMe.toString();
        } catch (IOException e) {
          return "ERROR, failed or interrupted I/O operation.";
        }
      }
    }
    // If '-README' option WASN'T given, then check if
    // '-print' or '-textFile' '.txt' option was given.
    boolean print = false;
    String filePath = null;
    String prettyPath = null;
    boolean prettyStdOut = false;
    ArrayList<String> newArgs = new ArrayList<>(Arrays.asList(args));

    String errorTextFileOption = "To use '-textFile' option, it must be in the order '-textFile' '.txt'.";
    String errorPrettyOption1 = "To use '-pretty' option, it must be in the order '-pretty' '.txt' to Pretty print the phone bill to a text file.";
    String errorPrettyOption2 = " Or '-pretty' '-' to Pretty print the phone bill to standard out.";

    for(int i = 0; i < newArgs.size(); ++i) {
        // If '-textFile' option is given
        // Then check if i+1 contains the .txt file
        if (newArgs.get(i).equals("-textFile")) {
            if (newArgs.get(i + 1).contains(".txt")) {
                filePath = newArgs.get(i + 1);
                // Removes '-textFile'
                newArgs.remove(i);
                // Removes '.txt' file
                newArgs.remove(i);
            } else {
                return errorTextFileOption;
            }
        }
        if (newArgs.get(i).equals("-pretty")) {
            if (newArgs.get(i + 1).contains(".txt")) {
                prettyPath = newArgs.get(i + 1);
                // Removes '-pretty'
                newArgs.remove(i);
                // Removes '.txt' file
                newArgs.remove(i);
            }
            else if(newArgs.get(i + 1).equals("-")) {
                prettyStdOut = true;
                // Removes '-pretty'
                newArgs.remove(i);
                // Removes '-' argument
                newArgs.remove(i);
            }
            else {
                return errorPrettyOption1 + errorPrettyOption2;
            }
        }
    }

    // Check for '-print' option
    for(int i = 0; i < newArgs.size(); ++i) {
        // If '-print' option is given
        // Then update the String array.
        // Update print flag to true
        if (newArgs.get(i).equals("-print")) {
            print = true;
            newArgs.remove(i);
            break;
        }
    }
    return validateArgLength(newArgs, print, filePath, prettyPath, prettyStdOut);
  }

  /**
   * Validates that there are the correct amount
   * of command line arguments before validating
   * each argument.
   *
   * @param args
   *        A <code>Collection</code> of <code>String</code> containing command line arguments
   * @param print
   *        Print flag to determine to print a description of the new phone call
   * @param filePath
   *        Contains the file path to the .txt file
   * @param prettyPath
   *        Contains the file to a .txt file to pretty print
   * @param prettyStdOut
   *        print flag to determine to pretty print a <code>PhoneBill</code> and its
   *        <code>PhoneCall</code>s to standard out.
   */
  @VisibleForTesting
  static String validateArgLength(ArrayList<String> args, boolean print, String filePath, String prettyPath, boolean prettyStdOut) throws IOException, ParseException {
    // Ignored args.length == 0, because main checked it before
    // calling checkForInputOptions method.
    if(args.size() == 1) {
      return "Missing caller number (nnn-nnn-nnnn)";
    }
    else if(args.size() == 2) {
      return "Missing callee number (nnn-nnn-nnnn)";
    }
    else if(args.size() == 3) {
      return "Missing beginning date (mm/dd/yyyy)";
    }
    else if(args.size() == 4) {
      return "Missing beginning time (hh:mm)";
    }
    else if(args.size() == 5) {
        return "Missing ending \"am\"/\"pm\"";
    }
    else if(args.size() == 6) {
      return "Missing ending date (mm/dd/yyyy)";
    }
    else if(args.size() == 7) {
      return "Missing ending time (hh:mm)";
    }
    else if(args.size() == 8) {
        return "Missing ending \"am\"/\"pm\"";
    }
    else if(args.size() == 9) {
      return validateEachArgument(args, print, filePath, prettyPath, prettyStdOut);
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
   *        A <code>Collection</code> of <code>String</code> containing command line arguments
   * @param print
   *        print flag to determine to print a description of the new phone call
   * @param filePath
   *        Contains the file path to the .txt file
   * @param prettyPath
   *        Contains the file to a .txt file to pretty print
   * @param prettyStdOut
   *        print flag to determine to pretty print a <code>PhoneBill</code> and its
   *        <code>PhoneCall</code>s to standard out.
   */
@VisibleForTesting
 static String validateEachArgument(ArrayList<String> args, boolean print, String filePath, String prettyPath, boolean prettyStdOut) throws IOException, ParseException {
    String customer = args.get(0);
    String callerNumber = args.get(1);
    String calleeNumber = args.get(2);
    String beginDate = args.get(3);
    String beginTime = args.get(4);
    String beginAmPm = args.get(5);
    String endDate = args.get(6);
    String endTime = args.get(7);
    String endAmPm = args.get(8);

    // Validate caller number
    if(!callerNumber.matches("(\\d{3})-(\\d{3})-(\\d{4})")) {
      // Incorrect format
      return "Caller number can only be in the format: nnn-nnn-nnnn.";
    }
    // Validate callee number
    else if(!calleeNumber.matches("(\\d{3})-(\\d{3})-(\\d{4})")) {
      // Incorrect format
      return "Callee number can only be in the format: nnn-nnn-nnnn.";
    }
    // Validate begin date
    else if(!beginDate.matches("([1-9]|0[1-9]|[1-6]\\d)/([1-9]|0[1-9]|[1-6]\\d)/(\\d{4})")) {
      // Incorrect format
      return "Beginning date can only be in the format: mm/dd/yyyy.\nmonth and day can be from 1-69 or 01-69 BUT cannot be \"0\".";
    }
    // Validate begin time
    else if(!beginTime.matches("((\\d|0\\d|1[0-2]):(\\d|0\\d|[1-6]\\d))")) {
        // Incorrect format
        return "Beginning time can only be in the format: hh:mm.\nhour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69.";
    }
    // Validate am/pm
    else if(!beginAmPm.matches("(am|pm)")) {
        // Incorrect format
        return "Beginning \"am\"/\"pm\" can only be in the format: \"am\"/\"pm\".";
    }
    // Validate end date
    else if(!endDate.matches("([1-9]|0[1-9]|[1-6]\\d)/([1-9]|0[1-9]|[1-6]\\d)/(\\d{4})")) {
      // Incorrect format
      return "Ending date can only be in the format: mm/dd/yyyy.\nmonth and day can be from 1-69 or 01-69 BUT cannot be \"0\".";
    }
    // Validate end time
    else if(!endTime.matches("((\\d|0\\d|1[0-2]):(\\d|0\\d|[1-6]\\d))")) {
        // Incorrect format
        return "Ending time can only be in the format: hh:mm.\nhour can be from 1-12 or 01-12 and minute can be from 0-69 or 00-69.";
    }
    // Validate am/pm
    else if(!endAmPm.matches("(am|pm)")) {
        // Incorrect format
        return "Ending \"am\"/\"pm\" can only be in the format: \"am\"/\"pm\".";
    }
    else {
        // All arguments are valid
        // Append date and time substrings
        String begin = args.get(3) + " " + args.get(4) + " " + args.get(5);
        String end = args.get(6) + " " + args.get(7) + " " + args.get(8);

        // Check if end date is before begin date.
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);

        Date dateBegin = sdf.parse(begin);
        Date dateEnd = sdf.parse(end);

        if(dateBegin.compareTo(dateEnd) > 0) {
            return "Call end time cannot be before begin time.";
        }

        String[] newArgs = {customer, callerNumber, calleeNumber, begin, end};

        //create new phone bill
        return phoneBill(newArgs, print, filePath, prettyPath, prettyStdOut);
    }
  }

  /**
   * Create a <code>PhoneBill</code> and <code>PhoneCall</code>
   * object with appropriate arguments. Then insert the new
   * phone call into the customers phone bill.
   *
   * If 'print' flag is true, then it will print the customers phone bill and phone call(s).
   *
   * if filePath is not null, then it will parse the text file, create a new <code>PhoneBill</code>,
   * insert the new <code>PhoneCall</code>, and dump the <code>PhoneBill</code> information back into
   * the filePath.
   *
   * @param args
   *        An array of <code>String</code> containing command line arguments
   * @param print
   *        print flag to determine to print a description of the new phone call
   * @param filePath
   *        Contains the file path to the .txt file
   * @param prettyPath
   *        Contains the file to a .txt file to pretty print
   * @param prettyStdOut
   *        print flag to determine to pretty print a <code>PhoneBill</code> and its
   *        <code>PhoneCall</code>s to standard out.
   */
  @VisibleForTesting
  static String phoneBill(String[] args, boolean print, String filePath, String prettyPath, boolean prettyStdOut) throws IOException, ParseException {
    PhoneCall call = new PhoneCall(args[1], args[2], args[3], args[4]);
    PhoneBill bill = new PhoneBill(args[0]);
    bill.addPhoneCall(call);

    // Check if prettyStdOut is true but no filepath to print to standard out
    if(filePath == null && prettyStdOut) {
        return "No .txt file to Pretty print to standard out.";
    }

    // Check for '-textFIle' option
    if(filePath != null) {
        File textFile = new File(filePath);

        // Check if file is not empty
        if(textFile.length() != 0) {
            // Parse through the .txt file.
            TextParser parser = new TextParser(new FileReader(textFile));
            bill = parser.parse();

            // Check if .txt file is malformed.
            if(bill.getCustomer().equals("The .txt file is formatted incorrectly. The correct format: customer;caller;callee;begin;end;")) {
                return bill.getCustomer();
            }
            // Check if content in .txt file is invalid
            else if(bill.getCustomer().contains("File cannot be parsed.. ")) {
                return bill.getCustomer();
            }
            // Check if customer in .txt file is the same as the customer passed in the command line.
            else if (!args[0].equals(bill.getCustomer())) {
                return "Customer name passed in the command line doesnt match the customer name in the .txt file.";
            }

            // Add new call to the phone bill
            bill.addPhoneCall(call);
        }

        if(prettyStdOut) {
            PrettyPrinter stdOut = new PrettyPrinter(new FileWriter(textFile, false));
            stdOut.prettyPrintStdOutput(bill);
        }
        else {
            // Dump the phone bill back to the .txt file.
            TextDumper dumper = new TextDumper(new FileWriter(textFile, false));
            dumper.dump(bill);
        }
    }

    // Check for '-pretty' option
    if(prettyPath != null) {
        File textFile = new File(prettyPath);

        // Check if file is not empty
        if(textFile.length() != 0) {
            // Parse through the .txt file.
            TextParser parser = new TextParser(new FileReader(textFile));
            bill = parser.parse();

            // Check if .txt file is malformed.
            if(bill.getCustomer().equals("The .txt file is formatted incorrectly. The correct format: customer;caller;callee;begin;end;")) {
                return bill.getCustomer();
            }
            // Check if content in .txt file is invalid
            else if(bill.getCustomer().contains("File cannot be parsed.. ")) {
                return bill.getCustomer();
            }
            // Check if customer in .txt file is the same as the customer passed in the command line.
            else if (!args[0].equals(bill.getCustomer())) {
                return "Customer name passed in the command line doesnt match the customer name in the .txt file.";
            }

            // Add new call to the phone bill
            bill.addPhoneCall(call);
        }

        // Dump the phone bill back to the .txt file.
        PrettyPrinter dumper = new PrettyPrinter(new FileWriter(textFile, false));
        dumper.dump(bill);
    }
    
    // Check for '-print' option
    if(print) {
        return bill + "\n" + call;
    }
    else {
        return "";
    }
  }
}