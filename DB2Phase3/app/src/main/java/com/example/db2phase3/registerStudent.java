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

public class registerStudent extends AppCompatActivity {
    private Button btnSubmit;
    private Button btnMain;
    private RequestQueue Q;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_register);

        btnSubmit = (Button) findViewById(R.id.button1);
        btnSubmit.setOnClickListener((v)->{
            EditText edit_email = findViewById(R.id.EditText1);
            String email = edit_email.getText().toString();
            //System.out.println(email);

            EditText edit_p_email = findViewById(R.id.EditText2);
            String p_email = edit_p_email.getText().toString();
            //System.out.println(p_email);

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
            params.put("Student_Email", email);
            params.put("Students_Parent_Email", p_email);
            params.put("Student_Pass", password);
            params.put("Student_Confirm_Pass", confirm);
            params.put("s_role", "Both");
            params.put("Student_Name", name);
            params.put("Student_Phone_Number", phone);
            params.put("s_username", username);
            params.put("s_grade", "12");

            submit(params);
        });
    }
    private void submit(Map<String, String> params) {
        Map<String, String> the_params = params;
        String url = "http://10.0.2.2/phase3/php_stuff/php/register-student.php";
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
                        setContentView(R.layout.message);
                        TextView banner = findViewById(R.id.text1);
                        banner.setText("Congratulations " + the_response.getString("s_username") + ", you have successfully registered!");
                        btnMain = (Button) findViewById(R.id.button1);
                        btnMain.setOnClickListener((v)-> {
                            Intent i = new Intent(registerStudent.this, MainActivity.class);
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
}
