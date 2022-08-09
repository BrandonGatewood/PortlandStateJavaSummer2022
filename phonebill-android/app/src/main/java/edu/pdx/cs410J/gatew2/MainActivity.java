package edu.pdx.cs410J.gatew2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Starts a new activity to display the readme.txt.
     *
     * @param view
     *        Reference to mainReadMeButton button
     */
    public void displayREADME(View view) {
        Intent intent = new Intent(this, ReadMeActivity.class);
        startActivity(intent);
    }

    /**
     *  Starts a new activity to add a new <code>PhoneCall</code>
     *  to a new or existing <code>PhoneBill</code>
     *
     * @param view
     *        Reference to mainAddToPhoneBillButton button
     */
    public void AddToPhoneBill(View view) {
        Intent intent = new Intent(this, AddCallToBill.class);
        startActivity(intent);
    }

    /**
     * Starts a new activity to pretty print
     * a customers <code>PhoneBill</code>
     *
     * @param view
     *        Reference to mainPrettyPrintButton button
     */
    public void prettyPrintAPhoneBill(View view) {
        Intent intent = new Intent(this, PrettyPrint.class);
        startActivity(intent);
    }

    /**
     * Starts a new activity to get user input
     * to search a customers <code>PhoneBill</code>
     *
     * @param view
     *        Reference to mainSearchAPhoneBillButton button
     */
    public void searchPhoneBill(View view) {
        Intent intent = new Intent(this, SearchPhoneBill.class);
        startActivity(intent);
    }
}