package edu.pdx.cs410J.gatew2;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.PrintWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class PrettyPrinter implements PhoneBillDumper<PhoneBill> {
    private final Writer writer;

    /**
     * Assigns passed in <code>Writer</code> to <code>TextDumper</code>'s <code>Writer</code>.
     * @param writer
     *        Contains writing information for the given .txt file.
     */
    public PrettyPrinter(Writer writer) {
        this.writer = writer;
    }

    /**
     * Dumps and formats a <code>PhoneBill</code> and <code>PhoneCall</code> into
     * an external .txt file.
     * @param bill
     *        <code>PhoneBill</code> object.
     */
    @Override
    public void dump(PhoneBill bill){
        try (
                PrintWriter pw = new PrintWriter(this.writer)
        ) {
            // Get the collection of phone calls from bill
            Vector<PhoneCall> phoneCalls = new Vector<>(bill.getPhoneCalls());

            // Customer has no PhoneCalls
            if(!phoneCalls.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy, hh:mm", Locale.US);

                // Get the customers name
                pw.println("Customer: " + bill.getCustomer());
                pw.println("Number: " + phoneCalls.get(0).getCaller());

                // Loop through each phone call and save their information
                for (int i = 0; i < phoneCalls.size(); ++i) {
                    String calleeNumber = phoneCalls.get(i).getCallee();
                    String begin = phoneCalls.get(i).getBeginTimeString();
                    String end = phoneCalls.get(i).getEndTimeString();

                    // Calculate duration of the phone call
                    Date firstDate = sdf.parse(begin);
                    Date secondDate = sdf.parse(end);
                    long diff = secondDate.getTime() - firstDate.getTime();
                    long min = TimeUnit.MILLISECONDS.toMinutes(diff);

                    pw.println(i+1 + ".\tCall with: " + calleeNumber + " from " + begin + " - " + end + ".");
                    pw.println("\tDuration of call: " + min + " minutes.");
                }
            }
            pw.flush();
        } catch (ParseException e) {
            System.out.println("ERROR, failed or interrupted I/O operation.");
        }
    }
}

