package com.example.db2phase3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.LinearLayout;

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

public class parentDashboard extends AppCompatActivity {

    private RequestQueue Q;
    private Button btnChangeProfile;
    private Button btnViewSections;
    private Button btnLogout;


    protected void onCreate(Bundle savedInstanceState) {


        the_global global = new the_global();
        Map<String, String> params = new HashMap<String, String>();
        System.out.println("BEFORE VOLLEY");
        System.out.println(global.active_id);
        params.put("active_id", global.active_id.toString());


        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_dash);


        btnLogout = (Button) findViewById(R.id.d_logout);
        btnLogout.setOnClickListener((v)->{
            Intent i = new Intent(parentDashboard.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        });

        btnChangeProfile = (Button) findViewById(R.id.p_dashboard3);
        btnChangeProfile.setOnClickListener((v)->{
            System.out.println(" Change Parent Profile View");
            Intent i = new Intent(this, ChangeParentProfile.class);
            startActivity(i);
        });
        btnViewSections = (Button) findViewById(R.id.p_dashboard9);
        btnViewSections.setOnClickListener((v)->{
            System.out.println(" View Sections");
            Intent i = new Intent(this, viewSectionsParent.class);
            startActivity(i);
        });
        String url = "http://10.0.2.2/phase3/php_stuff/php/parent-dashboard.php";
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
                    JSONArray child_objs = the_response.getJSONArray("children");
                    View line =  findViewById(R.id.p_dash);
                    for (int i = 0; i < child_objs.length(); i++){
                        View liner = new View(getApplicationContext());
                        liner.setBackgroundColor(getResources().getColor(android.R.color.black));
                        liner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,2));
                        ((LinearLayout) line).addView(liner);
                        JSONObject a_child = child_objs.getJSONObject(i);
                        String[] fields = new String[]{ "name","role"};
                        for (int j = 0; j < 2; j++){
                            TextView cloak = new TextView(getApplicationContext());
                            cloak.setText(a_child.getString(fields[j]));
                            cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            cloak.setTextSize(30);
                            ((LinearLayout) line).addView(cloak);
                        j++;

                            TextView zz = new TextView(getApplicationContext());
                            zz.setText(a_child.getString(fields[j]));
                            zz.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            zz.setTextSize(30);
                            ((LinearLayout) line).addView(zz);

                            Button yy = new Button(getApplicationContext());
                            yy.setText("Change Child Profile");
                            yy.setOnClickListener((v)->{
                                System.out.println(" Change Child Profile");
                                Intent d = new Intent(getApplicationContext(), ChangeChildProfile.class);
                                try {
                                    global.child_id = a_child.getInt("uid");
                                } catch (JSONException e) {
                                    System.out.println("bad");
                                }


                                startActivity(d);


                            });


                            yy.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                            ((LinearLayout) line).addView(yy);
                        }
                        System.out.println("**********CHILD*************");
                        System.out.println(a_child.getString("name"));
                        System.out.println(a_child.getString("role"));
                        System.out.println(a_child.getString("uid"));
                    }
                    /*LinearLayout linearLayout = (LinearLayout)findViewById(R.id.info);
                    TextView valueTV = new TextView(this);
                    valueTV.setText("hallo hallo");
                    valueTV.setId(5);
                    valueTV.setLayoutParams(new LayoutParams(
                            LayoutParams.FILL_PARENT,
                            LayoutParams.WRAP_CONTENT));*/


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
