package com.example.matth.transmitcobra;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    CobraView cobraView;
    Button startButton;
    byte[] binStream = new byte[100000];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        cobraView = (CobraView) findViewById(R.id.cobra);
        startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                Clock2 clock2 = new Clock2(handler);
                handler.post(clock2);
                startButton.setVisibility(View.GONE);
            }
        });
    }

    class Clock2 implements Runnable {
        private Handler handler;
        int count = 0;
        Integer color = Color.YELLOW;
        public Clock2(Handler handler) {
            this.handler = handler;
        }
        public void run() {
            if (count == 0) {
                cobraView.setNewMat(Color.rgb(255, 0, 255),false);
                handler.postDelayed(this, 5000);
                count++;
            } else if (count == 6) {
                cobraView.setNewMat(Color.rgb(255, 0, 255),true);
            } else if (count <= 6) {
                if (color == Color.YELLOW) {
                    color = Color.WHITE;
                } else {
                    color = Color.YELLOW;
                }
                cobraView.setNewMat(color,false);
                handler.postDelayed(this, 1000);
                count++;
            }
        }
    }
}
