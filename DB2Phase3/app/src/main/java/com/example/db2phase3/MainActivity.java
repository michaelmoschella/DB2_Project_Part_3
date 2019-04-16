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
    private RequestQueue Q;

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
            System.out.println("Hello World");
        });
        btnRegisterParent = (Button) findViewById(R.id.button2);
        btnRegisterParent.setOnClickListener((v)->{
            System.out.println("BYE");
            String data = "{\"the_stuff\":7}";
            submit(data);
        });
    }
    private void submit(String data){
        String save_data = data;
        String url = "http://10.0.2.2/phase3/php/dummy.php";
        Q = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                /*try {
                    JSONObject the_response = new JSONObject(response);

                } catch (JSONException e) {
                    System.out.println("JSON error");
                }*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }

        }){
            /*@Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }*/
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("the_stuff", "47");
                return params;
            };
        };
        sr.setShouldCache(false);
        Q.add(sr);
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
