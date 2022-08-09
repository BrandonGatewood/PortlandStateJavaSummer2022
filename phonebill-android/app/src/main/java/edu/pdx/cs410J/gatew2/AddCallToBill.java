package edu.pdx.cs410J.gatew2;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddCallToBill extends AppCompatActivity {
    private EditText customerField;
    private EditText callerField;
    private EditText calleeField;
    private EditText beginField;
    private RadioGroup beginAmPmField;
    private EditText endField;
    private RadioGroup endAmPmField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_call_to_bill);
    }

    /**
     * Resets all input fields in activity_add_call_to_bill.xml
     *
     * @param view
     *        Reference to addPhoneBillResetButton button
     */
    public void clearInputs(View view) {
        customerField = this.findViewById(R.id.addPhoneBillCustomer);
        callerField = this.findViewById(R.id.addPhoneBillCaller);
        calleeField = this.findViewById(R.id.addPhoneBillCallee);
        beginField = this.findViewById(R.id.addPhoneBillBeginDate);
        beginAmPmField = this.findViewById(R.id.addPhoneBillBeginAmPm);
        endField = this.findViewById(R.id.addPhoneBillEndDate);
        endAmPmField = this.findViewById(R.id.addPhoneBillEndAmPm);

        if(customerField != null) {
            customerField.setText("");
        }
        if(callerField != null) {
            callerField.setText("");
        }
        if(calleeField != null) {
            calleeField.setText("");
        }
        if(beginField != null) {
            beginField.setText("");
        }
        if(endField != null) {
            endField.setText("");
        }
        if(beginAmPmField != null) {
            beginAmPmField.clearCheck();
        }
        if(endAmPmField != null) {
            endAmPmField.clearCheck();
        }
    }

    /**
     * Checks if ALL input fields have been entered before validating them
     *
     * @param view
     *        Reference to addPhoneBillSubmitButton button
     */
    public void submitInputs(View view) {
        customerField = this.findViewById(R.id.addPhoneBillCustomer);
        callerField = this.findViewById(R.id.addPhoneBillCaller);
        calleeField = this.findViewById(R.id.addPhoneBillCallee);
        beginField = this.findViewById(R.id.addPhoneBillBeginDate);
        beginAmPmField = this.findViewById(R.id.addPhoneBillBeginAmPm);
        endField = this.findViewById(R.id.addPhoneBillEndDate);
        endAmPmField = this.findViewById(R.id.addPhoneBillEndAmPm);

        // First check if any of the required fields is missing
        if(TextUtils.isEmpty(customerField.getText().toString())) {
            Toast.makeText(this, "Missing Customer Name", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(callerField.getText().toString())) {
            Toast.makeText(this, "Missing Caller Number", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(calleeField.getText().toString())) {
            Toast.makeText(this, "Missing Callee Number", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(beginField.getText().toString())) {
            Toast.makeText(this, "Missing Beginning Date", Toast.LENGTH_LONG).show();
        }
        else if(beginAmPmField.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Missing Beginning am or pm", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(endField.getText().toString())) {
            Toast.makeText(this, "Missing Ending Date", Toast.LENGTH_LONG).show();
        }
        else if(endAmPmField.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Missing Ending am or pm", Toast.LENGTH_LONG).show();
        }
        else {
            try {
                validateInputFields();
            } catch (ParseException | IOException e) {
                Toast.makeText(this, "Could not parse date", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Validates ALL input fields before adding a new <code>PhoneCall</code>
     * into a <code>PhoneBill</code>
     */
    private void validateInputFields() throws ParseException, IOException {
        RadioButton selectedBeginAmPm = this.findViewById(beginAmPmField.getCheckedRadioButtonId());
        RadioButton selectedEndAmPm = this.findViewById(endAmPmField.getCheckedRadioButtonId());
        String customer = customerField.getText().toString();
        String caller = callerField.getText().toString();
        String callee = calleeField.getText().toString();
        String begin = beginField.getText().toString() + " " + selectedBeginAmPm.getText().toString();
        String end = endField.getText().toString() + " " + selectedEndAmPm.getText().toString();

        // Validate caller number
        if(!caller.matches("(\\d{3})-(\\d{3})-(\\d{4})")) {
            // Incorrect format
            Toast.makeText(this, "Caller number can only be in the format: nnn-nnn-nnnn.", Toast.LENGTH_LONG).show();
        }
        // Validate callee number
        else if(!callee.matches("(\\d{3})-(\\d{3})-(\\d{4})")) {
            // Incorrect format
            Toast.makeText(this, "Callee number can only be in the format: nnn-nnn-nnnn.", Toast.LENGTH_LONG).show();
        }
        // validate Beginning date
        else if(!begin.matches("(([1-9]|0[1-9]|[1-6]\\d)/([1-9]|0[1-9]|[1-6]\\d)/(\\d{4}) ((\\d|0\\d|1[0-2]):(\\d|0\\d|[1-6]\\d)) (am|pm))")) {
            AlertDialog dlg = new AlertDialog.Builder(AddCallToBill.this)
                    .setTitle("Invalid Beginning Date")
                    .setMessage("Beginning date can only be in the format: mm/dd/yyyy.\nmonth and day can be from 1-69 or 01-69 BUT cannot be \"0\".")
                    .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss())
                    .create();
            dlg.show();
        }
        // Validate ending date
        else if(!end.matches("(([1-9]|0[1-9]|[1-6]\\d)/([1-9]|0[1-9]|[1-6]\\d)/(\\d{4}) ((\\d|0\\d|1[0-2]):(\\d|0\\d|[1-6]\\d)) (am|pm))")) {
            AlertDialog dlg = new AlertDialog.Builder(AddCallToBill.this)
                    .setTitle("Invalid Ending Date")
                    .setMessage("Ending date can only be in the format: mm/dd/yyyy.\nmonth and day can be from 1-69 or 01-69 BUT cannot be \"0\".")
                    .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss())
                    .create();
            dlg.show();}
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);

            Date dateBegin = sdf.parse(begin);
            Date dateEnd = sdf.parse(end);

            // Check if end date occurs before beginning date.
            assert dateBegin != null;
            if(dateBegin.compareTo(dateEnd) > 0) {
                Toast.makeText(this, "Call end time cannot be before begin time.", Toast.LENGTH_LONG).show();
            }
            else {
                // Add the new Phone call
                PhoneCall newCall =  new PhoneCall(caller, callee, begin, end);
                addNewPhoneCall(customer, newCall);
            }
        }
    }

    /**
     * Parses customers .txt file if it exists and creates a <code>PhoneBill</code> for
     * that customer, then adds the new <code>PhoneCall</code>. It will then dump the new
     * or existing <code>PhoneBill</code> into an appropriate .txt file.
     *
     * @param customer
     *        customer name
     * @param newCall
     *        customers new <code>PhoneCall</code>
     */
    private void addNewPhoneCall(String customer, PhoneCall newCall) throws IOException {
        PhoneBill newBill = new PhoneBill(customer);
        newBill.addPhoneCall(newCall);
        String file = customer + ".txt";
        File dataDirectory = this.getDataDir();
        File customerFile = new File(dataDirectory, file);

        if(customerFile.length() != 0) {
            // Parse through the .txt file.
            TextParser parser = new TextParser(new FileReader(customerFile));
            newBill = parser.parse();

            // Check if .txt file is malformed.
            if(newBill.getCustomer().equals("The .txt file is formatted incorrectly. The correct format: customer;caller;callee;begin;end;")) {
                Toast.makeText(this, newBill.getCustomer(), Toast.LENGTH_LONG).show();
                return;
            }
            // Check if content in .txt file is invalid
            else if(newBill.getCustomer().contains("File cannot be parsed.. ")) {
                Toast.makeText(this, newBill.getCustomer(), Toast.LENGTH_LONG).show();
                return;
            }
            // Check if customer in .txt file is the same as the customer passed in the command line.
            else if (!customer.equals(newBill.getCustomer())) {
                Toast.makeText(this, "Customer name passed in the command line doesnt match the customer name in the .txt file.", Toast.LENGTH_LONG).show();
                return;
            }
            else {
                // Add new call to the phone bill
                newBill.addPhoneCall(newCall);
            }
        }

        // Dump the PhoneBill to .txt file
        TextDumper dumper = new TextDumper(new FileWriter(customerFile, false));
        dumper.dump(newBill);

        AlertDialog dlg = new AlertDialog.Builder(AddCallToBill.this)
                .setTitle("Added New Phone Call")
                .setMessage(newBill + "\n" + newCall.toString())
                .setPositiveButton("OK", (dialogInterface, i) -> finish())
                .create();
        dlg.show();
    }
}