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

public class ParentViewSessions extends AppCompatActivity {
    private Button btnLogout;
    private RequestQueue Q;
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("VIEW SESSIONS!!!!!!!!!!");
        the_global global = new the_global();
        Map<String, String> params = new HashMap<String, String>();
        System.out.println("BEFORE VOLLEY");
        System.out.println(global.active_id);
        params.put("active_ID", global.active_id.toString());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_view_sessions);

        btnLogout = (Button) findViewById(R.id.the_logout);
        btnLogout.setOnClickListener((v) -> {
            System.out.println("WHYYYYYYYYY");
            Intent i = new Intent(ParentViewSessions.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });

        String url = "http://10.0.2.2/phase3/php_stuff/php/parent-view-sessions.php";
        Q = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                try {
                    JSONObject the_response = new JSONObject(response);
                    JSONArray section_objs = the_response.getJSONArray("sections");
                    System.out.println(section_objs.length());
                    View line =  findViewById(R.id.p_sessions);
                    System.out.println("HERE1");
                    System.out.println(section_objs.length());
                    for (int i = 0; i < section_objs.length(); i++){
                        View liner = new View(getApplicationContext());
                        liner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100));
                        ((LinearLayout) line).addView(liner);

                        liner = new View(getApplicationContext());
                        liner.setBackgroundColor(getResources().getColor(android.R.color.black));
                        liner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,10));
                        ((LinearLayout) line).addView(liner);
                        System.out.println("HERE2");
                        JSONObject a_section = section_objs.getJSONObject(i);
                        String[] fields = new String[]{ "Course_Title", "Section_Name"};
                        for (int j = 0; j < fields.length; j++){
                            SpannableStringBuilder str = new SpannableStringBuilder(fields[j].replace('_', ' ') + ": " + a_section.getString(fields[j]));
                            int start = 0;
                            int end = fields[j].length();
                            str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                            TextView cloak = new TextView(getApplicationContext());
                            cloak.setText(str);
                            System.out.println("HERE3");
                            cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            if (j == 0) {
                                cloak.setTextSize(30);
                            } else {
                                cloak.setTextSize(20);
                            }
                            ((LinearLayout) line).addView(cloak);
                        }
                        JSONArray session_objs = a_section.getJSONArray("Sessions");
                        for (int j = 0; j < session_objs.length(); j++) {
                            System.out.println(j);
                            liner = new View(getApplicationContext());
                            liner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100));
                            ((LinearLayout) line).addView(liner);
                            System.out.println("HERE4");
                            liner = new View(getApplicationContext());
                            liner.setBackgroundColor(getResources().getColor(android.R.color.black));
                            liner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1));
                            ((LinearLayout) line).addView(liner);

                            JSONObject a_session = session_objs.getJSONObject(j);
                            fields = new String[]{ "Session_Name", "Announcement", "Date", "Start_Time", "End_Time", "Mentor_Count", "Mentee_Count"};
                            for (int k = 0; k < fields.length; k++) {
                                System.out.println(fields[k]);
                                SpannableStringBuilder str = new SpannableStringBuilder(fields[k].replace('_', ' ') + ": " + a_session.getString(fields[k]));
                                int start = 0;
                                int end = fields[k].length();
                                str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                                TextView cloak = new TextView(getApplicationContext());
                                cloak.setText(str);
                                cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                if (k == 0) {
                                    cloak.setTextSize(20);
                                } else {
                                    cloak.setTextSize(15);
                                }
                                ((LinearLayout) line).addView(cloak);
                            }
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
