package com.utkun.shuttleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TopUpActivity extends AppCompatActivity {

    private EditText ccno;
    private EditText ccmonth;
    private EditText ccyear;
    private EditText ccv;
    private EditText amounttoadd;
    private Button paynow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        ccno = (EditText) findViewById(R.id.ccno);
        ccmonth = (EditText) findViewById(R.id.ccmonth);
        ccyear = (EditText) findViewById(R.id.ccyear);
        ccv = (EditText) findViewById(R.id.ccv);
        amounttoadd = (EditText) findViewById(R.id.amounttoadd);
        paynow = (Button) findViewById(R.id.paynow);
        // intent = paynow go to topupsuccessactivity and check against cc database and show success if cc info ok and remaining cc credit ok

    }
}
