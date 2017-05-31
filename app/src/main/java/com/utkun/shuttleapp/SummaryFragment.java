package com.utkun.shuttleapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryFragment extends Fragment
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
	
	public SummaryFragment()
	{
		// Required empty public constructor
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_summary, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		
		mAuth = FirebaseAuth.getInstance();
		
		topup = (Button) getActivity().findViewById(R.id.topup);
		logout = (Button) getActivity().findViewById(R.id.logout);
		welcometext = (TextView) getActivity().findViewById(R.id.welcometext);
		credittext = (TextView) getActivity().findViewById(R.id.credittext);
		
		topup.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent i = new Intent(getActivity(), TopUpActivity.class);
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
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext()).build();
		imgLoader = ImageLoader.getInstance();
		imgLoader.init(config);
		
		qrImg = (ImageView) getActivity().findViewById(R.id.qrImg);
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
				Intent i = new Intent(getActivity(), LoginActivity.class);
				startActivity(i);
			}
		});
	}
}
