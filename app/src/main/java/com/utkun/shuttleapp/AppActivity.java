package com.utkun.shuttleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AppActivity extends AppCompatActivity {

    private Button topup;
    private Button balance;
    private Button logout;
    private TextView uidtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        topup = (Button) findViewById(R.id.topup);
        balance = (Button) findViewById(R.id.balance);
        logout = (Button) findViewById(R.id.logout);
        uidtext = (TextView) findViewById(R.id.uidtext);

        topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),TopUpActivity.class);
                startActivity(i);
            }
        });
    }


/*
    public void btnPush(View view) {
        switch(view.getId())
        {
            case R.id.topup:
                Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent1);
                break;

            case R.id.balance:
                Intent intent2 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent2);
                break;
        }
    }
    */
}

