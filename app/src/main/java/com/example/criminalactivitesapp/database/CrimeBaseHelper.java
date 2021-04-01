package com.example.criminalactivitesapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.MessageFormat;

public class CrimeBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DB_NAME = "crimeBase.db";

    public CrimeBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Data types are not required in sqlite
        String query = MessageFormat.format("CREATE TABLE {0} (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "{1}, " +
                "{2}, " +
                "{3}, " +
                "{4}, " +
                "{5} " +
                ")",
                CrimeDbSchema.CrimeTable.NAME,
                CrimeDbSchema.CrimeTable.Cols.UUID,
                CrimeDbSchema.CrimeTable.Cols.TITLE,
                CrimeDbSchema.CrimeTable.Cols.DATE,
                CrimeDbSchema.CrimeTable.Cols.SOLVED,
                CrimeDbSchema.CrimeTable.Cols.REQ_POLICE
                );
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
