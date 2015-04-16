package com.tarrabme;

import java.util.regex.Pattern;

import com.tarrabme.zxing.client.android.CaptureActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

/**
 * Class that implements the activity of the Login screen.
 * */
public class Login extends Activity
{
    /** Reference to Configuration button*/
	private Button btnConfigurar;
    /** Reference to login WebView object */
	private WebView wvLogin;
    /** Url login*/
	private String strURL;
    /** */
	private static String strHost;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);

        /** Sets the layout for the screen */
        setContentView(R.layout.login);

        /** Sets the local object with the screen webview */
        wvLogin = (WebView) findViewById(R.id.wvLogin);
        /** Sets the local object with the screen button */
        btnConfigurar = (Button) findViewById(R.id.btnConfigurar);

        /** Gets configuration data entered in the screen by the user */
        SharedPreferences prefs = getSharedPreferences("config", Context.MODE_PRIVATE);
        
        strURL = "";
                        
        if(prefs != null)
        {
        	strHost = prefs.getString("host", "");
        	String strLogin = prefs.getString("login", "");
        	
        	strURL = "http://" + strHost + strLogin;
        }
        
        wvLogin.getSettings().setJavaScriptEnabled(true);
        wvLogin.getSettings().setSaveFormData(false);
        wvLogin.getSettings().setSupportMultipleWindows(false); 
        wvLogin.loadUrl(strURL);
        wvLogin.setWebViewClient(new WebViewClient() 
        {
        	public boolean shouldOverrideUrlLoading(WebView view, String url)
        	{
        		view.loadUrl(url);
        		return false;
        	}
        	
        	@Override
            public void onReceivedError(WebView view, int errorCode,
                    String description, String failingUrl) {
                // TODO Auto-generated method stub
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        	
        	public void onPageFinished(WebView view, String url)
        	{
        		if(url.equals("http://" + strHost + "/users/me"))
        		{
        			try
        			{
        				String[] lstrAux = wvLogin.getTitle().split(Pattern.quote("|"));
        				Main.mainInst.strUserName = lstrAux[0].trim().toString();
        				Main.mainInst.setLogged(true);
        				Main.mainInst.btnLogin.setText(R.string.btnLogout);
        				Main.mainInst.btnLogin.setEnabled(true);
        				Main.mainInst.btnScan.setEnabled(true);
        			       			
        				Intent intent = new Intent();
        		        intent.setAction(Intent.ACTION_MAIN);
        		        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        		        intent.setClassName("com.tarrabme", CaptureActivity.class.getName());
        		        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        		        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        		        startActivity(intent);
        			}
        			catch (Exception ex) {}
        		}
        	}
        });

        /** Sets button event and creates the associated activity */
        btnConfigurar.setOnClickListener(new View.OnClickListener() 
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