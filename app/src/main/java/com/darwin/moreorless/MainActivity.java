package com.darwin.moreorless;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
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
        startService(new Intent(this, Sound.class).setAction("play"));

        btn_play = findViewById(R.id.btn_play);
        btn_shop = findViewById(R.id.btn_shop);

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
                finish();
            }
        });

        btn_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ShopActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - backPressedTime > 2000) {
            backPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "Click again to exit", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /*
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
  */

}