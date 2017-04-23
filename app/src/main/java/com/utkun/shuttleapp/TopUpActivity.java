package com.utkun.shuttleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TopUpActivity extends AppCompatActivity {

    private EditText ccno;
    private EditText ccmonth;
    private EditText ccyear;
    private EditText ccv;
    private EditText amounttoadd;
    private Button paynow;
    private String dbccno;
    private Integer dbbalance;

    private TextView textview;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference rootref = database.getReference();
    DatabaseReference ccroot = rootref.child("validcc");
    DatabaseReference ccinst = ccroot.child("1111222233334444");
    DatabaseReference ccref = ccinst.child("ccno");
    DatabaseReference ccbalance = ccinst.child("limit");

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
        textview = (TextView) findViewById(R.id.textView);
        // intent = paynow go to topupsuccessactivity and check against cc database and show success if cc info ok and remaining cc credit ok
       // myRef.setValue("Hello, World!");

        ccref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dbccno = dataSnapshot.getValue(String.class);
                textview.setText(dbccno);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
      /*  ccbalance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dbbalance = dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/
        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ccno.getText().toString().equals(dbccno)) {
                    textview.setText("true");
                    // cc balance dan dusur
                    // user balance a ekle
                    // field lari temizleyelim toast ile success diyelim ayri success activity yapmayalim
                }
            }
        });

    }
}
