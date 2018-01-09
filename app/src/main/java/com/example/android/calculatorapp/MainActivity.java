package com.example.android.calculatorapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String STATE_PENDING_OPERATION = "pendingOperation";
    public static final String STATE_OPERAND_1 = "operand1";
    private EditText result;

    // Variable to hold operands and Type  of operations
    private EditText newNumber;
    private TextView displayOperation;
    private Double operand1 = null;
    private String pendingOperation = "=";

    public MainActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.editText_result);
        newNumber = findViewById(R.id.editText_new_number);
        displayOperation = findViewById(R.id.textView_operation);

        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonDot = findViewById(R.id.button_dot);

        Button buttonEquals = findViewById(R.id.button_equal);
        Button buttonDivide = findViewById(R.id.button_div);
        Button buttonMultiply = findViewById(R.id.button_mul);
        Button buttonPlus = findViewById(R.id.button_plus);
        Button buttonMinus = findViewById(R.id.button_minus);
        Button buttonNeg = findViewById(R.id.button_neg);
        Button buttonClr = findViewById(R.id.button_clear);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                newNumber.append(b.getText().toString());
            }
        };

        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);


        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String op = b.getText().toString();
                String value = newNumber.getText().toString();
                try {
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, op);
                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }

                pendingOperation = op;
                displayOperation.setText(pendingOperation);


            }
        };

        buttonEquals.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);


        buttonNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = newNumber.getText().toString();

                if (value.length() == 0) {
                    newNumber.setText("-");
                } else {
                    try {
                        Double doubleValue = Double.valueOf(value);
                        doubleValue *= -1;
                        newNumber.setText(doubleValue.toString());
                    } catch (NumberFormatException e) {
                        // in case of -, .
                        newNumber.setText("");
                    }
                }
            }
        });

        buttonClr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newNumber.setText("");
                operand1 = null;
            }
        });


    }


    private void performOperation(Double value, String op) {

        if (null == operand1) {
            operand1 = value;
        } else {

            if (pendingOperation.equals("=")) {
                pendingOperation = op;
            }

            switch (pendingOperation) {
                case "=": {
                    operand1 = value;

                    break;
                }
                case "/": {
                    if (value == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;
                }

                case "*": {
                    operand1 *= value;
                }
                break;

                case "-": {
                    operand1 -= value;
                }
                break;

                case "+": {
                    operand1 += value;
                }
                break;
            }
        }


        result.setText(operand1.toString());
        newNumber.setText("");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_PENDING_OPERATION, displayOperation.getText().toString());

        if (operand1 != null) {
            outState.putDouble(STATE_OPERAND_1, operand1);
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        displayOperation.setText(savedInstanceState.getString(STATE_PENDING_OPERATION));
        operand1 = savedInstanceState.getDouble(STATE_OPERAND_1);
    }
}
