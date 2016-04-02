package com.example.robien.beachbuddy;

import android.content.pm.PackageInstaller;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Robien on 3/20/2016.
 */
public class ProfileActivity extends AppCompatActivity {

    private CallbackManager callbackManager;

    TextView name, email;
    Button addToGroup, sendMessage, viewProfile;
    private ProfilePictureView profilePictureView;
    URL img_url;
    Bitmap bmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        profilePictureView = (ProfilePictureView)findViewById(R.id.profilePic);
        name = (TextView)findViewById(R.id.nameText);
        email = (TextView)findViewById(R.id.emailText);

        addToGroup = (Button)findViewById(R.id.sendInvite);
        addToGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String acc_reg_url = "http://52.25.144.228/submitinvite.php";
                String sEmail = NavigationActivity.studentEmail;
                String cName = NavigationActivity.className;
                try {
                    URL url = new URL(acc_reg_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("cName", "UTF-8") + "=" + URLEncoder.encode(cName, "UTF-8") + "&" +
                            URLEncoder.encode("sEmail", "UTF-8") + "=" + URLEncoder.encode(sEmail, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();
                    InputStream IS = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS,"iso-8859-1"));
                    String response = "";
                    String line  = "";
                    while ((line = bufferedReader.readLine())!=null)
                    {
                        response+= line;
                    }
                    bufferedReader.close();
                    IS.close();
                    httpURLConnection.disconnect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        sendMessage = (Button)findViewById(R.id.sendMessage);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        viewProfile = (Button)findViewById(R.id.viewProfile);
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        try {
            img_url = new URL("https://graph.facebook.com/" + NavigationActivity.ID + "/picture");
            bmp = BitmapFactory.decodeStream(img_url.openConnection().getInputStream());
            profilePictureView.setDefaultProfilePicture(bmp);
            profilePictureView.setPresetSize(ProfilePictureView.NORMAL);
            profilePictureView.setVisibility(View.VISIBLE);

            name.setText(NavigationActivity.studentName);
            email.setText(NavigationActivity.studentEmail);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}

