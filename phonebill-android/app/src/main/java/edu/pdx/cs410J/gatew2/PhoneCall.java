package edu.pdx.cs410J.gatew2;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import edu.pdx.cs410J.AbstractPhoneCall;

/**
 * This class represents a <code>PhoneCall</code>.
 */
public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall> {
    private final String caller;
    private final String callee;
    private final String begin;
    private final String end;

    /**
     * Creates a new <code>PhoneCall</code>
     *
     * @param caller
     *        The caller's phone number
     * @param callee
     *        The callee's phone number
     * @param begin
     *        The beginning time and date of the phone call
     * @param end
     *        The end time and date of the phone call
     */
    PhoneCall(String caller, String callee, String begin, String end) {
        this.caller = caller;
        this.callee = callee;
        this.begin = begin;
        this.end = end;
    }

    /**
     * Returns a <code>String</code> that contains
     * the callers phone number.
     */
    @Override
    public String getCaller() {
        return caller;
    }

    /**
     * Returns a <code>String</code> that contains
     * the callees phone number.
     */
    @Override
    public String getCallee() {
        return callee;
    }

    /**
     * Returns a formatted <code>String</code> of the beginning
     * date and time of a <code>PhoneCall</code>.
     */
    @Override
    public String getBeginTimeString() {
        Date aDate = getBeginTime();

        return formatDateString(aDate);
    }

    /**
     * Returns a <code>Date</code> of the beginning
     * date and time of a <code>PhoneCall</code>
     */
    public Date getBeginTime() {
        return new Date(begin);
    }

    /**
     * Returns the unformatted begin date <code>String</code>.
     */
    public String getBeginTimeStringForTextFiles() {
        return this.begin;
    }
    /**
     * Returns a formatted <code>String</code> of the ending
     * date and time of a <code>PhoneCall</code>.
     */
    @Override
    public String getEndTimeString() {
        Date aDate = getEndTime();

        return formatDateString(aDate);
    }

    /**
     * Returns a <code>Date</code> of the ending
     * date and time of a <code>PhoneCall</code>.
     */
    public Date getEndTime() {
        return new Date(end);
    }

    /**
     * Returns the unformatted end date <code>String</code>.
     */
    public String getEndTimeStringForTextFiles() {
        return this.end;
    }

    /**
     * Formats a <code>Date</code> object and returns it
     * as a <code>String</code>.
     * @param aDate
     *        <code>Date</code> object of the beginning or
     *        ending date and time of a <code>PhoneCall</code>
     */
    public String formatDateString(Date aDate) {
        Locale currentLocale = Locale.US;
        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, currentLocale);

        return formatter.format(aDate);
    }

    /**
     * Overrides the compareTo function in the
     * <code>Comparable</code> interface to sort
     * <code>PhoneCalls</code>.
     *
     * @param aCall
     *        the object to be compared.
     */
    @Override
    public int compareTo(PhoneCall aCall) {
        // If dates are not equal
        if (this.getBeginTime().compareTo(aCall.getBeginTime()) != 0) {
            return this.getBeginTime().compareTo(aCall.getBeginTime());
        } else {
            // compare caller numbers if dates are equal
            return this.caller.compareTo(aCall.getCaller());
        }
    }
}
