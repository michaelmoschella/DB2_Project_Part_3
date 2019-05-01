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

public class studentDashboard extends AppCompatActivity {
    private Button btnMenteeViewSessions;
    private Button btnViewSessions;
    private Button btnViewSections;
    private Button btnSChangeProfile;
    private Button btnLogout;
    private RequestQueue Q;
    protected void onCreate(Bundle savedInstanceState) {
        the_global global = new the_global();
        Map<String, String> params = new HashMap<String, String>();
        System.out.println("BEFORE VOLLEY");
        System.out.println(global.active_id);
        params.put("active_ID", global.active_id.toString());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_dash);

        btnMenteeViewSessions = (Button) findViewById(R.id.s_dashboard12);
        btnMenteeViewSessions.setOnClickListener((v) -> {
            Intent i = new Intent(studentDashboard.this, menteeViewSessions.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });

        btnViewSessions = (Button) findViewById(R.id.s_dashboard9);
        btnViewSessions.setOnClickListener((v) -> {
            Intent i = new Intent(studentDashboard.this, studentViewSessions.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });

        btnViewSections = (Button) findViewById(R.id.s_dashboard6);
        btnViewSections.setOnClickListener((v) -> {
            Intent i = new Intent(studentDashboard.this, studentViewSections.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });
        btnSChangeProfile = (Button) findViewById(R.id.s_dashboard3);
        btnSChangeProfile.setOnClickListener((v) -> {
            Intent i = new Intent(studentDashboard.this, studentChangeProfile.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });
        btnLogout = (Button) findViewById(R.id.logout);
        btnLogout.setOnClickListener((v) -> {
            System.out.println("WHYYYYYYYYY");
            Intent i = new Intent(studentDashboard.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });

        String url = "http://10.0.2.2/phase3/php_stuff/php/student-dashboard.php";
        Q = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                try {
                    JSONObject the_response = new JSONObject(response);
                    String name = the_response.getString("name");
                    System.out.println(name);
                    String role = the_response.getString("role");
                    Integer grade = the_response.getInt("grade");
                    TextView grade_view = findViewById(R.id.grade_view);
                    grade_view.setText("Your grade is: " + grade.toString());
                    TextView user_view = findViewById(R.id.s_dashboard1);
                    user_view.setText("User: " + name);
                    TextView role_view = findViewById(R.id.s_dashboard2);
                    role_view.setText("Role: Student");

                    user_view = findViewById(R.id.s_dashboard4);
                    user_view.setText("User: " + name);
                    role_view = findViewById(R.id.s_dashboard5);
                    role_view.setText("Role: Student");

                    user_view = findViewById(R.id.s_dashboard7);
                    user_view.setText("User: " + name);

                    View line =  findViewById(R.id.s_dash);

                    if (role.equals("Mentor") || role.equals("Both")) {
                        View liner = new View(getApplicationContext());
                        liner.setBackgroundColor(getResources().getColor(android.R.color.black));
                        liner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,5));

                        ((LinearLayout) line).addView(liner);
                        TextView cloak = new TextView(getApplicationContext());
                        cloak.setText("User: " + name);
                        cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        cloak.setTextSize(30);
                        ((LinearLayout) line).addView(cloak);

                        cloak = new TextView(getApplicationContext());
                        cloak.setText("User: " + name);
                        cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        cloak.setTextSize(30);
                        ((LinearLayout) line).addView(cloak);

                        Button mentor_btn = new Button(getApplicationContext());
                        mentor_btn.setText("View Mentor");
                        mentor_btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        mentor_btn.setOnClickListener((v) -> {
                            Intent i = new Intent(studentDashboard.this, viewMentor.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        });
                        ((LinearLayout) line).addView(mentor_btn);

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
