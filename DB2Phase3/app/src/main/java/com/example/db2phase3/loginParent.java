package com.example.db2phase3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class loginParent extends AppCompatActivity {
    private Button btnSubmit;
    private Button btnMain;
    private RequestQueue Q;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_login);

        btnSubmit = (Button) findViewById(R.id.button1);
        btnSubmit.setOnClickListener((v)-> {
            EditText edit_email = findViewById(R.id.EditText1);
            String email = edit_email.getText().toString();
            //System.out.println(email);

            EditText edit_password = findViewById(R.id.EditText3);
            String password = edit_password.getText().toString();
            //System.out.println(password);

            Map<String, String> params = new HashMap<String, String>();
            params.put("Parent_Email_Login", email);
            params.put("Parent_Pass_Login", password);

            submit(params);
        });
    }
    private void submit(Map<String, String> params) {
        Map<String, String> the_params = params;
        String url = "http://10.0.2.2/phase3/php_stuff/php/parent-login.php";
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
                        // Change this to parent dashboard
                        Intent i = new Intent(loginParent.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    } else {
                        setContentView(R.layout.successful_registration);
                        btnMain = (Button) findViewById(R.id.button1);
                        btnMain.setOnClickListener((v)-> {
                            Intent i = new Intent(loginParent.this, MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        });
                        TextView banner = findViewById(R.id.text1);
                        if (status == -1) {
                            banner.setText("The email address " + the_response.getString("p_email") + " is not registered to a parent in our database.");
                        } else {
                            banner.setText("The provided password is not correct for: " + the_response.getString("p_email") + ".");
                        }
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
}
