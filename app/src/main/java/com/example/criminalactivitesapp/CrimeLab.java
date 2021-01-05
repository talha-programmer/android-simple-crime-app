package com.example.criminalactivitesapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab crimeLab;
    private List<Crime> allCrimes;

    private CrimeLab(Context context){
        allCrimes = new ArrayList<Crime>();

        // Adding dummy crimes in the arraylist
        for(int i = 1; i <= 100; i++){
            Crime crime = new Crime();
            crime.setCrimeTitle("Crime No # "+ i);
            crime.setCrimeSolved(i%2==0);
            crime.setRequirePolice(i%3==0);
            allCrimes.add(crime);
        }

    }

    public static CrimeLab get(Context context) {
        if(crimeLab == null){
            crimeLab = new CrimeLab(context);
        }
        return crimeLab;
    }

    public List<Crime> getAllCrimes() {
        return allCrimes;
    }

    public Crime getCrime(UUID crimeId){
        for(Crime crime: allCrimes){
            if(crime.getCrimeId().equals(crimeId)){
                return crime;
            }
        }
        return null;
    }
}
