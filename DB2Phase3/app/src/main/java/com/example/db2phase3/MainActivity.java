package com.example.db2phase3;

import android.content.Context;
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

        View liner = new View(getApplicationContext());
        liner.setBackgroundColor(getResources().getColor(android.R.color.black));
        liner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,4));
        ((LinearLayout) line).addView(liner);


        TextView hat = new TextView(this);

        //Context context = this.;

        hat.setText("Course Title: ");
        hat.setTextSize(20);


        hat.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

    ((LinearLayout) line).addView(hat);


        TextView cloak = new TextView(this);
        cloak.setText("Section Name:");
        cloak.setTextSize(20);


        cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        ((LinearLayout) line).addView(cloak);

        TextView a = new TextView(this);
        a.setText("Start Date: ");
        a.setTextSize(20);


        a.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        ((LinearLayout) line).addView(a);

        TextView c = new TextView(this);
        c.setText("End Date: ");
        c.setTextSize(20);


        c.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        ((LinearLayout) line).addView(c);


        TextView b = new TextView(this);
        b.setText("Time Slot: ");
        b.setTextSize(20);


        b.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        ((LinearLayout) line).addView(b);

        TextView d = new TextView(this);
        d.setText("Capacity: ");
        d.setTextSize(20);


        d.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        ((LinearLayout) line).addView(d);

        TextView e = new TextView(this);
        e.setText("Mentor Req: ");
        e.setTextSize(20);


        e.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        ((LinearLayout) line).addView(e);

        TextView f = new TextView(this);
        f.setText("Mentee Req: ");
        f.setTextSize(20);


        f.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        ((LinearLayout) line).addView(f);

        TextView g = new TextView(this);
        g.setText("Enrolled Mentor: ");
        g.setTextSize(20);


        g.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        ((LinearLayout) line).addView(g);

        TextView h = new TextView(this);
        h.setText("Enrolled Mentee: ");
        h.setTextSize(20);


        h.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        ((LinearLayout) line).addView(h);

        Button yy = new Button(this);
        yy.setText("Teach as Mentor");



        yy.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        ((LinearLayout) line).addView(yy);

        Button zz = new Button(this);
        zz.setText("Enroll as Mentee");



        zz.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        ((LinearLayout) line).addView(zz);

        View linet = new View(getApplicationContext());
        linet.setBackgroundColor(getResources().getColor(android.R.color.black));
        linet.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,4));
        ((LinearLayout) line).addView(linet);






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
