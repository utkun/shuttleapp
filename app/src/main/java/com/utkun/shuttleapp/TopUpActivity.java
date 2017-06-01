package com.utkun.shuttleapp;

import android.app.ActionBar;
import android.app.Activity;
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

public class TopUpActivity extends Activity
{

    private EditText ccno;
    private EditText ccmonth;
    private EditText ccyear;
    private EditText ccv;
    private EditText amounttoadd;
    private Button paynow;
    private String testccno = "1111222233334444";
    private Integer dbbalance;
    private Integer cclimit;

    private TextView textview;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference rootref = database.getReference();
    DatabaseReference cclistref = rootref.child("validcc");
  //  DatabaseReference sampleccref = cclistref.child("1111222233334444");
  //  DatabaseReference sampleccno = sampleccref.child("ccno");
  //  DatabaseReference samplecclimit = sampleccref.child("limit");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);
        
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ccno = (EditText) findViewById(R.id.ccno);
        ccmonth = (EditText) findViewById(R.id.ccmonth);
        ccyear = (EditText) findViewById(R.id.ccyear);
        ccv = (EditText) findViewById(R.id.ccv);
        amounttoadd = (EditText) findViewById(R.id.amounttoadd);
        paynow = (Button) findViewById(R.id.paynow);
        textview = (TextView) findViewById(R.id.textView);

       // myRef.setValue("Hello, World!");
    
        if (savedInstanceState != null)
        {
            ccno.setText(savedInstanceState.getString("ccno"));
            ccno.setText(savedInstanceState.getString("ccmonth"));
            ccno.setText(savedInstanceState.getString("ccyear"));
            ccno.setText(savedInstanceState.getString("ccv"));
            ccno.setText(savedInstanceState.getString("amounttoadd"));
        }
        
        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cclistref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(ccno.getText().toString())) {
                            textview.setText("card found - success");
                            /*
                                if(ccmonth.equals(dataSnapshot.child(ccno.getText().toString()).child("validmonth").getValue(String.class))
                                        && ccyear.equals(dataSnapshot.child(ccno.getText().toString()).child("validyear").getValue(String.class))
                                        && ccv.equals(dataSnapshot.child(ccno.getText().toString()).child("ccv").getValue(String.class))
                                  )
                                {
                                    //card exists on file

                                    cclimit = dataSnapshot.child(ccno.getText().toString()).child("limit").getValue(Integer.class);
                                    if(Integer.parseInt(amounttoadd.getText().toString()) <= cclimit) {
                                        textview.setText("success");
                                        //listen to user.remaingCredit from firebase and update it with new value
                                    }
                                }
                             */
                        } else textview.setText("failure");
                        // dbccno = dataSnapshot.getValue(String.class);
                        // textview.setText(dbccno);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
             /*   if(ccno.getText().toString().equals(dbccno)) {
                    textview.setText("true");
                    // cc balance dan dusur
                    // user balance a ekle
                    // field lari temizleyelim toast ile success diyelim ayri success activity yapmayalim
                }*/
            }
        });

    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("ccno",ccno.getText().toString());
        outState.putString("ccmonth",ccmonth.getText().toString());
        outState.putString("ccyear",ccyear.getText().toString());
        outState.putString("ccv",ccv.getText().toString());
        outState.putString("amounttoadd",amounttoadd.getText().toString());
    }
}
