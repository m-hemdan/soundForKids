package com.example.learnsound;

import androidx.appcompat.app.AppCompatActivity;


import android.animation.ObjectAnimator;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class color_activity extends AppCompatActivity {
    ArrayList<audioSelect> arrayListaudio=new ArrayList<audioSelect>();
    AudioManager audioManager;
    MediaPlayer md;
    ObjectAnimator scaleDown;
     public AudioManager.OnAudioFocusChangeListener afAudioFocusChangeListener=
             new AudioManager.OnAudioFocusChangeListener() {
                 @Override
                 public void onAudioFocusChange(int focusChange) {
                     switch (focusChange) {
                         case AudioManager.AUDIOFOCUS_GAIN:
                             md.start();
                             break;
                         case AudioManager.AUDIOFOCUS_LOSS:

                             releaseAudio();
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_activity);
        audioManager = (AudioManager) getSystemService(color_activity.AUDIO_SERVICE);
        ImageView blackImg=(ImageView)findViewById(R.id.blackbtn);
        ImageView blueImg=(ImageView)findViewById(R.id.bluebtn);
        ImageView greenImg=(ImageView)findViewById(R.id.greenbtn);
        ImageView indegoImg=(ImageView)findViewById(R.id.purbilbtn);
        ImageView orangeImg=(ImageView)findViewById(R.id.orangebtn);
        ImageView pinkImg=(ImageView)findViewById(R.id.pinkbtn);
        ImageView redImg=(ImageView)findViewById(R.id.redbtn);
        ImageView whiteImg=(ImageView)findViewById(R.id.whitebtn);
        ImageView yellowImg=(ImageView)findViewById(R.id.yellowbtn);
        ImageView darkblue=(ImageView)findViewById(R.id.dark_blue);
        ImageView gold=(ImageView)findViewById(R.id.gold);
        ImageView gray=(ImageView)findViewById(R.id.gray);

        arrayListaudio.add(new audioSelect(R.raw.black,blackImg));
        arrayListaudio.add(new audioSelect(R.raw.light_blue,blueImg));
        arrayListaudio.add(new audioSelect(R.raw.green,greenImg));
        arrayListaudio.add(new audioSelect(R.raw.purble,indegoImg));
        arrayListaudio.add(new audioSelect(R.raw.orange,orangeImg));
        arrayListaudio.add(new audioSelect(R.raw.pink,pinkImg));
        arrayListaudio.add(new audioSelect(R.raw.red,redImg));
        arrayListaudio.add(new audioSelect(R.raw.white,whiteImg));
        arrayListaudio.add(new audioSelect(R.raw.yellow,yellowImg));
        arrayListaudio.add(new audioSelect(R.raw.blue,darkblue));
        arrayListaudio.add(new audioSelect(R.raw.gold,gold));
        arrayListaudio.add(new audioSelect(R.raw.gray,gray));



            for (int i = 0; i < arrayListaudio.size(); i++) {
                   final audioSelect audSelect = arrayListaudio.get(i);
                   audSelect.getmImageView().setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           releaseAudio();
                           int result=audioManager.requestAudioFocus(afAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
                           if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                           md = MediaPlayer.create(color_activity.this, audSelect.getmAudio());
                           md.start();
                               scaleDown = ObjectAnimator.ofFloat(audSelect.getmImageView(), "rotation", 0, 360);
                               scaleDown.setDuration(1000);

                               startAnimation(audSelect.getmImageView());
                           md.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                               @Override
                               public void onCompletion(MediaPlayer mp) {
                                   releaseAudio();
                                   endAnimation(audSelect.getmImageView());
                               }
                           });
                       }
                   }
                   });
               }


    }
    public void startAnimation(View view) {
        scaleDown.start();
    }

    public void endAnimation(View view) {
        scaleDown.end();
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
           audioManager.abandonAudioFocus(afAudioFocusChangeListener);
        }
    }

}
