package com.darwin.moreorless;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    ImageView life5_image;
    ImageView life4_image;
    ImageView life3_image;
    ImageView life2_image;
    ImageView life1_image;
    TextView score_tv;
    Button btn_more;
    Button btn_less;
    Button btn_back;
    TextView num_tv;
    Random rand;
    int max = 99;
    int min = 1;
    int score;
    int number;
    int lives;
    int prevNum;
    final static String TAG = "LOG";
    MediaPlayer mp;
    MediaPlayer mp_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        life5_image = findViewById(R.id.life5_image);
        life4_image = findViewById(R.id.life4_image);
        life3_image = findViewById(R.id.life3_image);
        life2_image = findViewById(R.id.life2_image);
        life1_image = findViewById(R.id.life1_image);
        score_tv = findViewById(R.id.score_tv);
        btn_more = findViewById(R.id.btn_more);
        btn_less = findViewById(R.id.btn_less);
        btn_back = findViewById(R.id.btn_back);
        num_tv = findViewById(R.id.num_tv);
        score = Integer.parseInt(score_tv.getText().toString());

        mp_main = MediaPlayer.create(this, R.raw.background_music);
        mp_main.start();

        setFirstNumber();
        //number = randomNumber();
        if(Preferences.getIsBought(this).equals("yes")){
            lives = 5;
        }else {
            lives = 3;
        }
        checkLives();

        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prevNum = number;
                number = randomNumber();
                Log.i(TAG, "number = " + number + " , prevNum = " + prevNum);
                if (number > prevNum) {
                    Log.i(TAG,  number + " > " + prevNum);
                    setResult("correct");
                } else if(number < prevNum){
                    Log.i(TAG,  number + " < " + prevNum);
                    setResult("wrong");
                }
            }
        });

        btn_less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prevNum = number;
                number = randomNumber();
                Log.i(TAG, "number = " + number + " , prevNum = " + prevNum);
                if (number < prevNum) {
                    Log.i(TAG,  prevNum + " < " + number);
                    setResult("correct");
                } else if (number > prevNum){
                    Log.i(TAG,  prevNum + " > " + number);
                    setResult("wrong");
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GameActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    public int randomNumber(){
        rand = new Random();
        int randomNumber = rand.nextInt((max - min) + 1) + min;
        if (randomNumber == Integer.parseInt(num_tv.getText().toString())){
            randomNumber = randomNumber - 1;
        }
        return randomNumber;
    }

    public void setFirstNumber(){
        number = randomNumber();
        num_tv.setText(number+"");
    }

    public void setResult(String result){
        num_tv.setTextColor(Color.BLACK);
        Log.i(TAG, "number = " + number + " , prevNum = " + prevNum);
        if(result.equals("correct")){
            CountDownTimer timer = new CountDownTimer(1000, 1000) {
                @Override
                public void onTick(long l) {
                    num_tv.setTextColor(Color.GREEN);
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.correct);
                    mp.start();
                }
                @Override
                public void onFinish() {
                    num_tv.setTextColor(Color.BLACK);
                }
            }.start();
            score++;
            score_tv.setText(score+"");
        }else if(result.equals("wrong")){
            lives-=1;
            CountDownTimer timer = new CountDownTimer(1000, 1000) {
                @Override
                public void onTick(long l) {
                    num_tv.setTextColor(Color.RED);
                    mp = MediaPlayer.create(getApplicationContext(), R.raw.wrong);
                    mp.start();
                }

                @Override
                public void onFinish() {
                    num_tv.setTextColor(Color.BLACK);
                }
            }.start();
        }
        checkLives();
        num_tv.setText(number+"");
    }

    public void checkLives() {
        if (lives <= 0) {
            gameOver();
        } else if (lives == 1) {
            life1_image.setVisibility(View.VISIBLE);
            life2_image.setVisibility(View.INVISIBLE);
            life3_image.setVisibility(View.INVISIBLE);
            life4_image.setVisibility(View.INVISIBLE);
            life5_image.setVisibility(View.INVISIBLE);
        } else if (lives == 2) {
            life1_image.setVisibility(View.VISIBLE);
            life2_image.setVisibility(View.VISIBLE);
            life3_image.setVisibility(View.INVISIBLE);
            life4_image.setVisibility(View.INVISIBLE);
            life5_image.setVisibility(View.INVISIBLE);
        } else if (lives == 3) {
            life1_image.setVisibility(View.VISIBLE);
            life2_image.setVisibility(View.VISIBLE);
            life3_image.setVisibility(View.VISIBLE);
            life4_image.setVisibility(View.INVISIBLE);
            life5_image.setVisibility(View.INVISIBLE);
        } else if (lives == 4) {
            life1_image.setVisibility(View.VISIBLE);
            life2_image.setVisibility(View.VISIBLE);
            life3_image.setVisibility(View.VISIBLE);
            life4_image.setVisibility(View.VISIBLE);
            life5_image.setVisibility(View.INVISIBLE);
        } else if (lives == 5) {
            life1_image.setVisibility(View.VISIBLE);
            life2_image.setVisibility(View.VISIBLE);
            life3_image.setVisibility(View.VISIBLE);
            life4_image.setVisibility(View.VISIBLE);
            life5_image.setVisibility(View.VISIBLE);
        }
    }

    public void gameOver(){
        btn_more.setClickable(false);
        btn_more.setEnabled(false);
        btn_less.setClickable(false);
        btn_less.setEnabled(false);
        num_tv.setTextColor(Color.RED);
        mp = MediaPlayer.create(this, R.raw.game_over);
        mp.start();
        savePrefs();
        CountDownTimer timer = new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                mp_main.stop();
                startActivity(new Intent(GameActivity.this, FinalActivity.class));
                finish();
            }
        }.start();
    }

    public void savePrefs(){
        int coeffecient = Integer.parseInt(Preferences.getCoefficient(this));
        Preferences.setOldMoney(this, Preferences.getMoney(this));
        Preferences.setOldRecord(this, Preferences.getRecord(this));
        Preferences.setMoney(this, String.valueOf((score*coeffecient)
                + Integer.parseInt(Preferences.getOldMoney(this))));
        if (score > Integer.parseInt(Preferences.getOldRecord(this))) {
            Preferences.setRecord(this, String.valueOf(score));
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GameActivity.this, MainActivity.class));
    }
}