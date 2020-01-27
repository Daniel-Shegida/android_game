package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


public class GameLevelActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout test, blackScreen;
    LinearLayout.LayoutParams Params;
    Button storyBnt, loopBtn, simpBtn, deatBtn;
    ScrollView storyList;
    TextView btnNew;
    String[] storyArr, loopArr, simpArr, deathArr;
    Animation animSet, btnAnim;
    Handler handler;
    int i;
    int intLoop = -1;
    int intSimp = -1;
    int intDeath = -1;
    int TIME_SCIP = 3000;
    final int FINAL_SCIP_DUR = 4500;
    boolean wasLoop = false;
    boolean wasSimp = false;
    boolean DeathToken = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2);
        test = findViewById(R.id.test);
        storyList = findViewById(R.id.story1);
        blackScreen = findViewById(R.id.blackScreen);
        storyBnt = findViewById(R.id.storyBtn);
        loopBtn = findViewById(R.id.loopBtn);
        simpBtn = findViewById(R.id.simpBtn);
        deatBtn = findViewById(R.id.deathBtn);
        animSet = AnimationUtils.loadAnimation(this, R.anim.fast_appear);
        btnAnim = AnimationUtils.loadAnimation(this, R.anim.butn);
        Params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        storyArr = getResources().getStringArray(R.array.storyArrayRu);
        loopArr = getResources().getStringArray(R.array.choiceLoopRu);
        deathArr = getResources().getStringArray(R.array.deathArrRu);
        handler = new Handler();
        i = 0;
        loopBtn.setOnClickListener(this);
        storyBnt.setOnClickListener(this);
        simpBtn.setOnClickListener(this);
        deatBtn.setOnClickListener(this);
        readScene();
        showBtnSlw();

    }

    public void readScene() {

        while (!storyArr[i].equals("endOfScene")) {
            InitNewText(storyArr, i);
            AddNewText();
            i++;
        }
        i++;
        sendScroll();
    }

    public void showBtnSlw() {

        handler.postDelayed(new Runnable() {
            public void run() {
                RevealAndUpdateBrn();
            }
        }, TIME_SCIP);
    }

    public void showBtntech() {

        handler.postDelayed(new Runnable() {
            public void run() {
                storyBnt.setVisibility(View.VISIBLE);
                storyBnt.startAnimation(btnAnim);
                if (wasLoop == true) {
                    loopBtn.setVisibility(View.VISIBLE);
                    loopBtn.startAnimation(btnAnim);
                }
                if (wasSimp == true) {
                    simpBtn.setVisibility(View.VISIBLE);
                    simpBtn.startAnimation(btnAnim);
                }
                if (DeathToken == true) {
                    simpBtn.setVisibility(View.VISIBLE);
                    simpBtn.startAnimation(btnAnim);
                }
            }
        }, TIME_SCIP);
    }

    public void clearTokens() {
        wasSimp = false;
        wasLoop = false;
        DeathToken = false;
    }


    @Override
    public void onClick(View v) {
        hideBtn();
        switch (v.getId()) {
            case R.id.storyBtn:

                if (storyArr[i].equals("doFinalFlip")) {
                    doFinalFlip();
                } else {
                    clearTokens();
                    readScene();
                    checkChoiseLoop();
                    checkChoiseSimp();
                    checkDeath();
                    showBtnSlw();

                }
                break;

            case R.id.loopBtn:

                InitNewText(loopArr, intLoop);
                AddNewText();
                sendScroll();
                showBtntech();
                break;

            case R.id.simpBtn:

                wasSimp = false;
                simpBtn.setVisibility(View.GONE);
                InitNewText(simpArr, intSimp);
                AddNewText();
                sendScroll();
                showBtntech();
                break;

            case R.id.deathBtn:

                clearTokens();
                deatBtn.setVisibility(View.GONE);
                InitNewText(deathArr, intDeath);
                storyBnt.setText(getString(R.string.finakFrase));
                storyArr[i] = "doFinalFlip";
                AddNewText();
                sendScroll();
                showBtntech();
                break;
        }


    }

    public void doFinalFlip() {
        handler.postDelayed(new Runnable() {
            public void run() {
                returnToMainInit();
            }
        }, FINAL_SCIP_DUR);
        blackScreen.startAnimation(animSet);
    }

    public void returnToMainInit() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void hideBtn() {
        loopBtn.setVisibility(View.GONE);
        simpBtn.setVisibility(View.GONE);
        deatBtn.setVisibility(View.GONE);
        storyBnt.setVisibility(View.GONE);
    }

    public void checkChoiseLoop() {
        if (storyArr[i].equals("choiceLoop")) {
            intLoop++;
            i++;
            loopBtn.setText(storyArr[i]);
            loopBtn.setVisibility(View.VISIBLE);
            i++;
            wasLoop = true;
        }
    }

    public void checkChoiseSimp() {
        if (storyArr[i].equals("choiceSimp")) {
            intSimp++;
            i++;
            simpBtn.setText(storyArr[i]);
            simpBtn.setVisibility(View.VISIBLE);
            i++;
            wasSimp = true;
        }
    }

    public void checkDeath() {
        if (storyArr[i].equals("death")) {
            intDeath++;
            i++;
            deatBtn.setText(storyArr[i]);
            deatBtn.setVisibility(View.VISIBLE);
            i++;
        }
    }

    public void InitNewText(String[] arr, int cursor) {
        btnNew = new TextView(this);
        btnNew.setText(arr[cursor]);
        btnNew.setTextSize(28);
        btnNew.setTextColor(Color.GRAY);
    }

    public void RevealAndUpdateBrn() {
        RevealStory();
        if (storyArr[i].equals("choiceLoop")) {
            RevealLoop();
        }
        if (storyArr[i].equals("choiceSimp")) {
            RevealSimp();
        }
        if (storyArr[i].equals("death")) {
            RevealDeath();
        }
    }

    public void RevealDeath() {

        DeathToken = true;
        intDeath = 0;
        i++;
        deatBtn.setText(storyArr[i]);
        deatBtn.setVisibility(View.VISIBLE);
        deatBtn.startAnimation(btnAnim);
        i++;
    }

    public void RevealLoop() {
        wasLoop = true;
        intLoop++;
        i++;
        loopBtn.setText(storyArr[i]);
        loopBtn.setVisibility(View.VISIBLE);
        loopBtn.startAnimation(btnAnim);
        i++;
    }

    public void RevealSimp() {
        intSimp++;
        i++;
        simpBtn.setText(storyArr[i]);
        simpBtn.setVisibility(View.VISIBLE);
        simpBtn.startAnimation(btnAnim);
        i++;
    }

    public void RevealStory() {
        storyBnt.setText(storyArr[i]);
        storyBnt.setVisibility(View.VISIBLE);
        storyBnt.startAnimation(btnAnim);
        i++;
    }

    public void AddNewText() {
        test.addView(btnNew, Params);
        btnNew.startAnimation(animSet);
    }

    private void sendScroll() {
        final Handler handler1 = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
                handler1.post(new Runnable() {
                    @Override
                    public void run() {
                        storyList.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        }).start();
    }

}
