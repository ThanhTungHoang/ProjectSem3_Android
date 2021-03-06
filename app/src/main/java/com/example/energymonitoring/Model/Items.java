package com.example.energymonitoring.Model;


import java.util.HashMap;
import java.util.Map;

public class Items {

    private String Name;
    private String Id;
    private String V;
    private String A;
    private String W;
    private String Relay;
    private String kWh;
    private String Vnd;
    public Items() {

    }

    public Items(String name, String id, String v, String a, String w, String relay, String kwh, String vnd) {
        Name = name;
        Id = id;
        V = v;
        A = a;
        W = w;
        Relay = relay;
        kWh = kwh;
        Vnd = vnd;
    }

    public String getkWh() {
        return kWh;
    }

    public void setkWh(String kwh) {
        kWh = kwh;
    }

    public String getVnd() {
        return Vnd;
    }

    public void setVnd(String vnd) {
        Vnd = vnd;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getV() {
        return V;
    }

    public void setV(String v) {
        V = v;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getW() {
        return W;
    }

    public void setW(String w) {
        W = w;
    }

    public String getRelay() {
        return Relay;
    }

    public void setRelay(String relay) {
        Relay = relay;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", Name);
        return result;
    }
}