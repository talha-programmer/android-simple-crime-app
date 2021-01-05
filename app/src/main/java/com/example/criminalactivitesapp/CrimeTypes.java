package com.example.criminalactivitesapp;

public enum CrimeTypes {
    SIMPLE_CRIME(0), CRIME_REQ_POLICE(1);
    private int value;
    private CrimeTypes(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
