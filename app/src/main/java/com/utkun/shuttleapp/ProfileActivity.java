package com.utkun.shuttleapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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

public class ProfileActivity extends Activity
{
	private int currentPosition = 0;
	private String[] titles;
	private ListView drawerList;
	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle drawerToggle;
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
}
