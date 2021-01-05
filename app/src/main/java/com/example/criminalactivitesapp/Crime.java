package com.example.criminalactivitesapp;

import java.util.Date;
import java.util.UUID;

public class Crime {
    private UUID crimeId;
    private String crimeTitle;
    private Date crimeDate;
    private boolean crimeSolved;
    private boolean requirePolice;

    public Crime() {
        crimeId = UUID.randomUUID();
        crimeDate = new Date();

    }

    public UUID getCrimeId() {
        return crimeId;
    }

    public void setCrimeId(UUID crimeId) {
        this.crimeId = crimeId;
    }

    public String getCrimeTitle() {
        return crimeTitle;
    }

    public void setCrimeTitle(String crimeTitle) {
        this.crimeTitle = crimeTitle;
    }

    public Date getCrimeDate() {
        return crimeDate;
    }

    public void setCrimeDate(Date crimeDate) {
        this.crimeDate = crimeDate;
    }

    public boolean isCrimeSolved() {
        return crimeSolved;
    }

    public void setCrimeSolved(boolean crimeSolved) {
        this.crimeSolved = crimeSolved;
    }

    public boolean isRequirePolice() {
        return requirePolice;
    }

    public void setRequirePolice(boolean requirePolice) {
        this.requirePolice = requirePolice;
    }

    public int getCrimeType(){
        if(this.requirePolice){
            return CrimeTypes.CRIME_REQ_POLICE.getValue();
        }
        return CrimeTypes.SIMPLE_CRIME.getValue();
    }
}
