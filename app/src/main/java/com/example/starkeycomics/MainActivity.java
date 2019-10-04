package com.example.starkeycomics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import com.google.android.gms.common.util.Strings;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.util.Scanner;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class MainActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;

    //private Boolean loggedin = FALSE;
    private View v = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
     //   loggedin = FALSE;
    }

    public void login(View view) throws IOException, JSONException {
    //    loggedin=FALSE;
        v = view;
        String username = "";
        String password = "";
        EditText edittext2 = (EditText) findViewById(R.id.editText2);
        username = edittext2.getText().toString();
        EditText edittext = (EditText) findViewById(R.id.editText);
        password = edittext.getText().toString();
        new LoginUser().execute(username,password);
//        if(loggedin.equals(TRUE))
//        {
//            openingActivity(view);
//        }
//        if(username.equals("saurabh") && password.equals("admin"))
//        {
//            openingActivity(view);
//        }

    }

    private String getPassword(String username) throws IOException, JSONException {
        URL url = new URL("http://3.82.74.116:5000/user/"+username);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        //conn.setRequestProperty("Content-Type", "");
        conn.connect();
        int responsecode = conn.getResponseCode();
        if(responsecode != 200)
            throw new RuntimeException("HttpResponseCode: " +responsecode);
        else
        {
            Scanner sc = new Scanner(url.openStream());
            String inline="";
            while(sc.hasNext())
            {
                inline+=sc.nextLine();
            }
            //System.out.println("\nJSON data in string format");
            //System.out.println(inline);
            sc.close();
            JSONObject json = new JSONObject(inline);
            return json.getString("password");
        }
        //return "";
    }

    private class LoginUser extends AsyncTask<String, Void, String> {

        private String password="";
        @Override
        protected String doInBackground (String... users) {
            String username = users[0];
            password=users[1];
            URL url = null;
            try {
                url = new URL("http://35.153.151.7:5000/user/"+username);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection)url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                conn.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            try {
                conn.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int responsecode = 0;
            try {
                responsecode = conn.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(responsecode != 200)
                throw new RuntimeException("HttpResponseCode: " +responsecode);
            else
            {
                Scanner sc = null;
                try {
                    sc = new Scanner(url.openStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String inline="";
                while(sc.hasNext())
                {
                    inline+=sc.nextLine();
                }
                //System.out.println("\nJSON data in string format");
                //System.out.println(inline);
                sc.close();
                JSONObject json = null;
                try {
                    json = new JSONObject(inline);
                    return json.getString("password");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return "";
        }

        //@Override
        protected void onPostExecute(String result) {
            if(result.equals(password))
            {
            //    loggedin = TRUE;
                reportLogin(v);
                openingActivity(v);
            }
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    public void openingActivity(View view) {
//        String username = "";
//        String password = "";
//        // Boolean loggedin = FALSE;
//        EditText edittext2 = (EditText) findViewById(R.id.editText2);
//        String username = edittext2.getText().toString();
//        EditText edittext = (EditText) findViewById(R.id.editText);
//        String password = edittext.getText().toString();
//        if((username.equals("saurabh")) && (password.equals("admin")))
//        {
//            Intent intent = new Intent(MainActivity.this, OpeningActivity.class);
//            startActivity(intent);
//        }
        Intent intent = new Intent(MainActivity.this, OpeningActivity.class);
        startActivity(intent);
    }
    public void reportLogin(View view) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.METHOD, ((EditText)findViewById(R.id.editText2)).getText().toString());
        //bundle.putString(FirebaseAnalytics.Param.VALUE, "10");
        //bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "button");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
    }
}
