package com.utkun.shuttleapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements Subview
{
	MainActivity parent;
	
	EditText nameField;
	EditText oldPasswordField;
	EditText newPassword1Field;
	EditText newPassword2Field;
	EditText emailField;
	EditText phoneField;
	Button updateButton;
	
	public ProfileFragment()
	{
		// Required empty public constructor
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_profile, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		parent = (MainActivity) getActivity();
		nameField = (EditText) parent.findViewById(R.id.editTextName);
		oldPasswordField = (EditText) parent.findViewById(R.id.editTextOldPassword);
		newPassword1Field = (EditText) parent.findViewById(R.id.editTextNewPassword1);
		newPassword2Field = (EditText) parent.findViewById(R.id.editTextNewPassword2);
		emailField = (EditText) parent.findViewById(R.id.editTextEmail);
		phoneField = (EditText) parent.findViewById(R.id.editTextPhone);
		updateButton = (Button) parent.findViewById(R.id.updateButton);
		updateButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				 parent.updateUserInfo(nameField.getText().toString(),oldPasswordField.getText().toString(),
									   newPassword1Field.getText().toString(), newPassword2Field.getText().toString(),
									   emailField.getText().toString(),phoneField.getText().toString());
			}
		});
		updateUI();
	}
	
	@Override
	public void updateUI()
	{
		nameField.setText(parent.name);
		if (parent.email != null)
		{
			emailField.setText(parent.email);
		}
		if (parent.phone != null)
		{
			phoneField.setText(parent.phone);
		}
	}
}
