package com.example.learnsound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class numberActivity extends AppCompatActivity {
    MediaPlayer md;
    AudioManager audioManager;
    LinearLayout numberLayout,first,second;
    ImageView imageView1;
    //image of number//
    int[]arrayofImg={R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.five,R.drawable.six,R.drawable.seven,R.drawable.eight,R.drawable.nine,R.drawable.ten};
   //audio of number//
    final int[]audioNumber={R.raw.one,R.raw.two,R.raw.three,R.raw.four,R.raw.five,R.raw.six,R.raw.seven,R.raw.eight,R.raw.nine,R.raw.ten};
   //random image show with count number //
    int [] showShap={R.drawable.dog,R.drawable.cat,R.drawable.monkey,R.drawable.apple,R.drawable.banana,R.drawable.green,R.drawable.yellow,R.drawable.orangefruit,R.drawable.watermelon,R.drawable.grapes};
      // focus change listener to control of audio
    public AudioManager.OnAudioFocusChangeListener afAudioFocus=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    md.start();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                   relaseAudio();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                   relaseAudio();

                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    relaseAudio();

                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        imageView1 =(ImageView)findViewById(R.id.number);
        numberLayout=(LinearLayout) findViewById(R.id.number_relative_Layout);

        audioManager=(AudioManager) getSystemService(numberActivity.AUDIO_SERVICE);
        //Linear layout container all number
        LinearLayout numberLinearLayout=(LinearLayout)findViewById(R.id.numberlayout);
        for (int i=0 ; i<arrayofImg.length ; i++)
        {
            ImageView selectImg=new ImageView(this);
            //set image to Image view//
            Drawable im=getResources().getDrawable(arrayofImg[i]);
            selectImg.setImageDrawable(im);
            //set padding//
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(0,200,1);
            selectImg.setLayoutParams(params);
            numberLinearLayout.addView(selectImg);
            final int index=i;

            selectImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    relaseAudio();
                    int request=audioManager.requestAudioFocus(afAudioFocus,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
                    if (request==AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                    {
                        md = MediaPlayer.create(numberActivity.this, audioNumber[index]);
                        md.start();
                        // show number of image //
                        showNumber(index);
                        md.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                relaseAudio();

                            }
                        });
                    }
                    else
                    {
                        Log.v("depracated ","audio manger is depracated");
                    }
                }
            });


        }
    }
    public void showNumber(int num)
    {   // show specific number//


        imageView1.setImageResource(arrayofImg[num]);

        numberLayout.removeAllViews();  //release Layout
       //choose random image //
        double randomInteger = Math.random()*10;
        int selectRandomNum=(int)randomInteger;
        Drawable im=getResources().getDrawable(showShap[selectRandomNum]);
        //set padding //
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(0,200,1);
        //num may be 0  it cause problem  so start with 1
         for(int i=1;i<=num+1;i++) {
             ImageView numIm = new ImageView(this);
             numIm.setImageDrawable(im);
             numIm.setLayoutParams(params);

            numberLayout.addView(numIm);
         }


    }
    public void relaseAudio()
    {
        if(md != null)
        {
            md.release();
            md=null;
            audioManager.abandonAudioFocus(afAudioFocus);
        }

    }
    public void goBack(View view) {
        Intent intent=new Intent(numberActivity.this,chose.class);
        startActivity(intent);
    }
}
