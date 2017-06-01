package com.utkun.shuttleapp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity
{
    private static final String TAG = "MyActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText loginemail;
    private EditText loginpassword;
    private Button loginbutton;
	private Button signupbutton;

    // we need to handle firebase email login and later also signup perhaps in different activity
    // the signup process adds to auth page in firebase but not to actual database
    // we need to programatically handle pulling auto generated auth uid of new account
    // and create new entry in database using that uid
    // so that we hold corresponding remaining credit etc
    
    // test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginemail = (EditText) findViewById(R.id.loginemail);
        loginpassword = (EditText) findViewById(R.id.loginpassword);
        loginbutton = (Button) findViewById(R.id.loginbutton);
		signupbutton = (Button) findViewById(R.id.signupbutton);

        ///

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
				if (loginemail.getText().length() > 0 && loginpassword.getText().length() > 5) {
					login(loginemail.getText().toString(), loginpassword.getText().toString());
				}
				else
				{
					Toast.makeText(LoginActivity.this, "Fields are invalid. Minimum password lenght is 6",
								   Toast.LENGTH_SHORT).show();
				}
            }
        });
		
		signupbutton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				if (loginemail.getText().length() > 0 && loginpassword.getText().length() > 5) {
					signup(loginemail.getText().toString(), loginpassword.getText().toString());
				}
				else
				{
					Toast.makeText(LoginActivity.this, "Fields are invalid. Minimum password lenght is 6",
								   Toast.LENGTH_SHORT).show();
				}
			}
		});

        ///

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    private void signup(String email, String password)
	{
		mAuth.createUserWithEmailAndPassword(email, password)
			 .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
				 @Override
				 public void onComplete(@NonNull Task<AuthResult> task) {
					 Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
				
					 // If sign in fails, display a message to the user. If sign in succeeds
					 // the auth state listener will be notified and logic to handle the
					 // signed in user can be handled in the listener.
					 if (!task.isSuccessful()) {
						 Toast.makeText(LoginActivity.this, "Singup failed",
										Toast.LENGTH_SHORT).show();
					 }
					 else
					 {
						 String newUID = mAuth.getCurrentUser().getUid();
						 DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");
						 Map<String,Object> newUser = new HashMap<>();
						 newUser.put("status","student");
						 newUser.put("credit",0);
						 ref.child(newUID).setValue(newUser);
					 }
				
					 // ...
				 }
			 });
	}

	private void login(String email, String password)
	{
		mAuth.signInWithEmailAndPassword(email, password)
			 .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
				 @Override
				 public void onComplete(@NonNull Task<AuthResult> task) {
					 Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
				
					 // If sign in fails, display a message to the user. If sign in succeeds
					 // the auth state listener will be notified and logic to handle the
					 // signed in user can be handled in the listener.
					 if (!task.isSuccessful()) {
						 Log.w(TAG, "signInWithEmail:failed", task.getException());
						 Toast.makeText(LoginActivity.this, "Login failed",
										Toast.LENGTH_SHORT).show();
					 }
				
					 // ...
				 }
			 });
	}
	
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
	
	@Override
	public void onBackPressed()
	{
		
	}
}
