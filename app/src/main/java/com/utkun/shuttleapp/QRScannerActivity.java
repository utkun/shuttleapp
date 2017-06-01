package com.utkun.shuttleapp;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScannerActivity extends Activity implements ZXingScannerView.ResultHandler
{
	private int costOfTrip = 10;
	private ZXingScannerView zXingScannerView;
	private FirebaseAuth mAuth;
	FirebaseDatabase database = FirebaseDatabase.getInstance();
	DatabaseReference databaseRef;
	String driverUID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qrscanner);
		
		mAuth = FirebaseAuth.getInstance();
		driverUID = mAuth.getCurrentUser().getUid();
		databaseRef = database.getReference().child("users");
		 
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		zXingScannerView = new ZXingScannerView(getApplicationContext());
		setContentView(zXingScannerView);
		zXingScannerView.setResultHandler(this);
		zXingScannerView.startCamera();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		zXingScannerView.stopCamera();
	}
	
	@Override
	public void handleResult(final Result result)
	{
//		Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();
		databaseRef.addListenerForSingleValueEvent(new ValueEventListener()
		{
			@Override
			public void onDataChange(DataSnapshot dataSnapshot)
			{
				if (dataSnapshot.child(result.getText()).child("credit").getValue(Integer.class) != null &&
						dataSnapshot.child(driverUID).child("credit").getValue(Integer.class) != null) {
					int studentCredit = dataSnapshot.child(result.getText()).child("credit")
													.getValue(Integer.class);
					int driverBalance = dataSnapshot.child(driverUID).child("credit")
													.getValue(Integer.class);
					if (studentCredit >= costOfTrip) {
						databaseRef.child(result.getText()).child("credit").setValue(
								new Integer(studentCredit - costOfTrip));
						databaseRef.child(driverUID).child("credit").setValue(
								new Integer(driverBalance + costOfTrip));
						Toast.makeText(getApplicationContext(), "Approved", Toast.LENGTH_SHORT)
							 .show();
					}
					else {
						Toast.makeText(getApplicationContext(), "Declined", Toast.LENGTH_SHORT)
							 .show();
					}
				}
		//		databaseRef.removeEventListener(this);
			}
			
			@Override
			public void onCancelled(DatabaseError databaseError)
			{
				
			}
		});
		
		zXingScannerView.resumeCameraPreview(this);
	}
}
