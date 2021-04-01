package com.example.criminalactivitesapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.UUID;

public class CrimeFragment extends Fragment {
    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "dialog_date";

    private static final int REQ_DATE = 0;      // Req code used to get the date from date picker fragment
    private Button btnCrimeDate;
    private TextView tvCrimeTitle;
    private CheckBox cbCrimeSolved;
    private Crime crime;

    public static CrimeFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //UUID crimeId = (UUID) getActivity().getIntent().getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        crime = CrimeLab.get(getActivity()).getCrime(crimeId);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        btnCrimeDate = v.findViewById(R.id.crime_date);
        btnCrimeDate.setText(crime.getCrimeDate().toString());
        //btnCrimeDate.setEnabled(false);
        btnCrimeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(crime.getCrimeDate());

                // Set the target element to get the date back from date picker fragment.
                // It will build the relationship between crime fragment and datepicker fragment
                dialog.setTargetFragment(CrimeFragment.this, REQ_DATE);

                dialog.show(manager, DIALOG_DATE);
            }
        });

        cbCrimeSolved = v.findViewById(R.id.crime_solved);
        cbCrimeSolved.setChecked(crime.isCrimeSolved());
        cbCrimeSolved.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                crime.setCrimeSolved(isChecked);
            }
        });

        tvCrimeTitle = v.findViewById(R.id.crime_title);
        tvCrimeTitle.setText(crime.getCrimeTitle());
        tvCrimeTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                crime.setCrimeTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return v;
    }

    /**
     * Update the crime in database when on pause method is called
     * */
    @Override
    public void onPause() {
        super.onPause();

        CrimeLab.get(getActivity()).updateCrime(crime);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != Activity.RESULT_OK){
            return;
        }

        if(requestCode == REQ_DATE){
            // Get the date from date picker fragment, update on the crime object and button
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            crime.setCrimeDate(date);
            btnCrimeDate.setText(crime.getCrimeDate().toString());
        }
    }
}
