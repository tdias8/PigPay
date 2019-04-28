package com.example.tiago.pigpay2.db;

import android.provider.BaseColumns;

public class TaskContract {

    public static final String DB_NAME = "bdsae";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "movimentacao";

    public class  Columns{
        public static final String _ID = BaseColumns._ID;
        public static final String TIPO = "tipo";
        public static final String DESCRICAO = "descricao";
        public static final String VALOR = "valor";
        public static final String DATA = "data";
    }
}
