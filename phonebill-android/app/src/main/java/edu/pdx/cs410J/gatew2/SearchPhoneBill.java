package edu.pdx.cs410J.gatew2;

import android.content.Intent;
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
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SearchPhoneBill extends AppCompatActivity {
    private EditText customerField;
    private EditText beginField;
    private RadioGroup beginAmPmField;
    private EditText endField;
    private RadioGroup endAmPmField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_phone_bill);
    }

    /**
     * Checks if All input fields have been entered before validating them
     *
     * @param view
     *        Reference to searchPhoneBillSubmitButton button
     */
    public void submitInputs(View view) {
        customerField = this.findViewById(R.id.searchPhoneBillCustomer);
        beginField = this.findViewById(R.id.searchPhoneBillBeginDate);
        beginAmPmField = this.findViewById(R.id.searchPhoneBillBeginAmPm);
        endField = this.findViewById(R.id.searchPhoneBillEndDate);
        endAmPmField = this.findViewById(R.id.searchPhoneBillEndAmPm);

        // First check if any of the required fields is missing
        if(TextUtils.isEmpty(customerField.getText().toString())) {
            Toast.makeText(this, "Missing Customer Name", Toast.LENGTH_LONG).show();
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
                validateInputFields(view);
            } catch (ParseException | IOException e) {
                Toast.makeText(this, "Could not parse date", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Validates all input fields before searching a <code>PhoneCall</code>
     * in a customers <code>PhoneBill</code>
     */
    private void validateInputFields(View view) throws ParseException, IOException {
        RadioButton selectedBeginAmPm = this.findViewById(beginAmPmField.getCheckedRadioButtonId());
        RadioButton selectedEndAmPm = this.findViewById(endAmPmField.getCheckedRadioButtonId());
        String customer = customerField.getText().toString();
        String begin = beginField.getText().toString() + " " + selectedBeginAmPm.getText().toString();
        String end = endField.getText().toString() + " " + selectedEndAmPm.getText().toString();

        // Validate begin date
        if(!begin.matches("(([1-9]|0[1-9]|[1-6]\\d)/([1-9]|0[1-9]|[1-6]\\d)/(\\d{4}) ((\\d|0\\d|1[0-2]):(\\d|0\\d|[1-6]\\d)) (am|pm))")) {
            AlertDialog dlg = new AlertDialog.Builder(SearchPhoneBill.this)
                    .setTitle("Invalid Beginning Date")
                    .setMessage("Beginning date can only be in the format: mm/dd/yyyy.\nmonth and day can be from 1-69 or 01-69 BUT cannot be \"0\".")
                    .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss())
                    .create();
            dlg.show();
        }
        // Validate ending date
        else if(!end.matches("(([1-9]|0[1-9]|[1-6]\\d)/([1-9]|0[1-9]|[1-6]\\d)/(\\d{4}) ((\\d|0\\d|1[0-2]):(\\d|0\\d|[1-6]\\d)) (am|pm))")) {
            AlertDialog dlg = new AlertDialog.Builder(SearchPhoneBill.this)
                    .setTitle("Invalid Ending Date")
                    .setMessage("Ending date can only be in the format: mm/dd/yyyy.\nmonth and day can be from 1-69 or 01-69 BUT cannot be \"0\".")
                    .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss())
                    .create();
            dlg.show();
        }
        else {
            // Validate that end date isnt before begin date
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);

            Date dateBegin = sdf.parse(begin);
            Date dateEnd = sdf.parse(end);

            // Check if end date occurs before beginning date.
            assert dateBegin != null;
            if(dateBegin.compareTo(dateEnd) > 0) {
                Toast.makeText(this, "Call end time cannot be before begin time.", Toast.LENGTH_LONG).show();
            }
            else {
                String file = customer + ".txt";
                File dataDirectory = this.getDataDir();
                File customerFile = new File(dataDirectory, file);

                if(!customerFile.exists()) {
                    Toast.makeText(this, "PhoneBill for " + customer + " does not exist", Toast.LENGTH_LONG).show();
                }
                else if(customerFile.length() == 0) {
                    Toast.makeText(this, "PhoneBill for " + customer + " is empty", Toast.LENGTH_LONG).show();
                }
                else {
                    // Search through customers phone bill
                    Intent intent = new Intent(this, DisplaySearchedPhoneBill.class);
                    intent.putExtra("customer", customer);
                    intent.putExtra("begin", begin);
                    intent.putExtra("end", end);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }

    /**
     * Clears all inputs in activity_search_phone_bill.xml
     *
     * @param view
     *        Reference to searchPhoneBillResetButton button
     */
    public void clearInputs(View view) {
        customerField = this.findViewById(R.id.searchPhoneBillCustomer);
        beginField = this.findViewById(R.id.searchPhoneBillBeginDate);
        beginAmPmField = this.findViewById(R.id.searchPhoneBillBeginAmPm);
        endField = this.findViewById(R.id.searchPhoneBillEndDate);
        endAmPmField = this.findViewById(R.id.searchPhoneBillEndAmPm);

        if(customerField != null) {
            customerField.setText("");
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
}