package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // SUMAR
        findViewById(R.id.idsum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num1 = Integer.parseInt(((EditText)findViewById(R.id.idnumber1)).getText().toString());
                int num2 = Integer.parseInt(((EditText)findViewById(R.id.idnumbar2)).getText().toString());
                int sumResult = num1 + num2;
                ((TextView)findViewById(R.id.idresult)).setText(String.valueOf(sumResult));
            }
        });

        // RESTAR
        findViewById(R.id.idres).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num1 = Integer.parseInt(((EditText)findViewById(R.id.idnumber1)).getText().toString());
                int num2 = Integer.parseInt(((EditText)findViewById(R.id.idnumbar2)).getText().toString());
                int sumResult = num1 - num2;
                ((TextView)findViewById(R.id.idresult)).setText(String.valueOf(sumResult));
            }
        });

        // MULTIPLICAR
        findViewById(R.id.idmul).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num1 = Integer.parseInt(((EditText)findViewById(R.id.idnumber1)).getText().toString());
                int num2 = Integer.parseInt(((EditText)findViewById(R.id.idnumbar2)).getText().toString());
                int sumResult = num1 * num2;
                ((TextView)findViewById(R.id.idresult)).setText(String.valueOf(sumResult));
            }
        });

        // DIVIDIR
        findViewById(R.id.iddiv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num1 = Integer.parseInt(((EditText)findViewById(R.id.idnumber1)).getText().toString());
                int num2 = Integer.parseInt(((EditText)findViewById(R.id.idnumbar2)).getText().toString());
                int sumResult = num1 / num2;
                ((TextView)findViewById(R.id.idresult)).setText(String.valueOf(sumResult));
            }
        });

        // RESTART
        findViewById(R.id.idremove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EditText)findViewById(R.id.idnumber1)).getText().clear(); // .setText("") works too
                ((EditText)findViewById(R.id.idnumbar2)).getText().clear(); // .setText("") works too
                ((TextView)findViewById(R.id.idresult)).setText("");

                ((EditText)findViewById(R.id.idnumber1)).requestFocus();    // sets focus on first input
            }
        });
    }
}