package edu.pdx.cs410J.gatew2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class PrettyPrint extends AppCompatActivity {
    private EditText customerField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretty_print);
    }

    /**
     * Resets customer input in activity_pretty_print.xml
     *
     * @param view
     *        Reference to prettyPrintCustomerResetButton button
     */
    public void clearInputs(View view) {
        customerField = this.findViewById(R.id.prettyPrintCustomerName);
        if(customerField != null) {
            customerField.setText("");
        }
    }

    /**
     * Starts a new activity to pretty print customers <code>PhoneBill</code>
     *
     * @param view
     *        Reference to prettyPrintCustomerSubmitButton button
     */
    public void Print(View view) {
        customerField = this.findViewById(R.id.prettyPrintCustomerName);
        String customer = customerField.getText().toString();
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
            Intent intent = new Intent(this, PrettyPrinter.class);
            intent.putExtra("customer", customer);
            startActivity(intent);
            finish();
        }
    }
}