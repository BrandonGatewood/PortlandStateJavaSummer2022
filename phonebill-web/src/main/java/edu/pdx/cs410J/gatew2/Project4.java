package edu.pdx.cs410J.gatew2;

import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String... args) throws ParseException, IOException {
        if (args.length == 0) {
            usage(MISSING_ARGS);
        } else {
            List<String> newArgs = new ArrayList<>(Arrays.asList(args));
            String errorMessage = checkForInputOptions(newArgs);
            System.err.println(errorMessage);
        }
    }

    /**
     * Ensures that host and port are entered correctly and checks
     * for all possible command line options that can be entered.
     *
     * Returning a specific message based on the options entered or
     * if an error occurred.
     *
     * @param args
     *        Command line arguments
     */
    public static String checkForInputOptions(List<String> args) throws ParseException, IOException {
        String hostName;
        String portString;
        boolean search = false;
        boolean print = false;

        // Set hostName
        if(args.get(0).equals("-host")) {
            hostName = args.get(1);
        }
        else {
            return "-host hostName must be the first two arguments.";
        }
        // Set portString
        if(args.get(2).equals("-port")) {
            portString = args.get(3);
        }
        else {
            return "-port port must be the third and fourth argument.";
        }
        // remove -host and -port from list
        args.remove(3);
        args.remove(2);
        args.remove(1);
        args.remove(0);

        int printIndex = 0;
        int searchIndex = 0;

        // Check for options
        for(int i = 0; i < args.size(); ++i) {
            switch (args.get(i)) {
                case "-README":
                    try (
                            InputStream readme = Project4.class.getResourceAsStream("README.txt")
                    ) {
                        assert readme != null;
                        BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
                        String read = reader.readLine();
                        StringBuilder readMe = new StringBuilder();
                        while (read != null) {
                            readMe.append(read).append("\n");
                            read = reader.readLine();
                        }
                        return readMe.toString();
                    } catch (IOException e) {
                        return "ERROR, failed or interrupted I/O operation.";
                    }
                case "-print":
                    print = true;
                    printIndex = i;
                    break;
                case "-search":
                    search = true;
                    searchIndex = i;
                    break;
            }
        }
        // Check if hostName is null
        if (hostName == null) {
            usage( MISSING_ARGS );
            return "";
        }
        // Check if port is null
        else if ( portString == null) {
            usage( "Missing port" );
            return "";
        }
        // Check if both '-print and '-search' are true
        else if(search && print) {
            return "'-search' and '-print' options cannot be used at the same time.";
        }

        int port;
        try {
            port = Integer.parseInt( portString );

        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return "";
        }

        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // Remove index for '-print' and '-search'
        if(search) {
            args.remove(searchIndex);
        }
        if(print) {
            args.remove(printIndex);
        }

        return validateArgumentsLength(client, args, print, search);
    }

    /**
     * Validates all arguments entered after from the command line.
     * Argument lengths may vary depending on '-print', '-search', adding
     * a new <code>PhoneCall</code>, and printing all <code>PhoneCall</code> for a customer.
     *
     * Returning a specific message based on the options entered or
     * if an error occurred.
     *
     * '-search' option will need 7 command line arguments.
     * Printing all <code>PhoneCall</code>s for a customer will need 1 command line argument.
     * '-print' option and adding a new <code>PhoneCall</code> will need 9 command line arguments.
     *
     * @param client
     *        A <code>PhoneBilRestClient</code> object
     * @param args
     *        List of command line arguments
     * @param print
     *        Print flag for '-print' option
     * @param search
     *        Search flag for '-search' option
     */
    public static String validateArgumentsLength(PhoneBillRestClient client, List<String> args, boolean print, boolean search) throws ParseException, IOException {
        if(search) {
            // Must validate argument length for correct dates
            if(args.size() == 0) {
                return "Missing customer name.";
            }
            else if(args.size() == 1) {
                return "Missing beginning date (mm/dd/yyyy).";
            }
            else if(args.size() == 2) {
                return "Missing beginning time (hh:mm).";
            }
            else if(args.size() == 3) {
                return "Missing ending \"am\"/\"pm\".";
            }
            else if(args.size() == 4) {
                return "Missing ending date (mm/dd/yyyy).";
            }
            else if(args.size() == 5) {
                return "Missing ending time (hh:mm).";
            }
            else if(args.size() == 6) {
                return "Missing ending \"am\"/\"pm\".";
            }
            else if(args.size() == 7) {
                return validateEachArgumentForDates(client, args);
            }
            else {
                return "Too many arguments.";
            }
        }
        // Pretty print all PhoneCalls in PhoneBill
        else if(args.size() == 1) {
            // args.get(0) is customer name and must pretty print its PhoneCalls
            return prettyPrint(client, args);
        }
        else {
            // Must validate caller, callee, begin date, begin time, begin am/pm, end date, end time, and end am/pm
            if(args.size() == 2) {
                return "Missing callee number (nnn-nnn-nnnn).";
            }
            else if(args.size() == 3) {
                return "Missing beginning date (mm/dd/yyyy).";
            }
            else if(args.size() == 4) {
                return "Missing beginning time (hh:mm).";
            }
            else if(args.size() == 5) {
                return "Missing ending \"am\"/\"pm\".";
            }
            else if(args.size() == 6) {
                return "Missing ending date (mm/dd/yyyy).";
            }
            else if(args.size() == 7) {
                return "Missing ending time (hh:mm).";
            }
            else if(args.size() == 8) {
                return "Missing ending \"am\"/\"pm\".";
            }
            else if(args.size() == 9) {
                return validateEachArgument(client, args, print);
            }
            else {
                return "Too many arguments.";
            }
        }
    }

    /**
     * Returns a pretty formatted <code>String</code> representing all
     * <code>PhoneCalls</code> from a customer retrieved from the server.
     *
     * @param client
     *        A <code>PhoneBillRestClient</code> object
     * @param args
     *        List of arguments from the command line
     */
    public static String prettyPrint(PhoneBillRestClient client, List<String> args) throws IOException {
        HttpRequestHelper.Response response;

        response = client.getAllPhoneCallsForCustomer(args.get(0));

        return response.getContent();
    }

    /**
     * Validates each command line argument to add a new <code>PhoneCall</code>
     * to the server.
     *
     * Returning a specific message based on the '-print' option or
     * if an error occurred.
     *
     * @param client
     *        A <code>PhoneBillRestClient</code> object
     * @param args
     *        List of command line arguments
     * @param print
     *        Print flag to print the new <code>PhoneCall</code>
     */
    public static String validateEachArgument(PhoneBillRestClient client, List<String> args, boolean print) throws ParseException, IOException {
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
            String begin;
            String end;
            String dateError = checkDates(beginDate, beginTime, beginAmPm, endDate, endTime, endAmPm);
            if (dateError != null) {
                return dateError;
            }
            else {
                begin = beginDate + " " + beginTime + " " + beginAmPm;
                end = endDate + " " + endTime + " " + endAmPm;
            }

            String[] newArgs = {customer, callerNumber, calleeNumber, begin, end};

            return addPhoneCall(client, newArgs, print);
        }
    }

    /**
     * Validate each command line argument for finding all <code>PhoneCall</code>s
     * for a <code>PhoneBill</code> within a given range.
     *
     * Returning a specific message if an error occurred or a list of <code>PhoneCall</code>s.
     *
     * @param client
     *        A <code>PhoneBillRestClient</code>
     * @param args
     *        List of command line arguments
     */
    public static String validateEachArgumentForDates(PhoneBillRestClient client, List<String> args) throws ParseException, IOException {
        String beginDate = args.get(1);
        String beginTime = args.get(2);
        String beginAmPm = args.get(3);
        String endDate = args.get(4);
        String endTime = args.get(5);
        String endAmPm = args.get(6);

        if(!beginDate.matches("([1-9]|0[1-9]|[1-6]\\d)/([1-9]|0[1-9]|[1-6]\\d)/(\\d{4})")) {
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
            String begin;
            String end;
            String dateError = checkDates(beginDate, beginTime, beginAmPm, endDate, endTime, endAmPm);
            if (dateError != null) {
                return dateError;
            }
            else {
                begin = beginDate + " " + beginTime + " " + beginAmPm;
                end = endDate + " " + endTime + " " + endAmPm;
            }

            String[] newArgs = {args.get(0), begin, end};
            return search(client, newArgs);
        }
    }

    /**
     * Checks if the end date is before the beginning date.
     *
     * Returns null if end date is after the beginning date or
     * a <code>String</code> if end date is before the beginning date.
     *
     * @param beginDate
     *        The beginning date
     * @param beginTime
     *        The beginning time
     * @param beginAmPm
     *        The beginning am/pm
     * @param endDate
     *        The ending date
     * @param endTime
     *        The ending time
     * @param endAmPm
     *        The ending am/pm
     */
    public static String checkDates(String beginDate, String beginTime, String beginAmPm, String endDate, String endTime, String endAmPm) throws ParseException {
        // Append date and time substrings
        String begin = beginDate + " " + beginTime + " " + beginAmPm;
        String end = endDate + " " + endTime + " " + endAmPm;

        // Check if end date is before begin date.
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);

        Date dateBegin = sdf.parse(begin);
        Date dateEnd = sdf.parse(end);

        if(dateBegin.compareTo(dateEnd) > 0) {
            return "Call end time cannot be before begin time.";
        }
        return null;
    }

    /**
     * Contacts the server to search through a customers <code>PhoneBill</code>
     * to find any <code>PhoneCall</code>s within a given range.
     *
     * Returning a <code>String</code> if an error occurred or a list
     * of <code>PhoneCall</code> that were made withing the given range.
     *
     * @param client
     *        A <code>PhoneBillRestClient</code> object
     * @param args
     *        List of command line arguments
     */
    public static String search(PhoneBillRestClient client, String[] args) throws IOException {
        HttpRequestHelper.Response response;

        response = client.getRangePhoneCalls(args[0], args[1], args[2]);

        return response.getContent();
    }

    /**
     * Contacts the server to add a new <code>PhoneCall</code> to an
     * existing or new <code>PhoneBill</code>.
     *
     * Returns a <code>String</code> if an error occurred or if print flag
     * is true.
     *
     * @param client
     *        A <code>PhoneBilRestClient</code> object
     * @param args
     *        List of command line arguments
     * @param print
     *        print flag to print the newly added <code>PhoneCall</code>
     */
    public static String addPhoneCall(PhoneBillRestClient client, String[] args, boolean print) throws IOException {
        HttpRequestHelper.Response response;

        response = client.postPhoneBill(args[0], args[1], args[2], args[3], args[4]);
        if(print) {
            return response.getContent();
        }
        else {
            return "";
        }
    }

    /**
     * Prints usage information for this program and exits
     */
    private static void usage(String message) {
        PrintStream err = System.err;
        err.println("**" + message);
        err.println();
        err.println("usage: java -jar target/phonebill-client.jar -host hostname -port port [-README] [-print] [-search] customer caller callee beginDate beginTime am/pm endDate endTime am/pm\n");
        err.println("\t-host hostname: Host of web server");
        err.println("\t-port port: Port of web server");
        err.println("\t-README: Prints a README for this project and exits\n");
        err.println("\t-print: Prints a description of the new phone call\n");
        err.println("\t-search: Phone calls should be searched for");
        err.println();
    }
}