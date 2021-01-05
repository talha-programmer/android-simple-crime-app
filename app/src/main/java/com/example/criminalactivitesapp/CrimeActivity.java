package com.example.criminalactivitesapp;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {
    public static final String EXTRA_CRIME_ID = "com.example.criminalactivitesapp.crime_id";

    /**
     * This method will be used to create an intent of this class
     * Will be called by the activity that wants to create an intent of this class
     * */
    public static Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    public Fragment createFragment() {

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        return CrimeFragment.newInstance(crimeId);
    }

}