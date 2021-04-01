package com.example.criminalactivitesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Reg Number: 3491-FBAS/BSSE/F17
 * Name: Muhammad Talha
 * Section: A
 * */
public class CrimeListFragment extends Fragment {

    private RecyclerView crimeRecyclerView;
    private CrimeAdapter crimeAdapter;

    private boolean isSubtitleVisible;

    private static final String SUBTITLE_VISIBLE = "subtitle_visible";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_crime, container, false);

        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        crimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Get the isSubtitleVisible variable if saved already in the state
        if(savedInstanceState != null){
            isSubtitleVisible = savedInstanceState.getBoolean(SUBTITLE_VISIBLE);
        }

        updateSubtitle();

        // Used to call onCreateOptionsMenu() for this fragment
        setHasOptionsMenu(true);

        updateUI();

        return view;
    }

    /**
     * Save the boolean variable isSubtitleVisible so that the subtitle would not hide
     * in case of screen rotation or any other configuration changes
     * */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SUBTITLE_VISIBLE, isSubtitleVisible);
    }

    /**
     * Callback method. Called whenever menu options are created
     * Called by fragment manager
     * */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.fragment_crime_list, menu);

        // Get the menu item of show/hide subtitle and change its text
        // according to the option selected
        MenuItem item = menu.findItem(R.id.show_subtitle);
        if(isSubtitleVisible){
            item.setTitle(R.string.hide_subtitle);
        } else{
            item.setTitle(R.string.show_subtitle);
        }

    }

    /**
     * Called when any of the menu item is clicked
     * */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            // Check if the menu item clicked is new_crime
            case R.id.new_crime:
                Crime crime  = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);

                // Start crime pager activity so that user can add the details of crime
                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getCrimeId());
                startActivity(intent);
                return true;

            case R.id.show_subtitle:
                isSubtitleVisible = !isSubtitleVisible;     // Toggle the menu
                getActivity().invalidateOptionsMenu();      // Update the options of menu

                updateSubtitle();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void updateSubtitle() {
        CrimeLab lab = CrimeLab.get(getActivity());
        int count = lab.getAllCrimes().size();
        String subtitle = getString(R.string.subtitle_format, count);

        // Remove the subtitle if hide subtitle is selected
        if(!isSubtitleVisible){
            subtitle = null;
        }

        // Set the subtitle on the activity
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getAllCrimes();
        if(crimeAdapter == null) {
            crimeAdapter = new CrimeAdapter(crimes);
            crimeRecyclerView.setAdapter(crimeAdapter);
        } else{
            crimeAdapter.setCrimes(crimes);
        }
        crimeAdapter.notifyDataSetChanged();
    }


    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView crimeTitle;
        private TextView crimeDate;
        private ImageView crimeSolvedImageView;

        private Crime crime;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent, int resource){

            super(inflater.inflate(resource, parent, false));

            crimeTitle = itemView.findViewById(R.id.crime_title);
            crimeDate = itemView.findViewById(R.id.crime_date);
            crimeSolvedImageView = itemView.findViewById(R.id.crime_solved);

            itemView.setOnClickListener(this);
        }

        public void bind(Crime crime){
            this.crime = crime;

            crimeTitle.setText(crime.getCrimeTitle());
            crimeDate.setText(crime.getCrimeDate().toString());
            crimeSolvedImageView.setVisibility(crime.isCrimeSolved()? View.VISIBLE : View.GONE);

        }

        @Override
        public void onClick(View v) {
            // Toast.makeText(getActivity(), crime.getCrimeTitle(), Toast.LENGTH_LONG).show();

            Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getCrimeId());
            startActivity(intent);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Crime> allCrimes;

        public CrimeAdapter(List<Crime> allCrimes) {
            this.allCrimes = allCrimes;
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            // int value of resource layout. Will be initialized according to the crime type
            int resource;

            // Get layout file according to the crime type
            if(viewType == CrimeTypes.SIMPLE_CRIME.getValue() ){
                resource = R.layout.list_item_crime;
            } else {
                resource = R.layout.list_item_crime_req_police;
            }

            return new CrimeHolder(inflater, parent, resource);
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            Crime crime = allCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return allCrimes.size();
        }

        @Override
        public int getItemViewType(int position) {
            return allCrimes.get(position).getCrimeType();
        }

        public void setCrimes(List<Crime> crimes) {
            allCrimes = crimes;
        }
    }
}
