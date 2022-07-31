package edu.pdx.cs410J.gatew2;

import edu.pdx.cs410J.ParserException;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.mockito.ArgumentCaptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;

import static edu.pdx.cs410J.gatew2.PhoneBillServlet.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link PhoneBillServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class PhoneBillServletTest {
    static final private PhoneBillServlet servlet = new PhoneBillServlet();
    @Test
    void aRequestWithNoCustomer() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        servlet.doGet(request, response);
        verify(response).sendError(HttpServletResponse.SC_PRECONDITION_FAILED, Messages.missingRequiredParameter("customer"));
    }
    @Test
    void bGetCustomerWithNoPhoneBillReturns1() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn("brandon");
        HttpServletResponse response = mock(HttpServletResponse.class);
        servlet.doGet(request, response);
        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND, Messages.noPhoneBillForCustomer("brandon"));
    }
    @Test
    void cGetCustomerWithNoPhoneBill2() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn("brandon");
        when(request.getParameter(BEGIN_PARAMETER)).thenReturn("12/21/2022 1:30 pm");
        when(request.getParameter(END_PARAMETER)).thenReturn("12/21/2022 1:50 pm");
        HttpServletResponse response = mock(HttpServletResponse.class);
        servlet.doGet(request, response);
        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND, Messages.noPhoneBillForCustomer("brandon"));
    }
    @Test
    void dAddPhoneCallToPhoneBillWithInvalidCallerNumber() throws IOException{
        String customer = "Brandon";
        String caller = "123-423-423";
        String callee = "123-455-3423";
        String begin = "12/12/2021 1:12 pm";
        String end = "12/12/2021 1:30 pm";

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);
        when(request.getParameter(CALLER_PARAMETER)).thenReturn(caller);
        when(request.getParameter(CALLEE_PARAMETER)).thenReturn(callee);
        when(request.getParameter(BEGIN_PARAMETER)).thenReturn(begin);
        when(request.getParameter(END_PARAMETER)).thenReturn(end);

        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter pw = new PrintWriter(stringWriter, true);

        when(response.getWriter()).thenReturn(pw);

        servlet.doPost(request, response);
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
    @Test
    void eAddPhoneCallToPhoneBillWithInvalidCalleeNumber() throws IOException{
        String customer = "Brandon";
        String caller = "123-423-4233";
        String callee = "123/455-3423";
        String begin = "12/12/2021 1:12 pm";
        String end = "12/12/2021 1:30 pm";

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);
        when(request.getParameter(CALLER_PARAMETER)).thenReturn(caller);
        when(request.getParameter(CALLEE_PARAMETER)).thenReturn(callee);
        when(request.getParameter(BEGIN_PARAMETER)).thenReturn(begin);
        when(request.getParameter(END_PARAMETER)).thenReturn(end);

        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter pw = new PrintWriter(stringWriter, true);

        when(response.getWriter()).thenReturn(pw);

        servlet.doPost(request, response);
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
    @Test
    void fAddPhoneCallToPhoneBillWithInvalidBeginDate() throws IOException{
        String customer = "Brandon";
        String caller = "123-423-4233";
        String callee = "123-455-3423";
        String begin = "12-12/2021 1:12 pm";
        String end = "12/12/2021 1:30 pm";

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);
        when(request.getParameter(CALLER_PARAMETER)).thenReturn(caller);
        when(request.getParameter(CALLEE_PARAMETER)).thenReturn(callee);
        when(request.getParameter(BEGIN_PARAMETER)).thenReturn(begin);
        when(request.getParameter(END_PARAMETER)).thenReturn(end);

        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter pw = new PrintWriter(stringWriter, true);

        when(response.getWriter()).thenReturn(pw);

        servlet.doPost(request, response);
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
    @Test
    void gAddPhoneCallToPhoneBillWithInvalidEndDate() throws IOException{
        String customer = "Brandon";
        String caller = "123-423-4233";
        String callee = "123-455-3423";
        String begin = "12/12/2021 1:12 pm";
        String end = "12/12-201 1:30 pm";

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);
        when(request.getParameter(CALLER_PARAMETER)).thenReturn(caller);
        when(request.getParameter(CALLEE_PARAMETER)).thenReturn(callee);
        when(request.getParameter(BEGIN_PARAMETER)).thenReturn(begin);
        when(request.getParameter(END_PARAMETER)).thenReturn(end);

        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter pw = new PrintWriter(stringWriter, true);

        when(response.getWriter()).thenReturn(pw);

        servlet.doPost(request, response);
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
    @Test
    void faddPhoneCallToPhoneBill() throws ServletException, IOException{
        String customer = "Brandon";
        String caller = "123-423-4233";
        String callee = "123-455-3423";
        String begin = "12/12/2021 1:12 pm";
        String end = "12/12/2021 1:30 pm";

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);
        when(request.getParameter(CALLER_PARAMETER)).thenReturn(caller);
        when(request.getParameter(CALLEE_PARAMETER)).thenReturn(callee);
        when(request.getParameter(BEGIN_PARAMETER)).thenReturn(begin);
        when(request.getParameter(END_PARAMETER)).thenReturn(end);

        HttpServletResponse response = mock(HttpServletResponse.class);

        StringWriter stringWriter = new StringWriter();
        PrintWriter pw = new PrintWriter(stringWriter, true);

        when(response.getWriter()).thenReturn(pw);

        servlet.doPost(request, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);

        PhoneBill phoneBill = servlet.getPhoneBill(customer);
        assertThat(phoneBill, notNullValue());
        assertThat(phoneBill.getCustomer(), equalTo(customer));
        PhoneCall phoneCall = phoneBill.getPhoneCalls().iterator().next();
        assertThat(phoneCall.getCaller(), equalTo(caller));
        assertThat(phoneCall.getCallee(), equalTo(callee));
        assertThat(stringWriter.toString(), containsString(""));
    }
    @Test
    public void hGetCustomerFound1() throws ServletException, IOException {
        String customer = "Brandon";
        String begin = "12/12/2021 1:12 pm";
        String end = "12/12/2021 1:30 pm";


        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter pw = new PrintWriter(stringWriter, true);

        when(response.getWriter()).thenReturn(pw);
        when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);
        when(request.getParameter(BEGIN_PARAMETER)).thenReturn(begin);
        when(request.getParameter(END_PARAMETER)).thenReturn(end);
        servlet.doGet(request, response);

        ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(statusCode.capture());

        assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));
        assertThat(stringWriter.toString(), containsString(customer));
    }
    @Test
    public void gGetCustomerFound1() throws ServletException, IOException {
        String customer = "Brandon";
        String caller = "123-423-4233";
        String callee = "123-455-3423";


        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        StringWriter stringWriter = new StringWriter();
        PrintWriter pw = new PrintWriter(stringWriter, true);

        when(response.getWriter()).thenReturn(pw);
        when(request.getParameter(CUSTOMER_PARAMETER)).thenReturn(customer);

        servlet.doGet(request, response);

        ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
        verify(response).setStatus(statusCode.capture());

        assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));
        assertThat(stringWriter.toString(), containsString(customer));
        assertThat(stringWriter.toString(), containsString(callee));
    }
}
