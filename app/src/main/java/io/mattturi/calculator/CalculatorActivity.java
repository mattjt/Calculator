package io.mattturi.calculator;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Scanner;

public class CalculatorActivity extends AppCompatActivity {
    TextView equationEditor;
    String equation = "";
    double sum;
    String prevNumberSeq = "";
    Context context;
    int duration = Toast.LENGTH_SHORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        equationEditor = (TextView) findViewById(R.id.equationEditor);
        context = getApplicationContext();
    }

    public void doMath(View view) {
        String btnValue = ((Button) view).getText().toString();
        boolean isOperation = false;
        switch (btnValue) {
            case "BS":
                btnValue = "";
                try {
                    equation = equation.substring(0, equation.length() - 1);

                } catch (Exception e) {
                    makeToast("Invalid Action");
                }
                break;
            case "Clr":
                clear();
                setEquationEditorText("0");
                isOperation = true;
                btnValue = "";
                break;
            case "=":
                try {
                    Scanner prime = new Scanner(equation);
                    prime.useDelimiter("");
                    String s = "";
                    while (prime.hasNext()) {
                        while (prime.hasNext("\\s")) {
                            prime.skip("\\s*");
                        }
                        if (prime.hasNext()) {
                            s = s + prime.next();
                        }
                    }

                    Scanner sc = new Scanner(s);
                    sc.useDelimiter("\\+|\\-|\\*|/");

                    double solution = 0;
                    if (s.startsWith("-")) {
                        solution -= sc.nextDouble();
                    } else if (s.startsWith("*")) {
                        solution *= sc.nextDouble();
                    } else if (s.startsWith("/")) {
                        solution /= sc.nextDouble();
                    } else {
                        solution += sc.nextDouble();
                    }
                    while (sc.hasNextDouble()) {
                        String operation = sc.findInLine("\\+|\\-|\\*|/");
                        if (operation.equals("+")) {
                            solution += sc.nextDouble();
                        }
                        if (operation.equals("-")) {
                            solution -= sc.nextDouble();
                        }
                        if (operation.equals("*")) {
                            solution *= sc.nextDouble();
                        }
                        if (operation.equals("/")) {
                            solution /= sc.nextDouble();
                        }
                    }
                    isOperation = true;
                    setEquationEditorText(Double.toString(solution));
                    btnValue = "";
                    clear();
                } catch (NumberFormatException e) {
                    makeToast("Invalid");
                    btnValue = "";
                }
                break;
        }
        equation += btnValue;
        if (!isOperation) {
            setEquationEditorText(equation);
        }

    }

    public void resetNumberSeq() {
        prevNumberSeq = "";
    }

    public void clear() {
        sum = 0;
        equation = "";
        resetNumberSeq();
    }

    public void setEquationEditorText(String text) {
        equationEditor.setText(text);
    }

    public void makeToast(String text) {
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
