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

public class fruitActivity extends AppCompatActivity {
    MediaPlayer md;
    ObjectAnimator anim;
ImageView apple,banana,lemon,strawberry,watermelon,mango,grapes,beries,orange,peach;
AudioManager audioManager;
AudioManager.OnAudioFocusChangeListener af=new AudioManager.OnAudioFocusChangeListener() {
    @Override
    public void onAudioFocusChange(int focusChange) {
        if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ||
                focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT)
        {
            releaseAudio();
        }
        else if (focusChange==AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
        {
            md.start();
        }
        else if(focusChange==AudioManager.AUDIOFOCUS_LOSS)
        {
            releaseAudio();
        }
    }
};
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);
        audioManager=(AudioManager) getSystemService(fruitActivity.AUDIO_SERVICE);
         apple=(ImageView)findViewById(R.id.apple);
         banana=(ImageView)findViewById(R.id.banana);
         lemon=(ImageView)findViewById(R.id.lemon);
         strawberry=(ImageView)findViewById(R.id.strawberry);
         watermelon=(ImageView)findViewById(R.id.watermelon);
         mango=(ImageView)findViewById(R.id.mango);
         grapes=(ImageView)findViewById(R.id.grapes);
         beries=(ImageView)findViewById(R.id.beries);
         orange=(ImageView)findViewById(R.id.orange);
         peach=(ImageView)findViewById(R.id.peach);
      ArrayList<audioSelect>fruit=new ArrayList<audioSelect>();
      fruit.add(new audioSelect(R.raw.apple,apple));
      fruit.add(new audioSelect(R.raw.banana,banana));
      fruit.add(new audioSelect(R.raw.lemon,lemon));
      fruit.add(new audioSelect(R.raw.strawberry,strawberry));
      fruit.add(new audioSelect(R.raw.watermelon,watermelon));
      fruit.add(new audioSelect(R.raw.mango,mango));
      fruit.add(new audioSelect(R.raw.grapes,grapes));
      fruit.add(new audioSelect(R.raw.bearry,beries));
      fruit.add(new audioSelect(R.raw.orangefruit,orange));
      fruit.add(new audioSelect(R.raw.peach,peach));

      for (int i=0;i<fruit.size();i++)
      {
       final audioSelect  selectFruit= fruit.get(i);
       selectFruit.getmImageView().setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               releaseAudio();
               int result=audioManager.requestAudioFocus(af,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
               if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                   md = MediaPlayer.create(fruitActivity.this, selectFruit.getmAudio());
                   md.start();
                   anim = ObjectAnimator.ofFloat(selectFruit.getmImageView(), "rotation", 0, 360);
                   anim.setDuration(2000);

                   anim.setRepeatMode(ObjectAnimator.RESTART);
                   startAnimation(selectFruit.getmImageView() );
                   md.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                       @Override
                       public void onCompletion(MediaPlayer mp) {
                           releaseAudio();
                           endAnimation(selectFruit.getmImageView());
                       }
                   });

               }
           }
       });
      }

    }

    public void startAnimation(View view) {
        anim.start();
    }

    public void endAnimation(View view) {
        anim.end();
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
            audioManager.abandonAudioFocus(af);
        }
    }
}
