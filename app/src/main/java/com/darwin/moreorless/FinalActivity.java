package com.darwin.moreorless;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.Random;

public class FinalActivity extends AppCompatActivity {
    Random random;
    Button btn_replay;
    Button btn_menu;
    TextView new_record_tv;
    TextView money_tv;
    TextView comment_tv;
    MediaPlayer mp;
    String [] final_comments = {};
    final static String TAG = "LOG";
    final static String TEST_AD_ID = "ca-app-pub-3940256099942544/1033173712";
    final static String AD_ID = "ca-app-pub-2382402581294867/9240277497";
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        loadAd();

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
                startActivity(new Intent(FinalActivity.this, GameActivity.class));
                finish();
            }
        });

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        startActivity(new Intent(FinalActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        interstitialAd = null;
    }

    public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                this,
                AD_ID,
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        FinalActivity.this.interstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                        showAd();
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        FinalActivity.this.interstitialAd = null;
                                        Log.d("TAG", "The ad was dismissed.");
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        FinalActivity.this.interstitialAd = null;
                                        Log.d("TAG", "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        Log.d("TAG", "The ad was shown.");
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        interstitialAd = null;
                    }
                });
    }

    private void showAd() {
        if (interstitialAd != null) {
            interstitialAd.show(this);
        } else {
            Log.i(TAG, "showAd error");
        }
    }
}

