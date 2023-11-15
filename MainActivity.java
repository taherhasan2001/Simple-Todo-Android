package com.example.taherhasan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
public class MainActivity extends AppCompatActivity {
    boolean isFirstLevelIsOver = false,isSecondLevelIsOver=false;
    static int num1, num2,realResult,score,countdownValue = 60,oldScore;
    private Handler handler;
    private Runnable countdownRunnable;
    private ConstraintLayout constraintLayout;
    private TextView countdownTextView,TextViewScore;
    private RelativeLayout relativeLayout;
    private  Button button_num1,button_num2 ,button_resul1,button_resul2,button_resul3;
    private void updateCountdown() {
        countdownValue--;

        // Update the TextView with the current countdown value
        countdownTextView.setText(String.valueOf(countdownValue));

        // Check if the countdown has reached 0, and stop the countdown if needed
        if (countdownValue <= 0) {
            if(!isFirstLevelIsOver)
                levelUp();
            else{
                isSecondLevelIsOver=true;
                setContentView(R.layout.result);
                TextView level1,level2,totalScore;
                level1=(TextView) findViewById(R.id.textViewFinalLevel1);
                level2=(TextView) findViewById(R.id.textViewFinalLevel2);
                totalScore=(TextView) findViewById(R.id.textViewTotalScore);

                level1.setText("Level 1 : " + Integer.toString(oldScore));
                level2.setText("Level 2 : " + Integer.toString(score));
                totalScore.setText("Total Score : "+Integer.toString(score + oldScore));



            }
        }
    }
    private void startCountdown() {
        // Start the countdown by posting the runnable
        handler.post(countdownRunnable);
    }
    private void levelUp() {
        setContentView(R.layout.activity_level2); // start level 2
        countdownValue = 60;
        oldScore = score;
        score = 0;
        isFirstLevelIsOver = true;

        startGame(2);
    }
    protected void startGame(int level){
        constraintLayout = findViewById(R.id.ConstraintLayout);
        constraintLayout.setBackgroundColor(Color.WHITE);

        countdownTextView = (TextView) findViewById(R.id.countdownTextView);
        boolean isAdder; // used for the operation used
        int rangeNumbers; // used in random
        int limit; // level 1 (-+2) ///// level 2 (-+3)
        if(level == 1){
            isAdder = true;
            limit = 2;
            rangeNumbers = 21;
            countdownRunnable = new Runnable() {
                @Override
                public void run() {
                    if(!isFirstLevelIsOver){
                        updateCountdown();
                        handler.postDelayed(this, 1000); // Run this runnable again after 1 second
                    }
                }
            };

        }
        else {
            isAdder = false;
            limit = 3;
            rangeNumbers = 101;
            countdownRunnable = new Runnable() {
                @Override
                public void run() {
                    if(!isSecondLevelIsOver){
                        updateCountdown();
                        handler.postDelayed(this, 1000); // Run this runnable again after 1 second
                    }
                }
            };
        }
        startCountdown();

        TextViewScore=(TextView) findViewById(R.id.textViewScore);
        button_num2 = (Button)findViewById(R.id.button_num2);
        button_num1 = (Button)findViewById(R.id.button_num1);

        button_resul1 = (Button)findViewById(R.id.button_resul1);
        button_resul2 = (Button)findViewById(R.id.button_resul2);
        button_resul3 = (Button)findViewById(R.id.button_resul3);

        button_resul1.setEnabled(false);
        button_resul2.setEnabled(false);
        button_resul3.setEnabled(false);

        button_num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num1 = (int) (Math.random()*rangeNumbers);
                button_num1.setText(Integer.toString(num1));
                button_num1.setEnabled(false);// Disable the button after the first click
                if (!button_num2.isEnabled()){
                    stepResults(button_resul1,button_resul2,button_resul3,limit,isAdder);
                }

            }
        });
        button_num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num2 = (int) (Math.random()*rangeNumbers);
                button_num2.setText(Integer.toString(num2));
                button_num2.setEnabled(false);// Disable the button after the first click
                if (!button_num1.isEnabled()){
                    stepResults(button_resul1,button_resul2,button_resul3,limit,isAdder);
                }



            }
        });

        button_resul1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afterStepResults(button_resul1,realResult,TextViewScore);
                disableResultButtons( button_resul1, button_resul2, button_resul3);
                enableNumButtons( button_num1, button_num2);
            }
        });
        button_resul2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afterStepResults(button_resul2,realResult,TextViewScore);
                disableResultButtons( button_resul1, button_resul2, button_resul3);
                enableNumButtons( button_num1, button_num2);
            }
        });
        button_resul3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afterStepResults(button_resul3,realResult,TextViewScore);
                disableResultButtons( button_resul1, button_resul2, button_resul3);
                enableNumButtons( button_num1, button_num2);
            }
        });


    }
    protected void disableResultButtons(Button button_resul1,Button button_resul2,Button button_resul3){
        button_resul1.setEnabled(false);
        button_resul1.setText("Result1");

        button_resul2.setEnabled(false);
        button_resul2.setText("Result2");

        button_resul3.setEnabled(false);
        button_resul3.setText("Result3");

    }

    protected void enableNumButtons(Button button_num1,Button button_num2){
        button_num2.setEnabled(true);
        button_num2.setText("Num2");

        button_num1.setEnabled(true);
        button_num1.setText("Num1");
    }
    protected int getIndexFromLinkedListRandomly(LinkedList<Integer> myList){
        /*
            This Function to get a Not used index Randomly from LinkedList,
            after we use it it should convert to Null
         */
        int index;

        do {
            index = (int) (Math.random() * 3);
        }while(myList.get(index) == null);

        return index;
    }
    protected void afterStepResults(Button theSelectedButton, int realResult,TextView TextViewScore){
        /*
            This step is when the user select one from the result buttons,
            check the value of the selected button with the realResult
         */

        String TOAST_TEXT;

        int valueFromButton = Integer.parseInt((String) theSelectedButton.getText());

        if(valueFromButton == realResult){
            score+=1;
            TOAST_TEXT = "Correct +1";
            constraintLayout.setBackgroundColor(Color.GREEN);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    constraintLayout.setBackgroundColor(Color.WHITE);
                }
            }, 250);
        }
        else{
            score-=1;
            TOAST_TEXT = "Incorrect -1";
            constraintLayout.setBackgroundColor(Color.RED);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    constraintLayout.setBackgroundColor(Color.WHITE);
                }
            }, 250);
        }
        TextViewScore.setText("Score : " + Integer.toString(score));
        Toast toast =Toast.makeText(MainActivity.this,TOAST_TEXT,Toast.LENGTH_SHORT);
        toast.show();


    }
    protected void stepResults(Button button_resul1,Button button_resul2,Button button_resul3,int limit,boolean isAdder){
        /*
            This step is after the user click on the first two buttons,
            the the three buttons will have the values , then this step finish
         */
        int index;
        if (isAdder)
            realResult = num1 + num2;
        else
            realResult = num1 - num2;
        LinkedList<Integer> myList = new LinkedList<>();
        myList.add(realResult);
        myList.add(realResult - limit);
        myList.add(realResult + limit);

        // for result1
        index = getIndexFromLinkedListRandomly(myList);
        button_resul1.setText(Integer.toString(myList.get(index)));
        button_resul1.setEnabled(true);
        myList.set(index,null);


        // for result2
        index = getIndexFromLinkedListRandomly(myList);
        button_resul2.setText(Integer.toString(myList.get(index)));
        button_resul2.setEnabled(true);
        myList.set(index,null);


        // for result3
        index = getIndexFromLinkedListRandomly(myList);
        button_resul3.setText(Integer.toString(myList.get(index)));
        button_resul3.setEnabled(true);
        myList.set(index,null);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level1);

        handler = new Handler();
        startGame(1);

    }
}