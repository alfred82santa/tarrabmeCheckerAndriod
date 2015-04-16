package com.tarrabme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Class that implements the activity of the Configuration screen.
 * */
public class Config extends Activity
{
    /** Reference to host Edittext object */
	private EditText txtHost;
    /** Reference the attempt Edittext object */
	private EditText txtAttempt;
    /** Reference the login Edittext object */
	private EditText txtLogin;
    /** Reference to Save button object */
	private Button btnSave;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);

        /** Sets the layout for the screen */
        setContentView(R.layout.config);

        /** Sets the local object with the screen EditText */
        txtHost = (EditText) findViewById(R.id.etHost);
        /** Sets the local object with the screen EditText */
        txtAttempt = (EditText) findViewById(R.id.etAttempt);
        /** Sets the local object with the screen EditText */
        txtLogin = (EditText) findViewById(R.id.etLogin);
        /** Sets the local object with the screen Button */
        btnSave = (Button) findViewById(R.id.btnSave);

        /** Gets configuration data entered in the screen by the user */
        SharedPreferences prefs = getSharedPreferences("config", Context.MODE_PRIVATE);
        
        if(prefs != null)
        {
        	txtHost.setText(prefs.getString("host", ""));
        	if(prefs.getString("attempt", "").equals(""))
        	{
        		txtAttempt.setText("/codes/%code%/attempt");
        	}
        	else
        	{
        		txtAttempt.setText(prefs.getString("attempt", ""));
        	}
        	if(prefs.getString("login", "").equals(""))
        	{
        		txtLogin.setText("/users/login");  
        	}
        	else
        	{
        		txtLogin.setText(prefs.getString("login", ""));
        	}
        }        
        
        /** Sets button event and creates the associated activity */
        btnSave.setOnClickListener(new View.OnClickListener()
        {
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				String strHost = txtHost.getText().toString();
				String strAttempt = txtAttempt.getText().toString();
				String strLogin = txtLogin.getText().toString();
				
				//Save data
				SharedPreferences prefs = getSharedPreferences("config", Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = prefs.edit();
				editor.putString("host", strHost);
				editor.putString("attempt", strAttempt);
				editor.putString("login", strLogin);
				editor.commit();
				
				Intent intent = new Intent();
		        intent.setAction(Intent.ACTION_MAIN);
		        intent.addCategory(Intent.CATEGORY_LAUNCHER);
		        intent.setClassName("com.tarrabme", "com.tarrabme.Login");
	        	startActivity(intent);
			}
		});
	}

    /** The final call before activity is destroyed.
     * Releases the memory used by the activity
     * */
    public void onDestroy()
    {
		System.gc();
		super.onDestroy();
	}
}