package com.darwin.moreorless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mp_main;
    Button btn_play;
    Button btn_shop;
    ImageView sound_image;
    AudioManager amanager;
    long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amanager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mp_main = MediaPlayer.create(this, R.raw.background_music);
        mp_main.start();
        btn_play = findViewById(R.id.btn_play);
        btn_shop = findViewById(R.id.btn_shop);
        sound_image = findViewById(R.id.sound_image);

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp_main.stop();
                startActivity(new Intent(MainActivity.this, GameActivity.class));
            }
        });

        btn_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp_main.stop();
                startActivity(new Intent(MainActivity.this, ShopActivity.class));
            }
        });

        sound_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMute()){
                    unmute();
                }else{
                    mute();
                }
            }
        });
    }

    public void mute() {
        //mute audio
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            amanager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
        } else {
            amanager.setStreamMute(AudioManager.STREAM_MUSIC, true);
        }
    }

    public void unmute() {
        //unmute audio
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            amanager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0);
        } else {
            amanager.setStreamMute(AudioManager.STREAM_MUSIC, false);
        }
    }

    public boolean isMute() {
        amanager = (AudioManager) getSystemService(AUDIO_SERVICE);
        return amanager.isStreamMute(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - backPressedTime > 2000){
            Toast.makeText(this, "Click again to exit", Toast.LENGTH_SHORT).show();
        }else {
            mp_main.stop();
            super.onBackPressed();
        }
    }
}