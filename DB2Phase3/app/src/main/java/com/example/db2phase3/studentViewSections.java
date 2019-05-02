package com.example.db2phase3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import android.text.SpannableStringBuilder;
import android.text.Spannable;
import android.widget.Toast;

public class studentViewSections extends AppCompatActivity {
    private Button btnLogout;
    private RequestQueue Q;
    protected void onCreate(Bundle savedInstanceState) {
        the_global global = new the_global();
        Map<String, String> params = new HashMap<String, String>();
        System.out.println("BEFORE VOLLEY");
        System.out.println(global.active_id);
        params.put("active_ID", global.active_id.toString());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_view_sections);

        btnLogout = (Button) findViewById(R.id.logout);
        btnLogout.setOnClickListener((v) -> {
            Intent i = new Intent(studentViewSections.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });

        String url = "http://10.0.2.2/phase3/php_stuff/php/student-view-sections.php";
        Q = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                try {
                    JSONObject the_response = new JSONObject(response);
                    JSONArray section_objs = the_response.getJSONArray("sections");
                    View line =  findViewById(R.id.s_sections);
                    for (int i = 0; i < section_objs.length(); i++){
                        View liner = new View(getApplicationContext());
                        liner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100));
                        ((LinearLayout) line).addView(liner);

                        liner = new View(getApplicationContext());
                        liner.setBackgroundColor(getResources().getColor(android.R.color.black));
                        liner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,10));
                        ((LinearLayout) line).addView(liner);

                        JSONObject a_section = section_objs.getJSONObject(i);
                        String[] fields = new String[]{ "Course_Title", "Section_Name", "Description", "Start_Date", "End_Date", "Start_Time", "End_Time", "Mentor_Requirement", "Mentee_Requirement", "Mentors_Enrolled", "Mentor_Capacity", "Mentees_Enrolled", "Mentee_Capacity" };
                        for (int j = 0; j < fields.length; j++){
                            SpannableStringBuilder str = new SpannableStringBuilder(fields[j].replace('_', ' ') + ": " + a_section.getString(fields[j]));
                            int start = 0;
                            int end = fields[j].length();
                            str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                            TextView cloak = new TextView(getApplicationContext());
                            cloak.setText(str);
                            cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            if (j == 0) {
                                cloak.setTextSize(30);
                            } else {
                                cloak.setTextSize(20);
                            }
                            ((LinearLayout) line).addView(cloak);
                        }
                        int or_status = a_section.getInt("or_status");
                        if (or_status == 1) {
                            Button yy = new Button(getApplicationContext());
                            yy.setText("Teach as Mentor");
                            yy.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            ((LinearLayout) line).addView(yy);
                            yy.setOnClickListener((v) -> {
                                Map<String, String> inner_params = new HashMap<String, String>();
                                inner_params.put("active_ID", global.active_id.toString());
                                try {
                                    inner_params.put("cID", a_section.getString("cID"));
                                    inner_params.put("secID", a_section.getString("secID"));
                                } catch (JSONException e){
                                    System.out.println("JSON error");
                                }
                                String url = "http://10.0.2.2/phase3/php_stuff/php/enroll-mentor.php";
                                StringRequest inner_sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String inner_response) {
                                        System.out.println(inner_response);
                                        try {
                                            JSONObject the_inner_response = new JSONObject(inner_response);
                                            int inner_status = the_inner_response.getInt("status");

                                            if (inner_status == 1) {
                                                Intent inner_i = new Intent(studentViewSections.this, studentViewSections.class);
                                                startActivity(inner_i);
                                                Toast.makeText(getApplicationContext(),"Congratulations, you have been enrolled as a mentor!", Toast.LENGTH_LONG).show();
                                            } else {

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
                                        return inner_params;
                                    }
                                };
                                inner_sr.setShouldCache(false);
                                Q.add(inner_sr);
                            });
                        } else if ( or_status == -1) {
                            TextView cloak = new TextView(getApplicationContext());
                            cloak.setText("Time conflict, cannot mentor");
                            cloak.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                            cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            ((LinearLayout) line).addView(cloak);
                        } else if ( or_status == -2) {
                            TextView cloak = new TextView(getApplicationContext());
                            cloak.setText("Section full, cannot mentor");
                            cloak.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                            cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            ((LinearLayout) line).addView(cloak);
                        } else if ( or_status == -3) {
                            TextView cloak = new TextView(getApplicationContext());
                            cloak.setText("Currently Teaching");
                            cloak.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                            cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            ((LinearLayout) line).addView(cloak);
                        } else {
                            TextView cloak = new TextView(getApplicationContext());
                            cloak.setText("Cannot mentor");
                            cloak.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                            cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            ((LinearLayout) line).addView(cloak);
                        }

                        int ee_status = a_section.getInt("ee_status");
                        if (ee_status == 1) {
                            Button yy = new Button(getApplicationContext());
                            yy.setText("Learn as Mentee");
                            yy.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            ((LinearLayout) line).addView(yy);
                            yy.setOnClickListener((v) -> {
                                Map<String, String> inner_params = new HashMap<String, String>();
                                inner_params.put("active_ID", global.active_id.toString());
                                try {
                                    inner_params.put("cID", a_section.getString("cID"));
                                    inner_params.put("secID", a_section.getString("secID"));
                                } catch (JSONException e){
                                    System.out.println("JSON error");
                                }
                                String url = "http://10.0.2.2/phase3/php_stuff/php/enroll-mentee.php";
                                StringRequest inner_sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String inner_response) {
                                        System.out.println(inner_response);
                                        try {
                                            JSONObject the_inner_response = new JSONObject(inner_response);
                                            int inner_status = the_inner_response.getInt("status");

                                            if (inner_status == 1) {
                                                Intent inner_i = new Intent(studentViewSections.this, studentViewSections.class);
                                                startActivity(inner_i);
                                                Toast.makeText(getApplicationContext(),"Congratulations, you have been enrolled as a mentee!", Toast.LENGTH_LONG).show();
                                            } else {

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
                                        return inner_params;
                                    }
                                };
                                inner_sr.setShouldCache(false);
                                Q.add(inner_sr);
                            });
                        } else if ( ee_status == -1) {
                            TextView cloak = new TextView(getApplicationContext());
                            cloak.setText("Time conflict, cannot enroll as mentee");
                            cloak.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                            cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            ((LinearLayout) line).addView(cloak);
                        } else if ( ee_status == -2) {
                            TextView cloak = new TextView(getApplicationContext());
                            cloak.setText("Section full, cannot enroll as mentee");
                            cloak.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                            cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            ((LinearLayout) line).addView(cloak);
                        } else if ( ee_status == -3) {
                            TextView cloak = new TextView(getApplicationContext());
                            cloak.setText("Currently enrolled as mentee");
                            cloak.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                            cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            ((LinearLayout) line).addView(cloak);
                        } else if ( ee_status == -5) {
                            TextView cloak = new TextView(getApplicationContext());
                            cloak.setText("Section full, cannot enroll as mentee");
                            cloak.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                            cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            ((LinearLayout) line).addView(cloak);
                        } else {
                            TextView cloak = new TextView(getApplicationContext());
                            cloak.setText("Cannot enroll as mentee");
                            cloak.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                            cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            ((LinearLayout) line).addView(cloak);
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
