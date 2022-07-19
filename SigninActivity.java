package com.example.project_myvntour.ActivityMaintain;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_myvntour.Database.SelectAll;
import com.example.project_myvntour.R;

public class SigninActivity extends AppCompatActivity {
    private EditText etMail;
    private EditText etUsername;
    private EditText etPassword;
    private Button btLogin;
    private LinearLayout ll1;
    private TextView textDangKi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        etMail = (EditText) findViewById(R.id.etMail);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btLogin = (Button) findViewById(R.id.btLogin);
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        textDangKi = (TextView) findViewById(R.id.textDangKi);
        SelectAll selectAll = new SelectAll(this);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = etMail.getText().toString();
                String u = etUsername.getText().toString();
                String p = etPassword.getText().toString();
                if(e.isEmpty()){
                    etMail.setError("Email is empty");
                }else if(u.isEmpty()){
                    etUsername.setError("Username is empty");
                }else if(p.isEmpty()){
                    etPassword.setError("Password is empty");
                } else if(selectAll.CheckUser(u)==true){
                    Toast.makeText(SigninActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                } else {
                    if(selectAll.Signin(e,u,p)){
                        Toast.makeText(SigninActivity.this, "successfully", Toast.LENGTH_SHORT).show();
                        etMail.setText("");
                        etUsername.setText("");
                        etPassword.setText("");
                    }else {
                        Toast.makeText(SigninActivity.this, "fail", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        textDangKi.setOnClickListener(v ->{
            this.onBackPressed();
        });

    }
}