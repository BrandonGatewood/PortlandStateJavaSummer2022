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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class DisplaySearchedPhoneBill extends AppCompatActivity {
    private final List<String> list = new ArrayList<>();
    ArrayAdapter<String> ad;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_searched_phone_bill);

        // Create title based on customers name
        String customer;
        String begin;
        String end;
        TextView title = this.findViewById(R.id.printSearchedCallsTitle);
        TextView time = this.findViewById(R.id.printSearchedCallsTime);
        Intent i = getIntent();
        Bundle b = i.getExtras();

        // Create title based on customers name
        customer = (String)b.get("customer");
        begin = (String)b.get("begin");
        end = (String)b.get("end");
        title.setText(customer + "'s Phone Calls");
        time.setText("From: " + begin + "-" + end);

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
        ListView lv = this.findViewById(R.id.printSearchedCallsScroll);
        Date beginDate = new Date(begin);
        Date endDate = new Date(end);

        // Search for calls withing range
        if(calls.isEmpty()) {
            list.add("PhoneBill is empty");
        }
        else {
            for(PhoneCall range : calls) {
                if (range.getBeginTime().compareTo(beginDate) >= 0 && range.getBeginTime().compareTo(endDate) <= 0) {
                    list.add(range.toString());
                }
            }
        }

        // Check if there are any calls withing range
        if(list.isEmpty()) {
            list.add("No phone calls within the specified time.");
        }
        ad = new ArrayAdapter<>(DisplaySearchedPhoneBill.this, android.R.layout.simple_list_item_1, list);
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