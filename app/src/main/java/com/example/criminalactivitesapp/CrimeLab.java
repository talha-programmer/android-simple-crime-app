package com.example.criminalactivitesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.example.criminalactivitesapp.database.CrimeBaseHelper;
import com.example.criminalactivitesapp.database.CrimeCursorWrapper;
import com.example.criminalactivitesapp.database.CrimeDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab crimeLab;

    private SQLiteDatabase database;

    //private List<Crime> allCrimes;

    private CrimeLab(Context context){

        database = new CrimeBaseHelper(context.getApplicationContext()).getWritableDatabase();

        //allCrimes = new ArrayList<Crime>();

        // Adding dummy crimes in the arraylist
        /*for(int i = 1; i <= 100; i++){
            Crime crime = new Crime();
            crime.setCrimeTitle("Crime No # "+ i);
            crime.setCrimeSolved(i%2==0);
            crime.setRequirePolice(i%3==0);
            allCrimes.add(crime);
        }*/

    }

    public static CrimeLab get(Context context) {
        if(crimeLab == null){
            crimeLab = new CrimeLab(context);
        }
        return crimeLab;
    }

    public List<Crime> getAllCrimes() {
        //return allCrimes;
        List<Crime> allCrimes = new ArrayList<>();

        // This try block will automatically close the cursor at the end
        try (CrimeCursorWrapper cursor = queryCrimes(null, null)) {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                allCrimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        }


        return allCrimes;
    }

    public Crime getCrime(UUID crimeId){
        String whereClause = CrimeDbSchema.CrimeTable.Cols.UUID + " = ?";
        String[] whereArgs = {crimeId.toString()};

        // This try block will automatically close the cursor at the end
        try (CrimeCursorWrapper cursor = queryCrimes(whereClause, whereArgs)) {

            if(cursor.getCount() == 0){
                // No crime is found for this id
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        }

    }

    public void addCrime(Crime crime){
       // allCrimes.add(crime);

        ContentValues values = getContentValues(crime);
        database.insert(CrimeDbSchema.CrimeTable.NAME, null, values);

    }

    public void updateCrime(Crime crime){
        ContentValues values = getContentValues(crime);

        String uuid = crime.getCrimeId().toString();
        String whereClause = CrimeDbSchema.CrimeTable.Cols.UUID + " = ?";
        String[] args = {uuid};

        database.update(CrimeDbSchema.CrimeTable.NAME, values, whereClause, args);
    }

    public CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs){
        Cursor cursor = database.query(CrimeDbSchema.CrimeTable.NAME, null,
                whereClause, whereArgs, null, null, null);
        return new CrimeCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Crime crime){
        ContentValues values = new ContentValues();
        values.put(CrimeDbSchema.CrimeTable.Cols.UUID, crime.getCrimeId().toString());
        values.put(CrimeDbSchema.CrimeTable.Cols.TITLE, crime.getCrimeTitle());
        values.put(CrimeDbSchema.CrimeTable.Cols.DATE, crime.getCrimeDate().getTime());
        values.put(CrimeDbSchema.CrimeTable.Cols.SOLVED, crime.isCrimeSolved()? 1:0);
        values.put(CrimeDbSchema.CrimeTable.Cols.REQ_POLICE, crime.isRequirePolice()?1:0);

        return values;

    }
}
