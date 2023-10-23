package com.dicoding.bmstrans;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends AppCompatActivity {

    EditText etUsernameRegister, etPasswordRegister;
    Button btnLogin;
    TextView registerText;
//    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsernameRegister = findViewById(R.id.etUsernameRegister);
        etPasswordRegister = findViewById(R.id.etPasswordRegister);
        btnLogin = findViewById(R.id.btnLogin);
//        progress = findViewById(R.id.progress);
        registerText = findViewById(R.id.registerText);

        registerText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(
                                getApplicationContext(),
                                SignUp.class
                        );
                        startActivity(intent);
                        finish();
                    }
                }
        );

        btnLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String username, password;
                        username = String.valueOf(etUsernameRegister.getText());
                        password = String.valueOf(etPasswordRegister.getText());

                        if (
                                !username.equals("") && !password.equals("")
                        ) {

                            //Start ProgressBar first (Set visibility VISIBLE)
//                            progress.setVisibility(View.VISIBLE);

                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //Starting Write and Read data with URL
                                    //Creating array for parameters
                                    String[] field = new String[2];
                                    field[0] = "username";
                                    field[1] = "password";

                                    //Creating array for data
                                    String[] data = new String[2];
                                    data[0] = username;
                                    data[1] = password;

                                    PutData putData = new PutData(
                                            "http://192.168.1.12/bmstrans/login.php",
                                            "POST",
                                            field,
                                            data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
//                                            progress.setVisibility(View.GONE);
                                            String result = putData.getResult();

                                            if (
                                                    result.equals("Login Success")
                                            ) {
                                                Toast.makeText(getApplicationContext(),
                                                        result,
                                                        Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(),
                                                        result,
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                    //End Write and Read data with URL
                                }
                            });
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "All fields required! ", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );

    }
}