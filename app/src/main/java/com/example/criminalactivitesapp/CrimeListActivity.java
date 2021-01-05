package com.example.criminalactivitesapp;


import androidx.fragment.app.Fragment;

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return new CrimeListFragment();
    }
}
