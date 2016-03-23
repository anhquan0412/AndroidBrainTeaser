package com.anhquan.brainteaser;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    GridLayout playScreen;
    FrameLayout startScreen;
    Button a,b,c,d;
    int num1,num2,answer,score=0;
    int total=0;
    final int MAX= 1000;
    final int MIN = 11;
    final int START = 30000;
    long currentTime;
    TextView scoreText, timerText, noticeText, questionText,resultText;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playScreen = (GridLayout)findViewById(R.id.playScreen);
        startScreen = (FrameLayout)findViewById(R.id.startScreen);
        playScreen.setVisibility(View.INVISIBLE);
        startScreen.setVisibility(View.VISIBLE);
        scoreText = (TextView)findViewById(R.id.score);
        timerText = (TextView)findViewById(R.id.timer);
        noticeText = (TextView)findViewById(R.id.notice);
        questionText = (TextView)findViewById(R.id.question);
        resultText = (TextView)findViewById(R.id.textWelcome);
        a = (Button)findViewById(R.id.a);
        b = (Button)findViewById(R.id.b);
        c = (Button)findViewById(R.id.c);
        d = (Button)findViewById(R.id.d);

    }
    private void countdown(int start)
    {
        countDownTimer = new CountDownTimer(start,1000){
            public void onTick(long msUntilDone)
            {
                currentTime = msUntilDone;
                String temp = String.valueOf((int)msUntilDone/1000) + " s";
                timerText.setText(temp);

            }
            public void onFinish()
            {
                startScreen.setVisibility(View.VISIBLE);
                playScreen.setVisibility(View.INVISIBLE);
                resultText.setText(String.valueOf(String.valueOf("Your Score: ") + score + "/" + total));
                score = 0;
                total = 0;

            }
        };
        countDownTimer.start();
    }
    public void initiate(View view)
    {
        playScreen.setVisibility(View.VISIBLE);
        startScreen.setVisibility(View.INVISIBLE);
        settingQuestion();
        countdown(START);

    }

    private void settingQuestion()
    {
        HashSet<Integer> lookUp = new HashSet<Integer>();

        //int num1, num2, answer;
        int[] wrong = new int[3];
        num1 = random(MIN,MAX);
        num2 = random(MIN,MAX);
        questionText.setText(String.valueOf( (num1) + " + " + (num2)  ));
        answer = num1+num2;
        lookUp.add(answer);
        wrong[0] = answer+10;
        wrong[1] = answer-10;
        wrong[2] = answer+100;
//        for(int i = 0; i<3; i++) {
//            while (lookUp.contains(wrong[i] = random(((answer - 5) > 0 ? answer - 5 : 1), answer + 5))) {}
//            lookUp.add(wrong[i]);
//        }

        switch (random(1,4))
        {
            case 1:
                a.setText(String.valueOf(answer));
                b.setText(String.valueOf(wrong[0]));
                c.setText(String.valueOf(wrong[1]));
                d.setText(String.valueOf(wrong[2]));
                break;
            case 2:
                b.setText(String.valueOf(answer));
                a.setText(String.valueOf(wrong[0]));
                c.setText(String.valueOf(wrong[1]));
                d.setText(String.valueOf(wrong[2]));
                break;
            case 3:
                c.setText(String.valueOf(answer));
                a.setText(String.valueOf(wrong[0]));
                b.setText(String.valueOf(wrong[1]));
                d.setText(String.valueOf(wrong[2]));
                break;
            case 4:
                d.setText(String.valueOf(answer));
                a.setText(String.valueOf(wrong[0]));
                b.setText(String.valueOf(wrong[1]));
                c.setText(String.valueOf(wrong[2]));
                break;
        }

    }
    public void clickOn(View view)
    {
        Button b = (Button)view;
        if(Integer.parseInt( (String)b.getText() ) == answer)
        {
            score++;
            String temp = "Score: " + String.valueOf(score);
            scoreText.setText(temp);
            noticeText.setText(String.valueOf("CORRECT!"));
            countDownTimer.cancel();
            countdown( (int)currentTime+2000); //start another timer, bonus 2 seconds
        }
        else
        {
            noticeText.setText(String.valueOf("INCORRECT!"));
        }
        total++;
        settingQuestion();
    }


    private int random(int min, int max)
    {
        return min + (int)(Math.random()*((max-min)+1));
    }

}
