package edu.pdx.cs410J.gatew2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PrettyPrinter extends AppCompatActivity {
    private final List<String> list = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretty_printer);

        // Create title based on customers name
        String customer;
        TextView title = this.findViewById(R.id.PrettyPrinterTitle);
        Intent i = getIntent();
        Bundle b = i.getExtras();

        customer = (String)b.get("customer");
        title.setText(customer + "'s Phone Calls");

        // Parse through customers txt file and create a PhoneBill
        String file = customer + ".txt";
        File dataDirectory = this.getDataDir();
        File customerFile = new File(dataDirectory, file);
        PhoneBill newBill = null;
        if(customerFile.length() != 0) {
            try {
                newBill = parseFile(customerFile);
            } catch (FileNotFoundException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        Collection<PhoneCall> calls = newBill.getPhoneCalls();

        // Find the ListView widget
        ListView lv = this.findViewById(R.id.PrettyPrinterScroll);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.US);

        if(calls.isEmpty()) {
            list.add("PhoneBill is empty");
        }
        else {
            for(PhoneCall call : calls) {

                String callee = call.getCallee();
                String begin = call.getBeginTimeString();
                String end = call.getEndTimeString();

                // Calculate duration of the phone call
                Date firstDate = null;
                try {
                    firstDate = sdf.parse(begin);
                } catch (ParseException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                Date secondDate = null;
                try {
                    secondDate = sdf.parse(end);
                } catch (ParseException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                long diff = secondDate.getTime() - firstDate.getTime();
                long min = TimeUnit.MILLISECONDS.toMinutes(diff);

                String aCall = "Call with " + callee + "\nDuration: " + min + " minutes.";

                list.add(aCall);
            }
        }
        // Create an ArrayAdapter to print onto activity
        ArrayAdapter<String> ad = new ArrayAdapter<>(PrettyPrinter.this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(ad);
    }

    /**
     * Parses a customer .txt file and return a <code>PhoneBill</code> object
     * for the customer.
     *
     * @param customerFile
     *        Destination to customers .txt file
     */
    private PhoneBill parseFile(File customerFile) throws FileNotFoundException {
        TextParser parser = new TextParser(new FileReader(customerFile));
        PhoneBill newBill = parser.parse();

        // Check if .txt file is malformed.
        if(newBill.getCustomer().equals("The .txt file is formatted incorrectly. The correct format: customer;caller;callee;begin;end;")) {
            finish();
            Toast.makeText(this, newBill.getCustomer(), Toast.LENGTH_LONG).show();
        }
        // Check if content in .txt file is invalid
        else if(newBill.getCustomer().contains("File cannot be parsed.. ")) {
            finish();
            Toast.makeText(this, newBill.getCustomer(), Toast.LENGTH_LONG).show();
        }
        else {
            return newBill;
        }
        return null;
    }

    public void done(View view) {
        finish();
    }
}