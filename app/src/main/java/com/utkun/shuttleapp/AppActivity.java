package com.utkun.shuttleapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AppActivity extends AppCompatActivity {

    private Button topup;
    private Button logout;
    private TextView welcometext;
    private String uid = "28CXGydTzLScWTWPzkramqdMXCD3"; // set to uid coming from login page
    private String credit;
    private String name;
    private TextView credittext;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference rootref = database.getReference();
    DatabaseReference usersref = rootref.child("users");
    DatabaseReference uidref = usersref.child(uid);
    DatabaseReference creditref = uidref.child("credit");
    DatabaseReference nameref = uidref.child("name");

    ImageLoader imgLoader;
    ImageView qrImg;
    String copiedStr;
    //TextView qrTxt;
    //ClipboardManager clipboard;

    String BASE_QR_URL = "http://chart.apis.google.com/chart?cht=qr&chs=400x400&chld=M&choe=UTF-8&chl=";
    String fullUrl = BASE_QR_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        topup = (Button) findViewById(R.id.topup);
        logout = (Button) findViewById(R.id.logout);
        welcometext = (TextView) findViewById(R.id.welcometext);
        credittext = (TextView) findViewById(R.id.credittext);

        topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),TopUpActivity.class);
                startActivity(i);
            }
        });

        nameref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.getValue(String.class);
                welcometext.setText("Welcome, " + name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        creditref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                credit = dataSnapshot.getValue(String.class);
                credittext.setText("Your remaining credit is: " + credit);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //qrgenerate

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        imgLoader = ImageLoader.getInstance();
        imgLoader.init(config);

        qrImg = (ImageView)findViewById(R.id.qrImg);
        //qrTxt = (TextView)findViewById(R.id.qrTxt);
        try {
            copiedStr = uid;
            fullUrl += URLEncoder.encode(copiedStr, "UTF-8");
            imgLoader.displayImage(fullUrl, qrImg);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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

