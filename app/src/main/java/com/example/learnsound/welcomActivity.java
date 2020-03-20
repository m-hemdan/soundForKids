package com.example.learnsound;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class welcomActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
         ImageView welcome=(ImageView)findViewById(R.id.open);
        Animation rotateClock= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.infrotateclock);
        Animation rotateAntiClock= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.infrotateanticlock);
        welcome.startAnimation(rotateClock);
        welcome.startAnimation(rotateAntiClock);


    }

    public void goToLearn(View view) {
        Intent publicLearnIntent=new Intent(this,chose.class);
        startActivity(publicLearnIntent);
    }
}
