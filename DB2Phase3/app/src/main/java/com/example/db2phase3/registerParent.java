package com.example.db2phase3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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



public class registerParent extends AppCompatActivity {
    private Button btnSubmit;
    private Button btnMain;
    private RequestQueue Q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_register);

        btnSubmit = (Button) findViewById(R.id.button1);
        btnSubmit.setOnClickListener((v)->{
            EditText edit_email = findViewById(R.id.EditText1);
            String email = edit_email.getText().toString();
            //System.out.println(email);

            EditText edit_password = findViewById(R.id.EditText3);
            String password = edit_password.getText().toString();
            //System.out.println(password);

            EditText edit_confirm = findViewById(R.id.EditText4);
            String confirm = edit_confirm.getText().toString();
            //System.out.println(confirm);

            EditText edit_name = findViewById(R.id.EditText5);
            String name = edit_name.getText().toString();
            //System.out.println(name);

            EditText edit_phone = findViewById(R.id.EditText6);
            String phone = edit_phone.getText().toString();
            //System.out.println(phone);

            EditText edit_username = findViewById(R.id.EditText7);
            String username = edit_username.getText().toString();
            //System.out.println(username);

            Map<String, String> params = new HashMap<String, String>();
            params.put("Parent_Email", email);
            params.put("Parent_Pass", password);
            params.put("Parent_Confirm_Pass", confirm);
            params.put("p_role", "Moderator");
            params.put("Parent_Name", name);
            params.put("Parent_Phone_Number", phone);
            params.put("p_username", username);

            submit(params);
        });

    }
    private void submit(Map<String, String> params) {
        Map<String, String> the_params = params;
        String url = "http://10.0.2.2/phase3/php_stuff/php/register-parent.php";
        Q = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                try {
                    JSONObject the_response = new JSONObject(response);
                    int status = the_response.getInt("status");
                    System.out.println(status);
                    if (status == 1) {
                        setContentView(R.layout.successful_registration);
                        TextView banner = findViewById(R.id.text1);
                        banner.setText("Congratulations " + the_response.getString("p_username") + ", you have successfully registered!");
                        btnMain = (Button) findViewById(R.id.button1);
                        btnMain.setOnClickListener((v)-> {
                            Intent i = new Intent(registerParent.this, MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        });
                    }

                } catch (JSONException e) {
                    System.out.println("JSON error");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
               /* Map<String, String> params = new HashMap<String, String>();
                params.put("the_stuff", "47");*/
                return params;
            }
        };
        sr.setShouldCache(false);
        Q.add(sr);
    }
    /*
    .setOnClickListener((v)->{
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
                try {
                    JSONObject the_response = new JSONObject(response);

                } catch (JSONException e) {
                    System.out.println("JSON error");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }

        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("the_stuff", "47");
                return params;
            };
        };
        sr.setShouldCache(false);
        Q.add(sr);
    */
}
