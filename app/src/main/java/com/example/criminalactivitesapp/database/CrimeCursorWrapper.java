package com.example.criminalactivitesapp.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.criminalactivitesapp.Crime;

import java.util.Date;
import java.util.UUID;

public class CrimeCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime(){
        String uuidString = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.DATE));
        boolean solved = getInt(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.SOLVED)) == 1;
        boolean reqPolice = getInt(getColumnIndex(CrimeDbSchema.CrimeTable.Cols.REQ_POLICE)) == 1;

        Crime crime = new Crime(UUID.fromString(uuidString));

        crime.setCrimeTitle(title);
        crime.setCrimeDate(new Date(date));
        crime.setCrimeSolved(solved);
        crime.setRequirePolice(reqPolice);

        return crime;
    }
}
