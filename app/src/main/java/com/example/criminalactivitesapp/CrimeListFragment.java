package com.example.criminalactivitesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_crime, container, false);

        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        crimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
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
            crimeAdapter.notifyDataSetChanged();
        }
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
    }
}
