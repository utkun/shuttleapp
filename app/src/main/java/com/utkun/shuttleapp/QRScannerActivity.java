package com.utkun.shuttleapp;

import android.app.ActionBar;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScannerActivity extends Activity implements ZXingScannerView.ResultHandler
{
	private ZXingScannerView zXingScannerView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qrscanner);
		
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
	public void handleResult(Result result)
	{
		Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT).show();
		zXingScannerView.resumeCameraPreview(this);
	}
}
