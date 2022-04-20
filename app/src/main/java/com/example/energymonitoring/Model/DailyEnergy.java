package com.example.energymonitoring.Model;


public class DailyEnergy {
    private String Date;
    private String kWh;
    private String Vnd;

    public DailyEnergy() {
    }
    public DailyEnergy(String date, String kwh, String vnd) {
        Date = date;
        kWh = kwh;
        Vnd = vnd;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getKwh() {
        return kWh;
    }

    public void setKwh(String kwh) {
        kWh = kwh;
    }

    public String getVnd() {
        return Vnd;
    }

    public void setVnd(String vnd) {
        Vnd = vnd;
    }
}
