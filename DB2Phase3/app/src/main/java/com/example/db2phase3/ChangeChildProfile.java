package com.example.db2phase3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class ChangeChildProfile extends AppCompatActivity {
    private Button btnSubmit;
    private Button btnMain;
    private RequestQueue Q;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        the_global global = new the_global();
        Map<String, String> params = new HashMap<String, String>();
        params.put("active_ID", global.active_id.toString());
        params.put("cID", global.child_id.toString());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_child_profile);

        Map<String, String> the_params = params;
        String url = "http://10.0.2.2/phase3/php_stuff/php/change-c-profile.php";
        Q = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                try {
                    JSONObject the_response = new JSONObject(response);
                    View line = findViewById(R.id.c_change);
                    String[] fields = new String[]{"Name", "Username", "Password", "Confirm_Password", "Email", "Phone", "Role"};
                    EditText edit_name = new EditText(getApplicationContext());
                    EditText edit_username = new EditText(getApplicationContext());
                    EditText edit_password = new EditText(getApplicationContext());
                    EditText edit_c_password = new EditText(getApplicationContext());
                    EditText edit_email = new EditText(getApplicationContext());
                    EditText edit_phone = new EditText(getApplicationContext());
                    EditText edit_role = new EditText(getApplicationContext());
                    EditText[] ets = new EditText[]{edit_name, edit_username, edit_password, edit_c_password, edit_email, edit_phone, edit_role};

                    for (int i = 0; i < fields.length; i++) {
                        View liner = new View(getApplicationContext());
                        liner.setBackgroundColor(getResources().getColor(android.R.color.black));
                        liner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
                        ((LinearLayout) line).addView(liner);

                        TextView cloak = new TextView(getApplicationContext());
                        cloak.setText(fields[i].replace('_', ' ') + ":");
                        cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        ((LinearLayout) line).addView(cloak);

                        ets[i].setText(the_response.getString(fields[i]));
                        ets[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        ((LinearLayout) line).addView(ets[i]);
                    }



                    Button yy = new Button(getApplicationContext());
                    yy.setText("Submit Changes");
                    yy.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    ((LinearLayout) line).addView(yy);
                    yy.setOnClickListener((v) -> {
                        System.out.println("HERE1");
                        Map<String, String> inner_params = new HashMap<String, String>();
                        for (int i = 0; i < fields.length; i++) {
                            inner_params.put(fields[i], ets[i].getText().toString());
                        }
                        inner_params.put("active_ID", global.active_id.toString());
                        System.out.print(global.child_id.toString());
                        inner_params.put("c_ID", global.child_id.toString());

                        String url = "http://10.0.2.2/phase3/php_stuff/php/c-profile-altered.php";
                        StringRequest inner_sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String inner_response) {
                                System.out.println(inner_response);
                                try {
                                    System.out.println("HERE2");
                                    JSONObject the_inner_response = new JSONObject(inner_response);
                                    System.out.println("HERE55");
                                    int inner_status = the_inner_response.getInt("status");

                                    if (inner_status == 1) {
                                        Intent inner_i = new Intent(ChangeChildProfile.this, parentDashboard.class);
                                        startActivity(inner_i);
                                        Toast.makeText(getApplicationContext(),"Congratulations, your child's profile has been changed!", Toast.LENGTH_LONG).show();
                                    } else {

                                    }

                                } catch (JSONException e) {
                                    System.out.println("JSON error1");
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
                                return inner_params;
                            }
                        };
                        inner_sr.setShouldCache(false);
                        System.out.println("HERE3");
                        Q.add(inner_sr);
                    });

                } catch (JSONException e) {
                    System.out.println("JSON error2");
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