package edu.pdx.cs410J.gatew2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static edu.pdx.cs410J.web.HttpRequestHelper.Response;
import static edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class PhoneBillRestClient {

    private static final String WEB_APP = "phonebill";
    private static final String SERVLET = "calls";
    static final String CUSTOMER_PARAMETER = "customer";
    static final String CALLER_PARAMETER = "caller";
    static final String CALLEE_PARAMETER = "callee";
    static final String BEGIN_PARAMETER = "begin";
    static final String END_PARAMETER = "end";

  private final HttpRequestHelper http;


    /**
     * Creates a client to the Phone Bil REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public PhoneBillRestClient( String hostName, int port )
    {
      this(new HttpRequestHelper(String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET)));
    }

  @VisibleForTesting
  PhoneBillRestClient(HttpRequestHelper http) {
    this.http = http;
  }

  /**
   * GETS ALL <code>PhoneCall</code> in a <code>PhoneBill</code> for a customer
   * from the server.
   */
  public Response getAllPhoneCallsForCustomer(String customer) throws IOException {
    return http.get(Map.of(CUSTOMER_PARAMETER, customer));
  }

  /**
   * GETs all <code>PhoneCall</code> between begin and end time from
   * the server.
   *
   * @param customer
   *        A customers name
   * @param begin
   *        A beginning date
   * @param end
   *        An ending date
   */
  public Response getRangePhoneCalls(String customer, String begin, String end) throws IOException {
    return http.get(Map.of(CUSTOMER_PARAMETER, customer, BEGIN_PARAMETER, begin, END_PARAMETER, end));
  }

  /**
   * POSTs a new <code>PhoneCall</code> to an existing or new <code>PhoneBill</code>
   * to the server.
   *
   * @param customer
   *        A customers name
   * @param caller
   *        The customers phone number
   * @param callee
   *        The calles phone number
   * @param begin
   *        A beginning date of the phone call
   * @param end
   *        An ending date of the phone call
   */
  public Response postPhoneBill(String customer, String caller, String callee, String begin, String end) throws IOException {
    return http.post(Map.of(CUSTOMER_PARAMETER, customer, CALLER_PARAMETER, caller, CALLEE_PARAMETER, callee, BEGIN_PARAMETER, begin, END_PARAMETER, end));
  }

  /**
   * Returns the <code>PhoneCall</code>s for the given customer
   * from the server
   *
   * @param customer
   *        Customers name
   * @param begin
   *        Begin date and time of <code>PhoneCall</code>
   * @param end
   *        End date and time of <code>PhoneCall</code>
   */
  public String getPhoneBill(String customer, String begin, String end) throws IOException, ParserException {
    Response response;
    if(begin == null || end == null) {
        response = http.get(Map.of(CUSTOMER_PARAMETER, customer));
    }
    else {
      response = http.get(Map.of(CUSTOMER_PARAMETER, customer, BEGIN_PARAMETER, begin, END_PARAMETER, end));
    }

    throwExceptionIfNotOkayHttpStatus(response);
    return response.getContent();
  }

  /**
   * Throws a new <code>RestException</code> if the HTTP
   * response is not okay.
   * @param response
   *        A HTTP response
   */
  private void throwExceptionIfNotOkayHttpStatus(Response response) {
    int code = response.getHttpStatusCode();
    if (code != HTTP_OK) {
      String message = response.getContent();
      throw new RestException(code, message);
    }
  }
}
