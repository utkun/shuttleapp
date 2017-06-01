package com.utkun.shuttleapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryFragment extends Fragment implements Subview
{
	boolean onStart = true;
	private TextView welcometext;
	private TextView credittext;
	
	private MainActivity parent;
	
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
		
		parent = (MainActivity) getActivity();

		welcometext = (TextView) getActivity().findViewById(R.id.welcometext);
		credittext = (TextView) getActivity().findViewById(R.id.credittext);
		
		updateUI();
	}
	
	@Override
	public void updateUI()
	{
		if (parent.name != null) {
			welcometext.setText("Welcome, " + parent.name);
		}
		else
		{
			welcometext.setText("Welcome");
		}
		if (parent.driver)
		{
			credittext.setText("Your earned credit is: " + Integer.toString(parent.credit));
		}
		else
		{
			credittext.setText("Your remaining credit is: " + Integer.toString(parent.credit));
			//qrgenerate
			if (onStart) {
				ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext())
						.build();
				imgLoader = ImageLoader.getInstance();
				imgLoader.init(config);
				
				qrImg = (ImageView) getActivity().findViewById(R.id.qrImg);
				//qrTxt = (TextView)findViewById(R.id.qrTxt);
				try {
					copiedStr = parent.uid;
					fullUrl += URLEncoder.encode(copiedStr, "UTF-8");
					imgLoader.displayImage(fullUrl, qrImg);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				onStart = false;
			}
		}
	}
}
