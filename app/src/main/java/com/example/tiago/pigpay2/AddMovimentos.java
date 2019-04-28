package com.example.tiago.pigpay2;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.tiago.pigpay2.db.TaskDBHelper;

import java.util.Calendar;
import java.util.Date;

public class AddMovimentos extends AppCompatActivity {
    private EditText data;
    private EditText descricao;
    private EditText valor;
    private RadioButton entrada;
    private RadioButton saida;
    private Button addMovimento;
    int dia, mes, ano;
    Calendar dataMovimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_movimentos);

        descricao = (EditText) findViewById(R.id.txtDescricao);
        valor = (EditText) findViewById(R.id.txtValor);
        saida = (RadioButton) findViewById(R.id.btnSaida);
        entrada = (RadioButton) findViewById(R.id.btnEntrada);
        addMovimento = (Button) findViewById(R.id.btnaddMovimento);
        data = (EditText) findViewById(R.id.txtData);

        // ------------ calendario ---------------------

        dataMovimento = Calendar.getInstance();
        dia = dataMovimento.get(Calendar.DAY_OF_MONTH);
        mes = dataMovimento.get(Calendar.MONTH);
        ano= dataMovimento.get(Calendar.YEAR);

        data.setText(dia +"/"+(mes+1)+"/"+ano);

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMovimentos.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int ano, int mesDoAno, int diaDoMes) {
                        mesDoAno = mesDoAno + 1;
                        data.setText(diaDoMes+"/"+mesDoAno+"/"+ano);
                    }
                },ano,mes,dia);
                datePickerDialog.show();

            }
        });

        addMovimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues contentValues = new ContentValues();
                contentValues.put("descricao",descricao.getText().toString());
                contentValues.put("valor",valor.getText().toString());
                contentValues.put("data",data.getText().toString());

                if (entrada.isChecked()){
                    contentValues.put("tipo","+");
                }else {
                    contentValues.put("tipo","-");
                }


                TaskDBHelper.getInstance(getBaseContext()).insert("movimentacao",contentValues,null);
                Toast.makeText(AddMovimentos.this, "Movimento adicionado com sucesso", Toast.LENGTH_SHORT).show();
                setResult(1);
                finish();

            }
        });

    }
}