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

public class viewMentor extends AppCompatActivity {
    private Button btnLogout;
    private RequestQueue Q;
    protected void onCreate(Bundle savedInstanceState) {
        the_global global = new the_global();
        Map<String, String> params = new HashMap<String, String>();
        System.out.println("BEFORE VOLLEY");
        System.out.println(global.active_id);
        params.put("active_ID", global.active_id.toString());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_mentor);

        btnLogout = (Button) findViewById(R.id.logout);
        btnLogout.setOnClickListener((v) -> {
            Intent i = new Intent(viewMentor.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });

        String url = "http://10.0.2.2/phase3/php_stuff/php/view-mentor.php";
        Q = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                try {
                    JSONObject the_response = new JSONObject(response);
                    JSONArray section_objs = the_response.getJSONArray("sections");
                    View line =  findViewById(R.id.s_view_mentor);
                    for (int i = 0; i < section_objs.length(); i++) {
                        View liner = new View(getApplicationContext());
                        liner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100));
                        ((LinearLayout) line).addView(liner);

                        liner = new View(getApplicationContext());
                        liner.setBackgroundColor(getResources().getColor(android.R.color.black));
                        liner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10));
                        ((LinearLayout) line).addView(liner);

                        JSONObject a_section = section_objs.getJSONObject(i);
                        String[] fields = new String[]{"Course_Title", "Section_Name"};
                        for (int j = 0; j < fields.length; j++) {
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

                        liner = new View(getApplicationContext());
                        liner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 50));
                        ((LinearLayout) line).addView(liner);

                        TextView cloaker = new TextView(getApplicationContext());
                        cloaker.setText("Mentors");
                        cloaker.setTextSize(20);
                        cloaker.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        ((LinearLayout) line).addView(cloaker);

                        JSONArray section_mentors = a_section.getJSONArray("mentors");
                        for (int j = 0; j < section_mentors.length(); j++) {
                            System.out.println(j);
                            liner = new View(getApplicationContext());
                            liner.setBackgroundColor(getResources().getColor(android.R.color.black));
                            liner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
                            ((LinearLayout) line).addView(liner);

                            JSONObject a_mentor = section_mentors.getJSONObject(j);
                            String[] mentor_fields = new String[]{"Username", "Grade", "Email"};
                            for (int k = 0; k < mentor_fields.length; k++) {
                                SpannableStringBuilder str = new SpannableStringBuilder(mentor_fields[k].replace('_', ' ') + ": " + a_mentor.getString(mentor_fields[k]));
                                int start = 0;
                                int end = mentor_fields[k].length();
                                str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                                TextView cloak = new TextView(getApplicationContext());
                                cloak.setText(str);
                                cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                ((LinearLayout) line).addView(cloak);
                            }
                        }

                        liner = new View(getApplicationContext());
                        liner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 50));
                        ((LinearLayout) line).addView(liner);

                        cloaker = new TextView(getApplicationContext());
                        cloaker.setText("Mentees");
                        cloaker.setTextSize(20);
                        cloaker.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        ((LinearLayout) line).addView(cloaker);

                        JSONArray section_mentees = a_section.getJSONArray("mentees");
                        for (int j = 0; j < section_mentees.length(); j++) {
                            System.out.println(j);
                            liner = new View(getApplicationContext());
                            liner.setBackgroundColor(getResources().getColor(android.R.color.black));
                            liner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
                            ((LinearLayout) line).addView(liner);

                            JSONObject a_mentee = section_mentees.getJSONObject(j);
                            String[] mentee_fields = new String[]{"Username", "Grade", "Email"};
                            for (int k = 0; k < mentee_fields.length; k++) {
                                SpannableStringBuilder str = new SpannableStringBuilder(mentee_fields[k].replace('_', ' ') + ": " + a_mentee.getString(mentee_fields[k]));
                                int start = 0;
                                int end = mentee_fields[k].length();
                                str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                                TextView cloak = new TextView(getApplicationContext());
                                cloak.setText(str);
                                cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                ((LinearLayout) line).addView(cloak);
                            }
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