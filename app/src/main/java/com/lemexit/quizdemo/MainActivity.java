package com.lemexit.quizdemo;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupMenu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<TextView> textList = new ArrayList<TextView>();
    ArrayList<Button> buttonList = new ArrayList<Button>();
    ArrayList<Button> buttonClickList = new ArrayList<Button>();
    ArrayList<String> answerList = new ArrayList<String>();

    String correctAnswer="Cruise";
    String givenAnswer="";



    private Dialog pDialog;
    private PopupMenu popupMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //View decorView = getWindow().getDecorView();


        // int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        //decorView.setSystemUiVisibility(uiOptions);

        getSupportActionBar().hide();


        // initial textBox to an arraylist
        textList.add((TextView) findViewById(R.id.tv_1));
        textList.add((TextView) findViewById(R.id.tv_2));
        textList.add((TextView) findViewById(R.id.tv_3));
        textList.add((TextView) findViewById(R.id.tv_4));
        textList.add((TextView) findViewById(R.id.tv_5));
        textList.add((TextView) findViewById(R.id.tv_6));

        for (int i=0; i<textList.size();i++){
            answerList.add("");
        }

        buttonList.add((Button) findViewById(R.id.btn1));
        buttonList.add((Button) findViewById(R.id.btn2));
        buttonList.add((Button) findViewById(R.id.btn3));
        buttonList.add((Button) findViewById(R.id.btn4));
        buttonList.add((Button) findViewById(R.id.btn5));
        buttonList.add((Button) findViewById(R.id.btn6));
        buttonList.add((Button) findViewById(R.id.btn7));
        buttonList.add((Button) findViewById(R.id.btn8));
        buttonList.add((Button) findViewById(R.id.btn9));
        buttonList.add((Button) findViewById(R.id.btn10));
        buttonList.add((Button) findViewById(R.id.btn11));
        buttonList.add((Button) findViewById(R.id.btn12));
        buttonList.add((Button) findViewById(R.id.btn13));

        setTagToButton(); // set tag
    }


    // set tag to every QuizButton
    private void setTagToButton()
    {
        for (int j=0; j<buttonList.size();j++)
        {
            Button tButton = buttonList.get(j);
            tButton.setTag(0);

        }
    }


    public void onTextBoxClick(View v)
    {
        TextView tv = (TextView) v;
        String textChar = tv.getText().toString();

        //givenAnswer = "";

        for (int i=0; i<textList.size(); i++)
        {
            if (tv==textList.get(i))
            {
                answerList.add(i,"");
            }
        }

        for (int j=0; j<buttonList.size();j++)
        {
            Button tButton = buttonList.get(j);

            if (tButton.getVisibility()==View.INVISIBLE && tButton.getText().toString().equalsIgnoreCase(textChar))
            {
                tButton.setVisibility(View.VISIBLE);
                tv.setText("");
            }

        }

    }


    public void onQuizButtonClick(View v){

        Button btn = (Button) v;
        String vText = btn.getText().toString().toLowerCase();

        //Place the clicked char into answer text box if it is free
        for (int i=0; i<textList.size(); i++)
        {
            String tv = textList.get(i).getText().toString();

            if (tv=="" || tv.isEmpty())
            {
                textList.get(i).setText(vText);
                btn.setVisibility(View.INVISIBLE);
                buttonClickList.add(btn);
                break;
            }


        }


        //take given char into String
        givenAnswer="";
        for (int i=0; i<textList.size(); i++)
        {
            givenAnswer+= textList.get(i).getText().toString();
        }


        //check answer if correct then show custom dialog box on screen
        if(givenAnswer.length()>=correctAnswer.length())
        {
            if(givenAnswer.equalsIgnoreCase(correctAnswer))
            {
                customDialog(1, givenAnswer);
            }
        }



    }


    public void onHelpButtonClick(View v){

        //create a popupMenu
        popupMenu = new PopupMenu(this, v);
        popupMenu.getMenu().add(Menu.NONE, 1, Menu.NONE, "Show Answer");

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {

                for (int i=0; i<correctAnswer.length(); i++)
                {
                    String t = String.valueOf(correctAnswer.charAt(i));
                    textList.get(i).setText(t);

                    for (int j=0; j<buttonList.size();j++)
                    {
                        Button tButton = buttonList.get(j);

                        if (tButton.getText().toString().equalsIgnoreCase(t))
                        {
                            tButton.setTag(1);
                            //Log.e("Alex", tButton.getTag().toString());
                        }

                        int tag = (int) tButton.getTag();

                        if (tag==1){
                            if (tButton.getVisibility()==View.VISIBLE)
                                tButton.setVisibility(View.INVISIBLE);
                        }else{
                            if (tButton.getVisibility()==View.INVISIBLE)
                                tButton.setVisibility(View.VISIBLE);
                        }

                    }
                }

                return false;
            }
        });
        popupMenu.show();
    }


    public void onClearButtonClick(View v){

        for(int i=0; i<textList.size();i++){
            textList.get(i).setText("");
        }

        givenAnswer="";

        for (int i=0; i<buttonList.size();i++){

            if(buttonList.get(i).getVisibility()==View.INVISIBLE)
                buttonList.get(i).setVisibility(View.VISIBLE);
        }


    }

    public void onNextButtonClick(View v){
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
        finish();
    }


    private void customDialog(int status, String givenAnswer){
        pDialog = new Dialog(this);
        pDialog.setContentView(R.layout.p_dialog);
        pDialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        pDialog.setCancelable(false);

        TextView dTitle = (TextView) pDialog.findViewById(R.id.tv_dialog_title);
        TextView dDescription = (TextView) pDialog.findViewById(R.id.tv_dialog_description);
        TextView dAnswer = (TextView) pDialog.findViewById(R.id.tv_dialog_answer);
        Button btnNext = (Button) pDialog.findViewById(R.id.btn_next);

        if (status==1){
            dTitle.setText("Congratulation");
            dDescription.setText("You solved this puzzle.");
            dAnswer.setText(givenAnswer);
        }else{

        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
        pDialog.show();
    }


}
