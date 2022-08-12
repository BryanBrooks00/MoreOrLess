package com.darwin.moreorless;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class FinalActivity extends AppCompatActivity {
    Random random;
    Button btn_replay;
    Button btn_menu;
    TextView new_record_tv;
    TextView money_tv;
    TextView comment_tv;
    MediaPlayer mp;
    MediaPlayer mp_main;
    String [] final_comments = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);


        mp_main = MediaPlayer.create(this, R.raw.background_music);
        mp_main.start();

        final_comments = new String[]{getString(R.string.a), getString(R.string.b),
                getString(R.string.c), getString(R.string.d),
                getString(R.string.e), getString(R.string.f)};
        btn_replay = findViewById(R.id.btn_replay);
        btn_menu = findViewById(R.id.btn_menu);
        new_record_tv = findViewById(R.id.new_record_tv);
        money_tv = findViewById(R.id.money_tv);
        comment_tv = findViewById(R.id.comment_tv);
        setMoney();
        checkScore();
        setComment();

        btn_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp_main.stop();
                startActivity(new Intent(FinalActivity.this, GameActivity.class));
                finish();
            }
        });

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp_main.stop();
                startActivity(new Intent(FinalActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    public void setMoney(){
        int oldMoney = Integer.parseInt(Preferences.getOldMoney(this));
        money_tv.setText(oldMoney+"");
        int money = Integer.parseInt(Preferences.getMoney(this));
            money_tv.setText(money+"");
            mp = MediaPlayer.create(getApplicationContext(), R.raw.coins);
            mp.start();
    }

    public void checkScore(){
        if (Integer.parseInt(Preferences.getRecord(this))
                > Integer.parseInt(Preferences.getOldRecord(this))){
            new_record_tv.setVisibility(View.VISIBLE);
        } else{
            new_record_tv.setVisibility(View.INVISIBLE);
        }
    }

    public void setComment(){
        random = new Random();
        int randInt = random.nextInt(final_comments.length);
        comment_tv.setText(final_comments[randInt] + "");
    }

    @Override
    public void onBackPressed() {
        mp_main.stop();
        startActivity(new Intent(FinalActivity.this, MainActivity.class));
    }
}

