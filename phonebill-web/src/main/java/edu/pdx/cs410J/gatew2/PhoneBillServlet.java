package edu.pdx.cs410J.gatew2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.*;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>PhoneBill</code>.
 */
public class PhoneBillServlet extends HttpServlet
{
    static final String CUSTOMER_PARAMETER = "customer";
    static final String CALLER_PARAMETER = "caller";
    static final String CALLEE_PARAMETER = "callee";
    static final String BEGIN_PARAMETER = "begin";
    static final String END_PARAMETER = "end";
    private final Map<String, PhoneBill> phoneBills = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by fetching
     * <code>PhoneCall</code>s from a <code>PhoneBill</code>.
     *
     * If begin and end is null, then all <code>PhoneCall</code>s
     * for a customers <code>PhoneBill</code> is written to the
     * HTTP response.
     *
     * If begin and end is not null, then all <code>PhoneCall</code>s
     * withing the range of begin and end for a customers <code>PhoneBill</code>
     * is written to the HTTP response.
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
        response.setContentType( "text/plain" );

        String customer = getParameter(CUSTOMER_PARAMETER, request );
        String begin = getParameter(BEGIN_PARAMETER, request);
        String end = getParameter(END_PARAMETER, request);


        if (customer == null) {
            missingRequiredParameter(response, CUSTOMER_PARAMETER);
            return;
        }
        if(begin == null && end == null) {
                try {
                    DisplayAllPhoneCallsForCustomer(response, customer);
                } catch (ParseException | ParserException e) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, Messages.noPhoneBillForCustomer(customer));
                }
        }
        if (begin != null && end != null) {
            try {
                DisplayPhoneCallsWithinSpecifiedRange(customer, begin, end, response);
            } catch (ParseException | ParserException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, Messages.noPhoneBillForCustomer(customer));
            }
        }

    }

    /**
     * Handles an HTTP POST request by storing a <code>PhoneCall</code> entry
     * to an existing or new <code>PhoneBill</code>.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
        response.setContentType( "text/plain" );

        String customer = getParameter(CUSTOMER_PARAMETER, request );
        if (customer == null) {
            missingRequiredParameter(response, CUSTOMER_PARAMETER);
            return;
        }

        String caller = getParameter(CALLER_PARAMETER, request );
        if (caller != null) {
            try {
                ValidatePhoneNumber(caller);
            } catch (ParserException e) {
                writeIncorrectPhoneFormat(CALLER_PARAMETER, e.getMessage(), response);
                return;
            }
        }

        String callee = getParameter(CALLEE_PARAMETER, request );
        if ( callee != null) {
            try {
                ValidatePhoneNumber(callee);
            } catch (ParserException e) {
                writeIncorrectPhoneFormat(CALLEE_PARAMETER, e.getMessage(), response);
                return;
            }
        }

        String begin = getParameter(BEGIN_PARAMETER, request );

        if (begin != null) {
            try {
                validateDate(begin);
            }
            catch (ParserException e) {
                writeIncorrectDateFormat(BEGIN_PARAMETER, e.getMessage(), response);
                return;
            }
        }

        String end = getParameter(END_PARAMETER, request );
        if (end != null) {
            try {
                validateDate(end);
            } catch (ParserException e) {
                writeIncorrectDateFormat(END_PARAMETER, e.getMessage(), response);
                return;
            }
        }

        PhoneCall call = new PhoneCall(caller, callee, begin, end);

        // Create a new Phone Bill
        if(phoneBills.get(customer) == null) {
            PhoneBill newBill = new PhoneBill(customer);

            newBill.addPhoneCall(call);

            this.phoneBills.put(customer, newBill);
        }
        else {
            // Get customers existing PhoneBill
            PhoneBill existingPhoneBill = phoneBills.get(customer);
            existingPhoneBill.addPhoneCall(call);
        }

        PrintWriter pw = response.getWriter();
        pw.println(Messages.addedCall(call.toString()));
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName ) throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
      String value = request.getParameter(name);
      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }
    }

    /**
     * Writes all <code>PhoneCall</code>s for a customers <code>PhoneBill</code>
     * to the HTTP response.
     */
    public void DisplayAllPhoneCallsForCustomer(HttpServletResponse response, String customer) throws IOException, ParseException, ParserException {
        PrintWriter pw = response.getWriter();

        if(phoneBills.get(customer) != null) {
            pw.println("Customer: " + customer);
            Collection<PhoneCall> allCalls = phoneBills.get(customer).getPhoneCalls();
            for (PhoneCall call : allCalls) {
                pw.println(Messages.printPrettyPhoneCall(call));
            }
        }
        else {
            throw new ParserException("No PhoneBill or PhoneCalls for " + customer);
        }

        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Writes all <code>PhoneCall</code>s for a customers <code>PhoneBill</code>
     * thats within the given range to the HTTP response.
     * @param customer
     *        A customers name
     * @param begin
     *        The beginning date
     * @param end
     *        The ending date
     * @param response
     *        The servers response
     */
    @VisibleForTesting
    public void DisplayPhoneCallsWithinSpecifiedRange(String customer, String begin, String end, HttpServletResponse response) throws IOException, ParseException, ParserException {
        PrintWriter pw = response.getWriter();
        try {
            validateDate(begin);
        } catch (ParserException e) {
            writeIncorrectDateFormat(BEGIN_PARAMETER, e.getMessage(), response);
            return;
        }
        try {
            validateDate(end);
        } catch (ParserException e) {
            writeIncorrectDateFormat(END_PARAMETER,e.getMessage(), response);
            return;
        }

        // A phoneBill exist for customer
        if(phoneBills.get(customer) != null) {
            Collection<PhoneCall> allCalls = phoneBills.get(customer).getPhoneCalls();
            ArrayList<PhoneCall> rangeCalls = new ArrayList<>();
            Date beginDate = new Date(begin);
            Date endDate = new Date(end);

            // Find Calls within the range of begin and end
            for (PhoneCall call : allCalls) {
                if (call.getBeginTime().compareTo(beginDate) >= 0 && call.getBeginTime().compareTo(endDate) <= 0) {
                    rangeCalls.add(call);
                }
            }

            // No calls within the range
            if (rangeCalls.size() == 0) {
                pw.println("No phone calls were found within the specified begin and end time for " + customer + ".");
            }
            else {
                pw.println("PhoneCalls for: " + customer + " between " + begin + " - " + end);
                for (PhoneCall call : rangeCalls) {
                    pw.println(Messages.printPrettyPhoneCall(call));
                }
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }
        // No PhoneBill exist for customer
        else {
            throw new ParserException("No PhoneBill or PhoneCalls for " + customer);
        }

        pw.flush();
    }

    /**
     * Validates a phone number in the server
     * @param aNumber
     *        A phone number
     */
    private void ValidatePhoneNumber(String aNumber) throws ParserException {
        if(!aNumber.matches("(\\d{3})-(\\d{3})-(\\d{4})")) {
            throw new ParserException("Phone Numbers can only be in the format: nnn-nnn-nnnn.");
        }
    }

    /**
     * Validates a date in the server
     * @param aDate
     *        A date
     */
    public void validateDate(String aDate) throws ParserException {
        if(!aDate.matches("(([1-9]|0[1-9]|[1-6]\\d)/([1-9]|0[1-9]|[1-6]\\d)/(\\d{4}) ((\\d|0\\d|1[0-2]):(\\d|0\\d|[1-6]\\d)) (am|AM|pm|PM))")) {
            throw new ParserException("Dates can only be in the format: mm/dd/yyyy hh:mm am/pm.");
        }
    }

    /**
     * Writes a message to the HTTP response when a phone number in the server
     * is invalid.
     *
     * @param number
     *        A phone number
     * @param message
     *        Error message
     * @param response
     *        A HTTP response
     */
    private void writeIncorrectPhoneFormat(String number, String message, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        pw.println(number + ": " + message);
        pw.flush();

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Writes to the HTTP response when a date in the server is invalid.
     *
     * @param date
     *        A date
     * @param message
     *        Error message
     * @param response
     *        A HTTP response
     */
    private void writeIncorrectDateFormat(String date, String message, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        pw.println(date + " : " + message);
        pw.flush();
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Returns a <code>PhoneBill</code> for a specified customer.
     *
     * @param customer
     *        A customers name
     */
    @VisibleForTesting
    PhoneBill getPhoneBill(String customer) {
        return phoneBills.get(customer);
    }
}
