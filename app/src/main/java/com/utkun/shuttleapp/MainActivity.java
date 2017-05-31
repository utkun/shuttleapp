package com.utkun.shuttleapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends Activity
{

    private Button topup;
    private Button logout;
    private TextView welcometext;
    private String uid = "28CXGydTzLScWTWPzkramqdMXCD3"; // set to uid coming from login page
    private String credit;
    private String name;
    private TextView credittext;
    private FirebaseAuth mAuth;

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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        mAuth = FirebaseAuth.getInstance();
    
        topup = (Button) findViewById(R.id.topup);
        logout = (Button) findViewById(R.id.logout);
        welcometext = (TextView) findViewById(R.id.welcometext);
        credittext = (TextView) findViewById(R.id.credittext);
    
        topup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), TopUpActivity.class);
                startActivity(i);
            }
        });
    
        nameref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                name = dataSnapshot.getValue(String.class);
                welcometext.setText("Welcome, " + name);
            }
        
            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            
            }
        });
    
        creditref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                credit = dataSnapshot.getValue(String.class);
                credittext.setText("Your remaining credit is: " + credit);
            }
        
            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            
            }
        });
    
        //qrgenerate
    
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        imgLoader = ImageLoader.getInstance();
        imgLoader.init(config);
    
        qrImg = (ImageView) findViewById(R.id.qrImg);
        //qrTxt = (TextView)findViewById(R.id.qrTxt);
        try {
            copiedStr = uid;
            fullUrl += URLEncoder.encode(copiedStr, "UTF-8");
            imgLoader.displayImage(fullUrl, qrImg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    
        logout.setOnClickListener(new View.OnClickListener()
        {
        
            @Override
            public void onClick(View v)
            {
                mAuth.signOut();
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
    
    }
    
    public void scanQR(View view)
    {
        Intent i = new Intent(getApplicationContext(),QRScannerActivity.class);
    //    Intent i = new Intent(getApplicationContext(),ProfileActivity.class);
        startActivity(i);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_settings:
				Log.d("Menu","working");
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}
}


/*

TODO: drawer layout
TODO: database user info
TODO: http requestle qr kodu kaydet
TODO: schedule

 */