package com.example.learnsound;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.ArrayList;

public class transportActivity extends AppCompatActivity {
MediaPlayer md;
ObjectAnimator scaleDown;
AudioManager audioManager;
ObjectAnimator animX,animY;
AudioManager.OnAudioFocusChangeListener af=new AudioManager.OnAudioFocusChangeListener() {
    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange)
        {
            case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                md.start();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                releaseAudio();
                break;
        }
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);
        audioManager=(AudioManager)getSystemService(AUDIO_SERVICE);
        ImageView train=(ImageView)findViewById(R.id.train);
        ImageView boat=(ImageView)findViewById(R.id.boat);
        ImageView car=(ImageView)findViewById(R.id.car);
        ImageView plane=(ImageView)findViewById(R.id.plane);
        ImageView ambulance=(ImageView)findViewById(R.id.ambulance);
        ImageView police=(ImageView)findViewById(R.id.police);
        ImageView moto=(ImageView)findViewById(R.id.moto);
        ImageView bicycle=(ImageView)findViewById(R.id.bicycle);
        ImageView bus=(ImageView)findViewById(R.id.bus);
        ArrayList<audioSelect>transportList=new ArrayList<audioSelect>();
        transportList.add(new audioSelect(R.raw.train,train));
        transportList.add(new audioSelect(R.raw.boat,boat));
        transportList.add(new audioSelect(R.raw.car,car));
        transportList.add(new audioSelect(R.raw.plane,plane));
        transportList.add(new audioSelect(R.raw.ambulance,ambulance));
        transportList.add(new audioSelect(R.raw.police,police));
        transportList.add(new audioSelect(R.raw.moto,moto));
        transportList.add(new audioSelect(R.raw.bus,bus));
        transportList.add(new audioSelect(R.raw.bike,bicycle));
        final Animation rotat= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotateclock);
        final   Animation rotatAnti=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotateanticolock);
        for (int i=0;i<transportList.size();i++)
        {
            final audioSelect item=transportList.get(i);
            item.getmImageView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    releaseAudio();
                    int result=audioManager.requestAudioFocus(af,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
                    if (result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        md = MediaPlayer.create(transportActivity.this, item.getmAudio());
                        md.start();
                        item.getmImageView().startAnimation(rotat);
                        item.getmImageView().startAnimation(rotatAnti);
                         md.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                releaseAudio();

                            }
                        });
                    }
                }
            });
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (md!=null)
        {
            md.pause();
            if(isFinishing())
            {
                md.pause();
                md=null;
            }
        }
    }

    public void releaseAudio()
    {
        if (md !=null)
        {
            md.release();
            md=null;
            audioManager.abandonAudioFocus(af);
        }
    }
}
