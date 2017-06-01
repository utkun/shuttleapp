package com.utkun.shuttleapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends FragmentActivity
{
	private boolean start = true;
	// Drawer
	boolean driver = false;
	private int currentPosition = 1;
	private ArrayList<String> titles;
	private ListView drawerList;
	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle drawerToggle;
	
	//Auth
	String uid = "28CXGydTzLScWTWPzkramqdMXCD3";
	private FirebaseAuth mAuth;
	FirebaseDatabase database = FirebaseDatabase.getInstance();
	DatabaseReference rootref = database.getReference();
	DatabaseReference usersref = rootref.child("users");
	DatabaseReference uidref;
	String BASE_QR_URL = "http://chart.apis.google.com/chart?cht=qr&chs=400x400&chld=M&choe=UTF-8&chl=";
	String fullUrl = BASE_QR_URL;
	int credit;
	String name;
	String email;
	String phone;
	String status;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState != null) {
			currentPosition = savedInstanceState.getInt("currentPosition");
		}
		start = true;
		
		// Drawer
		titles = new ArrayList(Arrays.asList(getResources().getStringArray(R.array.dummy)));
		
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		
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
		uid = mAuth.getCurrentUser().getUid();
		email = mAuth.getCurrentUser().getEmail();
		uidref = usersref.child(uid);
		
		uidref.addValueEventListener(new ValueEventListener()
		{
			@Override
			public void onDataChange(DataSnapshot dataSnapshot)
			{
				name = dataSnapshot.child("name").getValue(String.class);
				credit = dataSnapshot.child("credit").getValue(Integer.class);
				phone = dataSnapshot.child("phone").getValue(String.class);
				if (start)
				{
					status = dataSnapshot.child("status").getValue(String.class);
					driver = status.equals("driver");
					if (driver)
					{
						titles.remove(1);
					}
					else {
						titles.remove(0);
					}
					drawerList = (ListView) findViewById(R.id.drawer);
					drawerList.setAdapter(new ArrayAdapter<>(getApplicationContext(),
															 android.R.layout.simple_list_item_activated_1,
															 titles));
					drawerList.setOnItemClickListener(new DrawerItemClickListener());
					Log.d("just before select",Integer.toString(currentPosition));
					selectItem(currentPosition);
					start = false;
				}
				else
				{
					((Subview) getSupportFragmentManager().findFragmentByTag("MY_FRAGMENT")).updateUI();
				}
				
			}
			
			@Override
			public void onCancelled(DatabaseError databaseError)
			{
				
			}
		});
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		drawerToggle.syncState();
		
		//Toast.makeText(this,uid,Toast.LENGTH_SHORT).show();
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
		Log.d("Position",Integer.toString(position));
		currentPosition = position;
		Fragment fragment;
		switch (position)
		{
			case 0:
				if (driver)
				{
					startActivity(new Intent(getApplicationContext(), QRScannerActivity.class));
				}
				else
				{
					startActivity(new Intent(getApplicationContext(), TopUpActivity.class));
				}
				break;
			case 1:
				fragment = new SummaryFragment();
				getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment,"MY_FRAGMENT").commit();
				break;
			case 2:
				fragment = new ProfileFragment();
				getSupportFragmentManager().beginTransaction().replace(R.id.content_frame,fragment,"MY_FRAGMENT").commit();
				break;
			case 3:
				mAuth.signOut();
				Intent i = new Intent(getApplicationContext(),LoginActivity.class);
				startActivity(i);
				return;
			default:
				
		}
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
	
	@Override
	protected void onDestroy()
	{
		
		super.onDestroy();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putInt("currentPosition",currentPosition);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		currentPosition = savedInstanceState.getInt("currentPosition");
	}
	
	@Override
	public void onBackPressed()
	{
		
	}
	
	public void updateUserInfo(final String newName, final String oldPassword,
							   final String newPassword1, final String newPassword2,
							   final String newEmail, final String newPhone)
	{
		if (!name.equals(newName))
		{
			uidref.child("name").setValue(newName);
		}
		if (!phone.equals(newPhone))
		{
			uidref.child("phone").setValue(newPhone);
		}
		AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword);
		mAuth.getCurrentUser().reauthenticate(credential).addOnCompleteListener(
				new OnCompleteListener<Void>()
				{
					@Override
					public void onComplete(@NonNull Task<Void> task)
					{
						if (task.isSuccessful())
						{
							if (!email.equals(newEmail))
							{
								mAuth.getCurrentUser().updateEmail(email).addOnCompleteListener(
										new OnCompleteListener<Void>()
										{
											@Override
											public void onComplete(@NonNull Task<Void> task)
											{
												if (task.isSuccessful())
												{
													Toast.makeText(getApplicationContext(),"Email successfully changed",Toast.LENGTH_SHORT);
												}
												else
												{
													Toast.makeText(getApplicationContext(),"An error occurred during email update",Toast.LENGTH_SHORT);
												}
											}
										});
							}
							if (!oldPassword.equals(newPassword1) && newPassword1.equals(newPassword2) && newPassword1.length() > 5)
							{
								mAuth.getCurrentUser().updatePassword(newPassword1).addOnCompleteListener(
										new OnCompleteListener<Void>()
										{
											@Override
											public void onComplete(@NonNull Task<Void> task)
											{
												if (task.isSuccessful())
												{
													Toast.makeText(getApplicationContext(),"Password successfully changed",Toast.LENGTH_SHORT);
												}
												else
												{
													Toast.makeText(getApplicationContext(),"An error occurred during password update",Toast.LENGTH_SHORT);
												}
											}
										});
							}
						}
						else
						{
							Toast.makeText(getApplicationContext(),"Your credentials are wrong",Toast.LENGTH_SHORT);
						}
					}
				});
	}
}
