package com.dicoding.bmstrans;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignUp extends AppCompatActivity {

    EditText etFullnameRegister, etUsernameRegister, etEmailRegister, etPasswordRegister;
    Button btnRegister;
    TextView loginText;
//    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etFullnameRegister = findViewById(R.id.etFullnameRegister);
        etUsernameRegister = findViewById(R.id.etUsernameRegister);
        etEmailRegister = findViewById(R.id.etEmailRegister);
        etPasswordRegister = findViewById(R.id.etPasswordRegister);
        btnRegister = findViewById(R.id.btnRegister);
        loginText = findViewById(R.id.loginText);
//        progress = findViewById(R.id.progress);


        loginText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(
                                getApplicationContext(),
                                Login.class
                        );
                        startActivity(intent);
                        finish();
                    }
                }
        );

        btnRegister.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String fullname, username, email, password;
                        fullname = String.valueOf(etFullnameRegister.getText());
                        username = String.valueOf(etUsernameRegister.getText());
                        email = String.valueOf(etEmailRegister.getText());
                        password = String.valueOf(etPasswordRegister.getText());

                        if (
                                !fullname.equals("") && !username.equals("") && !email.equals("") && !password.equals("")
                        ) {

                            //Start ProgressBar first (Set visibility VISIBLE)
//                            progress.setVisibility(View.VISIBLE);

                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //Starting Write and Read data with URL
                                    //Creating array for parameters
                                    String[] field = new String[4];
                                    field[0] = "fullname";
                                    field[1] = "username";
                                    field[2] = "email";
                                    field[3] = "password";

                                    //Creating array for data
                                    String[] data = new String[4];
                                    data[0] = fullname;
                                    data[1] = username;
                                    data[2] = email;
                                    data[3] = password;

                                    PutData putData = new PutData(
                                            "http://192.168.1.12/bmstrans/signup.php",
                                            "POST",
                                            field,
                                            data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
//                                            progress.setVisibility(View.GONE);
                                            String result = putData.getResult();

                                            if (
                                                    result.equals("Sign Up Success")
                                            ) {
                                                Toast.makeText(getApplicationContext(),
                                                        result,
                                                        Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), Login.class);
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