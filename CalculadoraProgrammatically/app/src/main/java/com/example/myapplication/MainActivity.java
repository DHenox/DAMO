package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView textViewTitle;
    EditText editTextNumber1, editTextNumber2;
    String[] operations = {"+", "-", "*", "/"};
    int num1, num2;
    TextView textViewResult;
    TextView textViewPopUp;

    public void setButtonBGColor(Button bt, String color){
        Drawable drawable = bt.getBackground();
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, Color.parseColor(color));
        bt.setBackground(drawable);
    }

    public Button createOperationButton(String  oper) {
        Button opButton = new Button(this);
        opButton.setText(oper);
        opButton.setTextSize(21);
        opButton.setTextColor(Color.WHITE);
        setButtonBGColor(opButton, "#6200ee");
        opButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean invalid = false;
                int result = 0;
                String val1 = editTextNumber1.getText().toString();
                String val2 = editTextNumber2.getText().toString();
                if(val1.length() != 0 && val2.length() != 0) {
                    num1 = Integer.parseInt(val1);
                    num2 = Integer.parseInt(val2);
                    switch(oper){
                        case "+":
                            Log.d("Operacion", "SUMA");
                            result = num1+num2;
                            break;
                        case "-":
                            Log.d("Operacion", "RESTA");
                            result = num1-num2;
                            break;
                        case "*":
                            Log.d("Operacion", "MULTIPLICA");
                            result = num1*num2;
                            break;
                        case "/":
                            if(num2 == 0){
                                invalid = true;
                                textViewPopUp.setText("You cant divide by 0!");
                            }
                            else {
                                Log.d("Operacion", "DIVIDE");
                                result = num1 / num2;
                            }
                            break;
                    };
                }
                else{
                    invalid = true;
                    textViewPopUp.setText("The fields can't ba empty");
                }
                if(!invalid) {
                    textViewPopUp.setText("");
                    textViewResult.setText(String.valueOf(result));
                }
                else
                    textViewResult.setText("");
            }
        });
        return opButton;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout linlay = new LinearLayout(this);

        textViewTitle = new TextView(this);
        textViewTitle.setTextSize(21);
        textViewTitle.setText("Write two numbers and press any button");
        linlay.addView(textViewTitle);

        editTextNumber1 = new EditText(this);
        editTextNumber1.setInputType(InputType.TYPE_CLASS_NUMBER);
        editTextNumber1.setTextSize(21);
        linlay.addView(editTextNumber1);

        editTextNumber2 = new EditText(this);
        editTextNumber2.setInputType(InputType.TYPE_CLASS_NUMBER);
        editTextNumber2.setTextSize(21);
        linlay.addView(editTextNumber2);

        for (int i = 0; i < 2; i++) {
            LinearLayout row = new LinearLayout(this);
            for (int j = 0; j < 2; j++) {
                row.addView(createOperationButton(operations[2*i+j]));
            }
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER_HORIZONTAL);
            linlay.addView(row);
        }


        textViewResult = new TextView(this);
        textViewResult.setTextSize(21);
        linlay.addView(textViewResult);

        Button restartButton = new Button(this);
        setButtonBGColor(restartButton, "#b36854");
        restartButton.setTextColor(Color.WHITE);
        restartButton.setText("Restart");
        restartButton.setTextSize(21);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextNumber1.setText("");
                editTextNumber2.setText("");
                textViewResult.setText("");
                textViewPopUp.setText("");
                editTextNumber1.requestFocus();
            }
        });
        linlay.addView(restartButton);

        textViewPopUp = new TextView(this);
        textViewPopUp.setTextSize(18);
        linlay.addView(textViewPopUp);

        linlay.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        linlay.setOrientation(LinearLayout.VERTICAL);
        linlay.setGravity(Gravity.CENTER_HORIZONTAL);
        setContentView(linlay);
    }
}