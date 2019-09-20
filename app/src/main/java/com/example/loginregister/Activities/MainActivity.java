package com.example.loginregister.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.loginregister.R;

public class MainActivity extends AppCompatActivity {
    ImageView im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        im=findViewById(R.id.im);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.effect);
        im.startAnimation(animation);
        final Intent i=new Intent(this,LoginPage.class);
        Thread timer=new Thread() {
            public void run () {
                try {
                    sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
}
