package edu.pdx.cs410J.gatew2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {
  /**
   * Prints out error messages or information about customer
   * and their <code>PhoneBill</code>.
   * @param args
   *        command line arguments
   */
  public static void main(String[] args) throws IOException, ParserException {
      if(args.length == 0) {
          String message = "Please include command line arguments. [options] <args>.";
          String options = "Options include: '-README', '-print', and \"'-textFile' '.txt'\". NOTE: Each Customer will have their own .txt file and will not check if each customer name in the file are the same.";
          String cmdLineArgs = "Args must be in the order: customer name, caller number (nnn-nnn-nnnn), callee number (nnn-nnn-nnnn), begin date (dd/dd/dddd), begin time (dd:dd), end date (dd/dd/dddd), and end time (dd:dd).";
          // No arguments entered, so display a "how to use" message.
          System.err.println(message + "\n" + options + "\n" + cmdLineArgs);
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
  static String checkForInputOptions(String[] args) throws IOException, ParserException {
    // Loop through args and check if README option was given
    for(String str:args) {
      // If '-README' option is given, then print README.txt.
      if(str.equals("-README")) {
        try (
                InputStream readme = Project1.class.getResourceAsStream("README.txt")
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
          throw new RuntimeException(e);
        }
      }
    }
    // If '-README' option WASN'T given, then check if
    // '-print' or '-textFile' '.txt' option was given.
    boolean print = false;
    String filePath = null;
    ArrayList<String> newArgs = new ArrayList<>(Arrays.asList(args));

    String errorTextFileOption = "To use '-textFile' option, it must be in the order '-textFile' '.txt'.";
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
                break;
            }
            else {
                return errorTextFileOption;
            }
        }
        else if(newArgs.get(i).contains(".txt")) {
            return errorTextFileOption;
        }
    }
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
    return validateArgLength(newArgs, print, filePath);
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
   */
  @VisibleForTesting
  static String validateArgLength(ArrayList<String> args, boolean print, String filePath) throws IOException, ParserException {
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
      return "Missing ending date (mm/dd/yyyy)";
    }
    else if(args.size() == 6) {
      return "Missing ending time (hh:mm)";
    }
    else if(args.size() == 7) {
      return validateEachArgument(args, print, filePath);
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
   */
@VisibleForTesting
 static String validateEachArgument(ArrayList<String> args, boolean print, String filePath) throws IOException, ParserException {
    String customer = args.get(0);
    String callerNumber = args.get(1);
    String calleeNumber = args.get(2);
    String beginDate = args.get(3);
    String beginTime = args.get(4);
    String endDate = args.get(5);
    String endTime = args.get(6);

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
        String begin = args.get(3) + " " + args.get(4);
        String end = args.get(5) + " " + args.get(6);
        String[] newArgs = {customer, callerNumber, calleeNumber, begin, end};
        //create new phone bill
        return phoneBill(newArgs, print, filePath);
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
   */
  @VisibleForTesting
  static String phoneBill(String[] args, boolean print, String filePath) throws IOException, ParserException {
    PhoneCall call = new PhoneCall(args[1], args[2], args[3],args[4]);
    PhoneBill bill;

    if(filePath != null) {
        File textFile = new File(filePath);
        // .txt file is empty so nothing to parse.
        if(textFile.length() == 0) {
            bill = new PhoneBill(args[0]);
            bill.addPhoneCall(call);
        }
        else {
            // Parse through the .txt file.
            TextParser parser = new TextParser(new FileReader(textFile));

            bill = parser.parse();
            // Add new call to the phone bill
            bill.addPhoneCall(call);
        }

        // Dump the phone bill back to the .txt file.
        TextDumper dumper = new TextDumper(new FileWriter(textFile, false));
        dumper.dump(bill);
    }
    else {
        // No .txt file given, so create new phone bill object for customer
        bill = new PhoneBill(args[0]);
        bill.addPhoneCall(call);
    }

    if(print)
      return bill + "\n" + call;
    else
      return "";
  }
}