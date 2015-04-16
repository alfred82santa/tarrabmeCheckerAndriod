package com.tarrabme;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import com.tarrabme.zxing.client.android.CaptureActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.widget.Button;

/**
 * Class that implements the activity of the Main screen.
 * */
public class Main extends Activity 
{
    /** Reference to Main instance */
    public static Main mainInst;
    /** Reference to Login button */
    public static Button btnLogin;
    /** Reference to Scan button */
    public static Button btnScan;
    /** Contains de User name */
    public static String strUserName;
    /** Reference to Configuration button */
    private Button btnConfig;
    /** Indicates if the User has logged successfully */
    private static boolean bLogged;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);

        /** Sets the layout for the screen */
        setContentView(R.layout.main);
        
        mainInst = this;

        /** Indicates if connection data are configured */
        boolean bConfigured = false;

        bLogged = false;

        /**  Sets the local object with the screen button */
        btnConfig = (Button) findViewById(R.id.btnConfigMain);
        /**  Sets button event and creates the associated activity */
        btnConfig.setOnClickListener(new OnClickListener()
        {
        	public void onClick(View v)
        	{
        		Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setClassName("com.tarrabme", "com.tarrabme.Config");
            	startActivity(intent);
        	}
        });

        /** Sets the local object with the screen button */
        btnLogin = (Button) findViewById(R.id.btnLoginMain);
        btnLogin.setEnabled(false);
        /** Sets button event and creates the associated activity */
        btnLogin.setOnClickListener(new OnClickListener()
        {
        	public void onClick(View v)
        	{
        		if(bLogged)
        		{
        			CookieManager.getInstance().removeAllCookie();
        			Main.mainInst.btnLogin.setText(R.string.btnLogin);
                	btnScan.setEnabled(false);
        		}
        		
        		Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setClassName("com.tarrabme", "com.tarrabme.Login");
            	startActivity(intent);
        	}
        });

        /** Sets the local object with the screen button */
        btnScan = (Button) findViewById(R.id.btnScanMain);
        btnScan.setEnabled(false);
        /** Sets button event and creates the associated activity */
        btnScan.setOnClickListener(new OnClickListener()
        {
        	public void onClick(View v)
        	{
        		Intent intent = new Intent();
		        intent.setAction(Intent.ACTION_MAIN);
		        intent.addCategory(Intent.CATEGORY_LAUNCHER);
		        intent.setClassName("com.tarrabme", CaptureActivity.class.getName());
		        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		        startActivity(intent);
        	}
        });
        
        /** Creates an Intent */
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        /** Gets config data entered in the screen by the user */
        SharedPreferences prefs = getSharedPreferences("config", Context.MODE_PRIVATE);

        if(prefs != null)
        {
        	String hostValue = prefs.getString("host", "");
        	String loginValue = prefs.getString("login", "");    	
        	
        	if(loginValue.equals("") || hostValue.equals(""))
                bConfigured = true;
        }
        else
            bConfigured = true;

        if(bConfigured)
        {
            /** If needs to be configured, configuration screen is shown */
        	intent.setClassName("com.tarrabme", "com.tarrabme.Config");
        	startActivity(intent);
        }
        else if (bLogged)
        {
        	btnLogin.setEnabled(true);
        	btnScan.setEnabled(true);
            /** If configuration and login are OK, starts de capture activity */
		    intent.setClassName("com.tarrabme", CaptureActivity.class.getName());
		    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
	        startActivity(intent);
        }
        else
        {
            /** If needs to be logged, login screen is shown */
        	btnLogin.setEnabled(true);
        	intent.setClassName("com.tarrabme", "com.tarrabme.Login");
        	startActivity(intent);
        }
    }

    /** Sets close button event */
    @Override
    public void onBackPressed() 
    {
        new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(R.string.strEixir)
            .setMessage(R.string.strPregunta)
            .setPositiveButton(R.string.strSi, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which) 
            {
                finish();    
            }
        })
        .setNegativeButton(R.string.strNo, null)
        .show();
    }

    /** Sets if the user has logged successfully */
    public void setLogged(boolean value)
    {   
    	bLogged = value;
    }

    /** The final call before activity is destroyed.
     * Releases the memory used by the activity
     * */
    public void onDestroy()
    {
    	CookieManager.getInstance().removeAllCookie();
		System.gc();
		super.onDestroy();
	}
}