package com.example.criminalactivitesapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Used to display date picker to select the date of crime
 * */
public class DatePickerFragment extends DialogFragment {
    private DatePicker datePicker;
    private static final String ARG_DATE = "date";

    public static final String EXTRA_DATE = "com.example.criminalactivitesapp.date";



    public static DatePickerFragment newInstance(Date date) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //return super.onCreateDialog(savedInstanceState);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);

        Date date = (Date) getArguments().getSerializable(ARG_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePicker = (DatePicker) v.findViewById(R.id.dialog_date_picker);
        datePicker.init(year, month, day, null);

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.date_picket_title)
                .setView(v)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth();
                        int day = datePicker.getDayOfMonth();

                        Date date = new GregorianCalendar(year, month, day).getTime();

                        sendResult(date, Activity.RESULT_OK);
                    }
                })
                .create();
    }

    private void sendResult(Date date, int resultOk) {
        // Pass the date to parent fragment through intent
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        // It will call the onActivityResult() method of the target fragment
        // which in this case is crime fragment
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultOk, intent);
    }
}
