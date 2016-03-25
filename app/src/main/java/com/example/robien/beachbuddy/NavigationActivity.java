package com.example.robien.beachbuddy;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;

/**
 * Created by Calvin on 2/24/2016.
 */
public class NavigationActivity extends AppCompatActivity {

    Button search;
    EditText searchClass;
    String JSON_String, idString;
    JSONObject jsonObject;
    JSONArray jsonArray;
    StudentAdapter studentAdapter;
    ListView listView;
    Student selectedStudent;

    static String name, email, studentName, studentEmail;


    public static String ID; // to save the facebook ID (will need in another class)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header_main);
        //studentName = LoginActivity.facebookName.getText().toString(); // pass this to search fb user
        search = (Button)findViewById(R.id.search);
        searchClass = (EditText)findViewById(R.id.classSearch);
        listView = (ListView)findViewById(R.id.listView);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                getJSON(v);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Get name from the row (position) clicked and pass it to search for the facebook profile
                selectedStudent = (Student)studentAdapter.getItem(position);

                //Extract name from row
                studentName = selectedStudent.getName();
                studentEmail = selectedStudent.getEmail();

                //Get the person's profile (the right way)
                //Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                //startActivity(profileIntent);

                // Get the user's ID to go to their profile
                //String method = "fetchFbId";
                //BackgroundTask bt = new BackgroundTask(getApplicationContext());
                //bt.execute(method, studentName);
                //Toast.makeText(getApplicationContext(), studentName, Toast.LENGTH_LONG).show();

                getFbId(view);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    class SearchBackground extends AsyncTask<Void, Void, String> {
        String json_url;
        String cName = searchClass.getText().toString();
        String sName = LoginActivity.facebookName.getText().toString();

        @Override
        protected void onPreExecute() {
            json_url = "http://52.25.144.228/search.php";
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String sData = URLEncoder.encode("cName", "UTF-8")+ "=" + URLEncoder.encode(cName, "UTF-8") + "&" +
                        URLEncoder.encode("sName", "UTF-8") + "=" + URLEncoder.encode(sName, "UTF-8");
                bufferedWriter.write(sData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(inputStream)));
                StringBuilder stringBuilder = new StringBuilder();
                while((JSON_String = bufferedReader.readLine()) != null) {
                    stringBuilder.append(JSON_String + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }
            catch(MalformedURLException e) {
                e.printStackTrace();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected void onPostExecute(String result) {
            JSON_String = result;
            try {
                studentAdapter = new StudentAdapter(getBaseContext(), R.layout.row_layout);
                listView.setAdapter(null);
                jsonObject = new JSONObject(JSON_String);
                jsonArray = jsonObject.getJSONArray("students");
                int count = 0;
                ;
                while(count < jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    name = JO.getString("sName");
                    email = JO.getString("sEmail");
                    Student student = new Student(name, email);
                    listView.setAdapter(studentAdapter);
                    studentAdapter.add(student);
                    count++;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void getJSON(View v) {

        new SearchBackground().execute();
    }

    class FetchFbId extends AsyncTask<Void, Void, String> {
        String fetchId_url;
        String studentName = selectedStudent.getName();

        @Override
        protected void onPreExecute() {
            fetchId_url = "http://52.25.144.228/fetchId.php";
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(fetchId_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String sData = URLEncoder.encode("studentName", "UTF-8") + "=" + URLEncoder.encode(studentName, "UTF-8");
                bufferedWriter.write(sData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(inputStream)));
                StringBuilder stringBuilder = new StringBuilder();
                while ((idString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(idString + "\n");
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
            idString = result;
            try {
                jsonObject = new JSONObject(idString);
                jsonArray = jsonObject.getJSONArray("ID");
                int count = 0;
                ID = "";

                JSONObject JO = jsonArray.getJSONObject(count);
                ID = JO.getString("sFacebookID"); // fetch the ID from database

                //go to user's profile
                //String URI = "https://www.facebook.com/" + ID;
                //Intent profileIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URI));
                //startActivity(profileIntent);

                Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(profileIntent);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Go to user's profile

        }
    }

    public void getFbId(View v) {
        new FetchFbId().execute();
    }
}

