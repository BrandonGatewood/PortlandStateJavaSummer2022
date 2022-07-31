package edu.pdx.cs410J.gatew2;

import java.text.ParseException;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages
{
    /**
     * Returns a <code>String</code> from the server side when
     * a required parameter is missing.
     * @param parameterName
     *        Name of the missing parameter
     */
    public static String missingRequiredParameter( String parameterName )
    {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

    /**
     * Returns a <code>String</code> from the server side when there
     * is no <code>PhoneBill</code> for a customer.
     * @param customer
     *        Name of a customer
     */
    public static String noPhoneBillForCustomer(String customer) {
        return String.format("No phone bill for customer named %s.",  customer);
    }

    /**
     * Returns a <code>String</code> from the server side when a
     * <code>PhoneCall</code> has been added into a customers
     * <code>PhoneBill</code>
     *
     * @param call
     *        <code>String</code> representation of a <code>PhoneCall</code>
     */
    public static String addedCall(String call) {
        return String.format("Newly added %s", call);
    }

    /**
     * Returns <code>String</code> from the server side when a <code>PhoneCall</code>
     * needs to be printed in a pretty format.
     *
     * @param aCall
     *        A <code>PhoneCall</code> object
     */
    public static String printPrettyPhoneCall(PhoneCall aCall) throws ParseException {
        return PrettyPrinter.dump(aCall);
    }
}
