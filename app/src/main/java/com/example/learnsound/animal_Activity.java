package com.example.learnsound;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class animal_Activity extends AppCompatActivity {
    MediaPlayer md;
    AudioManager am ;
    ObjectAnimator anim;


    AudioManager.OnAudioFocusChangeListener afChangeListener=new AudioManager.OnAudioFocusChangeListener() {
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
        setContentView(R.layout.activity_animal_);
        am =(AudioManager) getSystemService(animal_Activity.AUDIO_SERVICE);
        ArrayList<audioSelect>animalList=new ArrayList<audioSelect>();
        ImageView monkey=(ImageView)findViewById(R.id.monkey);
        ImageView hourse=(ImageView)findViewById(R.id.horse);
        ImageView duck=(ImageView)findViewById(R.id.duck);
        ImageView sheep=(ImageView)findViewById(R.id.sheep);
        ImageView dog=(ImageView)findViewById(R.id.dog);
        ImageView cat=(ImageView)findViewById(R.id.cat);
        ImageView elephant=(ImageView)findViewById(R.id.elephant);
        ImageView chicken=(ImageView)findViewById(R.id.chicken);
        ImageView hippo=(ImageView)findViewById(R.id.hippo);
        ImageView snake=(ImageView)findViewById(R.id.snake);
        ImageView lion=(ImageView)findViewById(R.id.lion);
        ImageView wolf=(ImageView)findViewById(R.id.wolf);

        animalList.add(new audioSelect(R.raw.monkey,monkey));
        animalList.add(new audioSelect(R.raw.hourse,hourse));
        animalList.add(new audioSelect(R.raw.duck,duck));
        animalList.add(new audioSelect(R.raw.sheep,sheep));
        animalList.add(new audioSelect(R.raw.dog,dog));
        animalList.add(new audioSelect(R.raw.cat,cat));
        animalList.add(new audioSelect(R.raw.elephant,elephant));
        animalList.add(new audioSelect(R.raw.checken,chicken));
        animalList.add(new audioSelect(R.raw.hippo,hippo));
        animalList.add(new audioSelect(R.raw.lion,lion));
        animalList.add(new audioSelect(R.raw.wolf,wolf));
        animalList.add(new audioSelect(R.raw.snack,snake));
        final Animation rotat=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotateclock);
        final   Animation rotatAnti=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotateanticolock);
      for (int i=0;i<animalList.size();i++)
     {
         final audioSelect currentView=animalList.get(i);
         currentView.getmImageView().setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                    releaseAudio();

                 int result = am.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                 if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                 {
                     md = MediaPlayer.create(animal_Activity.this, currentView.getmAudio());
                     md.start();
             currentView.getmImageView().startAnimation(rotat);
                     currentView.getmImageView().startAnimation(rotatAnti);
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

            am.abandonAudioFocus(afChangeListener);
        }
    }

}
