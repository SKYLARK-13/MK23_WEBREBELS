package com.example.navigation2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class EmailActivity extends AppCompatActivity {
private EditText mail,emailmessage;
private Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        btn1=findViewById(R.id.sendmail);
        mail=findViewById(R.id.dest);
        emailmessage=findViewById(R.id.emailbody);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link1="https://us-central1-fass1-7615b.cloudfunctions.net/sendEmail";
                link1=link1+"?dest=";
                link1=link1+mail.getText().toString();
                link1=link1+"&msg="+emailmessage.getText().toString().trim();
                RequestQueue queue= Volley.newRequestQueue(EmailActivity.this);
                StringRequest stringRequest=new StringRequest(Request.Method.GET, link1,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(EmailActivity.this,response,Toast.LENGTH_LONG).show();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EmailActivity.this,"error occured..." ,Toast.LENGTH_LONG).show();

                    }
                });
                queue.add(stringRequest);
            }
        });
    }
}
