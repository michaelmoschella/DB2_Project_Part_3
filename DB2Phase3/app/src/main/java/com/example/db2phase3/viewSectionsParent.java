package com.example.db2phase3;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

public class viewSectionsParent extends AppCompatActivity {


    private RequestQueue Q;
    protected void onCreate(Bundle savedInstanceState) {
        the_global global = new the_global();
        Map<String, String> params = new HashMap<String, String>();
        System.out.println("BEFORE VOLLEY");
        System.out.println(global.active_id);
        params.put("active_id", global.active_id.toString());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_dash);

        String url = "http://10.0.2.2/phase3/php_stuff/php/parent-dashboard.php";
        Q = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                try {

                    TextView hat = new TextView(getApplicationContext());
                    JSONObject the_response = new JSONObject(response);
                    String name = the_response.getString("name");
                    System.out.println(name);
                    String role = the_response.getString("role");
                    JSONArray child_objs = the_response.getJSONArray("children");
                    for (int i = 0; i < child_objs.length(); i++){
                        JSONObject a_child = child_objs.getJSONObject(i);
                        String[] fields = new String[]{ "name","role"};
                        View line =  findViewById(R.id.linlayout);



                        //Context context = this.;

                        hat.setText("29 bottles of beer on the wall,");


                        hat.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                        ((LinearLayout) line).addView(hat);


                        TextView cloak = new TextView(getApplicationContext());
                        cloak.setText("29 bottles of beer,");



                        cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                        ((LinearLayout) line).addView(cloak);

                        /*for (int j = 0; j < 2; j++){
                            TextView cloak = new TextView(this);
                            cloak.setText(a_child.GetString);

getApplicationContext();

                            cloak.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                            ((LinearLayout) line).addView(cloak);

                        }*/
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
