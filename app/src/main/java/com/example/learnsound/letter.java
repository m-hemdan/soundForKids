package com.example.learnsound;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class letter extends AppCompatActivity {
  MediaPlayer md;
  TextView showText;
  AudioManager audioManager;
  AudioManager.OnAudioFocusChangeListener af=new AudioManager.OnAudioFocusChangeListener() {
      @Override
      public void onAudioFocusChange(int focusChange) {
          switch (focusChange)
          {
              case AudioManager.AUDIOFOCUS_LOSS:
                  releaseAudio();
                  break;
              case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                  releaseAudio();
                  break;
              case AudioManager.AUDIOFOCUS_GAIN:
                  md.start();
              case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                  md.start();
                  break;
          }
      }
  };
    int [] imaLetter={R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e,R.drawable.f,R.drawable.g,R.drawable.h,R.drawable.i,R.drawable.j
        ,R.drawable.k,R.drawable.l,R.drawable.m,R.drawable.n,R.drawable.o,R.drawable.p,R.drawable.q,R.drawable.r,R.drawable.s,R.drawable.t
        ,R.drawable.u,R.drawable.v,R.drawable.w,R.drawable.x,R.drawable.y,R.drawable.z};
    int [] audioLetter={R.raw.a,R.raw.b,R.raw.c,R.raw.d,R.raw.e,R.raw.f,R.raw.g,R.raw.h,R.raw.i,R.raw.j
            ,R.raw.k,R.raw.l,R.raw.m,R.raw.n,R.raw.o,R.raw.p,R.raw.q,R.raw.r,R.raw.s,R.raw.t
            ,R.raw.u,R.raw.v,R.raw.w,R.raw.x,R.raw.y,R.raw.z};
    int[] showSpecificImage={R.drawable.apple,R.drawable.banana,R.drawable.cat,R.drawable.dog,R.drawable.elephant,R.drawable.fish,R.drawable.grapes,R.drawable.horse,R.drawable.icecream,R.drawable.juice
            ,R.drawable.kite,R.drawable.lemon,R.drawable.monkey,R.drawable.nose,R.drawable.orangefruit,R.drawable.peach,R.drawable.queen,R.drawable.rabbit,R.drawable.sheep,R.drawable.train
            ,R.drawable.umbrella,R.drawable.vase,R.drawable.watermelon,R.drawable.xylophone,R.drawable.yoyo,R.drawable.zebra};
String[] textShow={"apple","banana","cat","dog","elephant","fish","grapes","horse","ice cream","juice"
        ,"kite","lemon","monkey","nose","orange","peach","queen","rabbit","sheep","train"
        ,"umbrella","vase","water melon","xylophone","yoyo","zebra"};
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_letter);
        audioManager=(AudioManager)getSystemService(letter.AUDIO_SERVICE);
        LinearLayout LineOneActivity=(LinearLayout)findViewById(R.id.line_one);
        LinearLayout LineTwoActivity=(LinearLayout)findViewById(R.id.line_two);
        LinearLayout LineThreeActivity=(LinearLayout)findViewById(R.id.line_three);
        showText=(TextView)findViewById(R.id.describtion);

        ArrayList<audioSelect> letterArrayList=new ArrayList<audioSelect>();
        final ImageView show=(ImageView)findViewById(R.id.showImg);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1);
        for(int i=0;i<imaLetter.length;i++)
        {
            final int index=i;
            ImageView imageView=new ImageView(this);
            Drawable drawable=getResources().getDrawable(imaLetter[i]);
            imageView.setImageDrawable(drawable);
            imageView.setLayoutParams(params);

            if(i <8) {
                LineOneActivity.addView(imageView);
            }
            else if(i>=8 && i<16)
            {
                LineTwoActivity.addView(imageView);
            }
            else if( i>=16 && i<26)
            {
                LineThreeActivity.addView(imageView);
            }
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    releaseAudio();
                    int result=audioManager.requestAudioFocus(af,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
                    if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        Drawable drawable1=getResources().getDrawable(showSpecificImage[index]);
                        show.setImageDrawable(drawable1);
                        showText.setText(textShow[index]);
                        md = MediaPlayer.create(letter.this, audioLetter[index]);
                        md.start();
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
        if(md !=null) {
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
