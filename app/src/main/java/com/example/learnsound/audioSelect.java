package com.example.learnsound;

import android.widget.ImageView;

public class audioSelect {

    int mAudio;
    ImageView mImageView;
    int mImg;

    public audioSelect( int audio, ImageView im) {
        super();

        mAudio=audio;
        mImageView=im;
    }

    public int getmAudio()
    {
        return mAudio;
    }
    public ImageView getmImageView()
    {
        return mImageView;
    }

    public int getmImg() {
        return mImg;
    }
}
