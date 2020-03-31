package com.example.learnsound;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class chose extends AppCompatActivity {
    MediaPlayer md;
    AudioManager am ;
    AudioManager.OnAudioFocusChangeListener afChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {


                case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                    Log.v("start","Gain_start_transinet");
                    md.start();
                   break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    md.pause();

                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    releaseAudio();

                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_chose);
        super.onCreate(savedInstanceState);

        am =(AudioManager) getSystemService(chose.AUDIO_SERVICE);
        ArrayList<ImageView>imageViews=new ArrayList<ImageView>();
        int result = am.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            md = MediaPlayer.create(this, R.raw.background);
            md.start();

        }
        ImageView letter=(ImageView)findViewById(R.id.letter);
        ImageView number=(ImageView)findViewById(R.id.num);
        ImageView animal=(ImageView)findViewById(R.id.animals);
        ImageView fruit=(ImageView)findViewById(R.id.fruit);
        ImageView color=(ImageView)findViewById(R.id.color);
        ImageView transport=(ImageView)findViewById(R.id.transport);
        imageViews.add(letter);
        imageViews.add(number);
        imageViews.add(animal);
        imageViews.add(fruit);
        imageViews.add(color);
        imageViews.add(transport);
        Animation zoomIn=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomin);
        Animation zoomOut=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomout);
      int lenArrayList=0;
        while (lenArrayList <imageViews.size())
        {
            ImageView ii=imageViews.get(lenArrayList);
            ii.startAnimation(zoomIn);
            ii.startAnimation(zoomOut);
            lenArrayList++;
        }

 }
   @Override
    protected void onResume()
    {
        super.onResume();
        Log.v("Resume","resume");
        if(md ==null)
        {
            md = MediaPlayer.create(this, R.raw.background);
            md.start();
        }
        else
            md.start();


    }
    @Override
    protected void onPause() {
        super.onPause();
        if (md != null) {
            md.pause();
            if (isFinishing()) {
                md.stop();
                md.release();

            }
        }

    }
    public void releaseAudio()
    {
        if(md !=null)
        {
            md.release();
            md=null;
        }
    }



    public void colorFun(View view)
    {
        Intent intent=new Intent(this,color_activity.class);
        startActivity(intent);
    }


    public void animalFun(View view) {
        Intent intent=new Intent(this,animal_Activity.class);
        startActivity(intent);
    }

    public void numberFun(View view) {
        Intent intent=new Intent(this,numberActivity.class);
        startActivity(intent);
    }

    public void fruitFun(View view) {
        Intent intent= new Intent(this,fruitActivity.class);
        startActivity(intent);
    }

    public void letterFun(View view) {
        Intent intent= new Intent(this,letter.class);
        startActivity(intent);
    }

    public void transportFun(View view) {
        Intent intent= new Intent(this,transportActivity.class);
        startActivity(intent);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void closeApp(View view) {
        Intent homeintent = new Intent(chose.this,welcomActivity.class);
        startActivity(homeintent);
        chose.this.finish();
    }
}
