package com.example.tiago.pigpay2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.tiago.pigpay2.db.TaskContract;
import com.example.tiago.pigpay2.db.TaskDBHelper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TaskDBHelper helper;
    private ListView listaMovimentos;
    private Button adicionar;
    private TextView saldo;
    SimpleCursorAdapter listAdpater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new TaskDBHelper(this);

        listaMovimentos = (ListView) findViewById(R.id.listaMovimentacao);
        saldo = (TextView) findViewById(R.id.saldo);

        updateUI();

        adicionar = (Button) findViewById(R.id.adicionar);
        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it= new Intent(MainActivity.this, AddMovimentos.class);
                startActivityForResult(it,1);
            }
        });


        TaskDBHelper.getInstance(getBaseContext()).select("SELECT * FROM movimentacao");
    }

    private void updateUI() {
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TaskContract.TABLE, new String[]{TaskContract.Columns._ID, TaskContract.Columns.DATA, TaskContract.Columns.DESCRICAO, TaskContract.Columns.TIPO, TaskContract.Columns.VALOR}, null, null, null, null, null);

        listAdpater = new SimpleCursorAdapter(this, R.layout.ultimos_movimentos, cursor, new String[]{TaskContract.Columns.DATA, TaskContract.Columns.DESCRICAO, TaskContract.Columns.TIPO, TaskContract.Columns.VALOR}, new int[] {R.id.txtData,R.id.txtDescricao,R.id.txtTipo,R.id.txtValor});


        ListView teste = (ListView) findViewById(R.id.listaMovimentacao);
        teste.setAdapter(listAdpater);
        if(cursor.moveToFirst()) {
            Double valorTotal = 0.0;
            Boolean entrada = true;
            String tipo = "";
            do {
                tipo = cursor.getString(cursor.getColumnIndex("tipo"));
                entrada = tipo != null && tipo.equals("+") ? true : false;
                if (entrada) {
                    valorTotal += cursor.getDouble((cursor.getColumnIndex("valor")));
                } else {
                    valorTotal -= cursor.getDouble((cursor.getColumnIndex("valor")));
                }

            }while ((cursor.moveToNext()));


            saldo.setText("SALDO R$ "+ valorTotal.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        updateUI();

        super.onActivityResult(requestCode, resultCode, data);



    }
}

