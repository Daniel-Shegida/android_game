package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {
    Handler handler;
    Animation clickAnim;
    LinearLayout blackScreen;
    boolean ANIM_FLAG;
    final int FINAL_SCIP_DUR = 4500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button starter = findViewById(R.id.starter);
        ANIM_FLAG = false;
        blackScreen = findViewById(R.id.blackScreen);
        clickAnim = AnimationUtils.loadAnimation(this, R.anim.appear);
        starter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!ANIM_FLAG) {

                    /*on click blocks btn for 5 sec to play animation and change init to game level*/
                    ANIM_FLAG = true;
                    handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            ANIM_FLAG = false;
                            changeInit();
                        }
                    }, FINAL_SCIP_DUR);
                    blackScreen.startAnimation(clickAnim);
                }
            }
        });
    }

    public void changeInit() {
        Intent intent = new Intent(this, GameLevelActivity.class);
        startActivity(intent);
    }
}
