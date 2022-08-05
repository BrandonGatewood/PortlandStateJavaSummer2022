package edu.pdx.cs410J.gatew2;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
        customerField = (EditText)this.findViewById(R.id.addPhoneBillCustomer);
        callerField = (EditText)this.findViewById(R.id.addPhoneBillCaller);
        calleeField = (EditText)this.findViewById(R.id.addPhoneBillCallee);
        beginField = (EditText)this.findViewById(R.id.addPhoneBillBeginDate);
        beginAmPmField = (RadioGroup) this.findViewById(R.id.addPhoneBillBeginAmPm);
        endField = (EditText)this.findViewById(R.id.addPhoneBillEndDate);
        endAmPmField = (RadioGroup) this.findViewById(R.id.addPhoneBillEndAmPm);

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
        customerField = (EditText)this.findViewById(R.id.addPhoneBillCustomer);
        callerField = (EditText)this.findViewById(R.id.addPhoneBillCaller);
        calleeField = (EditText)this.findViewById(R.id.addPhoneBillCallee);
        beginField = (EditText)this.findViewById(R.id.addPhoneBillBeginDate);
        beginAmPmField = (RadioGroup) this.findViewById(R.id.addPhoneBillBeginAmPm);
        endField = (EditText)this.findViewById(R.id.addPhoneBillEndDate);
        endAmPmField = (RadioGroup) this.findViewById(R.id.addPhoneBillEndAmPm);

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
            validateInputFields(view);
        }
    }

    /**
     * Validates ALL input fields before adding a new <code>PhoneCall</code>
     * into a <code>PhoneBill</code>
     * @param view
     */
    private void validateInputFields(View view) {
        RadioButton selectedBeginAmPm = (RadioButton) this.findViewById(beginAmPmField.getCheckedRadioButtonId());
        RadioButton selectedEndAmPm = (RadioButton) this.findViewById(endAmPmField.getCheckedRadioButtonId());
        String customer = customerField.getText().toString();
        String caller = callerField.getText().toString();
        String callee = calleeField.getText().toString();
        String begin = beginField.getText().toString() + " " + selectedBeginAmPm.getText().toString();
        String end = endField.getText().toString() + " " + selectedEndAmPm.getText().toString();

        // Validate caller number
    }
}