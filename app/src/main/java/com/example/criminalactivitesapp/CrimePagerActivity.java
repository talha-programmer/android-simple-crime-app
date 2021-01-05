package com.example.criminalactivitesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<Crime> allCrimes;

    public static final String EXTRA_CRIME_ID = "com.example.criminalactivitesapp.crime_id";

    /**
     * This method will be used to create an intent of this class
     * Will be called by the activity that wants to create an intent of this class
     * */
    public static Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        viewPager = findViewById(R.id.crime_view_pager);
        allCrimes = CrimeLab.get(this).getAllCrimes();

        // Get the crime id which is clicked
        final UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        FragmentManager manager = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(manager) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return CrimeFragment.newInstance(allCrimes.get(position).getCrimeId());
            }

            @Override
            public int getCount() {
                return allCrimes.size();
            }
        });

        // Set the position of pager according to the crime id that is clicked
        for(int i=0; i<allCrimes.size(); i++){
            if(allCrimes.get(i).getCrimeId() == crimeId){
                viewPager.setCurrentItem(i);
                break;
            }
        }


    }
}