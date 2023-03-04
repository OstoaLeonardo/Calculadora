package com.example.calculadora;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity {

    private TextView tvNumeros, tvResultado;
    private Button btnTema;
    String resultado;
    boolean puntoColocado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNumeros = (TextView)findViewById(R.id.tvNumeros);
        tvResultado = (TextView)findViewById(R.id.tvResultado);
        btnTema = (Button) findViewById(R.id.btnTema);

        btnTema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentTheme = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                if (currentTheme == Configuration.UI_MODE_NIGHT_YES) {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                recreate();
            }
        });
    }

    public void pressButton(View view) {
        switch (view.getId()) {
            case R.id.btn0:
                tvNumeros.setText(tvNumeros.getText() + "0");
                calcular(view);
                break;
            case R.id.btn1:
                tvNumeros.setText(tvNumeros.getText() + "1");
                calcular(view);
                break;
            case R.id.btn2:
                tvNumeros.setText(tvNumeros.getText() + "2");
                calcular(view);
                break;
            case R.id.btn3:
                tvNumeros.setText(tvNumeros.getText() + "3");
                calcular(view);
                break;
            case R.id.btn4:
                tvNumeros.setText(tvNumeros.getText() + "4");
                calcular(view);
                break;
            case R.id.btn5:
                tvNumeros.setText(tvNumeros.getText() + "5");
                calcular(view);
                break;
            case R.id.btn6:
                tvNumeros.setText(tvNumeros.getText() + "6");
                calcular(view);
                break;
            case R.id.btn7:
                tvNumeros.setText(tvNumeros.getText() + "7");
                calcular(view);
                break;
            case R.id.btn8:
                tvNumeros.setText(tvNumeros.getText() + "8");
                calcular(view);
                break;
            case R.id.btn9:
                tvNumeros.setText(tvNumeros.getText() + "9");
                calcular(view);
                break;
            case R.id.btnSuma:
                if(tvNumeros.getText().toString().matches(".*\\d$") || tvNumeros.getText().toString().endsWith(")") || tvNumeros.getText().toString().endsWith("-")) {
                    tvNumeros.setText(tvNumeros.getText() + "+");
                    puntoColocado = false;
                    calcular(view);
                }
                break;
            case R.id.btnResta:
                if(tvNumeros.getText().toString().matches(".*\\d$") || tvNumeros.getText().toString().isEmpty() || tvNumeros.getText().toString().endsWith("+") || tvNumeros.getText().toString().endsWith("×") || tvNumeros.getText().toString().endsWith("÷") || tvNumeros.getText().toString().endsWith("(") || tvNumeros.getText().toString().endsWith(")")) {
                    tvNumeros.setText(tvNumeros.getText() + "-");
                    puntoColocado = false;
                    calcular(view);
                }
                break;
            case R.id.btnProducto:
                if(tvNumeros.getText().toString().matches(".*\\d$") || tvNumeros.getText().toString().endsWith(")")) {
                    tvNumeros.setText(tvNumeros.getText() + "×");
                    puntoColocado = false;
                    calcular(view);
                }
                break;
            case R.id.btnDivision:
                if(tvNumeros.getText().toString().matches(".*\\d$") || tvNumeros.getText().toString().endsWith(")")) {
                    tvNumeros.setText(tvNumeros.getText() + "÷");
                    puntoColocado = false;
                    calcular(view);
                }
                break;
            case R.id.btnPunto:
                if (tvNumeros.getText().toString().matches(".*\\d$") && (!puntoColocado || tvNumeros.getText().toString().endsWith("."))) {
                    String texto = tvNumeros.getText().toString();
                    String[] numeros = texto.split("\\+|-|\\*|/");
                    String ultimoNumero = numeros[numeros.length - 1];

                    if (!ultimoNumero.contains(".")) {
                        tvNumeros.setText(texto + ".");
                        puntoColocado = true;
                        calcular(view);
                    }
                }
                break;
            case R.id.btnPorcentaje:
                if(tvNumeros.getText().toString().matches(".*\\d$")) {
                    tvNumeros.setText(tvNumeros.getText() + "%");
                    calcular(view);
                }
                break;
            case R.id.btnPar1:
                if(!tvNumeros.getText().toString().endsWith(".")) {
                    tvNumeros.setText(tvNumeros.getText() + "(");
                    calcular(view);
                }
                break;
            case R.id.btnPar2:
                if(tvNumeros.getText().toString().matches(".*\\d$") || tvNumeros.getText().toString().endsWith(")")) {
                    tvNumeros.setText(tvNumeros.getText() + ")");
                    calcular(view);
                }
                break;
        }
    }

    public void calcular(View view) {
        if(!tvNumeros.getText().toString().isEmpty()) {
            String expresion = tvNumeros.getText().toString();
            resultado = calcularExpresion(expresion);
            tvResultado.setText(resultado);

            int currentTheme = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            if(tvResultado.getText().toString() == "Infinity") {
                tvResultado.setText(getResources().getText(R.string.et_error));
                if(currentTheme == Configuration.UI_MODE_NIGHT_YES) {
                    tvResultado.setTextColor(getResources().getColor(R.color.md_theme_dark_error));
                    tvNumeros.setTextColor(getResources().getColor(R.color.md_theme_dark_error));
                } else {
                    tvResultado.setTextColor(getResources().getColor(R.color.md_theme_light_error));
                    tvNumeros.setTextColor(getResources().getColor(R.color.md_theme_light_error));
                }
            } else {
                if(currentTheme == Configuration.UI_MODE_NIGHT_YES) {
                    tvResultado.setTextColor(getResources().getColor(R.color.md_theme_dark_onBackground));
                    tvNumeros.setTextColor(getResources().getColor(R.color.md_theme_dark_onBackground));
                } else {
                    tvResultado.setTextColor(getResources().getColor(R.color.md_theme_light_onBackground));
                    tvNumeros.setTextColor(getResources().getColor(R.color.md_theme_light_onBackground));
                }
            }
        }
    }

    public String calcularExpresion(String expresion) {
        resultado = "";

        try {
            expresion = expresion.replaceAll("×", "*");
            expresion = expresion.replaceAll("%", "/100");
            expresion = expresion.replaceAll("÷", "/");
            expresion = expresion.replaceAll("\\)\\(", ")*(");
            expresion = expresion.replaceAll("(\\d+)\\(", "$1*(");
            expresion = expresion.replaceAll("(\\))(\\d+)", "$1*$2");

            org.mozilla.javascript.Context rhino = org.mozilla.javascript.Context.enter();
            rhino.setOptimizationLevel(-1);

            Scriptable scriptable = rhino.initStandardObjects();
            resultado = rhino.evaluateString(scriptable, expresion, "Javascript", 1, null).toString();

            if(resultado.length() > 16) {
                resultado = resultado.substring(0, 16);
            }
        }catch (Exception e){
            tvResultado.setText("Error");
        }
        return resultado;
    }

    public void pressIgual(View view) {
        if(!tvResultado.getText().toString().isEmpty()) {
            tvNumeros.setText(tvResultado.getText().toString());
            tvResultado.setText("");
        }
    }

    public void pressDelete(View view) {
        tvNumeros.setText("");
        tvResultado.setText("");
    }

    public void pressBack(View view) {
        if(!tvNumeros.getText().toString().isEmpty()) {
            tvNumeros.setText(tvNumeros.getText().toString().substring(0, tvNumeros.length() - 1));
            calcular(view);
        }

        if (tvNumeros.getText().toString().trim().isEmpty()) {
            tvResultado.setText("");
        }
    }
}