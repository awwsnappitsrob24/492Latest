package com.example.robien.beachbuddy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Robien on 2/17/2016.
 */
public class RegPersonInfoActivity extends AppCompatActivity {

    EditText ET_FNAME, ET_LNAME, ET_PASS, ET_CPASS, ET_EMAIL;
    Button NEXTPAGE_BUTTON;
    String fname, lname, id, email, pass;
    Context ctx;
    //private static String url_create_student = "http://cecs492beachbuddy.site88.net/create_student2.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regperson_layout);

        //Intent regIntent = getIntent();

        ET_FNAME = (EditText)findViewById(R.id.fname_reg);
        //ET_MNAME = (EditText)findViewById(R.id.mname_reg);
        ET_LNAME = (EditText)findViewById(R.id.lname_reg);
        ET_PASS = (EditText)findViewById(R.id.pass_reg);
        //ET_CPASS = (EditText)findViewById(R.id.confirmpass_reg);
        ET_EMAIL = (EditText)findViewById(R.id.email_reg);
/*
        NEXTPAGE_BUTTON = (Button)findViewById(R.id.nextpage_butt);
        NEXTPAGE_BUTTON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), ET_FNAME.getText().toString() + " "
                        + ET_LNAME.getText().toString() + " " + ET_PASS.getText().toString() + " " +
                        ET_CPASS.getText().toString() + " " + ET_EMAIL.getText().toString(),
                        Toast.LENGTH_LONG).show();

                // ADD INFO TO DATABASE

                Intent logIntent = new Intent(RegPersonInfoActivity.this, RegClassActivity.class);
                RegPersonInfoActivity.this.startActivity(logIntent);
            }
        }
        );

     */
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void userReg(View v) {
        // edit text and button initalizations
        fname = ET_FNAME.getText().toString();
        lname = ET_LNAME.getText().toString();
        email = ET_EMAIL.getText().toString();
        pass = ET_PASS.getText().toString();
        String method = "register";
        BackgroundTask bt = new BackgroundTask(this);
        bt.execute(method, fname, lname, email, pass);
        finish();
    }
}
