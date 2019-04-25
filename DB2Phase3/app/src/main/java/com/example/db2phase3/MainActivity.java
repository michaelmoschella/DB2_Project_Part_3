package com.example.db2phase3;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity {
    private Button btnRegisterParent;
    private Button btnRegisterStudent;
    private Button btnLoginParent;
    private Button btnLoginStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btnRegisterStudent = (Button) findViewById(R.id.button1);
        btnRegisterStudent.setOnClickListener((v)->{
            System.out.println("Change to Register Student View");
            Intent i = new Intent(this, registerStudent.class);
            startActivity(i);
        });
        btnRegisterParent = (Button) findViewById(R.id.button2);
        btnRegisterParent.setOnClickListener((v)->{
            System.out.println("Change to Register Parent View");
            Intent i = new Intent(this, registerParent.class);
            startActivity(i);
        });
        btnLoginStudent = (Button) findViewById(R.id.button3);
        btnLoginStudent.setOnClickListener((v)->{
            System.out.println("Change to Login Student View");
            Intent i = new Intent(this, loginStudent.class);
            startActivity(i);
        });
        btnLoginParent = (Button) findViewById(R.id.button4);
        btnLoginParent.setOnClickListener((v)->{
            System.out.println("Change to Login Parent View");
            Intent i = new Intent(this, loginParent.class);
            startActivity(i);
        });


        View line =  findViewById(R.id.linlayout);

        TextView hat = new TextView(this);


        hat.setText("29 bottles of beer on the wall,");


        hat.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

    ((LinearLayout) line).addView(hat);


        TextView cloak = new TextView(this);
        cloak.setText("29 bottles of beer,");


        
        cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        ((LinearLayout) line).addView(cloak);





    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

}
