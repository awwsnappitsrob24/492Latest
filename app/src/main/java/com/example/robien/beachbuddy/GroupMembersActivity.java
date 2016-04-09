package com.example.robien.beachbuddy;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.robien.beachbuddy.Group;
import com.example.robien.beachbuddy.GroupAdapter;
import com.example.robien.beachbuddy.LoginActivity;
import com.example.robien.beachbuddy.R;

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

public class GroupMembersActivity extends AppCompatActivity {

    ListView memberList;
    TextView memberText;
    MemberAdapter memberAdapter;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String response, sEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_members_layout);

        memberText = (TextView)findViewById(R.id.memberText);
        memberText.setText("Group Members in " + GroupsView.groupName + " " + GroupsView.groupID);

        memberList = (ListView)findViewById(R.id.memberList);

        getGroupMembers();
    }

    class GetGroupMembers extends AsyncTask<Void, Void, String> {
        String fetchgroupmembers_url;
        String groupName = GroupsView.selectedGroup.getGroupType();
        String groupID = GroupsView.selectedGroup.getGroupID();

        @Override
        protected void onPreExecute() {
            fetchgroupmembers_url = "http://52.25.144.228/getgroupmembers.php";
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                URL url = new URL(fetchgroupmembers_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String sData = URLEncoder.encode("groupName", "UTF-8") + "=" + URLEncoder.encode(groupName, "UTF-8") + "&" +
                        URLEncoder.encode("groupID", "UTF-8") + "=" + URLEncoder.encode(groupID, "UTF-8");
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
            try {
                memberAdapter = new MemberAdapter(getBaseContext(), R.layout.member_row_layout);
                jsonObject = new JSONObject(response);
                jsonArray = jsonObject.getJSONArray("members");
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject JO = jsonArray.getJSONObject(count);
                    sEmail = JO.getString("sEmail");
                    memberList.setAdapter(memberAdapter);
                    memberAdapter.add(sEmail);
                    count++;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void getGroupMembers() {//view v?
        new GetGroupMembers().execute();
    }
}
