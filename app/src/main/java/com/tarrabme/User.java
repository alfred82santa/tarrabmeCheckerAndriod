package com.tarrabme;

import com.tarrabme.zxing.client.android.CaptureActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

/**
 * Class that implements the activity of the User screen.
 * */
public class User extends Activity
{
    /** Reference to main WebView object */
	private WebView wvView;
    /** Reference to Scan button object */
	private Button btnScan;
    /** Reference to Logout button object */
	private Button btnLogout;
    /** Reference to User textview object */
	private TextView tvUser;
    /** Contains the Host name */
	private String strHost = "";
    /** Contains the Attempt information */
	private String strAttempt = "";
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
	{
    	super.onCreate(savedInstanceState);

        /** Sets the layout for the screen */
        setContentView(R.layout.user);
               
        /** Gets configuration data entered in the screen by the user */
        SharedPreferences prefs = getSharedPreferences("config", Context.MODE_PRIVATE);
      
        if(prefs != null)
        {
      	  strHost = prefs.getString("host", "");
      	  strAttempt = prefs.getString("attempt", "");
        }

        /** Gets data entered by user */
        Bundle extras = getIntent().getExtras();
        String value = extras.getString("CODE");

        String strCode = strAttempt.replace("%code%", value);
        /** Builds the main url */
        String strURL = "http://" + strHost + strCode;

        /** Opens a webview with the url entered */
        wvView = (WebView) findViewById(R.id.wvView);
		wvView.getSettings().setJavaScriptEnabled(true);
		wvView.getSettings().setSupportMultipleWindows(false); 
		wvView.loadUrl(strURL);
		wvView.setWebViewClient(new WebViewClient() 
		{
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				view.loadUrl(url);
				return false;
			}
		});

        /** Sets the local object with the screen button */
        btnScan = (Button) findViewById(R.id.btnScan);
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

        /** Sets the local object with the screen button */
        btnLogout = (Button) findViewById(R.id.btnLogout);
        /** Sets button event and creates the associated activity */
        btnLogout.setOnClickListener(new OnClickListener()
        {
        	public void onClick(View v) 
			{
        		CookieManager.getInstance().removeAllCookie();
        		
        		Main.mainInst.setLogged(false);
        		Main.mainInst.btnLogin.setText(R.string.btnLogin);
        		Main.mainInst.btnScan.setEnabled(false);
        		
        		Intent intent = new Intent();
		        intent.setAction(Intent.ACTION_MAIN);
		        intent.addCategory(Intent.CATEGORY_LAUNCHER);
		        intent.setClassName("com.tarrabme", "com.tarrabme.Login");
	        	startActivity(intent);
			}
        });

        /** Sets the local object with the screen textview */
        tvUser = (TextView) findViewById(R.id.tvUser);
        /** Sets the local object with the screen button */
        tvUser.setText(Main.mainInst.strUserName);
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