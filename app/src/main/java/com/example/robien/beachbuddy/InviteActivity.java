

        package com.example.robien.beachbuddy;

        import android.content.Intent;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import com.facebook.Profile;

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
 * Created by Robien on 3/24/2016.
 */
public class InviteActivity extends AppCompatActivity {

    TextView output;
    Button accept, decline;
    String cName, sEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_layout);

        output = (TextView) findViewById(R.id.inviteText);
        output.setText(LoginActivity.responseString);

        accept = (Button) findViewById(R.id.accept);
        decline = (Button) findViewById(R.id.decline);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptInvite(v);
            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    class acceptInvite extends AsyncTask<Void, Void, String> {
        String accept_url;
        String class_Name = LoginActivity.responseString;
        String studentEmail = LoginActivity.email.getText().toString();
        String response = "";

        @Override
        protected void onPreExecute() {
            accept_url = "http://52.25.144.228/groupReg2.php";
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(accept_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String sData = URLEncoder.encode("cName", "UTF-8") + "=" + URLEncoder.encode(class_Name, "UTF-8") + "&" +
                        URLEncoder.encode("sEmail", "UTF-8") + "=" + URLEncoder.encode(studentEmail, "UTF-8");
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

        }





    }

    public void acceptInvite(View v) {
        new acceptInvite().execute();
    }
}

