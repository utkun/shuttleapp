package com.utkun.shuttleapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ProfileActivity extends FragmentActivity
{
	// Draver
	private boolean driver = false;
	private int currentPosition = 0;
	private ArrayList<String> titles;
	private ListView drawerList;
	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle drawerToggle;
	private RelativeLayout content;
	
	//Auth
	private String uid = "28CXGydTzLScWTWPzkramqdMXCD3";
	private FirebaseAuth mAuth;
	private FirebaseAuth.AuthStateListener mAuthListener;
	FirebaseDatabase database = FirebaseDatabase.getInstance();
	DatabaseReference rootref = database.getReference();
	DatabaseReference usersref = rootref.child("users");
	DatabaseReference uidref = usersref.child(uid);
	DatabaseReference creditref = uidref.child("credit");
	DatabaseReference nameref = uidref.child("name");
	String BASE_QR_URL = "http://chart.apis.google.com/chart?cht=qr&chs=400x400&chld=M&choe=UTF-8&chl=";
	String fullUrl = BASE_QR_URL;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		// Drawer
		titles = new ArrayList(Arrays.asList(getResources().getStringArray(R.array.dummy)));
		
		if (driver)
		{
			titles.remove(1);
		}
		else
		{
			titles.remove(0);
		}
		drawerList = (ListView) findViewById(R.id.drawer);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, titles));
		drawerList.setOnItemClickListener(new DrawerItemClickListener());
		
		content = (RelativeLayout) findViewById(R.id.content_frame);
		
		//Create the ActionBarDrawerToggle
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
												 R.string.open_drawer, R.string.close_drawer) {
			//Called when a drawer has settled in a completely closed state
			@Override
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				invalidateOptionsMenu();
			}
			
			//Called when a drawer has settled in a completely open state.
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
			}
		};
		drawerLayout.setDrawerListener(drawerToggle);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		//Auth
		mAuth = FirebaseAuth.getInstance();
		mAuthListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				FirebaseUser user = firebaseAuth.getCurrentUser();
				if (user != null) {
					// User is signed in
				} else {
					// User is signed out
					Intent i = new Intent(getApplicationContext(),LoginActivity.class);
					startActivity(i);
				}
				// ...
			}
		};
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		drawerToggle.syncState();
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener
	{
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			selectItem(position);
		}
	}
	
	void selectItem(int position)
	{
		currentPosition = 0;

		switch (position)
		{
			case 1:
				
				break;
			case 2:
				
				break;
			case 5:
				mAuth.signOut();
				Intent i = new Intent(getApplicationContext(),LoginActivity.class);
				startActivity(i);
				return;
			default:
				
		}
		SummaryFragment fragment = new SummaryFragment();
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment).commit();
		Log.d("Position", Integer.toString(position));
		drawerLayout.closeDrawer(drawerList);
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
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId())
		{
			case R.id.action_settings:
				Log.d("Menu","working");
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}
	
	public void scanQR(View view)
	{
		Intent i = new Intent(getApplicationContext(), QRScannerActivity.class);
		//    Intent i = new Intent(getApplicationContext(),ProfileActivity.class);
		startActivity(i);
	}
	
	@Override
	public void onBackPressed()
	{
		
	}
}
