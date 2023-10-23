package com.dicoding.bmstrans;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dicoding.bmstrans.MainActivity;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_TIMEOUT = 2000; // Waktu tampilan splash screen dalam milidetik

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Intent untuk berpindah ke activity utama
                Intent mainIntent = new Intent(SplashScreen.this,
                        Login.class);
                startActivity(mainIntent);
                finish(); // Menutup activity splash screen agar tidak bisa kembali
            }
        }, SPLASH_TIMEOUT);
    }
}
