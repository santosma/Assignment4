package com.example.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button calculate;
    private EditText    inputPartySize, inputCheckAmount, outputTip15,
                        outputTip20, outputTip25, outputTotal15, outputTotal20,
                        outputTotal25;
    int tip15 = 15;
    int tip20 = 20;
    int tip25 = 25;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculate = findViewById(R.id.buttonCompute);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputCheckAmount = (EditText) findViewById(R.id.checkAmountValue);
                inputPartySize = (EditText) findViewById(R.id.partySizeValue);

                keyPromptHandler();

                boolean emptyPartyValue = TextUtils.isEmpty(inputPartySize.getText().toString());
                boolean emptyCheckAmount = TextUtils.isEmpty(inputCheckAmount.getText().toString());

                if (emptyPartyValue || emptyCheckAmount){
                    errorPrompt("missing party size and/or check amount information!");
                }else{
                    double inputCA = Double.parseDouble(inputCheckAmount.getText().toString());
                    int inputPS = Integer.parseInt(inputPartySize.getText().toString());

                    outputTip15 = (EditText) findViewById(R.id.fifteenPercentTipValue);
                    outputTip20 = (EditText) findViewById(R.id.twentyPercentTipValue);
                    outputTip25 = (EditText) findViewById(R.id.twentyfivePercentTipValue);

                    outputTotal15 = (EditText) findViewById(R.id.fifteenPercentTotalValue);
                    outputTotal20 = (EditText) findViewById(R.id.twentyPercentTotalValue);
                    outputTotal25 = (EditText) findViewById(R.id.twentyfivePercentTotalValue);

                    computeTipsAndTotals(inputCA, inputPS);
                }



            }
        });
    }
    protected void errorPrompt(String msg){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();
    }

    private void keyPromptHandler(){
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    protected void computeTipsAndTotals(double checkAmount, int partySize){
        assignValuesToTipsAndTotals(outputTip15, outputTotal15, partySize, checkAmount, tip15);
        assignValuesToTipsAndTotals(outputTip20, outputTotal20, partySize, checkAmount, tip20);
        assignValuesToTipsAndTotals(outputTip25, outputTotal25, partySize, checkAmount, tip25);

    }

    private void assignValuesToTipsAndTotals(EditText tip, EditText ppTotal,int ps, double ca, int ta){
        double perPerson = (Math.ceil(ca/ps));
        double tipAmount =  Math.ceil(((perPerson*ta)/100));
        int totalPerPerson = (int) perPerson + (int) tipAmount;

        tip.setText(Integer.toString((int) tipAmount), TextView.BufferType.EDITABLE);

        ppTotal.setText(Integer.toString(totalPerPerson), TextView.BufferType.EDITABLE);
    }
}
