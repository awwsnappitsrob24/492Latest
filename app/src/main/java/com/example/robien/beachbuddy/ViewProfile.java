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
 * Created by DarthMerl on 5/6/2016.
 */
public class ViewProfile extends AppCompatActivity {

    JSONObject jsonObject;
    JSONArray jsonArray;
    Button editProf, returnHome;
    EditText major, prof, fvClass, gradyr;

    String userId, sEmail, response;
    static String maj,pro,fclass, gyear;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profileview_layout);
        userId = LoginActivity.sFbId;
        ProfilePictureView profilePictureView;
        profilePictureView = (ProfilePictureView) findViewById(R.id.userProf_image);
        profilePictureView.setProfileId(userId);
        sEmail = LoginActivity.sEmail;

        major = (EditText)findViewById(R.id.majorID);
        major.setBackground(null);
        major.setKeyListener(null);

        prof = (EditText)findViewById(R.id.favProf);
        prof.setKeyListener(null);
        prof.setBackground(null);

        fvClass = (EditText)findViewById(R.id.favClass);
        fvClass.setKeyListener(null);
        fvClass.setBackground(null);

        gradyr = (EditText)findViewById(R.id.gradYear);
        gradyr.setKeyListener(null);
        gradyr.setBackground(null);

        editProf = (Button)findViewById(R.id.editButton);
        editProf.setText("Edit Profile");


        displayProfile();

       // major.setText(maj);
       // prof.setText(pro);
       // fvClass.setText(fclass);
       // gradyr.setText(gyear);


        editProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewProfile.this, EditProfile.class));
            }
        });
        returnHome = (Button)findViewById(R.id.homeButton);
        returnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewProfile.this, LoginActivity.class));
            }
        });






    }

    class GetProfileView extends AsyncTask<Void, Void, String> {

        String fetchProf;

        @Override
        protected void onPreExecute() {
            fetchProf = "http://52.25.144.228/getProfile.php";
        }

        @Override
        protected String doInBackground(Void... params) {
            Log.v("sEmail", "sEmail view is: " + sEmail);

            try {
                URL url = new URL(fetchProf);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String sData = URLEncoder.encode("sEmail", "UTF-8") + "=" + URLEncoder.encode(sEmail, "UTF-8");
                bufferedWriter.write(sData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(inputStream)));
                StringBuilder stringBuilder = new StringBuilder();
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response + "\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
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
            //Log.v("response is","response is: "+response);
            try {

                jsonObject = new JSONObject(response);
                jsonArray = jsonObject.getJSONArray("profile");
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    fclass = JO.getString("favClass");
                    pro = JO.getString("favProf");
                    gyear = JO.getString("gradYear");
                    maj = JO.getString("major");


                    count++;
                }
                major.setText(maj);
                prof.setText(pro);
                fvClass.setText(fclass);
                gradyr.setText(gyear);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }

    public void displayProfile() {//view v?
        new GetProfileView().execute();
    }







}
