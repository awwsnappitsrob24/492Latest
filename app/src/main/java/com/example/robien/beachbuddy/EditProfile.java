package com.example.robien.beachbuddy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.widget.ProfilePictureView;

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
 * Created by DarthMerl on 5/6/2016.
 */
public class EditProfile extends AppCompatActivity {


    Button editProf, returnHome;
    EditText major, prof, favClass, gradYear;

    String userId, sEmail, major1, prof1, favClass1, gradYear1, response;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profileview_layout);
        userId = LoginActivity.sFbId;
        ProfilePictureView profilePictureView;
        profilePictureView = (ProfilePictureView) findViewById(R.id.userProf_image);
        profilePictureView.setProfileId(userId);
        major1 = ViewProfile.maj;
        prof1 = ViewProfile.pro;
        favClass1=ViewProfile.fclass;
        gradYear1 = ViewProfile.gyear;
        sEmail = LoginActivity.sEmail;

        major = (EditText)findViewById(R.id.majorID);
        major.setText(major1);
        prof = (EditText)findViewById(R.id.favProf);
        prof.setText(prof1);
        favClass = (EditText)findViewById(R.id.favClass);
        favClass.setText(favClass1);
        gradYear = (EditText)findViewById(R.id.gradYear);
        gradYear.setText(gradYear1);






        editProf = (Button)findViewById(R.id.editButton);
        editProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                major1 = major.getText().toString();
                prof1 = prof.getText().toString();
                favClass1 = favClass.getText().toString();
                gradYear1 = gradYear.getText().toString();
                displayProfile();



            }
        });

        returnHome = (Button)findViewById(R.id.homeButton);
        returnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfile.this, LoginActivity.class));
            }
        });













    }

    class GetProfileView extends AsyncTask<Void, Void, String> {

        String updateProf;

        @Override
        protected void onPreExecute() {
            updateProf = "http://52.25.144.228/setProfile.php";
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                URL url = new URL(updateProf);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("sEmail", "UTF-8") + "=" + URLEncoder.encode(sEmail, "UTF-8") + "&" +
                        URLEncoder.encode("favClass", "UTF-8") + "=" + URLEncoder.encode(favClass1, "UTF-8") + "&" +
                        URLEncoder.encode("favProf", "UTF-8") + "=" + URLEncoder.encode(prof1, "UTF-8")+ "&" +
                        URLEncoder.encode("gradYear", "UTF-8") + "=" + URLEncoder.encode(gradYear1, "UTF-8") + "&" +
                        URLEncoder.encode("major", "UTF-8") + "=" + URLEncoder.encode(major1, "UTF-8");
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
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected void onPostExecute(String result) {
            response = result;
            Toast.makeText(getApplicationContext(), "Profile Updated!", Toast.LENGTH_LONG).show();// Set your own toast  message
            startActivity(new Intent(EditProfile.this, LoginActivity.class));

        }


    }

    public void displayProfile() {//view v?
        new GetProfileView().execute();
    }




}
