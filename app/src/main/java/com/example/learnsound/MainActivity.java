package com.example.learnsound;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFormat(PixelFormat.UNKNOWN);
         VideoView spalshVideo=(VideoView)findViewById(R.id.splash_video);
        MediaController controller = new MediaController(this);
        controller.setAnchorView(spalshVideo);
        controller.setMediaPlayer(spalshVideo);
        spalshVideo.setMediaController(controller);
        spalshVideo.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.splash));
        spalshVideo.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this, welcomActivity.class);
                startActivity(intent);
            }
        }, 3000);

    }


}
//ca-app-pub-8614841521637670~7696516567
