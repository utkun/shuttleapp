package com.utkun.shuttleapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProfileActivity extends Activity
{
	private int currentPosition = 0;
	private String[] titles;
	private ListView drawerList;
	private DrawerLayout drawerLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		titles = getResources().getStringArray(R.array.dummy);
		drawerList = (ListView) findViewById(R.id.drawer);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, titles));
		drawerList.setOnItemClickListener(new DrawerItemClickListener());
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
		Fragment fragment;
		switch (position)
		{
			case 1:
				
				break;
			case 2:
				
				break;
			default:
				
		}
		Log.d("Position", Integer.toString(position));
	}
}
