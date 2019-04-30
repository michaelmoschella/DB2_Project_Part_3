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

public class viewSectionsParent extends AppCompatActivity {
    private Button btnLogout;
    private RequestQueue Q;
    protected void onCreate(Bundle savedInstanceState) {
        the_global global = new the_global();
        Map<String, String> params = new HashMap<String, String>();
        System.out.println("BEFORE VOLLEY");
        System.out.println(global.active_id);
        params.put("active_ID", global.active_id.toString());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_sections_list);

       /* btnLogout = (Button) findViewById(R.id.logout);
        btnLogout.setOnClickListener((v) -> {
            Intent i = new Intent(viewSectionsParent.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });*/

        String url = "http://10.0.2.2/phase3/php_stuff/php/parent-view-sections.php";
        Q = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                try {
                    JSONObject the_response = new JSONObject(response);
                    JSONArray section_objs = the_response.getJSONArray("sections");
                    View line =  findViewById(R.id.p_view_sections);
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

                        System.out.println("HERE1");
                        int or_status = a_section.getInt("moderator_status");
                        System.out.println("status = "+or_status);
                        if (or_status == 1) {
                            System.out.println("HERE3");
                            Button yy = new Button(getApplicationContext());
                            System.out.println("HERE4");
                            yy.setText("Moderate as Moderator");
                            System.out.println("HERE5");
                            yy.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            System.out.println("HERE6");
                            ((LinearLayout) line).addView(yy);
                            System.out.println("HERE7");
                            yy.setOnClickListener((v) -> {
                                System.out.println("HERE8");
                                Map<String, String> inner_params = new HashMap<String, String>();
                                System.out.println("HERE9");
                                inner_params.put("active_ID", global.active_id.toString());
                                System.out.println("HERE10");
                                try {
                                    inner_params.put("cID", a_section.getString("cID"));
                                    inner_params.put("secID", a_section.getString("secID"));
                                } catch (JSONException e){
                                    System.out.println("JSON error1");
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
                                                Intent inner_i = new Intent(viewSectionsParent.this, studentViewSections.class);
                                                startActivity(inner_i);
                                            } else {

                                            }

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
                                        return inner_params;
                                    }
                                };
                                inner_sr.setShouldCache(false);
                                Q.add(inner_sr);
                            });
                        } else if ( or_status == -1) {
                            TextView cloak = new TextView(getApplicationContext());
                            cloak.setText("Section ended, cannot moderate");
                            cloak.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                            cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            ((LinearLayout) line).addView(cloak);
                        } else if ( or_status == -2) {
                            TextView cloak = new TextView(getApplicationContext());
                            cloak.setText("already moderating");
                            cloak.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                            cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            ((LinearLayout) line).addView(cloak);
                        } else if ( or_status == -3) {
                            TextView cloak = new TextView(getApplicationContext());
                            cloak.setText("Moderated by user:" + a_section.getString("moderator"));
                            cloak.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                            cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            ((LinearLayout) line).addView(cloak);
                        } else {
                            TextView cloak = new TextView(getApplicationContext());
                            cloak.setText("Cannot moderate");
                            cloak.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                            cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            ((LinearLayout) line).addView(cloak);
                        }


                    }
                } catch (JSONException e) {
                    System.out.println("JSON error3");
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
